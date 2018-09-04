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
public class CERGeneralMain  {

    static ClassLoader classLoader = CERGeneralMain.class.getClassLoader();

    private static final Logger logger = Logger.getLogger(CERGeneralMain.class);

    public static void main(String args[]) {

        List<String> sourceAdress = new ArrayList<String>() {
        };
        sourceAdress.add("10.32.8.13");
        sourceAdress.add("10.32.8.14");
        sourceAdress.add("10.32.8.15");
        sourceAdress.add("10.32.8.16");

        Diameter output = createMessage(sourceAdress);

        System.out.println(output);

    }

    public static Diameter createMessage(List<String> sourceAddress) {
        String client = JdiameterProperties.getClient();
        Diameter diameter = new Diameter();
        diameter.setFlags(new DiameterFlag());
        try {

            List<AVP> avpList = new ArrayList<AVP>();

            diameter.setVersion(1);

            diameter.getFlags().setRequest(true);
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

            // Host-Ip-Address
            AVPUtils.addAvp(avpList,avpGenerator.hostIPAddresses(client,sourceAddress));

            // Vendor-Id
            AVPUtils.addAvp(avpList,avpGenerator.vendorId(client));

            // Product-Name
            AVPUtils.addAvp(avpList,avpGenerator.productName(client));

            // Auth-Application-Id
            AVPUtils.addAvp(avpList,avpGenerator.authApplicationId(client));

            //Inband-Security-Id
            AVPUtils.addAvp(avpList,avpGenerator.inbandSecurityId(client));

            // Acct-Application-Id
            AVPUtils.addAvp(avpList,avpGenerator.acctApplicationId(client));

            // populating Vendor-Specific-Application-Id  AVPGenerator ( 260 ) grouped vendor id 0
            AVPUtils.addAvp(avpList,avpGenerator.vendorSpesificApplicationId(client));

            diameter.setAvpList(avpList);

        } catch (Exception e) {

            logger.info("error when populating cer message:" + e.getMessage());
            e.printStackTrace();
        }

        return diameter;
    }

}
