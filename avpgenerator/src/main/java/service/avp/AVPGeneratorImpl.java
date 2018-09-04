package service.avp;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.firstwap.jdiameter.Properties.JdiameterProperties;
import constant.Client;
import model.AVP;
import model.AVPCode;
import model.AVPFlag;
import model.Address;
import model.diameter.AdditionalServingNode;
import model.diameter.ServingNode;
import model.ismscm.LcsEpsClientName;
import model.ismscm.LcsPrivacyCheckNonSession;
import model.ismscm.LcsQos;
import model.ismscm.UserIdentity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by aji on 29/03/18.
 */
public class AVPGeneratorImpl implements AVPGenerator {

    @Override
    public AVP originHost(String client) {

        if(JdiameterProperties.getOriginHost() != null && !JdiameterProperties.getOriginHost().isEmpty()) {

            AVP originHost = new AVP();

            originHost.setCode(AVPCode.ORIGIN_HOST.getCode());
            originHost. setDescription("Origin-Host");
            originHost.setFlags(new AVPFlag());
            originHost.getFlags().setMandatory(true);
            originHost.getFlags().setVendorSpecific(false);
            originHost.getFlags().setProtect(false);

            originHost.setVendorId(10415);
            originHost.setValueType("string");
            originHost.setValue(JdiameterProperties.getOriginHost());

            return originHost;
        }
        return null;
    }

    @Override
    public AVP originRealm(String client) {

        if(JdiameterProperties.getOriginRealm() != null && !JdiameterProperties.getOriginRealm().isEmpty()) {

            AVP originRealm = new AVP();

            originRealm.setCode(AVPCode.ORIGIN_REALM.getCode());
            originRealm. setDescription("Origin-Realm");
            originRealm.setFlags(new AVPFlag());
            originRealm.getFlags().setMandatory(true);
            originRealm.getFlags().setVendorSpecific(false);
            originRealm.getFlags().setProtect(false);

            originRealm.setVendorId(10415);
            originRealm.setValueType("string");
            originRealm.setValue(JdiameterProperties.getOriginRealm());

            return originRealm;
        }
        return null;
    }

    @Override
    public AVP destinationRealm(String client, String inputNode) {

        if(inputNode!= null) {

            String destinationRealmString = inputNode;

            AVP destinationRealm = new AVP();

            destinationRealm.setCode(AVPCode.DESTINATION_REALM.getCode());
            destinationRealm. setDescription("Destination-Realm");
            destinationRealm.setFlags(new AVPFlag());
            destinationRealm.getFlags().setMandatory(true);
            destinationRealm.getFlags().setVendorSpecific(false);
            destinationRealm.getFlags().setProtect(false);

            destinationRealm.setVendorId(10415);
            destinationRealm.setValueType("string");
            destinationRealm.setValue(destinationRealmString);

            return destinationRealm;
        }
        return null;
    }


    @Override
    public AVP destinationHost(String client, String inputNode) {

        if(inputNode!= null) {

            String destinationHostString = inputNode;

            AVP destinationHost = new AVP();

            destinationHost.setCode(AVPCode.DESTINATION_HOST.getCode());
            destinationHost. setDescription("Destination-Host");
            destinationHost.setFlags(new AVPFlag());
            destinationHost.getFlags().setMandatory(true);
            destinationHost.getFlags().setVendorSpecific(false);
            destinationHost.getFlags().setProtect(false);

            destinationHost.setVendorId(10415);
            destinationHost.setValueType("string");
            destinationHost.setValue(destinationHostString);

            return destinationHost;
        }
        return null;
    }

    @Override
    public AVP msisdn(String client, String inputNode) {

        if(inputNode!= null){

            String msisdnString = inputNode;

            AVP msisdn = new AVP();

            msisdn.setCode(AVPCode.MSISDN.getCode());
            msisdn. setDescription("MSISDN");
            msisdn.setFlags(new AVPFlag());
            msisdn.getFlags().setMandatory(true);
            msisdn.getFlags().setVendorSpecific(true);
            msisdn.getFlags().setProtect(false);

            msisdn.setVendorId(10415);
            msisdn.setValueType("isdn");
            msisdn.setValue(msisdnString);

            return msisdn;
        }
        return null;
    }

    @Override
    public AVP imsi(String client, String inputNode) {

        if(inputNode!= null){

            String imsiString = inputNode;

            AVP imsi = new AVP();

            imsi.setCode(AVPCode.USERNAME.getCode());
            imsi. setDescription("User-Name");
            imsi.setFlags(new AVPFlag());
            imsi.getFlags().setMandatory(true);
            imsi.getFlags().setVendorSpecific(false);
            imsi.getFlags().setProtect(false);

            imsi.setVendorId(0);
            imsi.setValueType("string");
            imsi.setValue(imsiString);

            return imsi;
        }
        return null;
    }


    public AVP scAddress(String client,String inputNode){

        if(inputNode!= null){

            String address = inputNode;

            AVP scAddress = new AVP();

            scAddress.setCode(AVPCode.SC_ADDRESS.getCode());
            scAddress. setDescription("SC-ADDRESS");
            scAddress.setFlags(new AVPFlag());
            scAddress.getFlags().setMandatory(true);
            scAddress.getFlags().setVendorSpecific(false);
            scAddress.getFlags().setProtect(false);

            scAddress.setVendorId(0);
            scAddress.setValueType("string");
            scAddress.setValue(address);

            return scAddress;
        }
        return null;

    }

    public AVP smRpMti(String client,Integer inputNode){

        if(inputNode!= null){

            Integer smRpMti = inputNode;

            AVP smRpMtiAvp = new AVP();

            smRpMtiAvp.setCode(AVPCode.SM_RP_MTI.getCode());
            smRpMtiAvp. setDescription("SM-RP-MTI");
            smRpMtiAvp.setFlags(new AVPFlag());
            smRpMtiAvp.getFlags().setMandatory(true);
            smRpMtiAvp.getFlags().setVendorSpecific(false);
            smRpMtiAvp.getFlags().setProtect(false);

            smRpMtiAvp.setVendorId(0);
            smRpMtiAvp.setValueType("integer");
            smRpMtiAvp.setValue(smRpMti);

            return smRpMtiAvp;
        }
        return null;

    }


    public AVP smRpSmea(String client,String inputNode){

        if(inputNode!= null){

            String smRpSmea = inputNode;

            AVP smRpSmeaAvp = new AVP();

            smRpSmeaAvp.setCode(AVPCode.SM_RP_SMEA.getCode());
            smRpSmeaAvp. setDescription("SM-RP-SMEA");
            smRpSmeaAvp.setFlags(new AVPFlag());
            smRpSmeaAvp.getFlags().setMandatory(true);
            smRpSmeaAvp.getFlags().setVendorSpecific(false);
            smRpSmeaAvp.getFlags().setProtect(false);

            smRpSmeaAvp.setVendorId(0);
            smRpSmeaAvp.setValueType("string");
            smRpSmeaAvp.setValue(smRpSmea);

            return smRpSmeaAvp;
        }
        return null;

    }



    public AVP srrFlags(String client,Integer inputNode){

        if(inputNode!= null){

            Integer srrFlags = inputNode;

            AVP srrFlagsAvp = new AVP();

            srrFlagsAvp.setCode(AVPCode.SRR_FLAGS.getCode());
            srrFlagsAvp. setDescription("SRR-FLAGS");
            srrFlagsAvp.setFlags(new AVPFlag());
            srrFlagsAvp.getFlags().setMandatory(true);
            srrFlagsAvp.getFlags().setVendorSpecific(false);
            srrFlagsAvp.getFlags().setProtect(false);

            srrFlagsAvp.setVendorId(0);
            srrFlagsAvp.setValueType("integer");
            srrFlagsAvp.setValue(srrFlags);

            return srrFlagsAvp;
        }
        return null;

    }


    @Override
    public AVP gmlcNumber(String client, String inputNode) {

        if(inputNode!= null){


            AVP gmlc = new AVP();

            gmlc.setCode(AVPCode.GMLC_NUMBER.getCode());
            gmlc. setDescription("GMLC-Number");
            gmlc.setFlags(new AVPFlag());
            gmlc.getFlags().setMandatory(true);
            gmlc.getFlags().setVendorSpecific(true);
            gmlc.getFlags().setProtect(false);

            gmlc.setVendorId(10415);
            gmlc.setValueType("isdn");
            gmlc.setValue(inputNode);

            return gmlc;
        }
        return null;
    }

    @Override
    public AVP vendorId(String client) {

        if(Long.valueOf(JdiameterProperties.getCerVendorId()) != null) {

            AVP vendorId = new AVP();

            vendorId.setCode(AVPCode.VENDOR_ID.getCode());
            vendorId. setDescription("Vendor-Id");
            vendorId.setFlags(new AVPFlag());
            vendorId.getFlags().setMandatory(true);
            vendorId.getFlags().setVendorSpecific(false);
            vendorId.getFlags().setProtect(false);

            vendorId.setVendorId(0);
            vendorId.setValueType("integer");
            vendorId.setValue(JdiameterProperties.getCerVendorId());

            return vendorId;
        }
        return null;
    }

    @Override
    public List<AVP> hostIPAddresses(String client, List<String> sourceAdress) {
        List<AVP> avpList = new ArrayList<AVP>();

        for (String address : sourceAdress) {

            AVP vendorId = new AVP();

            vendorId.setCode(AVPCode.HOST_IP_ADDRESS.getCode());
            vendorId. setDescription("Host-Ip-Address");
            vendorId.setFlags(new AVPFlag());
            vendorId.getFlags().setMandatory(true);
            vendorId.getFlags().setVendorSpecific(false);
            vendorId.getFlags().setProtect(false);

            vendorId.setVendorId(0);

            Address ipAddress = new Address();
            ipAddress.setFamily("ipv4");
            ipAddress.setValue(address);

            vendorId.setValueType("ipAddress");
            vendorId.setValue(ipAddress);

            avpList.add(vendorId);
        }

        if (avpList.size() > 0) {
            return avpList;
        } else {
            return null;
        }
    }

    @Override
    public AVP productName(String client) {

        if(JdiameterProperties.getCerProductName() != null && !JdiameterProperties.getCerProductName().isEmpty()) {

            AVP productName = new AVP();

            productName.setCode(AVPCode.PRODUCT_NAME.getCode());
            productName. setDescription("Product-Name");
            productName.setFlags(new AVPFlag());
            productName.getFlags().setMandatory(true);
            productName.getFlags().setVendorSpecific(false);
            productName.getFlags().setProtect(false);

            productName.setVendorId(0);
            productName.setValueType("string");
            productName.setValue(JdiameterProperties.getCerProductName());

            return productName;
        }
        return null;
    }

    @Override
    public List<AVP> authApplicationId(String client) {
        String[] listAuthApplicationId =  JdiameterProperties.getListAuthApplicationId();


        List<AVP> avpList = new ArrayList<AVP>();

        if (listAuthApplicationId != null) {
            if (listAuthApplicationId.length > 0) {

                for (String authApplicationIdItem : listAuthApplicationId) {

                    if (!authApplicationIdItem.equalsIgnoreCase("")) {
                        AVP authApplicationId = new AVP();

                        authApplicationId.setCode(AVPCode.AUTH_APPLICATION_ID.getCode());
                        authApplicationId.setDescription("Auth-Application-Id");
                        authApplicationId.setFlags(new AVPFlag());
                        if (client.toLowerCase().contains(Client.BIK)) {
                            authApplicationId.getFlags().setVendorSpecific(false);
                        } else {
                            authApplicationId.getFlags().setVendorSpecific(true);
                        }

                        authApplicationId.getFlags().setMandatory(true);

                        authApplicationId.getFlags().setProtect(false);

                        authApplicationId.setVendorId(0);

                        authApplicationId.setValueType("integer");
                        authApplicationId.setValue(Integer.parseInt(authApplicationIdItem));

                        avpList.add(authApplicationId);
                    }
                }

            }

        }
        if (avpList.size() > 0) {
            return avpList;
        } else {
            return null;
        }
    }

    @Override
    public AVP authSessionState(String client) {

        AVP authSessionState = new AVP();

        authSessionState.setCode(AVPCode.AUTH_SESSION_STATE.getCode());
        authSessionState. setDescription("Auth-Session-State");
        authSessionState.setFlags(new AVPFlag());
        authSessionState.getFlags().setMandatory(true);
        authSessionState.getFlags().setVendorSpecific(false);
        authSessionState.getFlags().setProtect(false);

        authSessionState.setVendorId(0);
        authSessionState.setValueType("integer");
        authSessionState.setValue(0);

        return authSessionState;
    }

    @Override
    public List<AVP> inbandSecurityId(String client) {
        String[] listInbandSecurityIds =  JdiameterProperties.getListInbandSecurityIds();


        List<AVP> avpList = new ArrayList<AVP>();

        if (listInbandSecurityIds != null) {
            if (listInbandSecurityIds.length > 0) {

                for (String inbandSecurityIds : listInbandSecurityIds) {
                    if (!inbandSecurityIds.equalsIgnoreCase("")) {

                        AVP inbandSecurityId = new AVP();

                        inbandSecurityId.setCode(AVPCode.INBAND_SECURITY_ID.getCode());
                        inbandSecurityId.setDescription("Inband-Security-Id");
                        inbandSecurityId.setFlags(new AVPFlag());
                        if (client.toLowerCase().contains(Client.BIK)) {
                            inbandSecurityId.getFlags().setVendorSpecific(false);
                        } else {
                            inbandSecurityId.getFlags().setVendorSpecific(true);
                        }

                        inbandSecurityId.getFlags().setMandatory(true);

                        inbandSecurityId.getFlags().setProtect(false);

                        inbandSecurityId.setVendorId(0);

                        inbandSecurityId.setValueType("integer");
                        inbandSecurityId.setValue(Integer.parseInt(inbandSecurityIds));

                        avpList.add(inbandSecurityId);
                    }
                }

            }
        }
        if (avpList.size() > 0) {
            return avpList;
        } else {
            return null;
        }
    }

    @Override
    public List<AVP> acctApplicationId(String client) {
        String[] listAcctApplicationIds = JdiameterProperties.getListAcctApplicationIds();

        List<AVP> avpList = new ArrayList<AVP>();

        if (listAcctApplicationIds != null) {
            if (listAcctApplicationIds.length > 0) {

                for (String acctApplicationIds : listAcctApplicationIds) {
                    if (!acctApplicationIds.equalsIgnoreCase("")) {

                        AVP acctApplicationId = new AVP();

                        acctApplicationId.setCode(AVPCode.ACCT_APPLICATION_ID.getCode());
                        acctApplicationId.setDescription("Acct-Application-Id");
                        acctApplicationId.setFlags(new AVPFlag());
                        acctApplicationId.getFlags().setMandatory(true);
                        acctApplicationId.getFlags().setVendorSpecific(true);
                        acctApplicationId.getFlags().setProtect(false);

                        acctApplicationId.setVendorId(0);

                        acctApplicationId.setValueType("integer");
                        acctApplicationId.setValue(Integer.parseInt(acctApplicationIds));

                        avpList.add(acctApplicationId);
                    }
                }

            }
        }
        if (avpList.size() > 0) {
            return avpList;
        } else {
            return null;
        }
    }

    @Override
    public List<AVP> vendorSpesificApplicationId(String client) {
        int maxCount = 15;
        List<AVP> avpList = new ArrayList<AVP>();
        for(int x=1;x<=maxCount;x++){
            // create Vendor-Specific-Application-Id
            AVP acctApplicationId = new AVP();

            acctApplicationId.setCode(AVPCode.VENDOR_SPECIFIC_APPLICATION_ID.getCode());
            acctApplicationId.setDescription("Vendor-Specific-Application-Id");
            acctApplicationId.setFlags(new AVPFlag());
            acctApplicationId.getFlags().setMandatory(true);
            acctApplicationId.getFlags().setVendorSpecific(false);
            acctApplicationId.getFlags().setProtect(false);

            acctApplicationId.setVendorId(0);

            acctApplicationId.setValueType("grouped");


            List<AVP> avpChildList = new ArrayList<AVP>();
            for(int y=1;y<=maxCount;y++){

                // create auth and acctApplicationId
                String keyauthProp="cer.vendorSpecificApplicationId["+x+"].auth.applicationId["+y+"]";
                String keyacctProp="cer.vendorSpecificApplicationId["+x+"].acctApplicationId.applicationId["+y+"]";

                String  authValue = (JdiameterProperties.getString(keyauthProp));
                String  acctValue = ( JdiameterProperties.getString(keyacctProp));

                if(authValue==null && acctValue==null){
                    break;
                }
                if(authValue!=null && !authValue.isEmpty()) {
                    avpChildList.add(AVPGeneratorImpl.authApplicationIdChild(client,authValue));
                }
                if(acctValue!=null && !acctValue.isEmpty()) {
                    avpChildList.add(AVPGeneratorImpl.acctApplicationIdChild(client,acctValue));
                }

                if(client.toLowerCase().contains(Client.BIK)){
                    avpChildList.add(vendorId(client));
                }
            }

            if(avpChildList.size()>0){
                acctApplicationId.setValue(avpChildList);
                avpList.add(acctApplicationId);
            }

        }

        if (avpList.size() > 0) {
            return avpList;
        } else {
            return null;
        }
    }

    @Override
    public AVP sessionId(String client, Integer sessionId) {
        if (sessionId!=null) {
    AVP sessionIdAvp = new AVP();

    sessionIdAvp.setCode(AVPCode.SESSION_ID.getCode());
    sessionIdAvp.setDescription("Session-ID");
    sessionIdAvp.setFlags(new AVPFlag());
    sessionIdAvp.getFlags().setMandatory(true);
    sessionIdAvp.getFlags().setVendorSpecific(false);
    sessionIdAvp.getFlags().setProtect(false);

    sessionIdAvp.setVendorId(10415);
    sessionIdAvp.setValueType("string");
    sessionIdAvp.setValue("" + sessionId);

    return sessionIdAvp;
    }
        return null;
        }

    @Override
    public AVP sLgLocationType(String client, Integer inputNode) {

        if(inputNode!= null){

            int slgLocationTypeInteger = inputNode;

            AVP sLgLocationType = new AVP();

            sLgLocationType.setCode(AVPCode.SLG_LOCATION_TYPE.getCode());
            sLgLocationType. setDescription("SLg-Location-Type");
            sLgLocationType.setFlags(new AVPFlag());
            sLgLocationType.getFlags().setMandatory(true);
            sLgLocationType.getFlags().setVendorSpecific(true);
            sLgLocationType.getFlags().setProtect(false);

            sLgLocationType.setVendorId(10415);
            sLgLocationType.setValueType("integer");
            sLgLocationType.setValue(slgLocationTypeInteger);

            return sLgLocationType;
        }
        return null;
    }

    @Override
    public AVP lcsEPSClientName(String client, LcsEpsClientName inputNode) {

        if (inputNode != null) {

            List<AVP> avpChildList = new ArrayList<AVP>();

            AVP lcsEpsClientName = new AVP();

            lcsEpsClientName.setCode(AVPCode.LCS_EPS_CLIENT_NAME.getCode());
            lcsEpsClientName.setDescription("LCS-EPS-Client-Name");
            lcsEpsClientName.setFlags(new AVPFlag());
            lcsEpsClientName.getFlags().setMandatory(true);
            lcsEpsClientName.getFlags().setVendorSpecific(true);
            lcsEpsClientName.getFlags().setProtect(false);

            lcsEpsClientName.setVendorId(10415);
            lcsEpsClientName.setValueType("grouped");

            //LCS-Name-String
            String lcsNameStringInputNode = inputNode.getLcsNameString();
            if (lcsNameStringInputNode != null) {
                avpChildList.add(lcsNameArray(client, lcsNameStringInputNode));
            }

            //LCS-Format-Indicator
            Integer lcsClientTypeInputNode = inputNode.getLcsFormatIndicator();
            if (lcsClientTypeInputNode != null) {
                avpChildList.add(lcsFormatIndicator(client, lcsClientTypeInputNode));
            }


            if (avpChildList.size() > 0) {
                lcsEpsClientName.setValue(avpChildList);
            }
            return lcsEpsClientName;
        }

        return null;
    }

    @Override
    public AVP lcsNameArray(String client, String inputNode) {
        if(inputNode!=null){
            AVP lcsNameStringInput = new AVP();

            lcsNameStringInput.setCode(AVPCode.LCS_NAME_STRING.getCode());
            lcsNameStringInput. setDescription("LCS-Name-String");
            lcsNameStringInput.setFlags(new AVPFlag());
            lcsNameStringInput.getFlags().setMandatory(true);
            lcsNameStringInput.getFlags().setVendorSpecific(true);
            lcsNameStringInput.getFlags().setProtect(false);

            lcsNameStringInput.setVendorId(10415);
            lcsNameStringInput.setValueType("string");
            lcsNameStringInput.setValue(inputNode);

            return lcsNameStringInput;
        }

        return null;
    }

    @Override
    public AVP lcsFormatIndicator(String client, Integer inputNode) {

        if(inputNode!= null){


            AVP lcsFormatIndicator = new AVP();

            lcsFormatIndicator.setCode(AVPCode.LCS_FORMAT_INDICATOR.getCode());
            lcsFormatIndicator. setDescription("LCS-Format-Indicator");
            lcsFormatIndicator.setFlags(new AVPFlag());
            lcsFormatIndicator.getFlags().setMandatory(true);
            lcsFormatIndicator.getFlags().setVendorSpecific(true);
            lcsFormatIndicator.getFlags().setProtect(false);

            lcsFormatIndicator.setVendorId(10415);
            lcsFormatIndicator.setValueType("integer");
            lcsFormatIndicator.setValue(inputNode);

            return lcsFormatIndicator;
        }
        return null;
    }

    @Override
    public AVP lcsClientType(String client, Integer inputNode) {

        if(inputNode!= null){

            AVP lcsFormatIndicator = new AVP();

            lcsFormatIndicator.setCode(AVPCode.LCS_CLIENT_TYPE.getCode());
            lcsFormatIndicator. setDescription("LCS-Client-Type");
            lcsFormatIndicator.setFlags(new AVPFlag());
            lcsFormatIndicator.getFlags().setMandatory(true);
            lcsFormatIndicator.getFlags().setVendorSpecific(true);
            lcsFormatIndicator.getFlags().setProtect(false);

            lcsFormatIndicator.setVendorId(10415);
            lcsFormatIndicator.setValueType("integer");
            lcsFormatIndicator.setValue(inputNode);

            return lcsFormatIndicator;
        }
        return null;
    }

    @Override
    public AVP lcsPriority(String client, Integer inputNode) {
        if(inputNode!= null){


            AVP lcsPriority = new AVP();

            lcsPriority.setCode(AVPCode.LCS_PRIORITY.getCode());
            lcsPriority. setDescription("LCS-Priority");
            lcsPriority.setFlags(new AVPFlag());
            lcsPriority.getFlags().setMandatory(true);
            lcsPriority.getFlags().setVendorSpecific(true);
            lcsPriority.getFlags().setProtect(false);

            lcsPriority.setVendorId(10415);
            lcsPriority.setValueType("integer");
            lcsPriority.setValue(inputNode);

            return lcsPriority;
        }
        return null;
    }

    @Override
    public AVP lcsQoS(String client, LcsQos inputNode) {
       if(inputNode!= null){
            List<AVP> avpChildList = new ArrayList<AVP>();

            AVP lcsQos = new AVP();

            lcsQos.setCode(AVPCode.LCS_QOS.getCode());
            lcsQos. setDescription("LCS-QoS");
            lcsQos.setFlags(new AVPFlag());
            lcsQos.getFlags().setMandatory(true);
            lcsQos.getFlags().setVendorSpecific(true);
            lcsQos.getFlags().setProtect(false);

            lcsQos.setVendorId(10415);
            lcsQos.setValueType("grouped");

            //LCS-QoS-Class
            Integer lcsQosClassInputNode = inputNode.getLcsQosClass();
            if(lcsQosClassInputNode!=null){
                avpChildList.add(lcsQoSClass(client,lcsQosClassInputNode));
            }

            //Horizontal-Accuracy
           Integer horizontalAccuracyInputNode = inputNode.getHorizontalAccuracy();
            if(horizontalAccuracyInputNode!=null){
                avpChildList.add(horizontalAccuracy(client,horizontalAccuracyInputNode));
            }


            //Vertical-Accuracy
           Integer verticalAccuracyInputNode = inputNode.getVerticalAccuracy();
            if(verticalAccuracyInputNode!=null){
                avpChildList.add(verticalAccuracy(client,verticalAccuracyInputNode));
            }

            //Vertical-Requested
           Integer verticalRequestedInputNode = inputNode.getVerticalRequested();
            if(verticalRequestedInputNode!=null){
                avpChildList.add(verticalRequested(client,verticalRequestedInputNode));
            }

            //MainResponse-Time
           Integer responseTimeInputNode = inputNode.getResponseTime();
            if(responseTimeInputNode!=null){
                avpChildList.add(responseTime(client,responseTimeInputNode));
            }


            if(avpChildList.size()>0){
                lcsQos.setValue(avpChildList);
            }

            return lcsQos;

        }
            return null;
    }

    @Override
    public AVP lcsQoSClass(String client, Integer inputNode) {

        if(inputNode!=null){

            AVP lcsQosClass = new AVP();

            lcsQosClass.setCode(AVPCode.LCS_QOS_CLASS.getCode());
            lcsQosClass. setDescription("LCS-QoS-Class");
            lcsQosClass.setFlags(new AVPFlag());
            lcsQosClass.getFlags().setMandatory(true);
            lcsQosClass.getFlags().setVendorSpecific(true);
            lcsQosClass.getFlags().setProtect(false);

            lcsQosClass.setVendorId(10415);
            lcsQosClass.setValueType("integer");
            lcsQosClass.setValue(inputNode);

            return lcsQosClass;
        }
        return null;
    }

    @Override
    public AVP horizontalAccuracy(String client, Integer inputNode) {
        if(inputNode!=null){


            AVP horizontalAccuracy = new AVP();

            horizontalAccuracy.setCode(AVPCode.HORIZONTAL_ACCURACY.getCode());
            horizontalAccuracy. setDescription("Horizontal-Accuracy");
            horizontalAccuracy.setFlags(new AVPFlag());
            horizontalAccuracy.getFlags().setMandatory(true);
            horizontalAccuracy.getFlags().setVendorSpecific(true);
            horizontalAccuracy.getFlags().setProtect(false);

            horizontalAccuracy.setVendorId(10415);
            horizontalAccuracy.setValueType("integer");
            horizontalAccuracy.setValue(inputNode);

            return  horizontalAccuracy;

        }
        return  null;
    }

    @Override
    public AVP verticalAccuracy(String client, Integer inputNode) {
        if(inputNode!=null){


            AVP verticalAccuracy = new AVP();

            verticalAccuracy.setCode(AVPCode.VERTICAL_ACCURACY.getCode());
            verticalAccuracy. setDescription("Vertical-Accuracy");
            verticalAccuracy.setFlags(new AVPFlag());
            verticalAccuracy.getFlags().setMandatory(true);
            verticalAccuracy.getFlags().setVendorSpecific(true);
            verticalAccuracy.getFlags().setProtect(false);

            verticalAccuracy.setVendorId(10415);
            verticalAccuracy.setValueType("integer");
            verticalAccuracy.setValue(inputNode);

            return  verticalAccuracy;
        }
        return  null;
    }

    @Override
    public AVP verticalRequested(String client, Integer inputNode) {

        if(inputNode!=null){

            AVP verticalRequested = new AVP();

            verticalRequested.setCode(AVPCode.VERTICAL_REQUESTED.getCode());
            verticalRequested. setDescription("Vertical-Requested");
            verticalRequested.setFlags(new AVPFlag());
            verticalRequested.getFlags().setMandatory(true);
            verticalRequested.getFlags().setVendorSpecific(true);
            verticalRequested.getFlags().setProtect(false);

            verticalRequested.setVendorId(10415);
            verticalRequested.setValueType("integer");
            verticalRequested.setValue(inputNode);

            return  verticalRequested;
        }
        return null;
    }

    @Override
    public AVP responseTime(String client, Integer inputNode) {

        if(inputNode!=null){

            AVP responseTime = new AVP();

            responseTime.setCode(AVPCode.RESPONSE_TIME.getCode());
            responseTime. setDescription("MainResponse-Time");
            responseTime.setFlags(new AVPFlag());
            responseTime.getFlags().setMandatory(true);
            responseTime.getFlags().setVendorSpecific(true);
            responseTime.getFlags().setProtect(false);

            responseTime.setVendorId(10415);
            responseTime.setValueType("integer");
            responseTime.setValue(inputNode);

            return  responseTime;
        }
        return null;
    }

    @Override
    public AVP lcsSupportedGADShapes(String client, Integer inputNode) {
        if(inputNode!= null){
            AVP lcsSupportedGadShapes = new AVP();

            lcsSupportedGadShapes.setCode(AVPCode.LCS_SUPPORTED_GAD_SHAPES.getCode());
            lcsSupportedGadShapes. setDescription("LCS-Supported-GAD-Shapes");
            lcsSupportedGadShapes.setFlags(new AVPFlag());
            lcsSupportedGadShapes.getFlags().setMandatory(true);
            lcsSupportedGadShapes.getFlags().setVendorSpecific(true);
            lcsSupportedGadShapes.getFlags().setProtect(false);

            lcsSupportedGadShapes.setVendorId(10415);
            lcsSupportedGadShapes.setValueType("integer");
            lcsSupportedGadShapes.setValue(inputNode);

            return  lcsSupportedGadShapes;
        }
        return null;
    }

    @Override
    public AVP lcsPrivacyCheckNonSession(String client, LcsPrivacyCheckNonSession inputNode) {

        if(inputNode!= null){

            List<AVP> avpChildList = new ArrayList<AVP>();

            AVP lcsPrivacyCheckNonSession = new AVP();

            lcsPrivacyCheckNonSession.setCode(AVPCode.LCS_PRIVACY_CHECK_NON_SESSION.getCode());
            lcsPrivacyCheckNonSession. setDescription("LCS-Privacy-Check-Non-Session");
            lcsPrivacyCheckNonSession.setFlags(new AVPFlag());
            lcsPrivacyCheckNonSession.getFlags().setMandatory(true);
            lcsPrivacyCheckNonSession.getFlags().setVendorSpecific(true);
            lcsPrivacyCheckNonSession.getFlags().setProtect(false);

            lcsPrivacyCheckNonSession.setVendorId(10415);
            lcsPrivacyCheckNonSession.setValueType("grouped");

            //LCS-Privacy-Check
            Integer lcsPrivacyCheckInputNode = inputNode.getLcsPrivacyCheck();
            if(lcsPrivacyCheckInputNode!=null){
                avpChildList.add(lcsPrivacyCheck(client,lcsPrivacyCheckInputNode));
            }

            if(avpChildList.size() > 0){
                lcsPrivacyCheckNonSession.setValue(avpChildList);
            }

            return  lcsPrivacyCheckNonSession;

        }
        return null;
    }

    @Override
    public AVP lcsPrivacyCheck(String client, Integer inputNode) {
        Integer lcsPrivacyCheckInputNode = inputNode;

        if(lcsPrivacyCheckInputNode!=null){

            int lcsPrivacyCheckInt = lcsPrivacyCheckInputNode;

            AVP lcsPrivacyCheck = new AVP();

            lcsPrivacyCheck.setCode(AVPCode.LCS_PRIVACY_CHECK.getCode());
            lcsPrivacyCheck. setDescription("LCS-Privacy-Check");
            lcsPrivacyCheck.setFlags(new AVPFlag());
            lcsPrivacyCheck.getFlags().setMandatory(true);
            lcsPrivacyCheck.getFlags().setVendorSpecific(true);
            lcsPrivacyCheck.getFlags().setProtect(false);

            lcsPrivacyCheck.setVendorId(10415);
            lcsPrivacyCheck.setValueType("integer");
            lcsPrivacyCheck.setValue(lcsPrivacyCheckInt);

            return  lcsPrivacyCheck;
        }
        return null;
    }

    @Override
    public AVP publicIdentity(String client, String inputNode) {
        String publicIdentityValue = inputNode;

        if(publicIdentityValue!= null) {
            AVP publicIdentity = new AVP();

            publicIdentity.setCode(AVPCode.PUBLIC_IDENTITY.getCode());
            publicIdentity. setDescription("Public-Identity");
            publicIdentity.setFlags(new AVPFlag());
            publicIdentity.getFlags().setMandatory(true);
            publicIdentity.getFlags().setVendorSpecific(true);
            publicIdentity.getFlags().setProtect(false);

            publicIdentity.setVendorId(0);
            publicIdentity.setValueType("string");
            publicIdentity.setValue(publicIdentityValue);

            return  publicIdentity;
        }
        return null;
    }

    @Override
    public AVP drmp(String client, Integer inputNode) {

        if(inputNode!= null ) {

            AVP drmp = new AVP();

            drmp.setCode(AVPCode.DRMP.getCode());
            drmp. setDescription("DRMP");
            drmp.setFlags(new AVPFlag());
            drmp.getFlags().setMandatory(false);
            drmp.getFlags().setVendorSpecific(false);
            drmp.getFlags().setProtect(false);

            drmp.setVendorId(0);
            drmp.setValueType("integer");
            drmp.setValue(inputNode);

            return drmp;
        }
        return null;
    }
    @Override
    public List<AVP> userIdentity(String client, UserIdentity inputNode) {
        List<AVP> avpList = new ArrayList<AVP>();

        AVP userIdentity = new AVP();

        userIdentity.setCode(AVPCode.USER_IDENTITY.getCode());
        userIdentity.setDescription("User-Identity");
        userIdentity.setFlags(new AVPFlag());
        userIdentity.getFlags().setMandatory(true);
        userIdentity.getFlags().setVendorSpecific(true);
        userIdentity.getFlags().setProtect(false);

        userIdentity.setVendorId(10415);
        userIdentity.setValueType("grouped");

        List<AVP> avpChildList = new ArrayList<AVP>();
        String msisdn = inputNode.getMsisdn();
        String publicIdentity = inputNode.getPublicIdentity();

        //MSISDN
        if (msisdn != null) {
            avpChildList.add(msisdn(client, msisdn));
        }

        //Public-Identity
        if (publicIdentity != null) {
            avpChildList.add(publicIdentity(client, publicIdentity));
        }

        if (avpChildList.size() > 0) {
            userIdentity.setValue(avpChildList);
            avpList.add(userIdentity);
        }




        if (avpList.size() > 0) {
            return avpList;
        } else {
            return null;
        }


    }

    @Override
    public AVP serverName(String client, String inputNode) {

        if (inputNode!=null) {

            AVP serverName = new AVP();

            serverName.setCode(AVPCode.SERVER_NAME.getCode());
            serverName. setDescription("Server-Name");
            serverName.setFlags(new AVPFlag());
            serverName.getFlags().setMandatory(true);
            serverName.getFlags().setVendorSpecific(true);
            serverName.getFlags().setProtect(false);

            serverName.setVendorId(10415);
            serverName.setValueType("string");
            serverName.setValue(inputNode);

            return serverName;
        }
        return null;
    }

    @Override
    public List<AVP> dataReference(String client, Integer[] inputNode) {
        List<Integer> inputList = new ArrayList<Integer>();
        if (inputNode != null) {
            inputList.addAll(Arrays.asList(inputNode));
            }else{
            for(int data : JdiameterProperties.getDataReference()){
                inputList.add(data);
            }

        }

        List<AVP> avpList = new ArrayList<AVP>();
        for (Integer input : inputList) {

            AVP dataReference = new AVP();

            dataReference.setCode(AVPCode.DATA_REFERENCE.getCode());
            dataReference. setDescription("Data-Reference");
            dataReference.setFlags(new AVPFlag());
            dataReference.getFlags().setMandatory(true);
            dataReference.getFlags().setVendorSpecific(true);
            dataReference.getFlags().setProtect(false);

            dataReference.setVendorId(10415);
            dataReference.setValueType("integer");
            dataReference.setValue(input);

            avpList.add(dataReference);
        }
        return avpList;
    }

    private static AVP authApplicationIdChild(String client, String authApplicationIdItem) {

        AVP authApplicationIdChild = new AVP();

        authApplicationIdChild.setCode(AVPCode.AUTH_APPLICATION_ID.getCode());
        authApplicationIdChild.setDescription("Auth-Application-Id");
        authApplicationIdChild.setFlags(new AVPFlag());
        authApplicationIdChild.getFlags().setMandatory(true);

        if(client.toLowerCase().contains(Client.BIK)){
            authApplicationIdChild.getFlags().setVendorSpecific(false);
        }
        else {
            authApplicationIdChild.getFlags().setVendorSpecific(true);
        }
        authApplicationIdChild.getFlags().setProtect(false);

        authApplicationIdChild.setVendorId(0);

        authApplicationIdChild.setValueType("integer");
        authApplicationIdChild.setValue(Integer.parseInt(authApplicationIdItem));

        return authApplicationIdChild;

    }

    private static AVP acctApplicationIdChild(String client, String acctApplicationId) {

        AVP acctApplicationIdChild = new AVP();

        acctApplicationIdChild.setCode(AVPCode.AUTH_APPLICATION_ID.getCode());
        acctApplicationIdChild.setDescription("Acct-Application-Id");
        acctApplicationIdChild.setFlags(new AVPFlag());
        acctApplicationIdChild.getFlags().setMandatory(true);
        acctApplicationIdChild.getFlags().setVendorSpecific(true);
        acctApplicationIdChild.getFlags().setProtect(false);

        acctApplicationIdChild.setVendorId(0);

        acctApplicationIdChild.setValueType("integer");
        acctApplicationIdChild.setValue(Integer.parseInt(acctApplicationId));

        return acctApplicationIdChild;

    }

    @Override
    public AVP RAT_Type(String client, Integer RATType) {
        if (RATType!=null) {
            AVP RATTypeNode = new AVP();
            RATTypeNode.setCode(AVPCode.RAT_TYPE.getCode());
            RATTypeNode.setDescription("RAT-Type");

            AVPFlag avpFlagNode2 =  new AVPFlag();
            avpFlagNode2.setMandatory(true);
            avpFlagNode2.setVendorSpecific(true);
            avpFlagNode2.setProtect(false);

            RATTypeNode.setFlags(avpFlagNode2);
            RATTypeNode.setVendorId(10415);
            RATTypeNode.setValueType("integer");
            RATTypeNode.setValue(RATType);
            return RATTypeNode;
        }
        return null;
    }

    @Override
    public AVP resultCode(String client) {
            AVP resultCode = new AVP();

            resultCode.setCode(AVPCode.RESULT_CODE.getCode());
            resultCode.setDescription("Result-Code");
            resultCode.setFlags(new AVPFlag());
            resultCode.getFlags().setMandatory(true);
            resultCode.getFlags().setVendorSpecific(false);
            resultCode.getFlags().setProtect(false);

            resultCode.setVendorId(0);

            resultCode.setValueType("integer");
            resultCode.setValue(2001);

            return resultCode;
        }

    @Override
    public AVP ecgi(String client, String inputNode) {
        if (inputNode != null) {
            AVP ecgi = new AVP();

            ecgi.setCode(AVPCode.ECGI.getCode());
            ecgi.setDescription("ECGI");
            ecgi.setFlags(new AVPFlag());
            ecgi.getFlags().setMandatory(true);
            ecgi.getFlags().setVendorSpecific(true);
            ecgi.getFlags().setProtect(false);

            ecgi.setVendorId(10415);

            ecgi.setValueType("byteString");
            ecgi.setValue(inputNode);

            return ecgi;
        }
        return null;
    }

    @Override
    public AVP userDataSH(String client, String inputNode) {
        if (inputNode != null) {
            AVP ecgi = new AVP();

            ecgi.setCode(AVPCode.USER_DATA_SH.getCode());
            ecgi.setDescription("ECGI");
            ecgi.setFlags(new AVPFlag());
            ecgi.getFlags().setMandatory(true);
            ecgi.getFlags().setVendorSpecific(true);
            ecgi.getFlags().setProtect(false);

            ecgi.setVendorId(10415);

            ecgi.setValueType("byteString");
            ecgi.setValue(inputNode);

            return ecgi;
        }
        return null;
    }
    @Override
    public AVP accuracyFulfilmentIndicator(String client, String inputNode) {
        if (inputNode != null) {
            AVP accuracyFulfilmentIndicator = new AVP();

            accuracyFulfilmentIndicator.setCode(AVPCode.ACCURACY_FULFILLMENT_INDICATOR.getCode());
            accuracyFulfilmentIndicator.setDescription("Accuracy-Fulfilment-Indicator");
            accuracyFulfilmentIndicator.setFlags(new AVPFlag());
            accuracyFulfilmentIndicator.getFlags().setMandatory(false);
            accuracyFulfilmentIndicator.getFlags().setVendorSpecific(false);
            accuracyFulfilmentIndicator.getFlags().setProtect(false);

            accuracyFulfilmentIndicator.setVendorId(10415);

            accuracyFulfilmentIndicator.setValueType("string");
            accuracyFulfilmentIndicator.setValue(inputNode);

            return accuracyFulfilmentIndicator;
        }
        return null;
    }

    @Override
    public AVP ageOfLocationEstimate(String client, Integer inputNode) {
        if (inputNode != null) {
            AVP ageOfLocationEstimate = new AVP();

            ageOfLocationEstimate.setCode(AVPCode.AGE_OF_LOCATION_ESTIMATE.getCode());
            ageOfLocationEstimate.setDescription("Age-Of-Location-Estimate");
            ageOfLocationEstimate.setFlags(new AVPFlag());
            ageOfLocationEstimate.getFlags().setMandatory(false);
            ageOfLocationEstimate.getFlags().setVendorSpecific(true);
            ageOfLocationEstimate.getFlags().setProtect(false);

            ageOfLocationEstimate.setVendorId(10415);

            ageOfLocationEstimate.setValueType("integer");
            ageOfLocationEstimate.setValue(inputNode);

            return ageOfLocationEstimate;
        }
        return null;
    }

    @Override
    public AVP velocityEstimate(String client, String inputNode) {
        if (inputNode != null) {
            AVP velocityEstimate = new AVP();

            velocityEstimate.setCode(AVPCode.VELOCITY_ESTIMATE.getCode());
            velocityEstimate.setDescription("Velocity-Estimate");
            velocityEstimate.setFlags(new AVPFlag());
            velocityEstimate.getFlags().setMandatory(true);
            velocityEstimate.getFlags().setVendorSpecific(true);
            velocityEstimate.getFlags().setProtect(false);

            velocityEstimate.setVendorId(10415);

            velocityEstimate.setValueType("string");
            velocityEstimate.setValue(inputNode);

            return velocityEstimate;
        }
        return null;
    }

    @Override
    public AVP eutranPositioningData(String client, String inputNode) {
        if (inputNode != null) {
            AVP eutranPositioningData = new AVP();

            eutranPositioningData.setCode(AVPCode.EUTRAN_POSITIONING_DATA.getCode());
            eutranPositioningData.setDescription("EUTRAN-Positioning-Data");
            eutranPositioningData.setFlags(new AVPFlag());
            eutranPositioningData.getFlags().setMandatory(false);
            eutranPositioningData.getFlags().setVendorSpecific(false);
            eutranPositioningData.getFlags().setProtect(false);

            eutranPositioningData.setVendorId(10415);

            eutranPositioningData.setValueType("string");
            eutranPositioningData.setValue(inputNode);

            return eutranPositioningData;
        }
        return null;
    }

    @Override
    public AVP locationEstimate(String client, String inputNode) {
        if (inputNode != null) {
            AVP locationEstimate = new AVP();

            locationEstimate.setCode(AVPCode.LOCATION_ESTIMATE.getCode());
            locationEstimate.setDescription("Location-Estimate");
            locationEstimate.setFlags(new AVPFlag());
            locationEstimate.getFlags().setMandatory(true);
            locationEstimate.getFlags().setVendorSpecific(true);
            locationEstimate.getFlags().setProtect(false);

            locationEstimate.setVendorId(10415);

            locationEstimate.setValueType("base64");
            locationEstimate.setValue(inputNode);

            return locationEstimate;
        }
        return null;
    }

    @Override
    public AVP servingNode(String client, ServingNode inputNode) {
        if (inputNode != null) {

            List<AVP> avpChildList = new ArrayList<AVP>();

            AVP servingNode = new AVP();

            servingNode.setCode(AVPCode.SERVING_NODE.getCode());
            servingNode.setDescription("Serving-Node");
            servingNode.setFlags(new AVPFlag());
            servingNode.getFlags().setMandatory(true);
            servingNode.getFlags().setVendorSpecific(true);
            servingNode.getFlags().setProtect(false);

            servingNode.setVendorId(10415);
            servingNode.setValueType("grouped");

            //TODO
            //MME-Name
            String mmeName = inputNode.getMmeName();
            if (mmeName != null) {
                avpChildList.add(mmeName(client, mmeName));
            }
            //MME-Realm
            String mmeRealm = inputNode.getMmeRealm();
            if (mmeRealm != null) {
                avpChildList.add(mmeRealm(client, mmeRealm));
            }

            if (avpChildList.size() > 0) {
                servingNode.setValue(avpChildList);
            }
            return servingNode;
        }

        return null;
    }

    @Override
    public AVP additionalServingNode(String client, AdditionalServingNode inputNode) {
        if (inputNode != null) {

            List<AVP> avpChildList = new ArrayList<AVP>();

            AVP additionalServingNode = new AVP();

            additionalServingNode.setCode(AVPCode.ADDITIONAL_SERVING_NODE.getCode());
            additionalServingNode.setDescription("Additional-Serving-Node");
            additionalServingNode.setFlags(new AVPFlag());
            additionalServingNode.getFlags().setMandatory(true);
            additionalServingNode.getFlags().setVendorSpecific(true);
            additionalServingNode.getFlags().setProtect(false);

            additionalServingNode.setVendorId(10415);
            additionalServingNode.setValueType("grouped");

            //TODO
            //MSC-Number
            String mscNumber = inputNode.getMscNumber();
            if (mscNumber != null) {
                avpChildList.add(mscNumber(client, mscNumber));
            }
            //LCS-CAPABILITIES-SET
            Integer lcsCapabilitiesSet = inputNode.getLcsCapabilitiesSet();
            if (lcsCapabilitiesSet != null) {
                avpChildList.add(lcsCapabilitiesSet(client, lcsCapabilitiesSet));
            }

            if (avpChildList.size() > 0) {
                additionalServingNode.setValue(avpChildList);
            }
            return additionalServingNode;
        }

        return null;
    }

    @Override
    public AVP mmeName(String client, String inputNode) {
        if (inputNode != null) {
            AVP mmeName = new AVP();

            mmeName.setCode(AVPCode.MME_NAME.getCode());
            mmeName.setDescription("MME-Name");
            mmeName.setFlags(new AVPFlag());
            mmeName.getFlags().setMandatory(true);
            mmeName.getFlags().setVendorSpecific(true);
            mmeName.getFlags().setProtect(false);

            mmeName.setVendorId(10415);

            mmeName.setValueType("string");
            mmeName.setValue(inputNode);

            return mmeName;
        }
        return null;
    }

    @Override
    public AVP mmeRealm(String client, String inputNode) {
        if (inputNode != null) {
            AVP mmeRealm = new AVP();

            mmeRealm.setCode(AVPCode.MME_REALM.getCode());
            mmeRealm.setDescription("MME-Realm");
            mmeRealm.setFlags(new AVPFlag());
            mmeRealm.getFlags().setMandatory(false);
            mmeRealm.getFlags().setVendorSpecific(true);
            mmeRealm.getFlags().setProtect(false);

            mmeRealm.setVendorId(10415);

            mmeRealm.setValueType("string");
            mmeRealm.setValue(inputNode);

            return mmeRealm;
        }
        return null;
    }

    @Override
    public AVP mmeNumber(String client, String inputNode) {
        if (inputNode != null) {
            AVP mmeName = new AVP();

            mmeName.setCode(AVPCode.MME_NUMBER.getCode());
            mmeName.setDescription("MME-Number");
            mmeName.setFlags(new AVPFlag());
            mmeName.getFlags().setMandatory(true);
            mmeName.getFlags().setVendorSpecific(true);
            mmeName.getFlags().setProtect(false);

            mmeName.setVendorId(10415);

            mmeName.setValueType("string");
            mmeName.setValue(inputNode);

            return mmeName;
        }
        return null;
    }


    @Override
    public AVP mmeAbsentUserDiagnosticSm(String client, Integer inputNode) {
        if (inputNode != null) {
            AVP mmeName = new AVP();

            mmeName.setCode(AVPCode.MME_ABSENT_USER_DIAGNOSTIC_SM.getCode());
            mmeName.setDescription("MME_ABSENT_USER_DIAGNOSTIC_SM");
            mmeName.setFlags(new AVPFlag());
            mmeName.getFlags().setMandatory(true);
            mmeName.getFlags().setVendorSpecific(true);
            mmeName.getFlags().setProtect(false);

            mmeName.setVendorId(10415);

            mmeName.setValueType("integer");
            mmeName.setValue(inputNode);

            return mmeName;
        }
        return null;
    }


    @Override
    public AVP sgsnName(String client, String inputNode) {
        if (inputNode != null) {
            AVP sgsnName = new AVP();

            sgsnName.setCode(AVPCode.SGSN_NAME.getCode());
            sgsnName.setDescription("SGSN-Name");
            sgsnName.setFlags(new AVPFlag());
            sgsnName.getFlags().setMandatory(true);
            sgsnName.getFlags().setVendorSpecific(true);
            sgsnName.getFlags().setProtect(false);

            sgsnName.setVendorId(10415);

            sgsnName.setValueType("string");
            sgsnName.setValue(inputNode);

            return sgsnName;
        }
        return null;
    }


    @Override
    public AVP sgsnRealm(String client, String inputNode) {
        if (inputNode != null) {
            AVP sgsnRealm = new AVP();

            sgsnRealm.setCode(AVPCode.SGSN_REALM.getCode());
            sgsnRealm.setDescription("SGSN-Realm");
            sgsnRealm.setFlags(new AVPFlag());
            sgsnRealm.getFlags().setMandatory(true);
            sgsnRealm.getFlags().setVendorSpecific(true);
            sgsnRealm.getFlags().setProtect(false);

            sgsnRealm.setVendorId(10415);

            sgsnRealm.setValueType("string");
            sgsnRealm.setValue(inputNode);

            return sgsnRealm;
        }
        return null;
    }


    @Override
    public AVP sgsnNumber(String client, String inputNode) {
        if (inputNode != null) {
            AVP mmeName = new AVP();

            mmeName.setCode(AVPCode.SGSN_NUMBER.getCode());
            mmeName.setDescription("SGSN-Number");
            mmeName.setFlags(new AVPFlag());
            mmeName.getFlags().setMandatory(true);
            mmeName.getFlags().setVendorSpecific(true);
            mmeName.getFlags().setProtect(false);

            mmeName.setVendorId(10415);

            mmeName.setValueType("string");
            mmeName.setValue(inputNode);

            return mmeName;
        }
        return null;
    }

    @Override
    public AVP MMENumberforMTSMS(String client, String inputNode) {
        if (inputNode != null) {
            AVP mmeNumberforMTSMS = new AVP();

            mmeNumberforMTSMS.setCode(AVPCode.MME_NUMBER_FOR_MT_SMS.getCode());
            mmeNumberforMTSMS.setDescription("MME-Number-for-MT-SMS");
            mmeNumberforMTSMS.setFlags(new AVPFlag());
            mmeNumberforMTSMS.getFlags().setMandatory(false);
            mmeNumberforMTSMS.getFlags().setVendorSpecific(true);
            mmeNumberforMTSMS.getFlags().setProtect(false);

            mmeNumberforMTSMS.setVendorId(10415);

            mmeNumberforMTSMS.setValueType("string");
            mmeNumberforMTSMS.setValue(inputNode);

            return mmeNumberforMTSMS;
        }
        return null;
    }

    @Override
    public AVP sgsnAbsentUserDiagnosticSm(String client, Integer inputNode) {
        if (inputNode != null) {
            AVP mmeName = new AVP();

            mmeName.setCode(AVPCode.SGSN_ABSENT_USER_DIAGNOSTIC_SM.getCode());
            mmeName.setDescription("SGSN_ABSENT_USER_DIAGNOSTIC_SM");
            mmeName.setFlags(new AVPFlag());
            mmeName.getFlags().setMandatory(true);
            mmeName.getFlags().setVendorSpecific(true);
            mmeName.getFlags().setProtect(false);

            mmeName.setVendorId(10415);

            mmeName.setValueType("integer");
            mmeName.setValue(inputNode);

            return mmeName;
        }
        return null;
    }


    @Override
    public AVP mscNumber(String client, String inputNode) {
        if (inputNode != null) {
            AVP mscNumber = new AVP();

            mscNumber.setCode(AVPCode.MSC_NUMBER.getCode());
            mscNumber.setDescription("MSC-Number");
            mscNumber.setFlags(new AVPFlag());
            mscNumber.getFlags().setMandatory(true);
            mscNumber.getFlags().setVendorSpecific(true);
            mscNumber.getFlags().setProtect(false);

            mscNumber.setVendorId(10415);

            mscNumber.setValueType("string");
            mscNumber.setValue(inputNode);

            return mscNumber;
        }
        return null;
    }
    @Override
    public AVP mscAbsentUserDiagnosticSm(String client, Integer inputNode) {
        if (inputNode != null) {
            AVP mmeName = new AVP();

            mmeName.setCode(AVPCode.MSC_ABSENT_USER_DIAGNOSTIC_SM.getCode());
            mmeName.setDescription("MSC_ABSENT_USER_DIAGNOSTIC_SM");
            mmeName.setFlags(new AVPFlag());
            mmeName.getFlags().setMandatory(true);
            mmeName.getFlags().setVendorSpecific(true);
            mmeName.getFlags().setProtect(false);

            mmeName.setVendorId(10415);

            mmeName.setValueType("integer");
            mmeName.setValue(inputNode);

            return mmeName;
        }
        return null;
    }

    @Override
    public AVP limsi(String client, String inputNode) {
        if (inputNode != null) {
            AVP mscNumber = new AVP();

            mscNumber.setCode(AVPCode.LMSI.getCode());
            mscNumber.setDescription("LIMSI");
            mscNumber.setFlags(new AVPFlag());
            mscNumber.getFlags().setMandatory(true);
            mscNumber.getFlags().setVendorSpecific(true);
            mscNumber.getFlags().setProtect(false);

            mscNumber.setVendorId(10415);

            mscNumber.setValueType("string");
            mscNumber.setValue(inputNode);

            return mscNumber;
        }
        return null;
    }

    @Override
    public AVP mwdStatus(String client, Integer inputNode) {
        if (inputNode != null) {
            AVP mscNumber = new AVP();

            mscNumber.setCode(AVPCode.MWD_STATUS.getCode());
            mscNumber.setDescription("MWD-Status");
            mscNumber.setFlags(new AVPFlag());
            mscNumber.getFlags().setMandatory(true);
            mscNumber.getFlags().setVendorSpecific(true);
            mscNumber.getFlags().setProtect(false);

            mscNumber.setVendorId(10415);

            mscNumber.setValueType("integer");
            mscNumber.setValue(inputNode);

            return mscNumber;
        }
        return null;
    }


    @Override
    public AVP lcsCapabilitiesSet(String client, Integer inputNode) {
        if (inputNode != null) {
            AVP lcsCapabilitiesSet =new AVP();

            lcsCapabilitiesSet.setCode(AVPCode.LCS_CAPABILITIES_SET.getCode());
            lcsCapabilitiesSet.setDescription("LCS-Capabilities-Set");
            lcsCapabilitiesSet.setFlags(new AVPFlag());
            lcsCapabilitiesSet.getFlags().setMandatory(true);
            lcsCapabilitiesSet.getFlags().setVendorSpecific(true);
            lcsCapabilitiesSet.getFlags().setProtect(false);

            lcsCapabilitiesSet.setVendorId(10415);

            lcsCapabilitiesSet.setValueType("integer");
            lcsCapabilitiesSet.setValue(inputNode);

            return lcsCapabilitiesSet;
        }
        return null;
    }

    @Override
    public AVP requestedNode(String client, Integer inputNode) {

        Integer requestednodevalue = JdiameterProperties.getRequestNode();
        if (inputNode!=null) {
            requestednodevalue=inputNode;
        }


        AVP requestedNode = new AVP();

        requestedNode.setCode(AVPCode.REQUESTED_NODES.getCode());
        requestedNode.setDescription("Acct-Application-Id");

        AVPFlag avpFlagNode8 = new AVPFlag();
        avpFlagNode8.setMandatory(false);
        avpFlagNode8.setVendorSpecific(true);
        avpFlagNode8.setProtect(false);
        requestedNode.setFlags(avpFlagNode8);

        requestedNode.setVendorId(10415);
        requestedNode.setValueType("integer");
        requestedNode.setValue(requestednodevalue);

        return requestedNode;
    }

    @Override
    public AVP requestedDomain(String client, Integer inputNode) {
        Integer requestDomain = JdiameterProperties.getRequestedDomain();
        if (inputNode != null) {
            requestDomain = inputNode;
        }
        if (requestDomain !=null) {
            AVP dataReff = new AVP();

            dataReff.setCode(AVPCode.REQUESTED_DOMAIN.getCode());
            dataReff.setDescription("Requested-Domain");

            AVPFlag avpFlagNode8 = new AVPFlag();
            avpFlagNode8.setMandatory(true);
            avpFlagNode8.setVendorSpecific(true);
            avpFlagNode8.setProtect(false);
            dataReff.setFlags(avpFlagNode8);

            dataReff.setVendorId(10415);
            dataReff.setValueType("integer");
            dataReff.setValue(requestDomain);
            return dataReff;
        }
        return null;

    }

    @Override
    public AVP currentLocation(String client, Integer inputNode) {
        Integer currentLocation = JdiameterProperties.getCurrentLocation();
        if (inputNode!=null){
            currentLocation= inputNode;
        }
        if (currentLocation!=null){
            AVP dataReff = new AVP();

            dataReff.setCode(AVPCode.CURRENT_LOCATION.getCode());
            dataReff.setDescription("Current-Location");

            AVPFlag avpFlagNode8 = new AVPFlag();
            avpFlagNode8.setMandatory(true);
            avpFlagNode8.setVendorSpecific(true);
            avpFlagNode8.setProtect(false);
            dataReff.setFlags(avpFlagNode8);

            dataReff.setVendorId(10415);
            dataReff.setValueType("integer");
            dataReff.setValue(currentLocation);
            return dataReff;
        }
        return null;
    }

    @Override
    public AVP visitedPLMN(String client, String mcc,String mnc){
        if(mcc!= null && mnc!=null){
            String plmnID= AVPUtils.getPLMNID(mcc,mnc);

            AVP visitedPLMNId = new AVP();
            visitedPLMNId.setCode(1407);
            visitedPLMNId.setDescription("Visited-PLMN-Id");

            AVPFlag avpFlagNode2 =  new AVPFlag();
            avpFlagNode2.setMandatory(true);
            avpFlagNode2.setVendorSpecific(true);
            avpFlagNode2.setProtect(false);

            visitedPLMNId.setFlags(avpFlagNode2);
            visitedPLMNId.setVendorId(10415);
            visitedPLMNId.setValueType("OctetString");

            visitedPLMNId.setValue(plmnID);
            return visitedPLMNId;
        }
        return null;
    }


    @Override
    public AVP ulrFlags(String client, Integer ULR_Flags) {
        if(ULR_Flags!=null){
            AVP ulrFlagsNode = new AVP();
            ulrFlagsNode.setCode(1405);
            ulrFlagsNode.setDescription("ULR-Flags");
            AVPFlag avpFlagNode2 =  new AVPFlag();
            avpFlagNode2.setMandatory(true);
            avpFlagNode2.setVendorSpecific(true);
            avpFlagNode2.setProtect(false);
            ulrFlagsNode.setFlags(avpFlagNode2);
            ulrFlagsNode.setVendorId(10415);
            ulrFlagsNode.setValueType("integer");
            ulrFlagsNode.setValue(ULR_Flags);
            return ulrFlagsNode;

        }
        return null;
    }
}
