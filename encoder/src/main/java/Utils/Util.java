package Utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * This class provides methods used to simplify the encoding or decoding process like a tools
 * Created by gede on 23/01/18.
 */
public class Util {


    /**
     * This method is used for get boolean result true or false
     * @param avpFlag this first parameter to getBoolFlag method
     * @param flagVendorSpecific this second parameter to getBoolFlag method
     * @return the result is boolean value true or false
     */
    public static boolean getBoolFlag(long avpFlag, long flagVendorSpecific){

        boolean result = true;
        long resultBitWise = avpFlag & flagVendorSpecific;

        if(resultBitWise == 0){
            result = false;
        }

        return result;
    }

    /**
     * This method is used for convert string into hexstring value
     * @param arg this first parameter to encodeToHex method
     * @return the return method is hexString value
     */
    public static String encodeToHex(String arg) {
        return String.format("%02x", new BigInteger(1, arg.getBytes()));
    }


    /**
     * This method is used for convert hex into string value
     * @param hex this first parameter to decodeFromHex method
     * @return the return method is string value
     */
    public static  String decodeFromHex(String hex){

        String result = "";
        byte[] b = new BigInteger(hex,16).toByteArray();
        try {
            result = new String(b, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return result;

    }


    /**
     * This method is used for convert number into hexavalue value
     * @param number this first parameter to toHexadecimal method
     * @return the return method is hexa value
     */
    public static String toHexadecimal(int number)
    {
        return Integer.toHexString(number);
    }


    /**
     * This method is used reversed the json format
     * @param input this first parameter to toHexadecimal method
     * @param mapper this first parameter to toHexadecimal method
     * @return the return method is reversed json array
     */
    public static ArrayNode reversedArrayNode(ArrayNode input, ObjectMapper mapper){

        //sorting
        List<JsonNode> avpsArrayList = new ArrayList<JsonNode>();
        Iterator avpArrayIterator = input.iterator();
        while (avpArrayIterator.hasNext()){
            avpsArrayList.add((JsonNode)avpArrayIterator.next());
        }
        Collections.reverse(avpsArrayList);
        ArrayNode reversedAvpArray = mapper.createArrayNode();

        for(JsonNode item :avpsArrayList ) {
            reversedAvpArray.add(item);
        }
        // end sorting

        return  reversedAvpArray;

    }



}


