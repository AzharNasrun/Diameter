package main;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.firstwap.jdiameter.Properties.JdiameterProperties;
import constant.Constants;
import model.*;
import model.request.SRRRequest;
import model.ApplicationID;
import model.srr.Param;
import org.apache.log4j.Logger;
import service.avp.AVPGenerator;
import service.avp.AVPGeneratorImpl;
import service.avp.AVPUtils;


import java.util.ArrayList;
import java.util.List;

public class SRRMain {
    private static final Logger logger = Logger.getLogger(Constants.API_LOG);

    public static void main (String args[]){

    }


    public static String createSRRMessage(ObjectNode inputNode,int sessionId,int transactionId){
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode outputNode= mapper.createObjectNode();
        outputNode.put("version",1);
        outputNode.put("type","srr");


        //flags
        ObjectNode flags= mapper.createObjectNode();
        flags.put("request",true);
        flags.put("proxyable",true);
        flags.put("error",false);
        flags.put("transmitted",false);
        outputNode.put("flags",flags);
        outputNode.put("commandCode",8388647);
        outputNode.put("applicationId",16777312);
        outputNode.put("endToEndId",transactionId);

        //avp
        ObjectNode avpFlagNode =  mapper.createObjectNode();
        avpFlagNode.put("mandatory",true);
        avpFlagNode.put("vendorSpecific",false);
        avpFlagNode.put("protected",false);

        ArrayNode avpArray  = mapper.createArrayNode();

        // session-id
        ObjectNode sessionIdNode = mapper.createObjectNode();
        sessionIdNode.put("code",263);
        sessionIdNode.put("description","Session-ID");
        sessionIdNode.put("flags",avpFlagNode);
        sessionIdNode.put("vendorId",10415);
        sessionIdNode.put("valueType","string");
        sessionIdNode.put("value",sessionId);


        avpArray.add(sessionIdNode);

        //Auth-Session-State
        ObjectNode authSession = mapper.createObjectNode();
        authSession.put("code",277);
        authSession.put("description","Auth-Session-State");
        authSession.put("flags",avpFlagNode);
        authSession.put("vendorId",10415);
        authSession.put("valueType","integer");
        authSession.put("value", 1);
        avpArray.add(authSession);

        //Origin-Host
        ObjectNode originHostNode = mapper.createObjectNode();
        originHostNode.put("code",264);
        originHostNode.put("description","Origin-Host");
        originHostNode.put("flags",avpFlagNode);
        originHostNode.put("vendorId",10415);
        originHostNode.put("valueType","string");
        originHostNode.put("value", JdiameterProperties.getOriginHost());
        avpArray.add(originHostNode);

        //Origin-Realm
        ObjectNode originRealm = mapper.createObjectNode();
        originRealm.put("code",296);
        originRealm.put("description","Origin-Realm");
        originRealm.put("flags",avpFlagNode);
        originRealm.put("vendorId",10415);
        originRealm.put("valueType","string");
        originRealm.put("value", JdiameterProperties.getOriginRealm());
        avpArray.add(originRealm);

        // Destination-Host

        JsonNode destinationHostInputNode = inputNode.findValue("destinationHost");

        if(destinationHostInputNode!= null) {

            String destinationHostString = destinationHostInputNode.asText();
            ObjectNode destinationHostNode = mapper.createObjectNode();
            destinationHostNode.put("code", 293);
            destinationHostNode.put("description", "Destination-Host");
            destinationHostNode.put("flags", avpFlagNode);
            destinationHostNode.put("vendorId", 10415);
            destinationHostNode.put("valueType", "string");
            destinationHostNode.put("value", destinationHostString);
            avpArray.add(destinationHostNode);
        }

        //Dest-Realm



        JsonNode destinationRealmInputNode = inputNode.findValue("destinationRealm");

        if(destinationRealmInputNode!= null) {

            String destinationRealmString = destinationRealmInputNode.asText();

            ObjectNode destRealm = mapper.createObjectNode();
            destRealm.put("code", 283);
            destRealm.put("description", "Destination-Realm");
            destRealm.put("flags", avpFlagNode);
            destRealm.put("vendorId", 10415);
            destRealm.put("valueType", "string");
            destRealm.put("value", destinationRealmString);
            avpArray.add(destRealm);
        }

        //flag2
        ObjectNode avpFlagNode2 =  mapper.createObjectNode();
        avpFlagNode2.put("mandatory",true);
        avpFlagNode2.put("vendorSpecific",true);
        avpFlagNode2.put("protected",false);

        //SC-Address
        JsonNode SC_Address=inputNode.findValue("Sc-Address");
        if(SC_Address!=null) {
            ObjectNode SCAddress = mapper.createObjectNode();
            SCAddress.put("code", 3300);
            SCAddress.put("description", "SC-Address");
            SCAddress.put("flags", avpFlagNode);
            SCAddress.put("vendorId", 10415);
            SCAddress.put("valueType", "OctetString");
            SCAddress.put("value", SC_Address.asText());
            avpArray.add(SCAddress);
        }

       // SM-RP-MTI
        JsonNode SM_RP_MTI =inputNode.findValue("SM-RP-MTI");
        if(SM_RP_MTI!=null) {
            ObjectNode SM_RP_MTInode = mapper.createObjectNode();
            SM_RP_MTInode.put("code", 3308);
            SM_RP_MTInode.put("description", "SM-RP-MTI");
            SM_RP_MTInode.put("flags", avpFlagNode);
            SM_RP_MTInode.put("vendorId", 10415);
            SM_RP_MTInode.put("valueType", "integer");
            SM_RP_MTInode.put("value", SM_RP_MTI.asText());
            avpArray.add(SM_RP_MTInode);
        }

        // SRR-FLAGS
        JsonNode SRR_FLAGS =inputNode.findValue("SRR-FLAGS");
        if(SRR_FLAGS!=null) {
            ObjectNode SRR_FLAGSnode = mapper.createObjectNode();
            SRR_FLAGSnode.put("code", 3310);
            SRR_FLAGSnode.put("description", "SRR-Flags");
            SRR_FLAGSnode.put("flags", avpFlagNode);
            SRR_FLAGSnode.put("vendorId", 10415);
            SRR_FLAGSnode.put("valueType", "integer");
            SRR_FLAGSnode.put("value", SRR_FLAGS.asText());
            avpArray.add(SRR_FLAGSnode);
        }

        JsonNode msisdnInput = inputNode.findValue("msisdn");

        if (msisdnInput!=null) {
            //MSISDN
            ObjectNode msisdn = mapper.createObjectNode();
            msisdn.put("code", 701);
            msisdn.put("description", "MSISDN");
            msisdn.put("flags", avpFlagNode2);
            msisdn.put("vendorId", 10415);
            msisdn.put("valueType", "isdn");
            msisdn.put("value", msisdnInput.asText());
            avpArray.add(msisdn);
        }

        // IMSI
        JsonNode imsiInputNode = inputNode.findValue("imsi");
        if(imsiInputNode!= null){
            String imsiString = imsiInputNode.asText();
            ObjectNode imsiNode = mapper.createObjectNode();
            imsiNode.put("code",1);
            imsiNode.put("description","User-Name");
            imsiNode.put("flags",avpFlagNode);
            imsiNode.put("vendorId",0);
            imsiNode.put("valueType","string");
            imsiNode.put("value", imsiString);
            avpArray.add(imsiNode);

        }
        outputNode.put("avp",avpArray);

        String outputString="";
        try {
            outputString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(outputNode);

        } catch (Exception e) {

            logger.info("error when populating srilcs message:"+e.getMessage());
            e.printStackTrace();
        }
        return outputString;

    }







    public static Diameter createMessage(SRRRequest inputNode, int sessionId, int transactionId) {

        String client = JdiameterProperties.getClient();
        Diameter diameter = new Diameter();
        List<AVP> avpList = new ArrayList<AVP>();
        Param param=inputNode.getParam();
        try {

            diameter.setVersion(1);
            diameter.setType(Constants.SRR_CONST);
            diameter.setFlags(new DiameterFlag());
            diameter.getFlags().setRequest(true);
            diameter.getFlags().setProxyable(true);
            diameter.getFlags().setError(false);
            diameter.getFlags().setTransmitted(false);

            diameter.setCommandCode(CommandCode.SRR.getComandCode());
            diameter.setApplicationId(ApplicationID.SRR.getApplicationID());
            diameter.setEndToEndId(transactionId);


            AVPGenerator avpGenerator = new AVPGeneratorImpl();

            // session-id
            AVPUtils.addAvp(avpList, avpGenerator.sessionId(client, sessionId));

            AVP  vendorSpesificApplicationIdNode = new AVP();
            List<AVP> valueArray = new ArrayList<AVP>();
            vendorSpesificApplicationIdNode.setCode(AVPCode.VENDOR_SPECIFIC_APPLICATION_ID.getCode());
            vendorSpesificApplicationIdNode.setDescription("Vendor-Specific-Application-Id");
            AVPFlag avpFlagNode8 = new AVPFlag();
            avpFlagNode8.setMandatory(true);
            avpFlagNode8.setVendorSpecific(false);
            avpFlagNode8.setProtect(false);
            vendorSpesificApplicationIdNode.setFlags(avpFlagNode8);
            vendorSpesificApplicationIdNode.setVendorId(0);
            vendorSpesificApplicationIdNode.setValueType("grouped");
            valueArray.add(createVendorID());
            vendorSpesificApplicationIdNode.setValue(valueArray);
            avpList.add(vendorSpesificApplicationIdNode);


            //authSessionstate
            AVPUtils.addAvp(avpList, avpGenerator.authSessionState(client));

            // Origin-Host
            AVPUtils.addAvp(avpList,avpGenerator.originHost(client));

            // Origin-Realm
            AVPUtils.addAvp(avpList,avpGenerator.originRealm(client));

            // Destination-Realm
            AVPUtils.addAvp(avpList,avpGenerator.destinationRealm(client,param.getDestinationRealm()));

            // Destination-Host
            AVPUtils.addAvp(avpList,avpGenerator.destinationHost(client,param.getDestinationHost()));

            //MSISDN
            AVPUtils.addAvp(avpList,avpGenerator.msisdn(client,param.getMsisdn()));


            //IMSI / Username
            AVPUtils.addAvp(avpList,avpGenerator.imsi(client,param.getImsi()));



            //SC-address

            AVPUtils.addAvp(avpList,avpGenerator.scAddress(client,param.getScAddress()));

           //SM-RP-MTI

            AVPUtils.addAvp(avpList,avpGenerator.smRpMti(client,param.getSmRpMti()));


            //SM-RP-SMEA
            AVPUtils.addAvp(avpList,avpGenerator.smRpSmea(client,param.getSmRpSmea()));


            //SRR-FLags

            AVPUtils.addAvp(avpList,avpGenerator.srrFlags(client,param.getSrrFlags()));

            diameter.setAvpList(avpList);

        } catch (Exception e) {

            logger.info("error when populating SRR message:" + e.getMessage());
            e.printStackTrace();
        }

        return diameter;
    }



    private static AVP createVendorID(){
        AVP vendorIdNode = new AVP();
        vendorIdNode.setCode(AVPCode.VENDOR_ID.getCode());
        vendorIdNode.setDescription("Vendor-Id");
        AVPFlag avpFlagNode6 = new AVPFlag();
        avpFlagNode6.setMandatory(true);
        avpFlagNode6.setVendorSpecific(false);
        avpFlagNode6.setProtect(false);
        vendorIdNode.setFlags(avpFlagNode6);
        vendorIdNode.setVendorId(0);
        vendorIdNode.setValueType("integer");
        vendorIdNode.setValue(10415);
        return vendorIdNode;
    }

}

