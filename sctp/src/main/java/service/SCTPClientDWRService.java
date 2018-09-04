package service;

import constant.Constants;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.sctp.SctpMessage;
import main.DMRMain;
import model.Diameter;
import org.apache.log4j.Logger;

import static main.DWRMain.createDWRMessage;

import java.util.List;

public class SCTPClientDWRService extends Thread{

    ChannelHandlerContext channel;

    private static final Logger logger = Logger.getLogger(Constants.SCTP_LOG);



   public SCTPClientDWRService(ChannelHandlerContext channel){
        this.channel = channel;
    }

    public void run(){
        try {
            logger.info("DWR START");
            Diameter dwrMessage =  createDWRMessage();

            List<Byte> result = DMRMain.ObjectToDiameterMessage(dwrMessage);

            byte[] diameterMessageByte = new byte[result.size()];

            for(int x=0; x<result.size();x++){

                Byte item = result.get(x);

                diameterMessageByte[x] = item;

            }
            SctpMessage sctpMessage = new SctpMessage(46, 0, Unpooled.copiedBuffer(diameterMessageByte));
            channel.writeAndFlush(sctpMessage).sync();

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("DWR",e);

        }

    }






}
