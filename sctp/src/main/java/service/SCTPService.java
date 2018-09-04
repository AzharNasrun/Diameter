package service;

import akka.actor.*;
import com.firstwap.jdiameter.Properties.JdiameterProperties;
import constant.Constants;
import io.netty.buffer.Unpooled;
import io.netty.channel.sctp.SctpMessage;
import main.DMRMain;
import main.SCTPMain;
import model.AssociationInfo;
import model.Diameter;
import model.SCTPRequestMessage;
import model.SCTPResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by aji on 14/02/18.
 */
public class SCTPService extends AbstractLoggingActor{

    private static final Logger logger = LoggerFactory.getLogger(Constants.SCTP_LOG);

    //host-sctpchanels
    public static Map<String, List<SCTPClientService>> clientsConnectionMap = Collections.synchronizedMap(new HashMap<String, List<SCTPClientService>>());

    private SCTPRequestMessage request;

    public static void loadSctpInstance(ActorSystem actorSystem) {


        try {

            // host
            for (String host : SCTPMain.assocMap.keySet()) {

                List<SCTPClientService> sctpChannelList = Collections.synchronizedList(new ArrayList<SCTPClientService>());


                List<AssociationInfo> assocInfoList = SCTPMain.assocMap.get(host);

                // association - sctpchannels - sctpclientservice

                for (AssociationInfo assocItem : assocInfoList) {

                    //open
                    SCTPClientService serviceChannel = new SCTPClientService(assocItem.sources, assocItem.detinations,actorSystem);

                    //bind
                    serviceChannel.initializeAssociation();

                    sctpChannelList.add(serviceChannel);
                }

                if(sctpChannelList.size() > 0){

                    clientsConnectionMap.put(host, sctpChannelList);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error",e);
        }
    }



    @Override
    public Receive createReceive() {
        final ActorRef self = getSelf();
        return receiveBuilder()
            .match(SCTPResponseMessage.class, response -> {
            try{
                SCTPRequestMessage sctpRequestMessage = request;
                final MonoSink sink = request.getSink();
                if (sink != null && sctpRequestMessage != null) {
                        Diameter result = response.getMessage();

                        sink.success(result);

                    }
                }finally {
                getSelf().tell(PoisonPill.getInstance(), null);
                         }
            }
            )

            .match(SCTPRequestMessage.class, request -> {
                this.request = request;

                String destinationHost = request.getDestinationHost();

                System.out.println("Thread:" + Thread.currentThread().getName() + " message size:" + request.getMessage() + " destinationHost:" + destinationHost);

                List<SCTPClientService> sctpServiceList = clientsConnectionMap.get(destinationHost);

                if (sctpServiceList != null && !sctpServiceList.isEmpty()) {


                    for (SCTPClientService item : sctpServiceList) {

                        List<Byte> message = DMRMain.ObjectToDiameterMessage(request.getMessage());

                        byte[] byteArray = new byte[message.size()];

                        for (int x = 0; x < message.size(); x++) {
                            byteArray[x] = message.get(x);
                        }

                        SctpMessage sctpMessage = new SctpMessage(46, 0, Unpooled.copiedBuffer(byteArray));
                        if (item.cf.channel().isWritable()&& item.cf.channel().isOpen()){

                        item.cf.channel().writeAndFlush(sctpMessage).sync();


                        } else {
                            requestReconnect(request);
                        }

                    }
                }

                // send empty response in case of timeout, change the timeout value
                getContext().getSystem().scheduler()
                    .scheduleOnce(FiniteDuration.apply(JdiameterProperties.getSetMessageTimeout(), TimeUnit.MILLISECONDS),
                        () -> {

                            System.out.println("scheduller timeout " +JdiameterProperties.getSetMessageTimeout());
                            request.getSink().success(new Diameter());
                            self.tell(PoisonPill.getInstance(), null);
                        },
                        getContext().dispatcher());


            })
            .build();
    }



    public static void requestReconnect (SCTPRequestMessage request) {
        try{

        String destinationHost = request.getDestinationHost();
        logger.info("trying to reconnect... ");

        List<AssociationInfo> assocInfoList = SCTPMain.assocMap.get(destinationHost);

        List<SCTPClientService> clientList = new ArrayList<SCTPClientService>();

        for(AssociationInfo associtem : assocInfoList) {


            SCTPClientService  serviceChannel = new SCTPClientService(associtem.sources, associtem.detinations,JdiameterProperties.getActorSystem());
            serviceChannel.initializeAssociation();

            clientList.add(serviceChannel);

            logger.info("trying to reconnect finish...");

            List<Byte> message = DMRMain.ObjectToDiameterMessage(request.getMessage());

            byte[] byteArray = new byte[message.size()];

            for (int x = 0; x < message.size(); x++) {
                byteArray[x] = message.get(x);
            }

            SctpMessage sctpMessage = new SctpMessage(46, 0, Unpooled.copiedBuffer(byteArray));
            serviceChannel.cf.sync().channel().writeAndFlush(sctpMessage).sync();

            logger.info("Sending current message  finish...");

        }
        clientsConnectionMap.put(destinationHost,clientList);



        }catch(Exception e){
            logger.error("Failed To reconnect ",e);
        }
    }

    public static Mono<Diameter> sendByAkka(Diameter messageRequest, String destinationHost) {

        Mono<Diameter> result = Mono.create(
            sink -> {
                SCTPRequestMessage requestMessage = new SCTPRequestMessage(sink, messageRequest, destinationHost);
                ActorRef sctpServiceActor = JdiameterProperties.getActorSystem().actorOf(Props.create(SCTPService.class), "sctpservice-" + messageRequest.getEndToEndId());
                sctpServiceActor.tell(requestMessage, ActorRef.noSender());
            }

        );
        return result;
    }
}
