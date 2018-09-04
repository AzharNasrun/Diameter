package main;


import com.firstwap.jdiameter.Properties.JdiameterProperties;
import constant.Constants;
import model.*;
import model.UDR.Param;
import model.request.UDRRequest;
import model.ApplicationID;
import org.apache.log4j.Logger;
import service.avp.AVPUtils;
import service.avp.AVPGenerator;
import service.avp.AVPGeneratorImpl;
import service.response.ResponseGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aji on 06/04/18.
 */
public class UDRMain {

    private static final Logger logger = Logger.getLogger(Constants.API_LOG);





    public static Diameter createMessage(UDRRequest inputNode, int sessionId, int transactionId) {

        String client = JdiameterProperties.getClient();
        Diameter diameter = new Diameter();
        List<AVP> avpList = new ArrayList<AVP>();
        Param param=inputNode.getParam();
        try {

            diameter.setVersion(1);
            diameter.setType(Constants.UDR_CONST);
            diameter.setFlags(new DiameterFlag());
            diameter.getFlags().setRequest(true);
            diameter.getFlags().setProxyable(true);
            diameter.getFlags().setError(false);
            diameter.getFlags().setTransmitted(false);

            diameter.setCommandCode(CommandCode.UDR.getComandCode());
            diameter.setApplicationId(ApplicationID.UDR.getApplicationID());
            diameter.setEndToEndId(transactionId);


            AVPGenerator avpGenerator = new AVPGeneratorImpl();

            // session-id
            AVPUtils.addAvp(avpList, avpGenerator.sessionId(client, sessionId));

            AVP  vendorSpesificApplicationIdNode = new AVP();
            List<AVP> valueArray = new ArrayList<AVP>();
            vendorSpesificApplicationIdNode.setCode(AVPCode.VENDOR_SPECIFIC_APPLICATION_ID.getCode());
            vendorSpesificApplicationIdNode.setDescription("Vendor-Specific-Application-Id");
            AVPFlag avpFlagNode8 = new AVPFlag();
            avpFlagNode8.setMandatory(true);
            avpFlagNode8.setVendorSpecific(false);
            avpFlagNode8.setProtect(false);
            vendorSpesificApplicationIdNode.setFlags(avpFlagNode8);
            vendorSpesificApplicationIdNode.setVendorId(0);
            vendorSpesificApplicationIdNode.setValueType("grouped");
            valueArray.add(createVendorID());
            vendorSpesificApplicationIdNode.setValue(valueArray);
            avpList.add(vendorSpesificApplicationIdNode);

            //Vendor ID
            avpList.add(createVendorID());


            //Auth Application ID
            AVPUtils.addAvp(avpList,avpGenerator.authApplicationId(client));

            //authSessionstate
            AVPUtils.addAvp(avpList, avpGenerator.authSessionState(client));

            // Origin-Host
            AVPUtils.addAvp(avpList,avpGenerator.originHost(client));

            // Origin-Realm
            AVPUtils.addAvp(avpList,avpGenerator.originRealm(client));

            // Destination-Realm
            param.setDestinationRealm(JdiameterProperties.getUdrDestinationRealm());
            AVPUtils.addAvp(avpList,avpGenerator.destinationRealm(client,param.getDestinationRealm()));

            // Destination-Host
            AVPUtils.addAvp(avpList,avpGenerator.destinationHost(client,param.getDestinationHost()));

            //Data Reference
            AVPUtils.addAvp(avpList,avpGenerator.dataReference(client,param.getDataReference()));

            //requested domain
            AVPUtils.addAvp(avpList,avpGenerator.requestedDomain(client,param.getRequestedDomain()));

            //current location

            AVPUtils.addAvp(avpList,avpGenerator.currentLocation(client,param.getCurrentLocation()));
            //User Identity
            AVPUtils.addAvp(avpList,avpGenerator.userIdentity(client,param.getUserIdentity()));

            //imsi
            AVPUtils.addAvp(avpList,avpGenerator.imsi(client,param.getImsi()));

            //requested Node
           AVPUtils.addAvp(avpList,avpGenerator.requestedNode(client,param.getRequestedNode()));



            diameter.setAvpList(avpList);

        } catch (Exception e) {

            logger.info("error when populating UDR message:" + e.getMessage());
            e.printStackTrace();
        }

        return diameter;
    }


    public static  String getISMSCMMessage(Diameter message,ResponseGenerator responseGenerator ) throws Exception{

        if (message==null){
            responseGenerator.setStatus(1);
            return  responseGenerator.getResponseString();
        }

        List<AVP> avpList = message.getAvpList();
        model.response.ismscm.locate.Param param = responseGenerator.getParamDto();
       for (AVP avpElement:avpList ) {
            AVPCode code = AVPCode.getByCode(avpElement.getCode());
            switch (code) {
                case USER_DATA_SH:
                    ECGI avpObject=(ECGI) avpElement.getValue();
                    String mcc = avpObject.getMCC();
                    String mnc = avpObject.getMNC();
                    String enb = avpObject.getENB()+"";
                    String ci = avpObject.getCellId()+"";
                    String globalCellReff = mcc+"."+ mnc+"."+ enb+"."+ ci;
                    param.setCellRef(globalCellReff);
                    break;
                case RESULT_CODE:
                    int resultcode = Integer.parseInt(String.valueOf( avpElement.getValue()));
                    logger.info("resultcode :" + resultcode);
                    responseGenerator.setStatus(resultcode);
                    break;
            }
        }
        return responseGenerator.getResponseString();
    }


    private static AVP createVendorID(){
        AVP vendorIdNode = new AVP();
        vendorIdNode.setCode(AVPCode.VENDOR_ID.getCode());
        vendorIdNode.setDescription("Vendor-Id");
        AVPFlag avpFlagNode6 = new AVPFlag();
        avpFlagNode6.setMandatory(true);
        avpFlagNode6.setVendorSpecific(false);
        avpFlagNode6.setProtect(false);
        vendorIdNode.setFlags(avpFlagNode6);
        vendorIdNode.setVendorId(0);
        vendorIdNode.setValueType("integer");
        vendorIdNode.setValue(10415);
        return vendorIdNode;
    }
}

