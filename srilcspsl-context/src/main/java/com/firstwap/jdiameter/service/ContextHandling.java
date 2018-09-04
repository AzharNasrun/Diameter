package com.firstwap.jdiameter.service;

import com.firstwap.jdiameter.Properties.JdiameterProperties;
import constant.Constants;
import model.AVP;
import model.AVPCode;
import model.Diameter;
import model.LocationEstimate;
import model.SRILCS.SRILCSParam;
import model.SRILCSRequest;
import model.ismscm.BaseRequestModel;
import model.ismscm.LcsEpsClientName;
import model.ismscm.LcsPrivacyCheckNonSession;
import model.ismscm.LcsQos;
import model.response.ismscm.locate.GeoInfo;
import model.response.ismscm.locate.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import psl.model.request.PSLRequest;
import service.response.ResponseGenerator;

import java.util.List;

@Service
public class ContextHandling {
    private static Logger log = Logger.getLogger(Constants.CONTEXT_LOG);


    public String mappingPSLAToResponse(Diameter pslaMessage, PSLRequest pslaRequestMessage,ResponseGenerator responseJson ) {
        try {

            List<AVP> avpElements = pslaMessage.getAvpList();
            Param param = responseJson.getParamDto();
            String imsi = pslaRequestMessage.getParam().getImsi();

            if (imsi != null) {
                param.setImsiFromSriFc(imsi);
                param.setImsiFromSriSm(imsi);
            }

            String mscNumber = pslaRequestMessage.getParam().getMscNumber();
            if (mscNumber != null) {
                param.setVisitedMscGt(mscNumber);
                param.setVisitedMscName(mscNumber);
            }

            for(AVP avp:avpElements ) {

                AVPCode  code = AVPCode.getByCode(avp.getCode());
                switch (code) {
                    case EXPERIMENTAL_RESULT:
                        List<AVP> experimentalResults =(List<AVP>)avp.getValue();
                        for(AVP experimentalResult :experimentalResults) {
                            log.info("expcodeNode :" + experimentalResult.getCode());
                            AVPCode  expCode = AVPCode.getByCode(experimentalResult.getCode());
                            log.info("expcode :" + expCode);
                            switch (expCode) {
                                case EXPERIMENTAL_RESULT_CODE:
                                    int resultcode = Integer.parseInt(String.valueOf(experimentalResult.getValue()));
                                    log.info("resultcode :" + resultcode);
                                    responseJson.setStatus(resultcode);
                                    break;
                            }
                        }
                        break;
                    case RESULT_CODE:
                        int resultcode =Integer.parseInt(String.valueOf(avp.getValue()));
                        log.info("resultcode :" + resultcode);
                        responseJson.setStatus(resultcode);
                        break;

                    case CELL_GLOBAL_IDENTITY:
                        param.setCellRef(String.valueOf(avp.getValue()));
                        break;

                    case LOCATION_ESTIMATE:
                        LocationEstimate geoInfosource = (LocationEstimate) avp.getValue();
                        GeoInfo geoInfo = responseJson.getGeoInfoDto();
                        int typeOfShape = geoInfosource.getTypeOfshape();
                        geoInfo.setTypeOfShape(typeOfShape);
                        switch (typeOfShape) {
                            case 0:
                                geoInfo.setDegreesOfLatitude(geoInfosource.getLatitudeTotalDegress());
                                geoInfo.setDegreesOfLongitude(geoInfosource.getLongitudeTotalDegress());
                                geoInfo.setRadius(0);
                                geoInfo.setUncertaintyCode(0);
                                break;
                            case 1:
                                geoInfo.setDegreesOfLatitude(geoInfosource.getLatitudeTotalDegress());
                                geoInfo.setDegreesOfLongitude(geoInfosource.getLongitudeTotalDegress());
                                geoInfo.setRadius(geoInfosource.getUncertaintyMeter());
                                geoInfo.setUncertaintyCode(geoInfosource.getUncertaintyCode());
                                break;
                            case 3:
                                geoInfo.setDegreesOfLatitude(geoInfosource.getLatitudeTotalDegress());
                                geoInfo.setDegreesOfLongitude(geoInfosource.getLongitudeTotalDegress());
                                geoInfo.setRadius(0);
                                geoInfo.setUncertaintyCode(0);
                                break;
                            case 5:
                                geoInfo.setDegreesOfLatitude(geoInfosource.getLatitudeTotalDegress());
                                geoInfo.setDegreesOfLongitude(geoInfosource.getLongitudeTotalDegress());
                                geoInfo.setRadius(0);
                                geoInfo.setUncertaintyCode(0);
                                break;
                            case 8:
                                geoInfo.setDegreesOfLatitude(geoInfosource.getLatitudeTotalDegress());
                                geoInfo.setDegreesOfLongitude(geoInfosource.getLongitudeTotalDegress());
                                geoInfo.setRadius(0);
                                geoInfo.setUncertaintyCode(0);
                                break;
                            case 9:
                                geoInfo.setDegreesOfLatitude(geoInfosource.getLatitudeTotalDegress());
                                geoInfo.setDegreesOfLongitude(geoInfosource.getLongitudeTotalDegress());
                                geoInfo.setRadius(0);
                                geoInfo.setUncertaintyCode(0);
                                break;
                            case 10:
                                geoInfo.setDegreesOfLatitude(geoInfosource.getLatitudeTotalDegress());
                                geoInfo.setDegreesOfLongitude(geoInfosource.getLongitudeTotalDegress());
                                geoInfo.setRadius(0);
                                geoInfo.setUncertaintyCode(0);
                                break;
                        }

                        break;

                }
            }

            return responseJson.getResponseString();
        } catch (Exception e) {
            log.error("error convert psla response:" + e.getMessage());
            e.printStackTrace();
            return new String("");
        }
    }

        public String mappingAPIToResponse(Diameter message,ResponseGenerator responseJson ) {
            try {

                List<AVP> avpElements = message.getAvpList();
                model.response.ismscm.api.Param param = responseJson.getAPIParam();

                for(AVP avp:avpElements ) {
                    AVPCode  code = AVPCode.getByCode(avp.getCode());
                    switch (code) {
                        case RESULT_CODE:
                            int resultcode =Integer.parseInt(String.valueOf(avp.getValue()));
                            log.info("resultcode :" + resultcode);
                            responseJson.setAPIStatus(resultcode);
                            break;
                        case USERNAME:
                            param.setImsi(String.valueOf(avp.getValue()));
                            break;
                        case SERVING_NODE:
                            List<AVP> servingNodes = (List<AVP>) avp.getValue();
                            for(AVP servingNode:servingNodes) {
                                AVPCode servCode = AVPCode.getByCode(servingNode.getCode());
                                switch (servCode) {
                                    case MSC_NUMBER:   param.setMsc(String.valueOf(servingNode.getValue()));break;
                                    //case "2404":  arrayNode.put("lcsCapabilitiesSets",servingNode.findValue("value").asText());break;
                                    // case "1489":  arrayNode.put("sgsnNumber",servingNode.findValue("value").asText());break;
                                    // case "2409":  arrayNode.put("sgsnName",servingNode.findValue("value").asText());break;
                                    // case "2410":  arrayNode.put("sgsnRealm",servingNode.findValue("value").asText());break;
                                    //case "2405":  param.put("gmlcAddress",servingNode.findValue("value").asText());break;
                                    //case "318":  arrayNode.put( "3GPPAAAServerName",servingNode.findValue("value").asText());break;
                                }

                            }
                        case ADDITIONAL_SERVING_NODE:
                            List<AVP> addServingNodes =  (List<AVP>) avp.getValue();
                            for(AVP addServingNode : addServingNodes) {
                                AVPCode servCode =AVPCode.getByCode(addServingNode.getCode());
                                switch (servCode) {
                                    case MSC_NUMBER:
                                        param.setMsc(String.valueOf(addServingNode.getValue()));
                                        break;
                                }
                            }
                            break;
                    }
                }

                return responseJson.getAPIResponseString();
            } catch (Exception e) {
                log.error("error convert api response:" + e.getMessage());
                e.printStackTrace();
                return new String("");
            }

    }


    public  PSLRequest convertToPSLJsonRequest(Diameter message,ResponseGenerator responseJson) {
        PSLRequest pslRequest = new PSLRequest();
        psl.model.request.PSL.Param param = new psl.model.request.PSL.Param();
        try {
            pslRequest.setVersion("1");
            pslRequest.setType("psl");
            List<AVP> elements = message.getAvpList();

            param.setSlgLocationType(JdiameterProperties.getSlgLocationType());

            LcsEpsClientName lcsEpsClientName = new LcsEpsClientName();
            lcsEpsClientName.setLcsNameString(JdiameterProperties.getLcsEpsClientNameLcsNameString());
            lcsEpsClientName.setLcsFormatIndicator(JdiameterProperties.getLcsEpsClientNameLcsFormatIndicator());
            param.setLcsEpsClientName(lcsEpsClientName);

            param.setLcsClientType(JdiameterProperties.getLcsClientType());
            param.setLcsPriority(JdiameterProperties.getLcsPriority());

            LcsQos lcsQos = new LcsQos();
            lcsQos.setLcsQosClass(JdiameterProperties.getLcsQosClass());
            lcsQos.setHorizontalAccuracy(JdiameterProperties.getHorizontalAccuracy());
            lcsQos.setVerticalAccuracy( JdiameterProperties.getVerticalAccuracy());
            lcsQos.setVerticalRequested(JdiameterProperties.getVerticalRequested());
            lcsQos.setResponseTime(JdiameterProperties.getResponseTime());
            param.setLcsQos(lcsQos);

            param.setLcsSupportedGadShapes(JdiameterProperties.getLcsSupportedGadShapes());

            LcsPrivacyCheckNonSession lcsPrivacyCheckNonSession = new LcsPrivacyCheckNonSession();
            lcsPrivacyCheckNonSession.setLcsPrivacyCheck(JdiameterProperties.getNonSessionLcsPrivacyCheck());
            param.setLcsPrivacyCheckNonSession(lcsPrivacyCheckNonSession);

            for (AVP avp : elements){
                AVPCode code = AVPCode.getByCode(avp.getCode());
                switch (code) {
                    //case "264":param.put("originHost",avpNode.findValue("value").asText());break;
                    //case "296":param.put("originRealm",avpNode.findValue("value").asText());break;
                    //case "268":param.put("resultCode",avpNode.findValue("value").asText());break;
                    case RESULT_CODE:
                        int resultcode = Integer.parseInt(String.valueOf(avp.getValue()));
                        log.info("resultcode :" + resultcode);
                        responseJson.setStatus(resultcode);
                        break;
                    case USERNAME:
                        param.setImsi(String.valueOf(avp.getValue()));
                        break;
                    case SERVING_NODE:
                        List<AVP> servingNodes = (List<AVP>) avp.getValue();
                        for(AVP servingNode:servingNodes) {
                            AVPCode servCode = AVPCode.getByCode(servingNode.getCode());
                            switch (servCode) {
                                // case "2403":  arrayNode.put("mscNumber",servingNode.findValue("value").asText());break;
                                //case "2404":  arrayNode.put("lcsCapabilitiesSets",servingNode.findValue("value").asText());break;
                                // case "1489":  arrayNode.put("sgsnNumber",servingNode.findValue("value").asText());break;
                                // case "2409":  arrayNode.put("sgsnName",servingNode.findValue("value").asText());break;
                                // case "2410":  arrayNode.put("sgsnRealm",servingNode.findValue("value").asText());break;
                                case MME_REALM:
                                    param.setDestinationRealm(String.valueOf(servingNode.getValue()));
                                    break;
                                case MME_NAME:
                                    param.setDestinationHost(String.valueOf(servingNode.getValue()));
                                    break;
                                //case "2405":  param.put("gmlcAddress",servingNode.findValue("value").asText());break;
                                //case "318":  arrayNode.put( "3GPPAAAServerName",servingNode.findValue("value").asText());break;
                            }

                        }
                    case ADDITIONAL_SERVING_NODE:
                        List<AVP> addServingNodes =  (List<AVP>) avp.getValue();
                        for(AVP addServingNode : addServingNodes) {
                            AVPCode servCode =AVPCode.getByCode(addServingNode.getCode());
                            switch (servCode) {
                                case MSC_NUMBER:
                                    param.setMscNumber(String.valueOf(addServingNode.getValue()));
                                    break;
                            }
                        }
                        break;
                }


            }

            // status error if no mme name
            if (param.getDestinationHost()==null || param.getDestinationHost().trim().length()<=0){
                responseJson.setStatus(1);
            }
            pslRequest.setParam(param);
            log.info("psl json request : " + pslRequest);
        } catch (Exception e) {
            log.error(e.getMessage());

        }


        return pslRequest;
    }

    public SRILCSRequest mappingLocateRequestToSrilcsRequest(BaseRequestModel requestModel) {
        SRILCSRequest srilcsRequest = new SRILCSRequest();
        SRILCSParam SRILCSParam = new SRILCSParam();
        String targetType = requestModel.getParam().getTargetType();
        String target =  requestModel.getParam().getTarget();
        String destinationHost =  requestModel.getParam().getDestinationHost();
        String destinationRealm =  requestModel.getParam().getDestinationRealm();

        if (destinationHost != null) {
            SRILCSParam.setDestinationHost(destinationHost);
        } else {
            SRILCSParam.setDestinationHost(JdiameterProperties.getSctpDefaultHost());
        }

        if (destinationRealm != null) {
            SRILCSParam.setDestinationRealm(destinationRealm);
        } else {
            SRILCSParam.setDestinationRealm(JdiameterProperties.getSctpDefaultRealm());
        }


        if (targetType.equalsIgnoreCase("imsi")) {
            SRILCSParam.setImsi(target);
        }

        if (targetType.equalsIgnoreCase("msisdn")) {
            SRILCSParam.setMsisdn(target);
        }

        if (targetType.equalsIgnoreCase("gmlcNumber")) {
            SRILCSParam.setGmlcNumber(target);
        }
        srilcsRequest.setParam(SRILCSParam);

        srilcsRequest.setAsync("");
        srilcsRequest.setKey("");
        srilcsRequest.setVersion("1");

        return srilcsRequest;
    }

}
