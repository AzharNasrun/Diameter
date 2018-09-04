package handler;

import com.firstwap.jdiameter.Properties.JdiameterProperties;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import constant.Constants;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.sctp.SctpMessage;
import main.DMRMain;
import main.DWAGeneralMain;
import model.Diameter;
import model.SCTPResponseMessage;
import org.apache.log4j.Logger;

import service.SCTPClientDWRService;

import java.util.List;
import java.util.concurrent.*;

public class ResponseHandler {


    private static final Logger logger = Logger.getLogger(Constants.SCTP_LOG);

   public boolean isDWRStart = false;
   public static ConcurrentMap<String,ScheduledFuture>  scheduledFutureMap = new ConcurrentHashMap<String, ScheduledFuture>();

    public  void executeResponse(Diameter resultObject, ActorSystem actorSystem, ChannelHandlerContext ctx) {


        long trxId = resultObject.getEndToEndId();
        long hopByHopId = resultObject.getHopByHopId();

        long commandCode = resultObject.getCommandCode();

        boolean requestFlag = resultObject.getFlags().isRequest();


        logger.info("received from network commandcode:" + commandCode);


        //dwr from server dwa from client
        if (commandCode == 280 && requestFlag) {

            // send DWA Respon to network

            Diameter dwa = DWAGeneralMain.createMessage((int)hopByHopId,(int) trxId);


            List<Byte> byteList = DMRMain.ObjectToDiameterMessage(dwa);


            byte[] byteArray = new byte[byteList.size()];

            for (int x = 0; x < byteList.size(); x++) {
                byteArray[x] = byteList.get(x);
            }


            SctpMessage sctpMessage = new SctpMessage(46, 0, Unpooled.copiedBuffer(byteArray));

            ctx.writeAndFlush(sctpMessage);


        } else if (commandCode == 280 && !requestFlag) {


        } else if (commandCode == 257 && !requestFlag) {

            //cea from server dwr start

            if (isDWRStart == false) {


                SCTPClientDWRService sctpClientDWRService = new SCTPClientDWRService(ctx);
                ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
                ScheduledFuture future = scheduledExecutorService.scheduleAtFixedRate(sctpClientDWRService, 0, JdiameterProperties.getDwrPeriodic(), TimeUnit.SECONDS);
                scheduledFutureMap.put(ctx.channel().remoteAddress().toString(), future);




                isDWRStart = true;
            }
            logger.info(" DWR key : "+ ctx.channel().remoteAddress().toString());

        } else {


            SCTPResponseMessage sctpResponseMessage = new SCTPResponseMessage(trxId, resultObject);
            ActorSelection actorSelection = actorSystem.actorSelection("/user/sctpservice-" + trxId);
            actorSelection.tell(sctpResponseMessage, ActorRef.noSender());



        }

    }



}
