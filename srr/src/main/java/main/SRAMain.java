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
import service.avp.AVPGeneratorImpl;
import service.avp.AVPUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SRAMain {

    private static final Logger logger = Logger.getLogger(Constants.API_LOG);

    public static Diameter createMessage(int sessionId1, int transactionId) {
        String client = JdiameterProperties.getClient();
        Diameter diameter = new Diameter();
        List<AVP> avpList = new ArrayList<AVP>();

        try {

            diameter.setVersion(1);
            diameter.setType(Constants.SRA_CONST);

            DiameterFlag df = new DiameterFlag();
            diameter.setFlags(df);
            diameter.getFlags().setRequest(false);
            diameter.getFlags().setProxyable(true);
            diameter.getFlags().setError(false);
            diameter.getFlags().setTransmitted(false);

            diameter.setCommandCode(8388647);
            diameter.setApplicationId(16777312);
            diameter.setEndToEndId(transactionId);

            AVPGeneratorImpl avpGenerator = new AVPGeneratorImpl();
            Object sessionID = sessionId1;
            Integer sessionId = sessionId1;
            if (sessionID!=null&&sessionID.toString().trim().length()>0){
                sessionId = Integer.parseInt(sessionID.toString());
            }
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
            servingNode.setMmeNumber("MMENumber");

            servingNode.setSgsnName("SGSNName");
            servingNode.setSgsnRealm("SGSNRealm");
            servingNode.setSgsnNumber("SGSNNumber");

            servingNode.setMscNumber("MSCNumber");

            AVPUtils.addAvp(avpList,avpGenerator.servingNode(client,servingNode));

            // Additional-Serving-Node
            AdditionalServingNode additionalServingNode = new AdditionalServingNode();
            additionalServingNode.setMscNumber("261806890900");
            additionalServingNode.setLcsCapabilitiesSet(1);
            AVPUtils.addAvp(avpList,avpGenerator.additionalServingNode(client,additionalServingNode));


            //LIMSI
            AVPUtils.addAvp(avpList,avpGenerator.limsi(client,"123456"));


           //MWD-Status
            AVPUtils.addAvp(avpList,avpGenerator.mwdStatus(client,1));

           //MME diagnostic
            AVPUtils.addAvp(avpList,avpGenerator.mmeAbsentUserDiagnosticSm(client,1));

            //MSC diagnostic
            AVPUtils.addAvp(avpList,avpGenerator.mscAbsentUserDiagnosticSm(client,1));

            //SGSN diagnostic
            AVPUtils.addAvp(avpList,avpGenerator.sgsnAbsentUserDiagnosticSm(client,1));

            //result-code
            AVPUtils.addAvp(avpList,avpGenerator.resultCode(client) );

            diameter.setAvpList(avpList);

        } catch (Exception e) {

            logger.info("error when populating SRA message:" + e.getMessage());
            e.printStackTrace();
        }

        return diameter;
    }
}
