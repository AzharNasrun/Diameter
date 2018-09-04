package com.firstwap.jdiameter.serverHandler;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.firstwap.jdiameter.model.JsmscNumberRange;
import com.firstwap.jdiameter.model.ServerParam;
import constant.Constants;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.sctp.SctpMessage;
import main.*;
import model.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import psl.PSLAnswer;
import com.firstwap.jdiameter.database.ResponseDao;
import service.ResponderDeserializer;
import ulr.ULAnswer;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

@Component
@Profile("server")
public class ResponServerHandler {

    @Autowired
    ResponseDao responseDao;
    private static final Logger logger = Logger.getLogger(Constants.SCTP_LOG);
    // TODO make this parameter from singleton object, and put the thread size configurable later.
    private ScheduledExecutorService scheduler;

    public  void executeResponse(Diameter resultObject, ChannelHandlerContext ctx) {
       try {
           SimpleModule module = new SimpleModule();
           ObjectMapper objectMapper = new ObjectMapper();
           objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
           module.addDeserializer(Diameter.class, new ResponderDeserializer());
           objectMapper.registerModule(module);
           long trxId = resultObject.getEndToEndId();

           long hopByHopId = resultObject.getHopByHopId();
           int delay = 0;
           long commandCode = resultObject.getCommandCode();
           CommandCode CommandCodeObject = CommandCode.getByCommandCode(commandCode);
           boolean requestFlag = resultObject.getFlags().isRequest();
           ServerParam serverParam = getServerParam(resultObject);
           JsmscNumberRange responseFromDB = responseDao.getResponse(serverParam.getTarget(), CommandCodeObject);
           logger.info("received from network commandcode:" + commandCode);

           //dwr from server dwa from client
           if (commandCode == 280 && requestFlag) {
               // send DWA Respon to client
               Diameter dwa = DWAGeneralMain.createMessage((int) hopByHopId, (int) trxId);
               sendMessagebySCTP(dwa, ctx);
           }
           //server get CER and send CEA
           else if (commandCode == 257 && requestFlag) {

               // send CEA Respon to client
               Diameter cea = CEAGeneralMain.createMessage();
               sendMessagebySCTP(cea, ctx);
               logger.info(" DWR key : " + ctx.channel().remoteAddress().toString());

           }

           if (responseFromDB != null) {
               Diameter answer = objectMapper.readValue(responseFromDB.getResponse(), Diameter.class);
               answer.setEndToEndId(trxId);
               delay = responseFromDB.getCallbackDelay();
               scheduler.schedule(() -> sendMessagebySCTP(answer, ctx), delay, TimeUnit.MILLISECONDS);
           }else {
               // send uda Respon to client
               if (commandCode == 306 && requestFlag) {
                   Diameter uda = UDAMain.createMessage(serverParam.getSessionID(), (int) trxId);
                   setErrorToDiameterAnswer(uda, DiameterStatus.DIAMETER_ERROR_ABSENT_USER);
                   scheduler.schedule(() -> sendMessagebySCTP(uda, ctx), delay, TimeUnit.MILLISECONDS);
                   logger.info(" uda key : " + ctx.channel().remoteAddress().toString());

               }
               //PSL Responder
               else if (commandCode == 8388620 && requestFlag) {
                   Diameter pslAnswer = PSLAnswer.createMessage(serverParam.getSessionID(), trxId);
                   setErrorToDiameterAnswer(pslAnswer, DiameterStatus.DIAMETER_ERROR_ABSENT_USER);
                   scheduler.schedule(() -> sendMessagebySCTP(pslAnswer, ctx), delay, TimeUnit.MILLISECONDS);
                   logger.info(" pslAnswer key : " + ctx.channel().remoteAddress().toString());
               }

               //SILCS Responder
               else if (commandCode == 8388622 && requestFlag) {
                   Diameter srilcsA = SRILCSAnswer.createMessage(serverParam.getSessionID(), trxId);
                   setErrorToDiameterAnswer(srilcsA, DiameterStatus.DIAMETER_ERROR_ABSENT_USER);
                   scheduler.schedule(() -> sendMessagebySCTP(srilcsA, ctx), delay, TimeUnit.MILLISECONDS);
                   logger.info(" srilcsA key : " + ctx.channel().remoteAddress().toString());
               }

               //ulr response
               else if (commandCode == CommandCode.ULR.getComandCode() && requestFlag) {
                   Diameter ula = ULAnswer.createULAMessage(serverParam.getSessionID(), (int) trxId);
                   setErrorToDiameterAnswer(ula, DiameterStatus.DIAMETER_ERROR_ABSENT_USER);
                   scheduler.schedule(() -> sendMessagebySCTP(ula, ctx), delay, TimeUnit.MILLISECONDS);
                   logger.info(" ula key : " + ctx.channel().remoteAddress().toString());

               }

               //srr response
               else if (commandCode == CommandCode.SRR.getComandCode() && requestFlag) {
                   Diameter sra = SRAMain.createMessage(serverParam.getSessionID(), (int) trxId);
                   setErrorToDiameterAnswer(sra, DiameterStatus.DIAMETER_ERROR_ABSENT_USER);
                   scheduler.schedule(() -> sendMessagebySCTP(sra, ctx), delay, TimeUnit.MILLISECONDS);
                   logger.info(" sra key : " + ctx.channel().remoteAddress().toString());
               }
           }

       }catch(IOException e){
           e.printStackTrace();
       }

    }

    private void sendMessagebySCTP(Diameter message, ChannelHandlerContext ctx) {
        List<Byte> byteList = DMRMain.ObjectToDiameterMessage(message);

        byte[] byteArray = new byte[byteList.size()];

        for (int x = 0; x < byteList.size(); x++) {
            byteArray[x] = byteList.get(x);
        }

        SctpMessage sctpMessage = new SctpMessage(46, 0, Unpooled.copiedBuffer(byteArray));
        ctx.writeAndFlush(sctpMessage);
    }

    public void setErrorToDiameterAnswer(Diameter answer,DiameterStatus diameterStatus){
        Iterator srilcsaIterator =answer.getAvpList().iterator();
        while(srilcsaIterator.hasNext()){
            AVP avp = (AVP) srilcsaIterator.next();
            AVPCode avpCode= AVPCode.getByCode((int) avp.getCode());
            switch (avpCode){
                case RESULT_CODE:
                    avp.setValue(diameterStatus.getCode());
                    break;
            }
        }
    }

    public ServerParam getServerParam(Diameter diameter){
        ServerParam serverParam = new ServerParam();
        List<AVP> avpIterator = diameter.getAvpList();
        Iterator iterator = avpIterator.iterator();
        while(iterator.hasNext()){
            AVP avp = (AVP) iterator.next();
            AVPCode avpCode= AVPCode.getByCode((int) avp.getCode());
            switch (avpCode){
                case MSISDN:
                serverParam.setTarget(avp.getValue().toString());
                serverParam.setTargetType("msisdn");
                    break;
                case USERNAME:
                    serverParam.setTarget(avp.getValue().toString());
                    serverParam.setTargetType("imsi");
                    break;
                case USER_IDENTITY:
                  List<AVP> userIdentityList=(List<AVP>)avp.getValue();
                  for(AVP userIdentity:userIdentityList){
                      AVPCode userIdentityCode= AVPCode.getByCode((int) userIdentity.getCode());
                      switch (userIdentityCode) {
                          case MSISDN:
                              serverParam.setTarget(userIdentity.getValue().toString());
                              serverParam.setTargetType("msisdn");
                              break;
                          case USERNAME:
                              serverParam.setTarget(userIdentity.getValue().toString());
                              serverParam.setTargetType("imsi");
                              break;
                      }
                  }
                    break;
                case SESSION_ID:
                    serverParam.setSessionID(Integer.parseInt(avp.getValue().toString()));
                    break;
            }
        }
        return serverParam;
    }

    public void setScheduler(ScheduledExecutorService scheduler) {
        this.scheduler = scheduler;
    }
}
