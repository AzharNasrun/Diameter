package service.avp;

import model.AVP;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by aji on 04/04/18.
 */
public abstract class AVPUtils {

    public static void addAvp(List<AVP> avpArray, AVP avpCheck){
        if(avpCheck != null){
            avpArray.add(avpCheck);
        }
    }
    public static void addAvp(List<AVP> avpArray, List<AVP> avpCheck){
        if(avpCheck != null){
            avpArray.addAll(avpCheck);
        }
    }

    public static String getPLMNID(String mcc,String mnc){

        if (mnc.length()<3){
            int padding =  3-mnc.length();
            for(int i=0;i<padding;i++){
                mnc = "0"+mnc;
            }

        }
        if (mcc.length()<3){
            int padding =  3-mcc.length();
            for(int i=0;i<padding;i++){
                mcc = "0"+mcc;
            }

        }
        String mccmnc= mcc+mnc;
        String temp3 = new StringBuilder(mccmnc.substring(3,6)).reverse().toString();
        String temp2 = mccmnc.substring(0,3);
        mccmnc = temp2+temp3;
        String temp1 = new StringBuilder(mccmnc.substring(0,2)).reverse().toString();
        temp2 = new StringBuilder(mccmnc.substring(2,4)).reverse().toString();
        temp3 = mccmnc.substring(4,6);
        mccmnc = temp1+temp2+temp3;
        return mccmnc;
    }
    public static String getNumberFrom(String regex,String source){
        source=source.toLowerCase();
        Pattern p = Pattern.compile(regex+"([0-9]{3})");
        Matcher m = p.matcher(source);

        if (m.find()) {
            return m.group(1); // first expression from round brackets (Testing)

        }
        return "";
    }

    public static String getMCCFromMMEName(String source){
        return getNumberFrom("mcc",source);

    }
    public static String getMNCFromMMEName(String source){
        return getNumberFrom("mnc",source);

    }
}
