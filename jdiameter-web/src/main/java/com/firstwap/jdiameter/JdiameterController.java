package com.firstwap.jdiameter;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firstwap.jdiameter.Properties.JdiameterProperties;
import com.firstwap.jdiameter.main.JammingContext;
import com.firstwap.jdiameter.service.ContextHandling;
import constant.Constants;
import io.vavr.control.Try;
import main.SRILCSMain;
import main.SRRMain;
import main.UDRMain;
import model.Diameter;
import model.DiameterStatus;
import model.SRILCSRequest;
import model.ismscm.BaseParamRequestModel;
import model.ismscm.BaseRequestModel;
import model.ismscm.UserIdentity;
import model.request.SRRRequest;
import model.request.UDRRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import psl.PSLMain;
import psl.model.request.PSLRequest;
import reactor.core.publisher.Mono;
import service.SCTPService;
import service.response.ResponseGenerator;
import ulr.ULRMain;
import ulr.model.request.ULRRequest;

import java.io.IOException;
import java.security.SecureRandom;


@Controller
public class JdiameterController {
    private static final Logger logger = Logger.getLogger(Constants.HTTP_LOG);
    final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    ContextHandling contextHandling;

    {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @RequestMapping(value = "/context", consumes = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public Mono<String> context(@RequestBody byte[] message) {
            return Mono.create(sink -> Try.run(() -> {
                BaseRequestModel base = objectMapper.readValue(message, BaseRequestModel.class);
                ResponseGenerator responseJson = new ResponseGenerator(base.getUuid());
                SecureRandom rand = new SecureRandom();
                int sessionId = Math.abs(rand.nextInt());
                // TODO please check whether we can use UUID (string)
                int transactionId = Math.abs(rand.nextInt());
                logger.info("Iniating request with " + base.getUuid() + " with internal id " + transactionId);
                String type = base.getType();
                String method = base.getParam().getMethod();
                BaseParamRequestModel baseParam = base.getParam();
                if (baseParam.getTargetType().equalsIgnoreCase("msisdn")) {
                    baseParam.setMsisdn(baseParam.getTarget());
                } else if (baseParam.getTargetType().equalsIgnoreCase("imsi")) {
                    baseParam.setImsi(baseParam.getTarget());
                }
                final String destinationHostvalue = base.getParam().getDestinationHost() == null ? JdiameterProperties.getSctpDefaultHost() : base.getParam().getDestinationHost();

                logger.info("request type : " + type + " transactionId :" + transactionId + " message : " + new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(message));

                // destination host
                if (base.getParam().getDestinationHost() == null) {
                    baseParam.setDestinationHost(destinationHostvalue);
                }
                if (Constants.TYPE_LOCATE.equalsIgnoreCase(type)) {
                    switch (method) {
                        case Constants.PSL_CONST:
                            SRILCSRequest srilcsRequest = objectMapper.readValue(message, SRILCSRequest.class);
                            srilcsRequest.getParam().setImsi(baseParam.getImsi());
                            srilcsRequest.getParam().setMsisdn(baseParam.getMsisdn());
                            Diameter srilcsMessage = SRILCSMain.createMessage(srilcsRequest, sessionId, transactionId);
                            SCTPService.sendByAkka(srilcsMessage, destinationHostvalue)
                                .subscribe(srilcsa -> {
                                    if (srilcsa.getAvpList().isEmpty()) {
                                        sink.success(responseJson.getErrorServerNetworkDown());
                                        return;
                                    }
                                    PSLRequest pslRequest = contextHandling.convertToPSLJsonRequest(srilcsa, responseJson);

                                    if (responseJson.getStatus() != null && responseJson.getStatus().compareTo(DiameterStatus.SUCCESS) != 0) {
                                        sink.success(responseJson.getResponseString());
                                        return;
                                    }
                                    final int pslTransactionId = Math.abs(rand.nextInt());
                                    Diameter pslMessage = PSLMain.createMessage(pslRequest, sessionId, pslTransactionId);
                                    logger.info("Iniating request with " + base.getUuid() + " with internal psl request id " + pslTransactionId);

                                    SCTPService.sendByAkka(pslMessage, destinationHostvalue)
                                        .subscribe(pslAnswer -> {

                                            if (pslAnswer.getAvpList().isEmpty()) {
                                                sink.success(responseJson.getErrorServerNetworkDown());
                                                return;
                                            }

                                            String resultPsl = contextHandling.mappingPSLAToResponse(pslAnswer, pslRequest, responseJson);
                                            sink.success(resultPsl);
                                        });
                                });
                            // TODO handle if the response is not received within defined time frame
                            break;
                        case Constants.UDR_CONST:
                            UDRRequest udrRequest = objectMapper.readValue(message, UDRRequest.class);
                                UserIdentity userIdentity = new UserIdentity();
                                userIdentity.setMsisdn(baseParam.getMsisdn());
                                udrRequest.getParam().setMsisdn(baseParam.getMsisdn());
                                udrRequest.getParam().setImsi(baseParam.getImsi());
                                udrRequest.getParam().setUserIdentity(userIdentity);
                            Diameter udrMessage = UDRMain.createMessage(udrRequest, sessionId, transactionId);
                            SCTPService.sendByAkka(udrMessage, destinationHostvalue)
                                .subscribe(diameter -> {
                                    if (diameter.getAvpList().isEmpty()) {
                                        sink.success(responseJson.getErrorServerNetworkDown());
                                        return;
                                    }
                                    Try.run(() -> sink.success(UDRMain.getISMSCMMessage(diameter, responseJson)))
                                        .onFailure(error -> sink.error(error));
                                });
                            break;
                        default:
                            logger.info("No Context Found ");
                            break;
                    }
                } else if (Constants.TYPE_DATA_JAMMING.equalsIgnoreCase(type)) {

                    new JammingContext().execute(base, sink, sessionId, transactionId, responseJson);


                } else if (Constants.SRILCS_CONST.equalsIgnoreCase(type)){
                    SRILCSRequest srilcsRequest = objectMapper.readValue(message, SRILCSRequest.class);

                    srilcsRequest.getParam().setImsi(baseParam.getImsi());
                    srilcsRequest.getParam().setMsisdn(baseParam.getMsisdn());

                    responseJson.getAPIParam().setTarget(baseParam.getTarget());
                    Diameter srilcsMessage = SRILCSMain.createMessage(srilcsRequest, sessionId, transactionId);
                    SCTPService.sendByAkka(srilcsMessage, destinationHostvalue).subscribe(Message ->{
                    String response = contextHandling.mappingAPIToResponse(Message,responseJson);
                        Try.run(() -> sink.success(response))
                            .onFailure(error -> sink.error(error));
                    });

              }else if (Constants.SRR_CONST.equalsIgnoreCase(type)){
                    SRRRequest srrRequest = objectMapper.readValue(message, SRRRequest.class);
                    srrRequest.getParam().setImsi(baseParam.getImsi());
                    srrRequest.getParam().setMsisdn(baseParam.getMsisdn());
                    responseJson.getAPIParam().setTarget(baseParam.getTarget());
                    Diameter srrMessage = SRRMain.createMessage(srrRequest, sessionId, transactionId);
                    SCTPService.sendByAkka(srrMessage, destinationHostvalue).subscribe(Message ->{
                        String response = contextHandling.mappingAPIToResponse(Message,responseJson);
                        Try.run(() -> sink.success(response))
                            .onFailure(error -> sink.error(error));
                    });

              }else{
                    String messaqe = "{\n" +
                        "    \"version\":           \"1.0\",\n" +
                        "    \"type\":              \""+base.getType()+"\",\n" +
                        "    \"requestUuid\":              \""+base.getUuid()+"\",\n" +
                        "    \"param\":\n" +
                        "    {\n" +
                        "        \"result\":            104,\n" +
                        "        \"resultDescription\": \"Unknown type.\" \n" +
                        "    }\n" +
                        "}";
                    sink.success(messaqe);
                }

            }).onFailure(error -> sink.error(error)));
    }

    @RequestMapping(value = "/api", consumes = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public Mono<Diameter> api(@RequestBody byte[] message) {
        try {

            try {
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                BaseRequestModel base = objectMapper.readValue(message, BaseRequestModel.class);

                SecureRandom rand = new SecureRandom();
                int sessionId = Math.abs(rand.nextInt());
                // TODO please check whether we can use UUID (string)
                int transactionId =  Math.abs(rand.nextInt());
                logger.info("Iniating request with " + base.getUuid() + " with internal id " + transactionId);
                String type = base.getType();
                String destinationHostvalue = base.getParam().getDestinationHost();

                Mono<Diameter> result = null;

                logger.info("request type : " + type + " transactionId :" + transactionId + " message : " + new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(message));

                // destination host
                if (destinationHostvalue == null) {
                    base.getParam().setDestinationHost(JdiameterProperties.getSctpDefaultHost());
                    destinationHostvalue = JdiameterProperties.getSctpDefaultHost();
                }

                if (type.equalsIgnoreCase(Constants.SRILCS_CONST)) {
                    SRILCSRequest srilcsRequest = objectMapper.readValue(message, SRILCSRequest.class);
                    Diameter srilcsMessage = SRILCSMain.createMessage(srilcsRequest, sessionId, transactionId);
                    return SCTPService.sendByAkka(srilcsMessage, destinationHostvalue);


                } else if (type.equalsIgnoreCase(Constants.PSL_CONST)) {
                    PSLRequest pslRequest = objectMapper.readValue(message, PSLRequest.class);
                    Diameter pslMessage = PSLMain.createMessage(pslRequest, sessionId, transactionId);
                    return SCTPService.sendByAkka(pslMessage, destinationHostvalue);

                } else if (type.equalsIgnoreCase(Constants.SRR_CONST)) {
                    //TODO
                    SRRRequest srrRequest = objectMapper.readValue(message, SRRRequest.class);
                    Diameter srrMessage = SRRMain.createMessage(srrRequest, sessionId, transactionId);
                    return SCTPService.sendByAkka(srrMessage, destinationHostvalue);

                } else if (type.equalsIgnoreCase(Constants.ULR_CONST)) {
                    //TODO
                    ULRRequest ulrRequest = objectMapper.readValue(message, ULRRequest.class);
                    Diameter ulrMessage = ULRMain.createULRMessage(ulrRequest, sessionId, transactionId);
                    return SCTPService.sendByAkka(ulrMessage, destinationHostvalue);


                } else if (type.equalsIgnoreCase(Constants.UDR_CONST)) {
                    //TODO
                    UDRRequest udrRequest = objectMapper.readValue(message, UDRRequest.class);
                    Diameter udrMessage = UDRMain.createMessage(udrRequest, sessionId, transactionId);
                    return SCTPService.sendByAkka(udrMessage, destinationHostvalue);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            logger.error("error request : ", e);
            return Mono.just(new Diameter());
        }
        return null;
    }


}
