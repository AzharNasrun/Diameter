package main;


import com.firstwap.jdiameter.Properties.JdiameterProperties;
import constant.Constants;
import model.AVP;
import model.Diameter;
import model.DiameterFlag;
import org.apache.log4j.Logger;
import service.avp.AVPUtils;
import service.avp.AVPGeneratorImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aji on 06/04/18.
 */
public class UDAMain {

    private static final Logger logger = Logger.getLogger(Constants.API_LOG);





    public static Diameter createMessage(int sessionId, int transactionId) {

        String client = JdiameterProperties.getClient();
        Diameter diameter = new Diameter();
        List<AVP> avpList = new ArrayList<AVP>();

        try {

            diameter.setVersion(1);
            diameter.setType(Constants.UDR_CONST);
            diameter.setFlags(new DiameterFlag());
            diameter.getFlags().setRequest(false);
            diameter.getFlags().setProxyable(true);
            diameter.getFlags().setError(false);
            diameter.getFlags().setTransmitted(false);

            diameter.setCommandCode(306);
            diameter.setApplicationId(16777217);
            diameter.setEndToEndId(transactionId);


            AVPGeneratorImpl avpGenerator = new AVPGeneratorImpl();

            // session-id
            AVPUtils.addAvp(avpList, avpGenerator.sessionId(client, sessionId));

            // auth session state
            AVPUtils.addAvp(avpList,avpGenerator.authSessionState(client));

            // Origin-Host
            AVPUtils.addAvp(avpList,avpGenerator.originHost(client));

            // Origin-Realm
            AVPUtils.addAvp(avpList,avpGenerator.originRealm(client));


            // USerdatash
            String result = "3c3f786d6c2076657273696f6e3d22312e302220656e636f64696e673d225554462d38223f3e3c53682d4461746120786d6c6e733a7873693d22687474703a2f2f7777772e77332e6f72672f323030312f584d4c536368656d612d696e7374616e636522207873693a6e6f4e616d657370616365536368656d614c6f636174696f6e3d22536844617461547970655f52656c382e787364223e3c457874656e73696f6e3e3c457874656e73696f6e3e3c457874656e73696f6e3e3c457874656e73696f6e3e3c4550535573657253746174653e303c2f4550535573657253746174653e3c4550534c6f636174696f6e496e666f726d6174696f6e3e3c452d555452414e43656c6c476c6f62616c49643e466641524272663842673d3d3c2f452d555452414e43656c6c476c6f62616c49643e3c4d4d454e616d653e4d4d43425430352e4550432e4d4e433031312e4d43433531302e334750504e4554574f524b2e4f52473c2f4d4d454e616d653e3c43757272656e744c6f636174696f6e5265747269657665643e303c2f43757272656e744c6f636174696f6e5265747269657665643e3c4167654f664c6f636174696f6e496e666f726d6174696f6e3e35323c2f4167654f664c6f636174696f6e496e666f726d6174696f6e3e3c2f4550534c6f636174696f6e496e666f726d6174696f6e3e3c2f457874656e73696f6e3e3c2f457874656e73696f6e3e3c2f457874656e73696f6e3e3c2f457874656e73696f6e3e3c2f53682d446174613e";
            AVPUtils.addAvp(avpList, avpGenerator.userDataSH(client,result));

            //result-code
            AVPUtils.addAvp(avpList,avpGenerator.resultCode(client) );

            diameter.setAvpList(avpList);

        } catch (Exception e) {

            logger.info("error when populating UDR message:" + e.getMessage());
            e.printStackTrace();
        }

        return diameter;
    }
}
