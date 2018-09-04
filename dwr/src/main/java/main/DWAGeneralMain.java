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

/**
 * This class is used for sending CER for initiator request
 * Created by gede on 23/01/18.
 */
public class DWAGeneralMain {

    static ClassLoader classLoader = DWAGeneralMain.class.getClassLoader();

    private static final Logger logger = Logger.getLogger(DWAGeneralMain.class);


    public static Diameter createMessage(Integer hopByHopId, Integer endToEndId) {
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

            diameter.setHopByHopId(hopByHopId);
            diameter.setEndToEndId(endToEndId);

            diameter.setCommandCode(280);
            diameter.setApplicationId(0);

            AVPGeneratorImpl avpGenerator = new AVPGeneratorImpl();

            // Origin-Host
            AVPUtils.addAvp(avpList,avpGenerator.originHost(client));

            // Origin-Realm
            AVPUtils.addAvp(avpList,avpGenerator.originRealm(client));

            //ResultCode
            AVPUtils.addAvp(avpList,avpGenerator.resultCode(client));

            diameter.setAvpList(avpList);

        } catch (Exception e) {

            logger.info("error when populating cer message:" + e.getMessage());
            e.printStackTrace();
        }

        return diameter;
    }

}
