package service;


import akka.actor.ActorSystem;
import constant.Constants;
import handler.SCTPClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.SctpChannel;
import io.netty.channel.sctp.SctpChannelOption;
import io.netty.channel.sctp.SctpMessage;
import io.netty.channel.sctp.nio.NioSctpChannel;
import io.netty.util.concurrent.EventExecutorGroup;
import main.CERGeneralMain;
import main.DMRMain;
import model.Diameter;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class SCTPClientService {

    public ActorSystem actorSystem;

    public InetSocketAddress sourcePrimarySocket;

    public InetSocketAddress destinationPrimarySocket;

    public List<String> sources = new ArrayList<String>();
    public List<String> sourcesIp = new ArrayList<String>();
    public List<String> destination = new ArrayList<String>();

    private static final Logger logger = Logger.getLogger(Constants.SCTP_LOG);

    public ChannelFuture cf;

    public Bootstrap bootstrap;

    public SCTPClientService(List<String> src, List<String> dst, ActorSystem actorSystem) throws Exception {

        this.sources = src;
        this.destination = dst;
        this.actorSystem = actorSystem;

        try {

            EventLoopGroup loopGroup = new NioEventLoopGroup(16);
            bootstrap = new Bootstrap().group(loopGroup).channel(NioSctpChannel.class).option(SctpChannelOption.SO_BACKLOG,128).handler(new ChannelInitializer<SctpChannel>() {
                @Override
                public void initChannel(io.netty.channel.sctp.SctpChannel ch) throws Exception {
                    ChannelPipeline p = ch.pipeline();
                    logger.info("channel inititialize" + this);
                    p.addLast(new SCTPClientHandler(actorSystem));
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error:", e);
        }

    }


    public void initializeAssociation() {

        try {

            // destination address

            for (int x = 0; x < destination.size(); x++) {

                String destini = destination.get(x);

                String ip = destini.split(":")[0];
                String port = destini.split(":")[1];

                if (x == 0) {
                    destinationPrimarySocket = new InetSocketAddress(ip, Integer.parseInt(port));
                    cf = bootstrap.connect(destinationPrimarySocket);
                }

            }


            // binding address
            SctpChannel sctpChannel = (SctpChannel) cf.channel();

            for (int x = 0; x < sources.size(); x++) {

                String source = sources.get(x);

                String ip = source.split(":")[0];
                sourcesIp.add(ip);
                String port = source.split(":")[1];

                System.out.println(ip + ":" + port);

                if (x == 0) {
                    sourcePrimarySocket = new InetSocketAddress(ip, Integer.parseInt(port));
                    sctpChannel.bind(sourcePrimarySocket);
                } else {
                    sctpChannel.bindAddress(InetAddress.getByName(ip));
                }

                Diameter CERMessage = CERGeneralMain.createMessage(sourcesIp);
                List<Byte> byteList = DMRMain.ObjectToDiameterMessage(CERMessage);
                byte[] byteArray = new byte[byteList.size()];

                for (int i = 0; i < byteList.size(); i++) {
                    byteArray[i] = byteList.get(i);
                }
                SctpMessage sctpMessage = new SctpMessage(46, 0, Unpooled.copiedBuffer(byteArray));


                if (sctpChannel.isOpen()){

                sctpChannel.writeAndFlush(sctpMessage).sync();
            }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
