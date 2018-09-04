package Encode;

import Constant.Constant;
import Utils.Util;
import com.fasterxml.jackson.databind.JsonNode;
import constant.Constants;
import model.AVP;
import model.AVPFlag;
import model.Address;
import org.apache.log4j.Logger;

import javax.xml.bind.DatatypeConverter;
import java.lang.instrument.Instrumentation;
import java.net.Inet4Address;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static javax.xml.bind.DatatypeConverter.parseBase64Binary;

/**
 * this class is used for encode diameter header and avp
 * @version 1.0
 * @since   2017-01-23
 */

public class EncodeImplements implements EncodeInterface {

    public Constant cons = new Constant();

    private static final Logger logger = Logger.getLogger(Constants.ENCODER_LOG);

    public static Instrumentation instrumentation;
    private List<Byte> bytes;

    /**
     * This method is used set format header diameter such as set format version,
     * set flag, set length, set application id, set hop by hop id and end to end id.
     * @param version this is first parameter to encodeDiameterHeader method
     * @param length this is second parameter to encodeDiameterHeader method
     * @param flags this is third parameter to encodeDiameterHeader method
     * @param commandCode this is fourth parameter to encodeDiameterHeader method
     * @param applicationId this is fifth parameter to encodeDiameterHeader method
     * @param hopByHopId this is sixth parameter to encodeDiameterHeader method
     * @param endToEndId this is seventh parameter to encodeDiameterHeader method
     * @return this return is encoded diameter header
     * @version 1.0
     * @since   2017-01-23
     */
    public List<Byte>  encodeDiameterHeader(char version, int length, char flags, int commandCode, int applicationId, int hopByHopId, long endToEndId) {

        List<Byte> buffer = new ArrayList<Byte>();		// temporary buffer for storing PDU

        int index = 0;			// PDU index

        /* Diameter version */
        buffer.add((byte)version);
        /* Diameter version -- end */
        /* Diameter length */

        buffer.add((byte) ((length & 0x00FF0000) >> 2 * 8));
        buffer.add((byte) ((length & 0x0000FF00) >> 1 * 8));
        buffer.add((byte) ((length & 0x000000FF) >> 0 * 8));
        /* Diameter length -- end */

        /* Diameter Flag */
        buffer.add((byte) flags);
        /* Diameter Flag -- end */

        /* Diameter Command Code */
        buffer.add((byte) ((commandCode & 0x00FF0000) >> 2 * 8));
        buffer.add((byte) ((commandCode & 0x0000FF00) >> 1 * 8));
        buffer.add((byte) ((commandCode & 0x000000FF) >> 0 * 8));
        /* Diameter Command Code -- end */

        /* Diameter Application ID */
        buffer.add((byte) ((applicationId & 0xFF000000) >> 3 * 8));
        buffer.add((byte) ((applicationId & 0x00FF0000) >> 2 * 8));
        buffer.add((byte) ((applicationId & 0x0000FF00) >> 1 * 8));
        buffer.add((byte) ((applicationId & 0x000000FF) >> 0 * 8));
        /* Diameter Application ID -- end */

        /* Diameter Hop by Hop ID */
        buffer.add((byte) ((hopByHopId & 0xFF000000) >> 3 * 8));
        buffer.add((byte) ((hopByHopId & 0x00FF0000) >> 2 * 8));
        buffer.add((byte) ((hopByHopId & 0x0000FF00) >> 1 * 8));
        buffer.add((byte) ((hopByHopId & 0x000000FF) >> 0 * 8));
        /* Diameter Hop by Hop ID -- end */

        /* Diameter End to End ID */
        buffer.add( (byte) ((endToEndId & 0xFF000000) >> 3 * 8));
        buffer.add( (byte) ((endToEndId & 0x00FF0000) >> 2 * 8));
        buffer.add( (byte) ((endToEndId & 0x0000FF00) >> 1 * 8));
        buffer.add( (byte) ((endToEndId & 0x000000FF) >> 0 * 8));
        /* Diameter End to End ID -- end */


        return  buffer;
    }

    /**
     * This method is used to set avp format such as avp code,
     * flag, length, vendor id if the avp vendor specific flagging is true, and avp value.
     * @param avpCode this is first parameter to encodeAvp method
     * @param avpFlag this is second parameter to encodeAvp method
     * @param avpVendorId this is third parameter to encodeAvp method
     * @param avpValue this is fourth parameter to encodeAvp method
     * @return this return is encoded avp
     * @version 1.0
     * @since   2017-01-23
     */
    public List<Byte> encodeAvp(int avpCode, char avpFlag, int avpVendorId, List<Byte> avpValue){

        int avpLength = 0;

        List<Byte> buffer = new ArrayList<Byte>();

        int avpHeaderSize = cons.AVP_CODE_SIZE + cons.AVP_FLAG_SIZE + cons.AVP_LENGTH_SIZE;

        boolean isVendorExits = Util.getBoolFlag(avpFlag,cons.AVP_FLAG_VENDOR_SPECIFIC);

        /* pading size */
        int avppaddingsize =0;
        int remnants = avpValue.size() % 4;
        if(remnants >0) {
            avppaddingsize = 4 - remnants;
        }
        /* pading size end*/

        avpLength = (int) (avpHeaderSize + avpValue.size() + (isVendorExits ? 4 : 0));

        /* AVP Code */
        buffer.add((byte)((avpCode & 0xFF000000) >> 3*8));
        buffer.add((byte)((avpCode & 0x00FF0000) >> 2*8));
        buffer.add((byte)((avpCode & 0x0000FF00) >> 1*8));
        buffer.add((byte)((avpCode & 0x000000FF) >> 0*8));
        /* AVP Code -- end */

        /* AVP Flag */
        buffer.add((byte)avpFlag);
        /* AVP Flag -- end */

        /* AVP length */
        buffer.add((byte)((avpLength & 0x00FF0000) >> 2*8));
        buffer.add((byte)((avpLength & 0x0000FF00) >> 1*8));
        buffer.add((byte)((avpLength & 0x000000FF) >> 0*8));
        /* AVP length End */


        if(isVendorExits){

            buffer.add((byte) ((avpVendorId & 0xFF000000) >> 3*8));
            buffer.add((byte) ((avpVendorId & 0x00FF0000) >> 2*8));
            buffer.add((byte) ((avpVendorId & 0x0000FF00) >> 1*8));
            buffer.add((byte) ((avpVendorId & 0x000000FF) >> 0*8));
        }


        /* value */
        for(Byte item : avpValue){
            buffer.add(item);
        }
        /* value end*/

        /* padding */
        for (int x = 0; x < avppaddingsize; x++) {
            buffer.add((byte) 0x00);
        }
        /* padding end*/

        return buffer;

    }

    /**
     * This method is used for convert the value from redis, where the value is still in json and
     * process the value into some function to encode it.
     * @param avp this is first parameter to encodeAvpJason method
     * @return this return is hexa value
     * @version 1.0
     * @since   2017-01-23
     */






    public List<Byte> encodeAvpObject(AVP avp){

        List<Byte> buffer = new ArrayList<Byte>();

        logger.info("AVP Child:");

        // code
        int code = (int)avp.getCode();
        logger.info("code:"+code);

        // description
        if(avp.getDescription()!=null) {
            String description = avp.getDescription();
            logger.info("description:" + description);
        }


        // flags:

        AVPFlag flag = avp.getFlags();
        char flags=0x00;


        if(flag!=null) {

            // vendorSpesific
            boolean vendorSpecific = flag.isVendorSpecific();
            logger.info("vendorSpecific:" + vendorSpecific);
            if (vendorSpecific) {
                flags = 0x80;
            }

            // mandatory
            boolean mandatory = flag.isMandatory();
            logger.info("mandatory:" + mandatory);
            if (mandatory) {
                flags = (char) (flags | 0x40);
            }


            // protected
            boolean protectedValue = flag.isProtect();
            logger.info("protected:" + protectedValue);
            if (protectedValue) {
                flags = (char) (flags | 0x20);
            }

        }


        /*
        // vendorIdflags
        JsonNode flags = json.path("flags");
        JsonNode vendorIdflagsNode = flags.findValue("vendorId") ;

        boolean vendorIdflags =vendorIdflagsNode.asBoolean();
        System.out.println("vendorId:"+vendorIdflags);
        */

        // vendorId

        int vendorId = (int)avp.getVendorId();
        logger.info("vendorId:"+vendorId);


        // valueType

        String valueType = avp.getValueType();
        logger.info("valueType:"+valueType);


        // value any kind

        if(valueType.equalsIgnoreCase("integer")) {


            int value = (Integer) avp.getValue();
            logger.info("value:" + value);

            buffer = encodeAvpIntegerValue(code,flags,vendorId,value);

        }else if(valueType.equalsIgnoreCase("string")){


            String value =(String) avp.getValue();
            logger.info("value:" + value);


            buffer = encodeAvpStringValue(code,flags,vendorId,value.toCharArray());

        }else if(valueType.equalsIgnoreCase("ipAddress") || valueType.equalsIgnoreCase("Address")){


            Address addr =(Address) avp.getValue();

            String family = addr.getFamily();
            String address = addr.getValue();

            short ipFamily = 0;

            if(family.equalsIgnoreCase("ipv6")){

                ipFamily = (short) cons.IP_ADDRESS_FAMILY_IPV6;
            }else{
                ipFamily = cons.IP_ADDRESS_FAMILY_IPV4;

            }
            try {
                buffer = encodeAvpIpAddressValue(code, flags, vendorId, ipFamily, address.toCharArray());
            }catch (Exception e){
                e.printStackTrace();
            }


        }else if(valueType.equalsIgnoreCase("isdn")){


            String value = (String) avp.getValue();

            buffer = encodeAvpIsdnValue(code,flags,vendorId,value.toCharArray());


        }else if(valueType.equalsIgnoreCase("base64")){


            String value = (String)avp.getValue();

            byte[] content = DatatypeConverter.parseBase64Binary(value);

            List<Byte> contentList = new ArrayList<Byte>();

            for(byte item : content){
                contentList.add(item);
            }

            buffer = encodeAvp(code,flags,vendorId,  contentList);
        }

        else if(valueType.equalsIgnoreCase("byteString")||valueType.equalsIgnoreCase("OctetString")){


            String value = (String) avp.getValue();


            buffer = encodeAvpByteStringValue(code,flags,vendorId,value.toCharArray());


        }else if(valueType.equals("grouped")){

            List<Byte> result = new ArrayList<Byte>();

            List<AVP> avps = (List<AVP>) avp.getValue();


            for(AVP item : avps){
                List<Byte> temp   = encodeAvpObject(item);
                result.addAll(temp);
            }


            buffer = encodeAvp(code,flags,vendorId,result);

        }


        return buffer;
    }





    /**
     * This method is used for convert avp ip address value into its numeric binary form
     * @param avpCode this is first parameter to encodeAvpIpAddressValue method
     * @param avpFlag this is second parameter to encodeAvpIpAddressValue method
     * @param avpVendorId this is third parameter to encodeAvpIpAddressValue method
     * @param ipAddressFamily this is fourth parameter to encodeAvpIpAddressValue method
     * @param ipAddress this is fourth parameter to encodeAvpIpAddressValue method
     * @return this return is encoded avp where the ip address become numeric binary form
     * @version 1.0
     * @since   2017-01-23
     */
    public List<Byte> encodeAvpIpAddressValue(int avpCode, char avpFlag, int avpVendorId, short ipAddressFamily, char[] ipAddress)   throws Exception  {

        List<Byte> buffer = new ArrayList<Byte>();
        char[] inetAddress = null;
        int index = 0;

        Constant cons = new Constant();
        /* IP Address Family */
        buffer.add((byte) ((ipAddressFamily & 0xFF00) >> 1 * 8));
        buffer.add((byte) ((ipAddressFamily & 0x00FF) >> 0 * 8));
        /* IP Address Family -- end */


        byte[] inetaddressbyte= Inet4Address.getByName(String.valueOf(ipAddress)).getAddress();


        for(int b = 0; b < inetaddressbyte.length;b++){
            buffer.add((byte)inetaddressbyte[b]);
        }



        return encodeAvp(avpCode, avpFlag,avpVendorId,buffer);

    }

    /**
     * This method is used for convert avp string value
     * @param avpCode this is first parameter to encodeAvpStringValue method
     * @param avpFlag this is second parameter to encodeAvpStringValue method
     * @param avpVendorId this is third parameter to encodeAvpStringValue method
     * @param stringValue this is fourth parameter to encodeAvpStringValue method
     * @return this return is encoded avp string
     * @version 1.0
     * @since   2017-01-23
     */

    public List<Byte> encodeAvpStringValue(int avpCode, char avpFlag, int avpVendorId, char[] stringValue) {
        List<Byte> buffer = new ArrayList<Byte>();
        for(char item : stringValue){
            buffer.add((byte)item);
        }
        return encodeAvp(avpCode,avpFlag,avpVendorId,buffer);
    }

    /**
     * This method is used for convert avp integer value
     * where the integer value byte will reversed
     * @param avpCode this is first parameter to encodeAvpIntegerValue method
     * @param avpFlag this is second parameter to encodeAvpIntegerValue method
     * @param avpVendorId this is third parameter to encodeAvpIntegerValue method
     * @param avpValue this is fourth parameter to encodeAvpIntegerValue method
     * @return this return is encoded avp integer where the integer value byte was reversed
     * @version 1.0
     * @since   2017-01-23
     */

    public List<Byte> encodeAvpIntegerValue(int avpCode, char avpFlag, int avpVendorId, int avpValue) {

        List<Byte> buffer = new ArrayList<Byte>();

        // Integer dword = Integer.reverseBytes(avpValue);
        Integer dword =  avpValue;

        buffer.add((byte) ((dword & 0xff000000) >> (3 * 8) ));
        buffer.add((byte) ((dword & 0x00ff0000) >> (2 * 8) ));
        buffer.add((byte) ((dword & 0x0000ff00) >> (1 * 8) ));
        buffer.add((byte) ((dword & 0x000000ff) >> (0 * 8) ));

        return encodeAvp(avpCode,avpFlag,avpVendorId,buffer);
    }

    /**
     * This method is used for convert avp value into Hexstring value
     * @param avpCode this is first parameter to encodeAvpByteStringValue method
     * @param avpFlag this is second parameter to encodeAvpByteStringValue method
     * @param avpVendorId this is third parameter to encodeAvpByteStringValue method
     * @param input this is fourth parameter to encodeAvpByteStringValue method
     * @return this return is encoded avp value where the value was converted into hexstring
     * @version 1.0
     * @since   2017-01-23
     */

    public List<Byte> encodeAvpByteStringValue(int avpCode, char avpFlag, int avpVendorId, char[] input){

        List<Byte> buffer = new ArrayList<Byte>();

        String temp = new String(input);

        //  String hexString2 = Util.encodeToHex(temp);

        byte[] hexBytes=  DatatypeConverter.parseHexBinary(temp);
        // byte[] hexBytes = hexString2.getBytes();

        for(byte item : hexBytes){
            buffer.add(item);
        }
        return encodeAvp(avpCode,avpFlag,avpVendorId,buffer) ;
    }

    /**
     * This method is used for swap isdn value between 2 bytes
     * @param avpCode this is first parameter to encodeAvpIsdnValue method
     * @param avpFlag this is second parameter to encodeAvpIsdnValue method
     * @param avpVendorId this is third parameter to encodeAvpIsdnValue method
     * @param isdn this is fourth parameter to encodeAvpIsdnValue method
     * @return this return is encoded avp value where the isdn was swapped between 2 bytes
     * @version 1.0
     * @since   2017-01-23
     */

    public List<Byte> encodeAvpIsdnValue(int avpCode, char avpFlag, int avpVendorId, char[] isdn) {

        List<Byte> result = new ArrayList<Byte>();

        int bufferlength =(int)Math.ceil((float)isdn.length/(float)2);

        int index =0;


        byte[] buffer = new byte[bufferlength];


        for(int j = 0;j<isdn.length;++j){

            buffer[index] =  (byte) (buffer[index] + ((isdn[j] -  48) << ((j % 2 != 0) ? 4 : 0)));
            index = index + ((j % 2));

        }


        if(isdn.length % 2 != 0) {
            buffer[index] |= 0xf0;
        }


        for(byte item : buffer){
            result.add(item);
        }

        return encodeAvp(avpCode,avpFlag,avpVendorId,result);
    }

}
