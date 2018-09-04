package psl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firstwap.jdiameter.Properties.JdiameterProperties;
import constant.Constants;
import model.AVP;
import model.AVPCode;
import model.Diameter;
import model.DiameterFlag;
import org.apache.log4j.Logger;
import service.avp.AVPUtils;
import service.avp.AVPGeneratorImpl;

import javax.xml.bind.DatatypeConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PSLAnswer {


    private static final Logger logger = Logger.getLogger(Constants.API_LOG);



    public static Diameter createMessage(int sessionId,long trxid) {

        String client = JdiameterProperties.getClient();
        Diameter diameter = new Diameter();
        List<AVP> avpList = new ArrayList<AVP>();
        try {

            diameter.setVersion(1);
            diameter.setType(Constants.PSL_CONST);
            diameter.setFlags(new DiameterFlag());
            diameter.getFlags().setRequest(false);
            diameter.getFlags().setProxyable(true);
            diameter.getFlags().setError(false);
            diameter.getFlags().setTransmitted(false);

            diameter.setCommandCode(8388620);
            diameter.setApplicationId(16777255);
            diameter.setEndToEndId(trxid);
            AVPGeneratorImpl avpGenerator = new AVPGeneratorImpl();

            // session-id
            AVPUtils.addAvp(avpList,avpGenerator.sessionId(client,sessionId));

            // auth session state
            AVPUtils.addAvp(avpList,avpGenerator.authSessionState(client));

            // Origin-Host
            AVPUtils.addAvp(avpList,avpGenerator.originHost(client));

            // Origin-Realm
            AVPUtils.addAvp(avpList,avpGenerator.originRealm(client));

            //ECGI
            String ecgi = "24f23000273953";
            AVPUtils.addAvp(avpList, avpGenerator.ecgi(client,ecgi));

            //Location-Estimate
            byte[] temp = DatatypeConverter.parseHexBinary("10230bb921325323");
            String locationEstimate = DatatypeConverter.printBase64Binary(temp);
            AVPUtils.addAvp(avpList, avpGenerator.locationEstimate(client,locationEstimate));

            //ESMLC-Cell-Info
            AVPUtils.addAvp(avpList, avpGenerator.resultCode(client));


            diameter.setAvpList(avpList);

        }
        catch (Exception e){
            logger.info("error when populating psl message:" + e.getMessage());
            e.printStackTrace();
        }

        return diameter;
    }

}
