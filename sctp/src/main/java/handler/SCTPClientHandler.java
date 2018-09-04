package handler;

import akka.actor.ActorSystem;

import constant.Constants;
import io.netty.buffer.ByteBuf;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.sctp.SctpMessage;

import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import main.DMRMain;

import model.Diameter;
import org.apache.log4j.Logger;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

/**
 * Created by gede on 13/04/18.
 */

public class SCTPClientHandler extends ChannelInboundHandlerAdapter {
    private ActorSystem actorSystem;
    private static final Logger logger = Logger.getLogger(Constants.SCTP_LOG);

    public SCTPClientHandler(ActorSystem actors) {
        this.actorSystem = actors;
    }

    private List<Byte> allMessage = new ArrayList<Byte>();




    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        logger.info("channel active" + Thread.currentThread().getName());

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        logger.info("ini object handler:"+this);
        logger.info("ini Threadnya:"+Thread.currentThread().getName());

        try {

            if (msg instanceof SctpMessage) {
                SctpMessage sctpMessage = (SctpMessage) msg;




                    ByteBuf bb = sctpMessage.content();


                    System.out.println("bb.capacity():" + bb.capacity());
                    System.out.println("bb.readableBytes():" + bb.readableBytes());



                    while (bb.isReadable()) {
                        allMessage.add(bb.readByte());
                    }




                    if(sctpMessage.isComplete() && allMessage.size()>0){

                        byte[] result = new byte[allMessage.size()];

                        for (int x = 0; x < allMessage.size(); x++) {
                            result[x] = allMessage.get(x);
                        }

                        allMessage.clear();

                        Diameter resultObject = DMRMain.diameterMessageToObject(result);
                        ResponseHandler resp = new ResponseHandler();
                        resp.executeResponse(resultObject, actorSystem, ctx);

                    }

            }
            logger.info("dapet message:" + (msg.toString()));



        }catch (Exception e){
            logger.error("there is error on receiving the response message:",e);
        }finally{
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        logger.info("client channelReadComplete");

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.close();
        logger.info("channel inactive" + ctx.channel().read() + " DWR key : "+ ctx.channel().remoteAddress().toString() );
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        Boolean removed=false;
        if (evt.getClass().toString().contains("sun.nio.ch.sctp.Shutdown")){
            ScheduledFuture future= ResponseHandler.scheduledFutureMap.get(ctx.channel().remoteAddress().toString());
            if (future!=null){
                future.cancel(true);
                removed =    ResponseHandler.scheduledFutureMap.remove(ctx.channel().remoteAddress().toString(),future);
                ctx.close();
            }

        }
        logger.info("event trigered " + evt.getClass().toString() + " DWR key : "+ ctx.channel().remoteAddress().toString() +" "+removed);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        logger.error("exceptionCaught ",cause);
    }


    private void reconnecting(){}
}
