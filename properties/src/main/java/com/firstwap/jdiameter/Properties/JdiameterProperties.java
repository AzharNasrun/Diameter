package com.firstwap.jdiameter.Properties;

import akka.actor.ActorSystem;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by gede on 03/01/18.
 *environment
 * @author gede
 * @version 1.0
 * create for accomodating the properties value into memory
 */

@Component
public class JdiameterProperties {
    private final Logger logger = Logger.getLogger(JdiameterProperties.class);

    @Autowired
    private Environment env;
    private static JdiameterProperties instance = null;

    public ActorSystem actorSystem;

    @Value("${parameter.origin.host:localhost}")
    public String originHost;

    @Value("${sctp.destination.host}")
    public String destinationHost[];

    @Value("${parameter.origin.realm}")
    public String originRealm;

    @Value("${udr.currentLocation}")
    public Integer currentLocation;

    @Value("${udr.dataReference}")
    public int[] dataReference;

    @Value("${udr.requestedDomain}")
    public Integer requestedDomain;

    @Value("${dwr.periodic}")
    public int dwrPeriodic;

    @Value("${setMessageTimeout}")
    public Integer setMessageTimeout;

    @Value("${sctp.server.ip}")
    public String sctpServerIp;

    @Value("${sctp.server.port}")
    public String sctpServerPort;


    @Value("${cer.vendorId}")
    public int cerVendorId;

    @Value("${cer.productName}")
    public String cerProductName;

    @Value("${psl.slgLocationType}")
    public Integer slgLocationType;

    @Value("${psl.lcsEpsClientName.lcsNameString}")
    public String lcsEpsClientNameLcsNameString;

    @Value("${psl.lcsEpsClientName.lcsFormatIndicator}")
    public Integer lcsEpsClientNameLcsFormatIndicator;

    @Value("${psl.lcsRequestorName.IDString}")
    public String lcsRequestorNameIDString;

    @Value("${psl.lcsRequestorName.lcsformatIndicator}")
    public Integer lcsRequestorNameLcsFormatIndicator;

    @Value("${psl.lcsClientType}")
    public Integer lcsClientType;

    @Value("${psl.lcsPriority}")
    public Integer lcsPriority;

    @Value("${psl.lcsQosClass}")
    public Integer lcsQosClass;

    @Value("${psl.horizontalAccuracy}")
    public Integer horizontalAccuracy;

    @Value("${psl.verticalAccuracy}")
    public Integer verticalAccuracy;

    @Value("${psl.verticalRequested}")
    public Integer verticalRequested;

    @Value("${psl.responseTime}")
    public Integer responseTime;

    @Value("${psl.lcsSupportedGadShapes}")
    public Integer lcsSupportedGadShapes;

    @Value("${psl.session.lcsPrivacyCheck}")
    public Integer sessionLcsPrivacyCheck;

    @Value("${psl.nonsession.lcsPrivacyCheck}")
    public Integer nonSessionLcsPrivacyCheck;

    @Value("${client}")
    public String client;

    @Value("#{'${avp.type.integer}'.split(',')}")
    public List<String> avpTypeInteger;

    @Value("#{'${avp.type.string}'.split(',')}")
    public List<String> avpTypeString;

    @Value("#{'${avp.type.isdn}'.split(',')}")
    public List<String> avpTypeIsdn;

    @Value("#{'${avp.type.ipaddress}'.split(',')}")
    public List<String> avpTypeIpAddress;

    @Value("#{'${avp.type.grouped}'.split(',')}")
    public List<String> avpTypeGrouped;

    @Value("#{'${avp.type.locationEstimate}'.split(',')}")
    public List<String> avpTypeLocationEstimate;

    @Value("#{'${avp.type.shdata}'.split(',')}")
    public List<String> avpTypeSHDataUDR;

    @Value("#{'${avp.type.ecgi}'.split(',')}")
    public List<String> avpTypeECGI;

    @Value("${appId}")
    public String appId;

    @Value("${cer.auth.applicationId}")
    String[] listAuthApplicationId;

    @Value("${cer.inbandSecurityId}")
    String[] listInbandSecurityIds;

    @Value("${cer.acct.applicationId}")
    String[] listAcctApplicationIds;

    @Value("${sctp.default.host}")
    String sctpDefaultHost;

    @Value("${sctp.default.realm}")
    String sctpDefaultRealm;

    @Value("${udr.destinationRealm}")
    String udrDestinationRealm;

    @Value("${udr.requestedNode}")
    Integer requestNode;

    @PostConstruct
    public void postConstruct() {
        instance = this;
        actorSystem = ActorSystem.create("JdiameterActorSystem");

    }

    public static String getSctpDefaultRealm() {
        return instance.sctpDefaultRealm;
    }

    public static String getSctpDefaultHost() {
        return instance.sctpDefaultHost;
    }

    public static  String[] getListAcctApplicationIds() {
        return instance.listAcctApplicationIds;
    }

    public static String[] getListInbandSecurityIds() {
        return instance.listInbandSecurityIds;
    }

    public static String getString(String property){
    return  instance.env.getProperty(property);
    }

    public static String[] getListAuthApplicationId() {
        return instance.listAuthApplicationId;
    }

    public static String getAppId() {
        return instance.appId;
    }

    public static List<String> getAvpTypeECGI() {
        return instance.avpTypeECGI;
    }

    public static String getOriginHost() {
        return instance.originHost;
    }


    public static String getOriginRealm() {
        return instance.originRealm;
    }

    public static List<String> getAvpTypeLocationEstimate() {
        return instance.avpTypeLocationEstimate;
    }

    public static List<String> getAvpTypeSHDataUDR() {
        return instance.avpTypeSHDataUDR;
    }

    public static int getDwrPeriodic() {
        return instance.dwrPeriodic;
    }



    public static String getSctpServerIp() {
        return instance.sctpServerIp;
    }

    public static String getSctpServerPort() {
        return instance.sctpServerPort;
    }



    public static Integer getSessionLcsPrivacyCheck() {
        return instance.sessionLcsPrivacyCheck;
    }

    public static Integer getNonSessionLcsPrivacyCheck() {
        return instance.nonSessionLcsPrivacyCheck;
    }


    public void setSctpServerMode(String sctpServerMode) {
        sctpServerMode = sctpServerMode;
    }


    public static int getCerVendorId() {
        return instance.cerVendorId;
    }

    public void setCerVendorId(int cerVendorId) {
        cerVendorId = cerVendorId;
    }

    public static String getCerProductName() {
        return instance.cerProductName;
    }

    public static String getUdrDestinationRealm() {
        return instance.udrDestinationRealm;
    }

    public static Integer getRequestNode() {
        return instance.requestNode;
    }

    public static Integer getSlgLocationType() {
        return instance.slgLocationType;
    }


    public static ActorSystem getActorSystem() {
        return instance.actorSystem;
    }

    public static Integer getLcsClientType() {
        return instance.lcsClientType;
    }

    public void setLcsClientType(Integer lcsClientType) {
        lcsClientType = lcsClientType;
    }

    public static Integer getLcsPriority() {
        return instance.lcsPriority;
    }


    public static Integer getLcsQosClass() {
        return instance.lcsQosClass;
    }


    public static  Integer getHorizontalAccuracy() {
        return instance.horizontalAccuracy;
    }


    public static Integer getVerticalAccuracy() {
        return instance.verticalAccuracy;
    }


    public static Integer getVerticalRequested() {
        return instance.verticalRequested;
    }


    public static Integer getResponseTime() {
        return instance.responseTime;
    }


    public static Integer getLcsSupportedGadShapes() {
        return instance.lcsSupportedGadShapes;
    }


    public static List<String> getAvpTypeInteger() {
        return instance.avpTypeInteger;
    }


    public static String getLcsEpsClientNameLcsNameString() {
        return instance.lcsEpsClientNameLcsNameString;
    }

    public static Integer getLcsEpsClientNameLcsFormatIndicator() {
        return instance.lcsEpsClientNameLcsFormatIndicator;
    }

    public static String getLcsRequestorNameIDString() {
        return instance.lcsRequestorNameIDString;
    }

    public static Integer getLcsRequestorNameLcsFormatIndicator() {
        return instance.lcsRequestorNameLcsFormatIndicator;
    }

    public static List<String> getAvpTypeString() {
        return instance.avpTypeString;
    }

    public static List<String> getAvpTypeIsdn() {
        return instance.avpTypeIsdn;
    }

    public static List<String> getAvpTypeIpAddress() {
        return instance.avpTypeIpAddress;
    }

    public static List<String> getAvpTypeGrouped() {
        return instance.avpTypeGrouped;
    }

    public static Integer getSetMessageTimeout() {
        return instance.setMessageTimeout;
    }

    public static String getClient() {
        return instance.client;
    }


    public static Integer getCurrentLocation() {
        return instance.currentLocation;
    }

    public static int[] getDataReference() {
        return instance.dataReference;
    }

    public static Integer getRequestedDomain() {
        return instance.requestedDomain;
    }

    public static String[] getDestinationHost() {
        return instance.destinationHost;
    }

}
