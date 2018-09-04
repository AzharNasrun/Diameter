package ulr;



import com.firstwap.jdiameter.Properties.JdiameterProperties;
import constant.Constants;
import model.AVP;
import model.Diameter;
import model.DiameterFlag;
import org.apache.log4j.Logger;
import service.avp.AVPUtils;
import service.avp.AVPGenerator;
import service.avp.AVPGeneratorImpl;
import ulr.model.request.ULRRequest;
import ulr.model.request.ulr.Param;


import java.util.ArrayList;
import java.util.List;

public class ULRMain {

        private static final Logger logger = Logger.getLogger(Constants.API_LOG);

        public static void main (String args[]){
            String test = "123456";

       }


        public static Diameter createULRMessage(ULRRequest inputNode, int sessionId, int transactionId){
            String client = JdiameterProperties.getClient();
            Diameter diameter = new Diameter();
            List<AVP> avpList = new ArrayList<AVP>();
            Param param =  inputNode.getParam();

            diameter.setVersion(1);
            diameter.setType("ulr");
            diameter.setFlags(new DiameterFlag());
            diameter.getFlags().setRequest(true);
            diameter.getFlags().setProxyable(true);
            diameter.getFlags().setError(false);
            diameter.getFlags().setTransmitted(false);

            diameter.setCommandCode(316);
            diameter.setApplicationId(16777251);
            diameter.setEndToEndId(transactionId);


            AVPGenerator avpGenerator = new AVPGeneratorImpl();


        // session-id
        AVPUtils.addAvp(avpList, avpGenerator.sessionId(client, sessionId));

        //Auth-Session-State
        AVPUtils.addAvp(avpList, avpGenerator.authSessionState(client));

        //Origin-Host
        AVPUtils.addAvp(avpList, avpGenerator.originHost(client));

        //Origin-Realm
         AVPUtils.addAvp(avpList, avpGenerator.originRealm(client));

        // Destination-Host
         AVPUtils.addAvp(avpList, avpGenerator.destinationHost(client,param.getDestinationHost()));


        //Dest-Realm
         AVPUtils.addAvp(avpList, avpGenerator.destinationRealm(client,param.getDestinationRealm()));

        //RAT-Type
          AVPUtils.addAvp(avpList, avpGenerator.RAT_Type(client,param.getRatType()));

         //MSISDN
          AVPUtils.addAvp(avpList, avpGenerator.msisdn(client,param.getMsisdn()));

         // IMSI
          AVPUtils.addAvp(avpList, avpGenerator.imsi(client,param.getImsi()));

            // Visited-PLMN-Id
          AVPUtils.addAvp(avpList, avpGenerator.visitedPLMN(client,param.getMcc(),param.getMnc()));

            // SgsnNumber
            AVPUtils.addAvp(avpList, avpGenerator.sgsnNumber(client,param.getSgsnNumber()));

            // mmeNumberForMTSms
            AVPUtils.addAvp(avpList, avpGenerator.MMENumberforMTSMS(client,param.getMmeNumberForMTSMS()));

            // ULR-Flags
          AVPUtils.addAvp(avpList, avpGenerator.ulrFlags(client,param.getUlrFlags()));

            diameter.setAvpList(avpList);

        return diameter;

    }




}
