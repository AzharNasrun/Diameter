package com.firstwap.jdiameter.service;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.firstwap.jdiameter.Properties.JdiameterProperties;
import constant.Constants;
import main.*;
import model.*;
import model.ismscm.BaseRequestModel;
import model.request.SRRRequest;
import model.response.ismscm.jamming.JamModel;
import model.response.ismscm.jamming.NetworkError;
import model.response.ismscm.jamming.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;
import service.SCTPService;
import service.avp.AVPUtils;
import service.response.ResponseGenerator;
import ulr.ULRMain;
import ulr.model.request.ULRRequest;


import java.security.SecureRandom;
import java.util.List;
import java.util.concurrent.*;

public class JammingService {
    private static Logger log = Logger.getLogger(Constants.CONTEXT_LOG);
    private static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private static ConcurrentMap<String,ScheduledFuture> jammingMap = new ConcurrentHashMap<String,ScheduledFuture>();
    final ObjectMapper objectMapper = new ObjectMapper();
    private static int toogle=1;
    private static ConcurrentMap<String,ResponseGenerator> jammingstatus = new ConcurrentHashMap<String,ResponseGenerator>();
    {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }




 public Mono<Diameter> sendMessageToSRILCS(BaseRequestModel message, int transactionId, int sessionId) {
     try {
         SRILCSRequest srilcsRequest = objectMapper.convertValue(message, SRILCSRequest.class);
         Diameter srilcsMessage = SRILCSMain.createMessage(srilcsRequest, sessionId, transactionId);
         Mono<Diameter> srilcsaMessage = SCTPService.sendByAkka(srilcsMessage, srilcsRequest.getParam().getDestinationHost());
         return srilcsaMessage;
     }catch(Exception e){
         e.printStackTrace();
    }
     return Mono.just(new Diameter());
     }





    public Mono<Diameter> sendMessageToULR(ULRRequest message, int transactionId, int sessionId) {
        try {
            Diameter ulrMessage = ULRMain.createULRMessage(message, sessionId, transactionId);
            Mono<Diameter> ulaMessage = SCTPService.sendByAkka(ulrMessage, message.getParam().getDestinationHost());
            return ulaMessage;
        }catch(Exception e){
            e.printStackTrace();
        }
        return Mono.just(new Diameter());
    }

    public static void stopSchedule(BaseRequestModel baseRequest){
        String key =baseRequest.getParam().getTarget();

        if (jammingMap.get(key)!=null) {

            ScheduledFuture future = jammingMap.get(key);

            future.cancel(true);

            jammingMap.remove(key);
            jammingstatus.remove(key);
            log.info("jamming-stop");
        }
    }

  public static void stopJamming(MonoSink<String> sink,BaseRequestModel baseRequest){
      String key =baseRequest.getParam().getTarget();

      if (jammingMap.get(key)!=null) {

          ScheduledFuture future = jammingMap.get(key);

          future.cancel(true);

          ResponseGenerator responseGenerator = jammingstatus.get(key);
          responseGenerator.getJamParam().setResult(3);
          responseGenerator.getJamModel().setStatus(1);
          responseGenerator.getJamModel().setStatus(1);
          responseGenerator.getJamParam().setStatusDescription("jamming-stop");
          responseGenerator.getJamParam().setResultDescription("jamming-stop");
          responseGenerator.getJamParam().setMethod(baseRequest.getParam().getMethod());
          sink.success(responseGenerator.getJamResponseString());
          jammingstatus.remove(key);
          jammingMap.remove(key);
      }else {
          ResponseGenerator  responseGenerator = new ResponseGenerator(baseRequest.getUuid());
          responseGenerator.getJamParam().setStatus(1);
          responseGenerator.getJamParam().setStatusDescription("target is not in jamming list");
          responseGenerator.getJamParam().setMethod(baseRequest.getParam().getMethod());
          sink.success(responseGenerator.getJamResponseString());
      }
  }



  public static void startJamming(final BaseRequestModel baseRequest, MonoSink<String> sink, int sessionId, int transactionId,ResponseGenerator responseGenerator) {
      String key = baseRequest.getParam().getTarget();
     if(jammingMap.get(key)==null){
         toogle=1;
         responseGenerator.createJamTemplateResponse(baseRequest.getUuid());
         JamModel jamModel = responseGenerator.getJamModel();
         Param responseParam = jamModel.getParam();
         responseParam.setTarget(baseRequest.getParam().getTarget());
         responseParam.setMsisdn(baseRequest.getParam().getMsisdn());
         responseParam.setImsi(baseRequest.getParam().getImsi());
         responseParam.setTargetType(baseRequest.getParam().getTargetType());
         responseParam.setMethod(baseRequest.getParam().getMethod());
        Runnable runnable =new Runnable() {
             @Override
             public void run() {
                  new JammingService().process(sink,baseRequest,sessionId,transactionId,responseGenerator);
             }

        };
         ScheduledFuture future = executorService.scheduleAtFixedRate(runnable,1, 1000, TimeUnit.MILLISECONDS);
         jammingMap.put(key,future);

     }else{
         ResponseGenerator  responseGenerator2 = jammingstatus.get(key);
         JamModel jamModel = responseGenerator2.getJamModel();
         Param responseParam = jamModel.getParam();
         responseParam.setResult(3);
         jamModel.setStatus(1);
         responseParam.setResultDescription("already Jammed");
         sink.success(responseGenerator2.getJamResponseString());
     }
  }


    public Mono<Diameter> sendMessageToSRR(BaseRequestModel message,int transactionId,int sessionId){
        try {
            SRRRequest srrRequest = objectMapper.convertValue(message, SRRRequest.class);
            Diameter srrMessage = SRRMain.createMessage(srrRequest, sessionId, transactionId);
            Mono<Diameter> sraMessage = SCTPService.sendByAkka(srrMessage, srrRequest.getParam().getDestinationHost());
            return sraMessage;
        }catch(Exception e){
            e.printStackTrace();
        }
        return Mono.just(new Diameter());
    }

    public void mappingToULRRequest(Diameter message, ULRRequest ULRRequest,ResponseGenerator responseGenerator) {

        List<AVP> avpElements = message.getAvpList();
       ulr.model.request.ulr.Param param =new ulr.model.request.ulr.Param();
       for(AVP avpObject:avpElements) {
            AVPCode code = AVPCode.getByCode(avpObject.getCode());
            switch (code) {
                case USERNAME:
                    responseGenerator.getJamParam().setImsi(String.valueOf(avpObject.getValue()));
                    param.setImsi(String.valueOf(avpObject.getValue()));
                    break;
                case SERVING_NODE:
                    List<AVP> servingNodes =  (List<AVP>) avpObject.getValue();
                    for(AVP servingNode:servingNodes){
                        AVPCode servCode = AVPCode.getByCode(servingNode.getCode());
                        switch (servCode) {
                            case MME_NAME:
                                param.setMcc(AVPUtils.getMCCFromMMEName(String.valueOf(servingNode.getValue())));
                                param.setMnc(AVPUtils.getMNCFromMMEName(String.valueOf(servingNode.getValue())));
                                param.setDestinationHost(String.valueOf(servingNode.getValue()));
                                break;
                            case MME_REALM:
                                param.setDestinationRealm(String.valueOf(servingNode.getValue()));
                                break;
                            case MME_NUMBER:
                                param.setMmeNumberForMTSMS(String.valueOf(servingNode.getValue()));
                                break;
                            case SGSN_NUMBER:
                                param.setSgsnNumber(String.valueOf(servingNode.getValue()));

                            //case "2405":  param.put("gmlcAddress",servingNode.findValue("value").asText());break;
                            //case "318":  arrayNode.put( "3GPPAAAServerName",servingNode.findValue("value").asText());break;
                        }

                    }
                    break;
                case RESULT_CODE:
                    int resultcode = Integer.parseInt(String.valueOf(avpObject.getValue()));
                    responseGenerator.setJamStatus(resultcode);
                    log.info("resultcode :" + resultcode);
                    break;
            }
        }
        ULRRequest.setParam(param);
    }

    private  void process (MonoSink<String> sink,BaseRequestModel baseRequest,int transactionId,int sessionId,ResponseGenerator responseGenerator){
        try {
            sendMessageToSRILCS(baseRequest, transactionId, sessionId).subscribe(sra -> {
                if (sra.getAvpList().isEmpty()) {
                    stopSchedule(baseRequest);
                    responseGenerator.getJamParam().setResult(3);
                    responseGenerator.getJamModel().setStatus(1);
                    responseGenerator.getJamParam().setStatus(1);
                    responseGenerator.getJamParam().setStatusDescription("timeout");
                    responseGenerator.getJamParam().setResultDescription("timeout");
                    sink.success(responseGenerator.getJamResponseString());
                    return;
                }

                ULRRequest ulrRequest = new ULRRequest();
                mappingToULRRequest(sra, ulrRequest, responseGenerator);
                ulrRequest.getParam().setUlrFlags(0);

                if  (responseGenerator.getJamStatus()!=null && responseGenerator.getJamStatus().compareTo(DiameterStatus.SUCCESS)!=0){
                     sink.success(responseGenerator.getJamResponseString());
                    return;
                }

                //check and update change mme
                 ulr.model.request.ulr.Param param= ulrRequest.getParam();
                if (!"12312123".equalsIgnoreCase(param.getSgsnNumber())||
                    !"12312123".equalsIgnoreCase(param.getMmeNumberForMTSMS())){

                    param.setSgsnNumber("12312123");
                    param.setMmeNumberForMTSMS("12312123");

                final int ulrTransactionId = Math.abs(new SecureRandom().nextInt());
                sendMessageToULR(ulrRequest,ulrTransactionId, sessionId).subscribe(pslAnswer -> {

                    if (pslAnswer.getAvpList().isEmpty()) {
                        stopSchedule(baseRequest);
                        responseGenerator.getJamParam().setResult(3);
                        responseGenerator.getJamModel().setStatus(1);
                        responseGenerator.getJamParam().setStatus(1);
                        responseGenerator.getJamParam().setStatusDescription("timeout");
                        responseGenerator.getJamParam().setResultDescription("timeout");
                        sink.success(responseGenerator.getJamResponseString());
                        return;
                    }
                    mappingResultToResponse(pslAnswer,responseGenerator);

                    if  (responseGenerator.getJamStatus()!=null && responseGenerator.getJamStatus().compareTo(DiameterStatus.SUCCESS)!=0){
                        stopSchedule(baseRequest);
                        sink.success(responseGenerator.getJamResponseString());
                        return;
                    }
                    //returning once per request
                    if (toogle == 1) {
                        toogle = 0;
                        sink.success(responseGenerator.getJamResponseString());
                        System.out.println("printed once");
                    }

                    jammingstatus.put(baseRequest.getParam().getTarget(), responseGenerator);
                });}
            });


        }catch(Exception e){
            e.printStackTrace();
        }
 }

    public void mappingResultToResponse(Diameter message, ResponseGenerator responseGenerator){
        List<AVP> avpElements = message.getAvpList();
        for(AVP avpObject:avpElements) {
            AVPCode code = AVPCode.getByCode(avpObject.getCode());
            switch (code) {
                case RESULT_CODE:
                    int resultcode = Integer.parseInt(String.valueOf(avpObject.getValue()));
                    responseGenerator.setJamStatus(resultcode);
                    break;
            }
        }

    }
 public static void JammingStatus(MonoSink<String> sink,BaseRequestModel baseRequest){
     String key= baseRequest.getParam().getTarget();
     ResponseGenerator responseGenerator =jammingstatus.get(key);
     String messagedesc="";
     int status= 0;



     if (responseGenerator==null){
         responseGenerator = new ResponseGenerator(baseRequest.getUuid());
         messagedesc = "target is not in jamming list";
         status=1;
     }
     else
     if (responseGenerator.getJamStatus().compareTo(DiameterStatus.SUCCESS)!=0){
         messagedesc =messagedesc+"still trying to jam";
         status=0;
     }else{
         messagedesc=responseGenerator.getJamParam().getStatusDescription();
         status= responseGenerator.getJamParam().getStatus();
     }

     responseGenerator.getJamParam().setStatus(status);
     responseGenerator.getJamParam().setStatusDescription(messagedesc);
     responseGenerator.getJamParam().setMethod(baseRequest.getParam().getMethod());
    sink.success(responseGenerator.getJamResponseString());
 }

}
