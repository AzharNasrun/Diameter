package psl;

import com.firstwap.jdiameter.Properties.JdiameterProperties;
import constant.Constants;
import model.AVP;
import model.Diameter;
import model.DiameterFlag;
import org.apache.log4j.Logger;
import psl.model.request.PSL.Param;
import psl.model.request.PSLRequest;
import service.avp.AVPUtils;
import service.avp.AVPGenerator;
import service.avp.AVPGeneratorImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gede on 09/03/18.
 */
public class PSLMain {


    private static final Logger logger = Logger.getLogger(Constants.API_LOG);



    public static Diameter createMessage(PSLRequest inputNode, int sessionId, int transactionId) {

        String client = JdiameterProperties.getClient();
        Diameter diameter = new Diameter();
        List<AVP> avpList = new ArrayList<AVP>();
        Param param =  inputNode.getParam();
        try {

        diameter.setVersion(1);
        diameter.setType(Constants.PSL_CONST);
        diameter.setFlags(new DiameterFlag());
        diameter.getFlags().setRequest(true);
        diameter.getFlags().setProxyable(true);
        diameter.getFlags().setError(false);
        diameter.getFlags().setTransmitted(false);

        diameter.setCommandCode(8388620);
        diameter.setApplicationId(16777255);
        diameter.setEndToEndId(transactionId);

        AVPGenerator avpGenerator = new AVPGeneratorImpl();

        // session-id
        AVPUtils.addAvp(avpList,avpGenerator.sessionId(client,sessionId));

        // auth session state
        AVPUtils.addAvp(avpList,avpGenerator.authSessionState(client));

        // Origin-Host
        AVPUtils.addAvp(avpList,avpGenerator.originHost(client));

        // Origin-Realm
        AVPUtils.addAvp(avpList,avpGenerator.originRealm(client));

        // Destination-Realm
        AVPUtils.addAvp(avpList,avpGenerator.destinationRealm(client,param.getDestinationRealm()));

        // Destination-Host
        AVPUtils.addAvp(avpList,avpGenerator.destinationHost(client,param.getDestinationHost()));

        // SLg-Location-Type
        AVPUtils.addAvp(avpList,avpGenerator.sLgLocationType(client,param.getSlgLocationType()));

        String msisdn = param.getMsisdn();
        if (msisdn != null) { //this logic to handle if request from api and msisdn is choosed
            AVPUtils.addAvp(avpList,avpGenerator.msisdn(client,msisdn));
        }
        else {
            AVPUtils.addAvp(avpList,avpGenerator.imsi(client,param.getImsi()));
        }

        // LCS-EPS-Client-Name
        AVPUtils.addAvp(avpList,avpGenerator.lcsEPSClientName(client,param.getLcsEpsClientName()));

        // LCS-Client-Type
        AVPUtils.addAvp(avpList,avpGenerator.lcsClientType(client,param.getLcsClientType()));

        // LCS-Priority
        AVPUtils.addAvp(avpList,avpGenerator.lcsPriority(client,param.getLcsPriority()));

        // LCS-QoS
        AVPUtils.addAvp(avpList,avpGenerator.lcsQoS(client,param.getLcsQos()));

        // LCS-Supported-GAD-Shapes
        AVPUtils.addAvp(avpList,avpGenerator.lcsSupportedGADShapes(client,param.getLcsSupportedGadShapes()));

        // LCS-Privacy-Check-Non-Session
        AVPUtils.addAvp(avpList,avpGenerator.lcsPrivacyCheckNonSession(client,param.getLcsPrivacyCheckNonSession()));


        diameter.setAvpList(avpList);

        }
        catch (Exception e){
            logger.info("error when populating psl message:" + e.getMessage());
            e.printStackTrace();
        }

        return diameter;
    }

}
