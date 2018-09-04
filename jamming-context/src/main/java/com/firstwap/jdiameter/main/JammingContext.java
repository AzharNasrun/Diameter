package com.firstwap.jdiameter.main;


import constant.Constants;
import model.ismscm.BaseRequestModel;
import reactor.core.publisher.MonoSink;
import com.firstwap.jdiameter.service.JammingService;
import service.response.ResponseGenerator;

public class JammingContext {

   public void execute(final BaseRequestModel baseRequest, MonoSink<String> sink, int sessionId, int transactionId, ResponseGenerator responseGenerator) throws Exception{
       String method = baseRequest.getParam().getMethod();
       if (Constants.METHOD_JAM.equalsIgnoreCase(method)) {
            JammingService.startJamming(baseRequest,sink,sessionId,transactionId,responseGenerator);

        }else if (Constants.METHOD_UNJAM.equalsIgnoreCase(method)) {
           JammingService.stopJamming(sink, baseRequest);

       }else if (Constants.METHOD_JAMSTATUS.equalsIgnoreCase(method)){
            JammingService.JammingStatus(sink, baseRequest);
       }
    }

}
