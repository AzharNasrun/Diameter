package main;

import com.firstwap.jdiameter.Properties.JdiameterProperties;
import constant.Constants;
import model.AVP;
import model.AVPCode;
import model.Diameter;
import model.DiameterFlag;
import model.diameter.AdditionalServingNode;
import model.diameter.ServingNode;
import org.apache.log4j.Logger;
import service.avp.AVPUtils;
import service.avp.AVPGeneratorImpl;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SRILCSAnswer{
    private static final Logger logger = Logger.getLogger(Constants.API_LOG);

    public static Diameter createMessage(int sessionId,long trxId) {
        String client = JdiameterProperties.getClient();
        Diameter diameter = new Diameter();
        List<AVP> avpList = new ArrayList<AVP>();

        try {

            diameter.setVersion(1);
            diameter.setType(Constants.SRILCS_CONST);
            diameter.setFlags(new DiameterFlag());
            diameter.getFlags().setRequest(false);
            diameter.getFlags().setProxyable(true);
            diameter.getFlags().setError(false);
            diameter.getFlags().setTransmitted(false);

            diameter.setCommandCode(8388622);
            diameter.setApplicationId(16777291);
            diameter.setEndToEndId(trxId);
            AVPGeneratorImpl avpGenerator = new AVPGeneratorImpl();

            // session-id
            AVPUtils.addAvp(avpList,avpGenerator.sessionId(client,sessionId));

            // auth session state
            AVPUtils.addAvp(avpList,avpGenerator.authSessionState(client));

            // Origin-Host
            AVPUtils.addAvp(avpList,avpGenerator.originHost(client));

            // Origin-Realm
            AVPUtils.addAvp(avpList,avpGenerator.originRealm(client));

            // IMSI
            AVPUtils.addAvp(avpList,avpGenerator.imsi(client, ""+sessionId));

            // Serving-Node
            ServingNode servingNode = new ServingNode();
            servingNode.setMmeName("MME-JKT3.EPC.MNC001.MCC510.3GPPNETWORK.ORG");
            servingNode.setMmeRealm("EPC.MNC001.MCC510.3GPPNETWORK.ORG");
            AVPUtils.addAvp(avpList,avpGenerator.servingNode(client,servingNode));

            // Additional-Serving-Node
            AdditionalServingNode additionalServingNode = new AdditionalServingNode();
            additionalServingNode.setMscNumber("261806890900");
            additionalServingNode.setLcsCapabilitiesSet(1);
            AVPUtils.addAvp(avpList,avpGenerator.additionalServingNode(client,additionalServingNode));

            //result-code
            AVPUtils.addAvp(avpList,avpGenerator.resultCode(client) );

            diameter.setAvpList(avpList);

        } catch (Exception e) {

            logger.info("error when populating srilcs message:" + e.getMessage());
            e.printStackTrace();
        }

        return diameter;
    }
}
