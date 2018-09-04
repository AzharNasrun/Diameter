package main;

import com.firstwap.jdiameter.Properties.JdiameterProperties;
import model.AVP;
import model.CommandCode;
import model.Diameter;
import model.DiameterFlag;
import service.avp.AVPUtils;
import service.avp.AVPGeneratorImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gede on 25/02/18.
 */
public class DWRMain extends Thread{


    public static int originStateId=0;





    public DWRMain(){

        originStateId= (int) (Math.random()*1000000);
    }

    static ClassLoader classLoader = DWRMain.class.getClassLoader();






    public static void main(String args[]){

    }


    /**
     * This method is used for get json template file and send the data based on template
     * @return json format based on template
     */
    public static Diameter createDWRMessage(){

        String client = JdiameterProperties.getClient();
        Diameter diameter = new Diameter();

        List<AVP> avpList = new ArrayList<AVP>();

        diameter.setVersion(1);
        diameter.setFlags(new DiameterFlag());
        diameter.getFlags().setRequest(true);
        diameter.getFlags().setProxyable(true);
        diameter.getFlags().setError(false);
        diameter.getFlags().setTransmitted(false);

        diameter.setCommandCode(CommandCode.DWR.getComandCode());
        diameter.setApplicationId(0);



        AVPGeneratorImpl avpGenerator = new AVPGeneratorImpl();

        // Origin-Host
        AVPUtils.addAvp(avpList,avpGenerator.originHost(client));

        // Origin-Realm
        AVPUtils.addAvp(avpList,avpGenerator.originRealm(client));



        diameter.setAvpList(avpList);

        return diameter;
    }



}
