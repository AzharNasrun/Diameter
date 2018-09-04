package main;


import com.firstwap.jdiameter.Properties.JdiameterProperties;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

/**
 * Created by gede on 04/03/18.
 */
public class DWAMain {

    static ClassLoader classLoader = DWAMain.class.getClassLoader();

    public static String createDWAMessage(int hopByHopId,int endToEndId){

        String result ="";

        try {
            String dwaJson = IOUtils.toString(classLoader.getResourceAsStream("json/dwa.json"));

            // mapping

            dwaJson=dwaJson.replaceAll("dwa.originHost", JdiameterProperties.getOriginHost());
            dwaJson=dwaJson.replaceAll("dwa.originRealm",JdiameterProperties.getOriginRealm());
            dwaJson=dwaJson.replaceAll("dwa.hopByHopId", String.valueOf(hopByHopId));
            dwaJson=dwaJson.replaceAll("dwa.endToEndId", String.valueOf(endToEndId));



            System.out.println(dwaJson);
            result = dwaJson;

        } catch (IOException e) {
            e.printStackTrace();
        }


        return result;
    }


}
