/*
package Service;

*/
/**
 * Created by gede on 03/01/18.
 *//*


import Constant.Constant;
import Decode.DecodeImplements;
import Decode.DecodeInterface;
import Encode.EncodeImplements;
import Encode.EncodeInterface;
//import main.DMRMain;

import com.firstwap.jdiameter.Properties.JdiameterProperties;
import Utils.Util;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Assert;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


*/
/**
 * Class for Help to conect to redis and modify data in it.
 * @author gede
 * @version 1.0
 *
 *//*

public class TestService {











//    @Test
//    public void testDecodeDiameterMessage() {
//
//        EncodeInterface enc = new EncodeImplements();
//        DecodeInterface dec = new DecodeImplements();
//
//
//        String hexString ="010000A4C080000E0100004B00000000000000000000010740000021317273747761702E636F6D3B313531303330363730313B303B000000000001154000000C15010000000001084000001974656C636F2E317273747761702E636F6D0000000000012840000013317273747761702E636F6D000000011B4000001C7765622E617070732E317273747761702E636F6D000002BDC0000012000028AF2658123254F70000";
//
//        byte[] diameterMessageByte= Util.toByteArray(hexString);
//        ObjectNode json = null;
//
//        // client.rpush("decode".getBytes(), diameterMessageByte);
//
//        //  List<byte[]> result = client.blpop(1000,"decode".getBytes());
//
//        // byte[] message = result.get(1);
//
//        byte[] message =diameterMessageByte;
//        System.out.println();
//        System.out.println("panjang message:"+message.length);
//        System.out.println();
//        if(message!= null) {
//            if(message.length > 0) {
//                json = diameterMessageToJson(message);
//            }
//        }
//
//        Assert.assertEquals(json.toString(),"{\"version\":1,\"flags\":{\"request\":true,\"proxyable\":true,\"error\":false,\"transmitted\":false},\"commandCode\":8388622,\"applicationId\":16777291,\"hopByHopId\":0,\"endToEndId\":0,\"avp\":[{\"code\":701,\"flags\":{\"vendorSpecific\":true,\"mandatory\":true,\"protected\":false},\"vendorId\":10415,\"valueType\":\"isdn\",\"value\":\"62852123457\"},{\"code\":283,\"flags\":{\"vendorSpecific\":false,\"mandatory\":true,\"protected\":false},\"value\":\"web.apps.1rstwap.com\",\"valueType\":\"string\"},{\"code\":296,\"flags\":{\"vendorSpecific\":false,\"mandatory\":true,\"protected\":false},\"value\":\"1rstwap.com\",\"valueType\":\"string\"},{\"code\":264,\"flags\":{\"vendorSpecific\":false,\"mandatory\":true,\"protected\":false},\"value\":\"telco.1rstwap.com\",\"valueType\":\"string\"},{\"code\":277,\"flags\":{\"vendorSpecific\":false,\"mandatory\":true,\"protected\":false},\"value\":277,\"valueType\":\"integer\"},{\"code\":263,\"flags\":{\"vendorSpecific\":false,\"mandatory\":true,\"protected\":false},\"value\":\"1rstwap.com;1510306701;0;\",\"valueType\":\"string\"}]}");
//
//
//
//    }

    @Test
    public void TestEncodeDiameterMessage(){

    }




    public byte[] getDecodeAvp(byte[] input){

        Constant cons =  new Constant();

        int index = 0;

//        long header =  cons.AVP_CODE_SIZE + cons.AVP_FLAG_SIZE + cons.AVP_LENGTH_SIZE;
//
//
//        int avpCode = 0;
//        avpCode = 0xff000000 & (avpCode | input[index++] - 48 << (8 * 3));
//
//        int  avpCode1 =0;
//        avpCode1 = 0x00ff0000 & (avpCode1 | input[index++] - 48 << (8 * 2));
//
//        int avpCode2 =0;
//        avpCode2 = 0x0000ff00 & (avpCode2 | input[index++] - 48 << (8 * 1));
//
//        int  avpCode3 =0;
//        avpCode3 =  0x000000ff &(avpCode3 | input[index++]  - 48 << (8 * 0));
//
//        int avpCodeTotal = avpCode3 | avpCode2 | avpCode1 | avpCode;
//
//
//
//        long avpCodelong = 0;
//        avpCodelong = 0x00000000ffffffffl & avpCodeTotal ;
//
//
//
//        // flag
//        byte flagByte =  input[index++];
//
//
//        boolean vendor = false;
//        if((flagByte & 0x80)== 0x80){
//            vendor = true;
//            header = header + 4;
//
//        }
//
//
//        boolean mandatory = false;
//        if((flagByte & 0x40)== 0x40){
//            mandatory = true;
//        }
//
//
//        boolean protectedFlag = false;
//        if((flagByte & 0x20)== 0x20){
//            protectedFlag = true;
//        }
//
//
//
//
//
//
//        int  length =0;
//        length = 0x00ff0000 & (length | input[index++] << (8 * 2));
//
//        int length1 =0;
//        length1 = 0x0000ff00 & (length1 | input[index++] << (8 * 1));
//
//        int  length2 =0;
//        length2 =  0x000000ff &(length2 | input[index++] << (8 * 0));
//
//        int lengthTotal = length1 | length2 | length ;
//
//
//
//        long lengthLong = 0;
//        lengthLong = 0x00000000ffffffffl & lengthTotal ;
//
//        System.out.println("length long:"+lengthLong);

//
//
//        // vendor id
//
//
//        if(vendor==true){
//
//
//            int vendorValue = 0;
//            vendorValue = 0xff000000 & (vendorValue | input[index++] << (8 * 3));
//
//            int  vendorValue1 =0;
//            vendorValue1 = 0x00ff0000 & (vendorValue1 | input[index++] << (8 * 2));
//
//            int vendorValue2 =0;
//            vendorValue2 = 0x0000ff00 & (vendorValue2 | input[index++] << (8 * 1));
//
//            int  vendorValue3 =0;
//            vendorValue3 =  0x000000ff &(vendorValue3 | input[index++] << (8 * 0));
//
//            int vendorValueTotal = vendorValue | vendorValue1 | vendorValue2 | vendorValue3;
//
//
//            long vendorLong =0;
//            vendorLong = 0x00000000ffffffffl & vendorValueTotal ;
//        }
//
//

        //value

//        int valueSize =(int) (lengthLong - header);

        index =0;

        byte [] value = new byte[input.length];

        for(int x=0;x<input.length;x++){
            value[x] = input[index++];
        }

        return value;

    }


    @Test
    public void testEncodeAvp() {

        EncodeInterface enc = new EncodeImplements();
        DecodeInterface dec = new DecodeImplements();

//        Assert.assertEquals();



    }

    @Test
    public void testEncodeAvpSting() {

        EncodeInterface enc = new EncodeImplements();
        DecodeInterface dec = new DecodeImplements();



    }
    @Test
    public void testEncodeAvpDoubleWord() {

        EncodeInterface enc = new EncodeImplements();
        DecodeInterface dec = new DecodeImplements();



    }
*/
/*
    @Test
    public void testByteStringValuePositiveCase(){

        String plaintext="Bli Gede";

        List<Byte> encodeString = encode.encodeAvpByteStringValue(263, (char) 1, 0, plaintext.toCharArray());

        int lengHeader = 8;
        int valueSize = encodeString.size()-lengHeader;
        byte[] value= new byte[valueSize];

        for(int x=lengHeader;x<encodeString.size();x++){
            value[x-lengHeader] = encodeString.get(x);
        }

        byte[] stringValue = decode.decodeByteStringValue(value);

        String resultDecodeByteString = new String(stringValue);

        System.out.println(plaintext+"---"+resultDecodeByteString);

        Assert.assertEquals(plaintext, resultDecodeByteString);

    }

    @Test
    public void testEncodeDecodeAvpIsdn(){

        String plaintext="628984093644";

        List<Byte> encodeIsdn = encode.encodeAvpIsdnValue(784, (char)1, 0, plaintext.toCharArray());

        int lengHeader = 8;
        int valueSize = encodeIsdn.size()-lengHeader;
        byte[] value= new byte[valueSize];

        for(int x=lengHeader;x<encodeIsdn.size();x++){
            value[x-lengHeader] = encodeIsdn.get(x);
        }

        byte[] stringValue = decode.decodeIsdnAvpValue(value);

        StringBuffer resultString =  new StringBuffer();

        for(int x=0;x<stringValue.length - 2;x++){

            resultString.append(  (char)   (((stringValue[x] &  0xf0 ) >> 4 ) +48) );

            if( !((stringValue[x] & 0x0f) == 0x0f) ) {
                resultString.append((char) ((stringValue[x] & 0x0f) + 48));
            }
        }

//        String resultDecodeByteString = new String(stringValue);

        System.out.println(plaintext+"---"+resultString.toString());

        Assert.assertEquals(plaintext, resultString.toString());

    }

    @Test
    public void testEncodeDecodeInteger(){

        long plaintext=500;

        List<Byte> encodeInteger = encode.encodeAvpIntegerValue(784, (char)1, 0, (int) plaintext);

        int lengHeader = 8;
        int valueSize = encodeInteger.size()-lengHeader;
        byte[] value= new byte[valueSize];

        for(int x=lengHeader;x<encodeInteger.size();x++){
            value[x-lengHeader] = encodeInteger.get(x);
        }

        byte[] integerValue = decode.decodeIntegerAvpValue(value);

        int intValue = 0;
        intValue = 0xff000000 & (intValue | integerValue[0] << (8 * 3));

        int  intValue1 =0;
        intValue1 = 0x00ff0000 & (intValue1 | integerValue[1] << (8 * 2));

        int intValue2 =0;
        intValue2 = 0x0000ff00 & (intValue2 | integerValue[2] << (8 * 1));

        int  intValue3 =0;
        intValue3 =  0x000000ff &(intValue3 | integerValue[3] << (8 * 0));

        int intValueTotal = intValue1 | intValue | intValue3 | intValue2;


        long intlong = 0;
        intlong = 0x00000000ffffffffl & intValueTotal ;

        System.out.println(plaintext+"---"+intlong);

        Assert.assertEquals(plaintext, intlong);

    }


//    @Test
//    public void testEncodeAvpIsdn() {
//
//        EncodeInterface enc = new EncodeImplements();
//        DecodeInterface dec = new DecodeImplements();
//
//        String bd2 ="";
//
//        ArrayList<Byte> test1 = (ArrayList<Byte>) enc.encodeAvpIsdnValue(784, (char)1, 0, "6289".toCharArray());
//
//        StringBuilder sb = new StringBuilder();
//        StringBuilder sb2 = new StringBuilder();
//
//
//        for(byte tst:test1){
//
//            bd2 = new String(String.format("%02X", tst));
//
//            sb.append(bd2);
//
//        }
//
//////        byte[] decodeResult = getDecodeAvp("000003100100000A26980000".getBytes());
//////
//////        byte[] testDecode =  dec.decodeIsdnAvpValue(decodeResult);
//////
//////
//////
//////
//////
//////        String resultDecode = new String(String.format("%02X", testDecode));
//////
//////        Assert.assertEquals(resultDecode,"6289");
////
////        Assert.assertEquals(sb.toString(),"000003100100000A26980000");
////
////        byte[] decodeResult = getDecodeAvp("26980000".getBytes());
////        byte[] decodeIsdnResult = dec.decodeIsdnAvpValue(decodeResult);
////
////        for(byte tst2 : decodeIsdnResult){
////            String resultDecode = new String(String.format("%02X", tst2));
////            sb2.append(resultDecode);
////
////        }
////
////
////        System.out.print(sb2.toString());
//
//        long lengHeader = 8;
//        long valueSize = sb.length()-lengHeader;
//        byte[] value= new byte[(int) valueSize];
//
//        for(int x = (int) lengHeader; x<sb.length(); x++){
//
//            value[(int) (x-lengHeader)] = test1.get(x);
//
//        }
//
//
//
//        byte[] isdnValue = decode.decodeIsdnAvpValue(sb.toString().getBytes());
//
//        String resultDecodeIsdnValue = new String(isdnValue);
//
////        System.out.println(plaintext+"---"+resultDecodeByteString);
//
//        Assert.assertEquals("6289", resultDecodeIsdnValue);
//
//
//
//
//
//    }

    @Test
    public void testByteStringValueNegativeCase(){

        String plaintext="Bli Gede";




        List<Byte> encodeString = encode.encodeAvpByteStringValue(263, (char) 1, 0, plaintext.toCharArray());

        int lengHeader = 8;
        int valueSize = encodeString.size()-lengHeader;
        byte[] value= new byte[valueSize];

        for(int x=lengHeader;x<encodeString.size();x++){
            value[x-lengHeader] = encodeString.get(x);
        }

        byte[] stringValue = decode.decodeByteStringValue(value);

        String resultDecodeByteString = new String(stringValue);

        Assert.assertNotEquals("NotEqualsPlainText", resultDecodeByteString);
    }

    @Test
    public void testEncodeAvpIpAddress() {

        EncodeInterface enc = new EncodeImplements();
        DecodeInterface dec = new DecodeImplements();



    }


    @Test
    public void testEncodeAvpStrToByte() {

        EncodeInterface enc = new EncodeImplements();
        DecodeInterface dec = new DecodeImplements();

    }

    @Test
    public void testStringValueNegative() {

        String plaintext="Bli Gede";

        List<Byte> encodeString = encode.encodeAvpStringValue(263, (char) 1, 0, plaintext.toCharArray());

        int lengHeader = 8;
        int valueSize = encodeString.size()-lengHeader;
        byte[] value= new byte[valueSize];

        for(int x=lengHeader;x<encodeString.size();x++){
            value[x-lengHeader] = encodeString.get(x);
        }

        byte[] stringValue = decode.decodeString(value);

        String resultDecodeByteString = new String(stringValue);

//        Assert.assertEquals(plaintext, resultDecodeByteString);
        Assert.assertTrue(plaintext,true);
//        assertEquals(plaintext, resultDecodeByteString);

    }
*//*


}
*/
