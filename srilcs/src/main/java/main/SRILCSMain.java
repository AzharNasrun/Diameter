package main;

import com.firstwap.jdiameter.Properties.JdiameterProperties;
import constant.Constants;
import model.AVP;
import model.Diameter;
import model.DiameterFlag;
import model.SRILCS.SRILCSParam;
import model.SRILCSRequest;
import org.apache.log4j.Logger;
import service.avp.AVPUtils;
import service.avp.AVPGeneratorImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gede on 09/03/18.
 */
public class SRILCSMain {

    private static final Logger logger = Logger.getLogger(Constants.API_LOG);


    public static Diameter createMessage(SRILCSRequest model, int sessionId, int transactionId) {
        String client = JdiameterProperties.getClient();
        Diameter diameter = new Diameter();
        List<AVP> avpList = new ArrayList<AVP>();
        SRILCSParam srilcsParam = model.getParam();

        try {

            diameter.setVersion(1);
            diameter.setType(Constants.SRILCS_CONST);
            diameter.setFlags(new DiameterFlag());
            diameter.getFlags().setRequest(true);
            diameter.getFlags().setProxyable(true);
            diameter.getFlags().setError(false);
            diameter.getFlags().setTransmitted(false);

            diameter.setCommandCode(8388622);
            diameter.setApplicationId(16777291);
            diameter.setEndToEndId(transactionId);

            AVPGeneratorImpl avpGenerator = new AVPGeneratorImpl();


            // session-id
            AVPUtils.addAvp(avpList,avpGenerator.sessionId(client,sessionId));

            // auth session state
            AVPUtils.addAvp(avpList,avpGenerator.authSessionState(client));

            // Origin-Host
            AVPUtils.addAvp(avpList,avpGenerator.originHost(client));

            // Origin-Realm
            AVPUtils.addAvp(avpList,avpGenerator.originRealm(client));

            // Destination-Realm
            AVPUtils.addAvp(avpList,avpGenerator.destinationRealm(client, srilcsParam.getDestinationRealm()));

            // Destination-Host
            AVPUtils.addAvp(avpList,avpGenerator.destinationHost(client, srilcsParam.getDestinationHost()));

            // MSISDN
            AVPUtils.addAvp(avpList,avpGenerator.msisdn(client, srilcsParam.getMsisdn()));

            // IMSI
            AVPUtils.addAvp(avpList,avpGenerator.imsi(client, srilcsParam.getImsi()));

            // GMLC Number
            AVPUtils.addAvp(avpList,avpGenerator.gmlcNumber(client, srilcsParam.getGmlcNumber()));

            diameter.setAvpList(avpList);

        } catch (Exception e) {

            logger.info("error when populating srilcs message:" + e.getMessage());
            e.printStackTrace();
        }

        return diameter;
    }

}
