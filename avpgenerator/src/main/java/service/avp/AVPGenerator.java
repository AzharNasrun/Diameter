package service.avp;

import com.fasterxml.jackson.databind.node.ObjectNode;
import model.AVP;
import model.diameter.AdditionalServingNode;
import model.diameter.ServingNode;
import model.ismscm.LcsEpsClientName;
import model.ismscm.LcsPrivacyCheckNonSession;
import model.ismscm.LcsQos;
import model.ismscm.UserIdentity;

import java.util.List;

/** This use for interface of registered the avp
 * Created by aji on 29/03/18.
 */
public interface AVPGenerator {

    public AVP originHost(String client);

    public AVP originRealm(String client);

    public AVP destinationRealm(String client, String inputNode);

    public AVP destinationHost(String client, String inputNode);

    public AVP msisdn(String client, String inputNode);

    public AVP imsi(String client, String inputNode);

    public AVP gmlcNumber(String client, String inputNode);

    public AVP vendorId(String client);

    public List<AVP> hostIPAddresses(String client, List<String> sourceAddress);

    public AVP productName(String client);

    public List<AVP> authApplicationId(String client);

    public AVP authSessionState(String client);

    public List<AVP> inbandSecurityId(String client);

    public List<AVP> acctApplicationId(String client);

    public List<AVP> vendorSpesificApplicationId(String client); //arrayNode

    public AVP sessionId(String client, Integer sessionId);


    //PSL
    public AVP sLgLocationType(String client, Integer inputNode);

    public AVP lcsEPSClientName(String client, LcsEpsClientName inputNode); //arrayNode

    public AVP lcsNameArray(String client, String inputNode);

    public AVP lcsFormatIndicator(String client, Integer inputNode);

    public AVP lcsClientType(String client, Integer inputNode);

    public AVP lcsPriority(String client, Integer inputNode);

    public AVP lcsQoS(String client, LcsQos inputNode); //arrayNode

    public AVP lcsQoSClass(String client, Integer inputNode);

    public AVP horizontalAccuracy(String client, Integer inputNode);

    public AVP verticalAccuracy(String client, Integer inputNode);

    public AVP verticalRequested(String client, Integer inputNode);

    public AVP responseTime(String client, Integer inputNode);

    public AVP lcsSupportedGADShapes(String client, Integer inputNode);

    public AVP lcsPrivacyCheckNonSession(String client, LcsPrivacyCheckNonSession inputNode); //arrayNode

    public AVP lcsPrivacyCheck(String client, Integer inputNode);

    public AVP publicIdentity(String client, String inputNode);

    //UDR
    public AVP drmp(String client, Integer inputNode);

    public List<AVP> userIdentity(String client, UserIdentity inputNode);

    public AVP serverName(String client, String inputNode);

    public List<AVP> dataReference(String client, Integer[] inputNode);

    public AVP requestedDomain(String client, Integer inputNode);

    public AVP requestedNode(String client, Integer inputNode);

    public AVP currentLocation(String client, Integer inputNode);

    public AVP scAddress(String client,String inputNode);


    public AVP smRpMti(String client, Integer inputNode);

    public AVP smRpSmea(String client, String inputNode);

    public AVP srrFlags(String client, Integer inputNode);

    public AVP sgsnName(String client, String inputNode);

    public AVP sgsnRealm(String client, String inputNode);

    public AVP sgsnNumber(String client, String inputNode);

    public AVP mmeNumber(String client, String inputNode);

    public AVP mmeAbsentUserDiagnosticSm(String client, Integer inputNode);


    public AVP mscAbsentUserDiagnosticSm(String client, Integer inputNode);


    public AVP sgsnAbsentUserDiagnosticSm(String client, Integer inputNode);



    public AVP limsi(String client, String inputNode);

    public AVP mwdStatus(String client, Integer inputNode);

    public AVP resultCode(String client);


    //Responder, be carefully!! all is a dummy data
    public AVP ecgi(String client, String ecgi);
    public AVP accuracyFulfilmentIndicator(String client, String inputNode);
    public AVP ageOfLocationEstimate(String client, Integer inputNode);
    public AVP velocityEstimate(String client, String inputNode);
    public AVP eutranPositioningData(String client, String inputNode);
    public AVP locationEstimate(String client, String inputNode); //locationEstimate
    public AVP userDataSH(String client, String inputNode);
    //TODO
    public AVP servingNode(String client, ServingNode inputNode);
    public AVP additionalServingNode(String client, AdditionalServingNode inputNode);
    public AVP mmeName(String client, String inputNode);
    public AVP mmeRealm(String client, String inputNode);
    public AVP mscNumber(String client, String inputNode);
    public AVP lcsCapabilitiesSet(String client, Integer inputNode);

    //ulr
    public AVP RAT_Type(String client, Integer inputNode);
    public AVP visitedPLMN(String client, String mcc,String mnc);
    public AVP ulrFlags(String client, Integer ULR_Flags);
    public AVP MMENumberforMTSMS(String client, String inputNode);
}
