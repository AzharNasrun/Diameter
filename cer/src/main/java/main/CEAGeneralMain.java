package main;

import com.firstwap.jdiameter.Properties.JdiameterProperties;
import model.AVP;
import model.Diameter;
import model.DiameterFlag;
import org.apache.log4j.Logger;
import service.avp.AVPUtils;
import service.avp.AVPGeneratorImpl;

import java.util.ArrayList;
import java.util.List;

public class CEAGeneralMain {

    private static final Logger logger = Logger.getLogger(CEAGeneralMain.class);

    public static Diameter createMessage() {
        String client = JdiameterProperties.getClient();
        Diameter diameter = new Diameter();
        try {

            List<AVP> avpList = new ArrayList<AVP>();

            diameter.setVersion(1);
            diameter.setFlags(new DiameterFlag());
            diameter.getFlags().setRequest(false);
            diameter.getFlags().setProxyable(false);
            diameter.getFlags().setError(false);
            diameter.getFlags().setTransmitted(false);

            diameter.setCommandCode(257);
            diameter.setApplicationId(0);

            AVPGeneratorImpl avpGenerator = new AVPGeneratorImpl();

            // Origin-Host
            AVPUtils.addAvp(avpList,avpGenerator.originHost(client));

            // Origin-Realm
            AVPUtils.addAvp(avpList,avpGenerator.originRealm(client));

            diameter.setAvpList(avpList);

        } catch (Exception e) {

            logger.info("error when populating cer message:" + e.getMessage());
            e.printStackTrace();
        }

        return diameter;
    }
}
