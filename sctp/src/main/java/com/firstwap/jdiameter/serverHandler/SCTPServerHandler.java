package com.firstwap.jdiameter.serverHandler;

import com.firstwap.jdiameter.Properties.JdiameterProperties;
import com.firstwap.jdiameter.serverHandler.ResponServerHandler;
import constant.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.sctp.SctpMessage;
import main.DMRMain;
import model.Diameter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Component
@Profile("server")
@ChannelHandler.Sharable
public class SCTPServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = Logger.getLogger(Constants.SCTP_LOG);
    private ScheduledExecutorService scheduler=Executors.newScheduledThreadPool(4);
    private List<Byte> allMessage = new ArrayList<Byte>();
    @Autowired
    ResponServerHandler responServerHandler;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {


        try {

            if (msg instanceof SctpMessage) {
                SctpMessage sctpMessage = (SctpMessage) msg;


                ByteBuf bb = sctpMessage.content();

                if (logger.isDebugEnabled()) {
                    logger.debug("server bb.capacity():" + bb.capacity());
                    logger.debug("server bb.readableBytes():" + bb.readableBytes());
                }

                while (bb.isReadable()) {
                    allMessage.add(bb.readByte());
                }


                if (sctpMessage.isComplete() && allMessage.size() > 0) {

                    byte[] result = new byte[allMessage.size()];

                    for (int x = 0; x < allMessage.size(); x++) {
                        result[x] = allMessage.get(x);
                    }

                    allMessage.clear();

                    Diameter resultObject = DMRMain.diameterMessageToObject(result);


                    responServerHandler.setScheduler(scheduler);
                    responServerHandler.executeResponse(resultObject, ctx);

                }

            }


        } catch (Exception e) {

            e.printStackTrace();
            ctx.channel().close();
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext arg0, Throwable arg1) throws Exception {
        logger.info(" exception " + arg1);
        // TODO Auto-generated method stub

    }

    @Override
    public void handlerAdded(ChannelHandlerContext arg0) throws Exception {
        logger.info(" handler add");
        // TODO Auto-generated method stub

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext arg0) throws Exception {
        logger.info(" handler remove");

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info(" server read complete");

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info(" channel inactive");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info(" channel active");
    }
}
