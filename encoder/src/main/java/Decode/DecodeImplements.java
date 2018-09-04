package Decode;


import Constant.Constant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.firstwap.jdiameter.Properties.JdiameterProperties;
import constant.Constants;
import model.*;
import org.apache.log4j.Logger;

import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * this class is used for decode diameter header and avp
 * @version 1.0
 * @since   2017-01-23
 */

public class DecodeImplements implements DecodeInterface {
    private static final Logger logger = Logger.getLogger(Constants.ENCODER_LOG);
    Constant cons = new Constant();

    /**
     * This method is used to convert avp value which derive from network for convert it to json format
     *
     * @param avpList this is second parameter to decodeAvpsJson method
     * @param input this is third parameter to decodeAvpsJson method
     * @param index this is fourth parameter to decodeAvpsJson method
     * @param diameterLength this is fifth parameter to decodeAvpsJson method
     * @since   2017-01-23
     */



    public void decodeAvpObject(List<AVP> avpList, byte[] input, int index, long diameterLength) {

String targetCode="";
        try {

            AVP avp = new AVP();


            long header = cons.AVP_CODE_SIZE + cons.AVP_FLAG_SIZE + cons.AVP_LENGTH_SIZE;

            int avpCode = 0;
            avpCode = 0xff000000 & (avpCode | input[index++] << (8 * 3));

            int avpCode1 = 0;
            avpCode1 = 0x00ff0000 & (avpCode1 | input[index++] << (8 * 2));

            int avpCode2 = 0;
            avpCode2 = 0x0000ff00 & (avpCode2 | input[index++] << (8 * 1));

            int avpCode3 = 0;
            avpCode3 = 0x000000ff & (avpCode3 | input[index++] << (8 * 0));

            int avpCodeTotal = avpCode3 | avpCode2 | avpCode1 | avpCode;


            long avpCodelong = 0;
            avpCodelong = 0x00000000ffffffffl & avpCodeTotal;

            avp.setCode(avpCodelong);
            avp.setDescription(AVPCode.getByCode(avpCodelong).toString());
            String avpCodeString = String.valueOf(avpCodelong);
            logger.info("decode avp code:" + avpCodelong);
            targetCode = ""+avpCodelong;
            // flag
            byte flagByte = input[index++];


            AVPFlag avpFlag = new AVPFlag();

            boolean vendor = false;
            if ((flagByte & 0x80) == 0x80) {
                vendor = true;
                header = header + 4;

            }

            avpFlag.setVendorSpecific(vendor);

            boolean mandatory = false;
            if ((flagByte & 0x40) == 0x40) {
                mandatory = true;
            }
            avpFlag.setMandatory(mandatory);

            boolean protectedFlag = false;
            if ((flagByte & 0x20) == 0x20) {
                protectedFlag = true;
            }


            avpFlag.setProtect(protectedFlag);


            avp.setFlags(avpFlag);


            int length = 0;
            length = 0x00ff0000 & (length | input[index++] << (8 * 2));

            int length1 = 0;
            length1 = 0x0000ff00 & (length1 | input[index++] << (8 * 1));

            int length2 = 0;
            length2 = 0x000000ff & (length2 | input[index++] << (8 * 0));

            int lengthTotal = length1 | length2 | length;


            long lengthLong = 0;
            lengthLong = 0x00000000ffffffffl & lengthTotal;


            // vendor id


            if (vendor == true) {


                int vendorValue = 0;
                vendorValue = 0xff000000 & (vendorValue | input[index++] << (8 * 3));

                int vendorValue1 = 0;
                vendorValue1 = 0x00ff0000 & (vendorValue1 | input[index++] << (8 * 2));

                int vendorValue2 = 0;
                vendorValue2 = 0x0000ff00 & (vendorValue2 | input[index++] << (8 * 1));

                int vendorValue3 = 0;
                vendorValue3 = 0x000000ff & (vendorValue3 | input[index++] << (8 * 0));

                int vendorValueTotal = vendorValue | vendorValue1 | vendorValue2 | vendorValue3;


                long vendorLong = 0;
                vendorLong = 0x00000000ffffffffl & vendorValueTotal;

                avp.setVendorId(vendorLong);
            }


            //value

            int valueSize = (int) (lengthLong - header);

            byte[] value = new byte[valueSize];

            for (int x = 0; x < valueSize; x++) {
                value[x] = input[index++];
            }


            //padding shift

            if ((lengthLong % 4) != 0) {

                int paddingLength = (int) (4 - (lengthLong % 4));

                for (int x = 0; x < paddingLength; x++) {
                    index++;
                }
            }


            if (JdiameterProperties.getAvpTypeIsdn().contains(avpCodeString)) {

                byte[] result = decodeIsdnAvpValue(value);

                StringBuffer resultString = new StringBuffer();


                for (int x = 0; x < result.length; x++) {

                    resultString.append((char) (((result[x] & 0xf0) >> 4) + 48));

                    if (!((result[x] & 0x0f) == 0x0f)) {
                        resultString.append((char) ((result[x] & 0x0f) + 48));
                    }
                }


                avp.setValueType("isdn");

                avp.setValue(resultString.toString());

            } else if (JdiameterProperties.getAvpTypeIpAddress().contains(avpCodeString)) {

                byte[] result = decodeIpAddressAvpValue(value);

                avp.setValueType("ipAddress");

                avp.setValue(new String(result));

            } else if (JdiameterProperties.getAvpTypeString().contains(avpCodeString)) {


                byte[] result = decodeString(value);

                avp.setValueType("string");
                avp.setValue(new String(result).trim());

            } else if (JdiameterProperties.getAvpTypeInteger().contains(avpCodeString)) {


                byte[] result = decodeIntegerAvpValue(value);


                int intValue = 0;
                intValue = 0xff000000 & (intValue | result[0] << (8 * 3));

                int intValue1 = 0;
                intValue1 = 0x00ff0000 & (intValue1 | result[1] << (8 * 2));

                int intValue2 = 0;
                intValue2 = 0x0000ff00 & (intValue2 | result[2] << (8 * 1));

                int intValue3 = 0;
                intValue3 = 0x000000ff & (intValue3 | result[3] << (8 * 0));

                int intValueTotal = intValue1 | intValue | intValue3 | intValue2;


                long intlong = 0;
                intlong = 0x00000000ffffffffl & intValueTotal;

                avp.setValue(intlong);
                avp.setValueType("integer");

            } else if (JdiameterProperties.getAvpTypeGrouped().contains(avpCodeString)) {


                List<AVP> avpChilList = new ArrayList<AVP>();

                decodeAvpObject(avpChilList, value, 0, valueSize);

                avp.setValue(avpChilList);
                avp.setValueType("grouped");
            } else if (JdiameterProperties.getAvpTypeLocationEstimate().contains(avpCodeString)) {


                LocationEstimate valueNode = decodeLocationEstimateAvpValue(value);

                avp.setValue(valueNode);

                avp.setValueType("locationEstimate");

            }else  if (JdiameterProperties.getAvpTypeSHDataUDR().contains(avpCodeString)){

                XmlMapper xmlMapper = new XmlMapper();

                ECGI valueNode = decodeSHUserDataUDRAvpValue(value,xmlMapper);
                avp.setValue(valueNode);
                avp.setValueType("shdata");

            }else if(JdiameterProperties.getAvpTypeECGI().contains(avpCodeString)){

                ECGI valueNode = decodeECGIAvpValue(value);
                avp.setValue(valueNode);
                avp.setValueType("ecgi");

            } else {

                // decode byte string

                byte[] result = decodeByteStringValue(value);


                avp.setValue(new String(result));

                avp.setValueType("byteString");


            }

            if (index < diameterLength) {
                decodeAvpObject(avpList, input, index, diameterLength);
            }


            avpList.add(avp);

        }catch(Exception e){

            System.out.println("ini avp code penyebabnya:"+targetCode);
            e.printStackTrace();
        }

    }


    /**
     * This method is used to convert/decode hexstring avp avp value into plain text
     * @param input this is parameter to decodeByteStringValue method
     * @return this return is decoded hexstring
     * @version 1.0
     * @since   2017-01-23
     */
    public byte[] decodeByteStringValue(byte[] input){
        byte[] result = null;
        String hexString = new String(input);
        // String temo = Util.decodeFromHex(hexString);
        String temp= DatatypeConverter.printHexBinary(input);
        result = temp.getBytes();
        return result;
    }

    public static  ECGI decodeECGIAvpValue(byte[] value) {

        ECGI output = new ECGI();

        try {


            output.setECGI_HEX(DatatypeConverter.printHexBinary(value));


            byte mcc2 = (byte) (0xf0 & value[0]);
            byte mcc1 = (byte) (0x0f & value[0]);

            byte mnc3 = (byte) (0xf0 & value[1]);
            byte mcc3 = (byte) (0x0f & value[1]);

            byte mnc2 = (byte) (0xf0 & value[2]);
            byte mnc1 = (byte) (0x0f & value[2]);

            int mcc = 0;

            mcc =0x00000f00 & (mcc1 << 8);
            mcc =mcc | (0x000000f0 & mcc2);
            mcc =mcc | (0x0000000f & mcc3);


            output.setMCC(Integer.toHexString(mcc));


            int mnc =0;

            if(mnc3 == ((byte)0xF0)) {
                mnc = 0x000000f0 & (mnc1 << 4);
                mnc = mnc | (0x0000000f & (mnc2>>4));
            }



            output.setMNC(Integer.toHexString(mnc));


            // ECI Eutran Cell Id

            int eci = 0;

            eci = 0xff000000 & (value[3] << 8*3);

            eci =  eci | 0x00ff0000 & (value[4] << 8*2);

            eci =  eci | 0x0000ff00 & (value[5] << 8*1);

            eci =  eci | 0x000000ff & (value[6] << 8*0);



            output.setECI(eci);

            output.setECGI_HEX(Integer.toHexString(eci));



            int enb =0;

            enb = 0x00ff0000 & (value[3] << 8*2);

            enb =  enb | 0x0000ff00 & (value[4] << 8*1);

            enb =  enb | 0x000000ff & (value[5] << 8*0);


            output.setENB(enb);

            output.setENB_HEX(Integer.toHexString(enb));



            int ci =0;


            ci =  ci | 0x000000ff & (value[6] << 8*0);

            output.setCellId(ci);

            output.setCellId_HEX(Integer.toHexString(ci));


            System.out.println("decoding  ecgi result:");
            System.out.println(((new ObjectMapper()).writerWithDefaultPrettyPrinter().writeValueAsString(output)));

        } catch (Exception e) {
            logger.error("error when decoding",e);
        }

        return output;

    }

    public static ECGI decodeSHUserDataUDRAvpValue(byte[] value, XmlMapper mapper) {

        ECGI output = new ECGI();

        try {

            JsonNode input =   mapper.readTree(value);

            JsonNode ecgiNode =     input.findValue("E-UTRANCellGlobalId");

            String ecgi = ecgiNode.asText();

            byte[] ecgiByte =  DatatypeConverter.parseBase64Binary(ecgi);

            output.setECGI_HEX(DatatypeConverter.printHexBinary(ecgiByte));


            byte mcc2 = (byte) (0xf0 & ecgiByte[0]);
            byte mcc1 = (byte) (0x0f & ecgiByte[0]);

            byte mnc3 = (byte) (0xf0 & ecgiByte[1]);
            byte mcc3 = (byte) (0x0f & ecgiByte[1]);

            byte mnc2 = (byte) (0xf0 & ecgiByte[2]);
            byte mnc1 = (byte) (0x0f & ecgiByte[2]);

            int mcc = 0;

            mcc =0x00000f00 & (mcc1 << 8);
            mcc =mcc | (0x000000f0 & mcc2);
            mcc =mcc | (0x0000000f & mcc3);


            output.setMCC(Integer.toHexString(mcc));


            int mnc =0;

            if(mnc3 == ((byte)0xF0)) {
                mnc = 0x000000f0 & (mnc1 << 4);
                mnc = mnc | (0x0000000f & (mnc2>>4));
            }



            output.setMNC(Integer.toHexString(mnc));


            // ECI Eutran Cell Id

            int eci = 0;

            eci = 0xff000000 & (ecgiByte[3] << 8*3);

            eci =  eci | 0x00ff0000 & (ecgiByte[4] << 8*2);

            eci =  eci | 0x0000ff00 & (ecgiByte[5] << 8*1);

            eci =  eci | 0x000000ff & (ecgiByte[6] << 8*0);



            output.setECI(eci);

            output.setECGI_HEX(Integer.toHexString(eci));



            int enb =0;

            enb = 0x00ff0000 & (ecgiByte[3] << 8*2);

            enb =  enb | 0x0000ff00 & (ecgiByte[4] << 8*1);

            enb =  enb | 0x000000ff & (ecgiByte[5] << 8*0);


            output.setENB(enb);

            output.setENB_HEX(Integer.toHexString(enb));



            int ci =0;


            ci =  ci | 0x000000ff & (ecgiByte[6] << 8*0);

            output.setCellId(ci);

            output.setCellId_HEX(Integer.toHexString(ci));


            System.out.println("decoding  SHUserDataUDR result:");
            System.out.println(((new ObjectMapper()).writerWithDefaultPrettyPrinter().writeValueAsString(output)));

        } catch (Exception e) {
            logger.error("error when decoding",e);
            e.printStackTrace();
        }

        return output;

    }
    /**
     * This method is used to convert/decode string avp avp value into plain text
     * @param input this is parameter to decodeString method
     * @return this return is decoded string
     * @version 1.0
     * @since   2017-01-23
     */
    public byte[] decodeString(byte[] input){

        String stringValue = new String(input);

        return stringValue.getBytes();
    }


    /**
     * This method is used to convert/decode isdn avp value, swap back between 2 bytes and convert it into plain text
     * @param isdn this is parameter to decodeIsdnAvpValue method
     * @return this return is isdn which already swapped back
     * @version 1.0
     * @since   2017-01-23
     */

    @Override
    public byte[] decodeIsdnAvpValue(byte[] isdn) {


        byte[] result = new byte[isdn.length];


        for(int x=0;x<isdn.length;x++){


            byte temp1 = (byte) (0xf0 & (isdn[x] << 4 ));
            byte temp2 = (byte) (0x0f & (isdn[x] >> 4 ));

            byte temptotal = (byte) (temp1 | temp2);

            result[x] = temptotal;

        }

        return  result;
    }



    public static void main(String args[]){


        //type 0
        byte[] temp= DatatypeConverter.parseHexBinary("00230bb921325323");

        // String value = new String(temp);
        //System.out.println(value);

        // type 1
        // byte[] temp= DatatypeConverter.parseHexBinary("10230bb921325323");

        // type 3
        //byte[] temp= DatatypeConverter.parseHexBinary("30230bb9213253232222ff");


        //type 5 polygon
        //byte[] temp= DatatypeConverter.parseHexBinary("52230bb9213253232222ff2323");


        //type 8 ellipsoid with altitude
        // byte[] temp= DatatypeConverter.parseHexBinary("82230bb92132538322");

        //type 9
        // byte[] temp= DatatypeConverter.parseHexBinary("92230bb9213253832223232323ff");


        // type 10
        //byte[] temp= DatatypeConverter.parseHexBinary("a2230bb9213253832223232323");



        decodeLocationEstimateAvpValue(temp);


    }

    /**
     * this method and module is hard please carefully to change it
     * creator gede
     * @param value
     * @param
     * @return
     */


    public static  LocationEstimate decodeLocationEstimateAvpValue(byte[] value) {

        LocationEstimate result = new LocationEstimate();

        int sign =0;
        double latitudeDouble=0.0;
        double longitudeDouble=0.0;





        // for uncertainty circle
        int uncertaintyCode=0;
        double uncertaintyMeter =0.0;


        // for uncertainty ellipse
        int unCertaintyCodeSemiMinor=0;
        double unCertaintyCodeSemiMinorMeter=0.0;

        int unCertaintyCodeSemiMajor=0;
        double unCertaintyCodeSemiMajorMeter=0.0;

        int orientationMajorAxis=0;
        int confidence =0;



        // for polygon
        List<Double> latitudeList = new ArrayList<Double>();
        List<Double> longitudeList = new ArrayList<Double>();
        List<Integer> signList = new ArrayList<Integer>();



        // for altitude
        //0 height 1 depth
        int directionAltitude=0;
        // in meter
        int altitude=0;


        int unCertaintyAltitude=0;
        double unCertaintyAltitudeMeter=0.0;


        // for innerRadius meter
        int innerRadius =0;

        int unCertaintyInnerRadius=0;
        double unCertaintyInnerRadiusMeter=0.0;



        int offsetAngle=0;

        int includedAngle=0;


        if(value!=null){
            if(value.length>0){

                // type of shape
                int typeOfshape = (byte) ((0xf0  & value[0]) >> 4);

                System.out.println("typeOfshape:"+typeOfshape);

                result.setTypeOfshape(typeOfshape);
                /*
                 * 0 ellipsoidPoint
                 * 1 ellipsoidPoint with uncertainty circle
                 * 3 ellipsoidPoint with uncertainty ellipse
                 * 5 pollygon
                 * 8 ellipsoidPoint with altitude
                 * 9 ellipsoidPoint with altitude and uncertainty ellipsoid
                 * 10 ellipsoidPoint Arc
                 * */


                //ellipsoidPoint
                if(typeOfshape ==0){
                    // sign 0 north, sign 1 south
                    sign = (byte)((0x80 & value[1]) >>7);

                    System.out.println("sign:"+sign);

                    result.setSign(sign);

                    int latitudeInt1 =    ((0x7f & value[1]) << (8*2)) & 0x00FF0000 ;
                    int latitudeInt2 =   (value[2] << (8*1)) & 0x0000FF00;
                    int latitudeInt3 =     (value[3] << (8*0)) & 0x000000FF;
                    int latitudeTotal = latitudeInt1 | latitudeInt2 | latitudeInt3;

                    System.out.println("latitudeTotalInt:"+latitudeTotal);


                    result.setLatitudeTotalInt(latitudeTotal);


                    latitudeDouble = latitudeTotal/((Math.pow(2,23))/90);

                    System.out.println("latitudeTotalDegress:"+latitudeDouble);
                    result.setLatitudeTotalDegress(latitudeDouble);



                    int longitudeInt1 =    (value[4] << (8*2)) & 0x00FF0000 ;
                    int longitudeInt2 =   (value[5] << (8*1)) & 0x0000FF00;
                    int longitudeInt3 =     (value[6] << (8*0)) & 0x000000FF;
                    int longitudeTotal = longitudeInt1 | longitudeInt2 | longitudeInt3;

                    System.out.println("longitudeTotalInt:"+longitudeTotal);
                    result.setLongitudeTotalInt(longitudeTotal);

                    longitudeDouble = longitudeTotal/((Math.pow(2,24))/360);

                    System.out.println("longitudeTotalDegress:"+longitudeDouble);

                    result.setLongitudeTotalDegress(longitudeDouble);

                }


                //ellipsoidPoint with uncertainty circle
                else if(typeOfshape ==1){
                    // sign 0 north, sign 1 south
                    sign = (byte)((0x80 & value[1]) >>7);

                    System.out.println("sign:"+sign);
                    result.setSign(sign);

                    int latitudeInt1 =    ((0x7f & value[1]) << (8*2)) & 0x00FF0000 ;
                    int latitudeInt2 =   (value[2] << (8*1)) & 0x0000FF00;
                    int latitudeInt3 =     (value[3] << (8*0)) & 0x000000FF;
                    int latitudeTotal = latitudeInt1 | latitudeInt2 | latitudeInt3;

                    System.out.println("latitudeTotalInt:"+latitudeTotal);
                    result.setLatitudeTotalInt(latitudeTotal);

                    latitudeDouble = latitudeTotal/((Math.pow(2,23))/90);

                    System.out.println("latitudeTotalDegress:"+latitudeDouble);

                    result.setLatitudeTotalDegress(latitudeDouble);

                    int longitudeInt1 =    (value[4] << (8*2)) & 0x00FF0000 ;
                    int longitudeInt2 =   (value[5] << (8*1)) & 0x0000FF00;
                    int longitudeInt3 =     (value[6] << (8*0)) & 0x000000FF;
                    int longitudeTotal = longitudeInt1 | longitudeInt2 | longitudeInt3;

                    System.out.println("longitudeTotalInt:"+longitudeTotal);
                    result.setLongitudeTotalInt(longitudeTotal);

                    longitudeDouble = longitudeTotal/((Math.pow(2,24))/360);

                    System.out.println("longitudeTotalDegress:"+longitudeDouble);
                    result.setLongitudeTotalDegress(longitudeDouble);

                    uncertaintyCode = (0x0000007f & value[7]);

                    System.out.println("uncertaintyCode:"+uncertaintyCode);

                    result.setUncertaintyCode(uncertaintyCode);

                    uncertaintyMeter = 10*(Math.pow((1+0.1),uncertaintyCode)-1);

                    System.out.println("uncertaintyMeter:"+uncertaintyMeter);

                    result.setUncertaintyMeter(uncertaintyMeter);

                }

                //ellipsoidPoint with uncertainty elipsse
                else if(typeOfshape ==3){
                    // sign 0 north, sign 1 south
                    sign = (byte)((0x80 & value[1]) >>7);

                    System.out.println("sign:"+sign);
                    result.setSign(sign);

                    int latitudeInt1 =    ((0x7f & value[1]) << (8*2)) & 0x00FF0000 ;
                    int latitudeInt2 =   (value[2] << (8*1)) & 0x0000FF00;
                    int latitudeInt3 =     (value[3] << (8*0)) & 0x000000FF;
                    int latitudeTotal = latitudeInt1 | latitudeInt2 | latitudeInt3;

                    System.out.println("latitudeTotalInt:"+latitudeTotal);
                    result.setLatitudeTotalInt(latitudeTotal);
                    latitudeDouble = latitudeTotal/((Math.pow(2,23))/90);

                    System.out.println("latitudeTotalDegress:"+latitudeDouble);

                    result.setLatitudeTotalDegress(latitudeDouble);
                    int longitudeInt1 =    (value[4] << (8*2)) & 0x00FF0000 ;
                    int longitudeInt2 =   (value[5] << (8*1)) & 0x0000FF00;
                    int longitudeInt3 =     (value[6] << (8*0)) & 0x000000FF;
                    int longitudeTotal = longitudeInt1 | longitudeInt2 | longitudeInt3;

                    System.out.println("longitudeTotalInt:"+longitudeTotal);
                    result.setLongitudeTotalInt(longitudeTotal);

                    longitudeDouble = longitudeTotal/((Math.pow(2,24))/360);

                    System.out.println("longitudeTotalDegress:"+longitudeDouble);
                    result.setLongitudeTotalDegress(longitudeDouble);


                    // uncertaintySemiMajor
                    unCertaintyCodeSemiMajor = (0x0000007f  & value[7]);

                    System.out.println("unCertaintyCodeSemiMajor:"+unCertaintyCodeSemiMajor);
                    result.setUnCertaintyCodeSemiMajor(unCertaintyCodeSemiMajor);


                    unCertaintyCodeSemiMajorMeter = 10*(Math.pow((1+0.1),unCertaintyCodeSemiMajor)-1);

                    System.out.println("unCertaintyCodeSemiMajorMeter:"+unCertaintyCodeSemiMajorMeter);
                    result.setUnCertaintyCodeSemiMajorMeter(unCertaintyCodeSemiMajorMeter);

                    // uncertaintySemiMajor
                    unCertaintyCodeSemiMinor = (0x0000007f  & value[8]);

                    System.out.println("unCertaintyCodeSemiMinor:"+unCertaintyCodeSemiMinor);

                    result.setUnCertaintyCodeSemiMinor(unCertaintyCodeSemiMinor);


                    unCertaintyCodeSemiMinorMeter = 10*(Math.pow((1+0.1),unCertaintyCodeSemiMinor)-1);

                    System.out.println("unCertaintyCodeSemiMinorMeter:"+unCertaintyCodeSemiMinorMeter);


                    result.setUnCertaintyCodeSemiMinorMeter(unCertaintyCodeSemiMinorMeter);

                    // orientationMajorAxis
                    orientationMajorAxis = 2*(0x000000ff & value[9]);

                    System.out.println("orientationMajorAxis:"+orientationMajorAxis);



                    result.setOrientationMajorAxis(orientationMajorAxis);

                    // confidence
                    confidence = (0x000000ff & value[10]);

                    System.out.println("confidence:"+confidence);
                    result.setConfidence(confidence);


                }  // polygon
                if(typeOfshape ==5){

                    // number of points
                    int count = ((0x0f & value[0]));




                    for(int x=0;x<count;x++) {

                        int n= x*6;

                        // sign 0 north, sign 1 south
                        sign = (byte) ((0x80 & value[(1+n)]) >> 7);

                        System.out.println("sign:" + sign);
                        result.signList.add(sign);

                        int latitudeInt1 = ((0x7f & value[(1+n)]) << (8 * 2)) & 0x00FF0000;
                        int latitudeInt2 = (value[(2+n)] << (8 * 1)) & 0x0000FF00;
                        int latitudeInt3 = (value[(3+n)] << (8 * 0)) & 0x000000FF;
                        int latitudeTotal = latitudeInt1 | latitudeInt2 | latitudeInt3;

                        System.out.println("latitudeTotalInt:" + latitudeTotal);

                        result.latitudeTotalIntList.add(latitudeTotal);

                        latitudeDouble = latitudeTotal / ((Math.pow(2, 23)) / 90);

                        System.out.println("latitudeTotalDegress:" + latitudeDouble);

                        result.latitudeTotalDegressList.add(latitudeDouble);

                        latitudeList.add(latitudeDouble);


                        int longitudeInt1 = (value[(4+n)] << (8 * 2)) & 0x00FF0000;
                        int longitudeInt2 = (value[(5+n)] << (8 * 1)) & 0x0000FF00;
                        int longitudeInt3 = (value[(6+n)] << (8 * 0)) & 0x000000FF;
                        int longitudeTotal = longitudeInt1 | longitudeInt2 | longitudeInt3;

                        System.out.println("longitudeTotalInt:" + longitudeTotal);


                        result.longitudeTotalIntList.add(longitudeTotal);

                        longitudeDouble = longitudeTotal / ((Math.pow(2, 24)) / 360);

                        System.out.println("longitudeTotalDegress:" + longitudeDouble);


                        result.longitudeTotalDegressList.add(longitudeDouble);
                        longitudeList.add(longitudeDouble);




                    }
                    // ellipsoidPoint with altitude

                }else if(typeOfshape ==8){
                    // sign 0 north, sign 1 south
                    sign = (byte)((0x80 & value[1]) >>7);

                    System.out.println("sign:"+sign);
                    result.setSign(sign);


                    int latitudeInt1 =    ((0x7f & value[1]) << (8*2)) & 0x00FF0000 ;
                    int latitudeInt2 =   (value[2] << (8*1)) & 0x0000FF00;
                    int latitudeInt3 =     (value[3] << (8*0)) & 0x000000FF;
                    int latitudeTotal = latitudeInt1 | latitudeInt2 | latitudeInt3;

                    System.out.println("latitudeTotalInt:"+latitudeTotal);
                    result.setLatitudeTotalInt(latitudeTotal);
                    latitudeDouble = latitudeTotal/((Math.pow(2,23))/90);

                    System.out.println("latitudeTotalDegress:"+latitudeDouble);
                    result.setLatitudeTotalDegress(latitudeDouble);
                    int longitudeInt1 =    (value[4] << (8*2)) & 0x00FF0000 ;
                    int longitudeInt2 =   (value[5] << (8*1)) & 0x0000FF00;
                    int longitudeInt3 =     (value[6] << (8*0)) & 0x000000FF;
                    int longitudeTotal = longitudeInt1 | longitudeInt2 | longitudeInt3;

                    System.out.println("longitudeTotalInt:"+longitudeTotal);
                    result.setLongitudeTotalInt(longitudeTotal);

                    longitudeDouble = longitudeTotal/((Math.pow(2,24))/360);

                    System.out.println("longitudeTotalDegress:"+longitudeDouble);
                    result.setLongitudeTotalDegress(longitudeDouble);
                    // altitude

                    directionAltitude = (byte) ((0x80 & value[7]) >> 7)  ;


                    System.out.println("directionAltitude:"+directionAltitude);
                    result.setDirectionAltitude(directionAltitude);

                    int altitudeInt1 =    (value[7] << (8*1)) & 0x00007F00 ;
                    int altitudeInt2 =   (value[8] << (8*0)) & 0x000000FF;
                    int altitudeTotal = altitudeInt1 | altitudeInt2;

                    altitude = altitudeTotal;

                    System.out.println("altitude:"+altitude);
                    result.setAltitude(altitude);


                }
                else if(typeOfshape ==9){
                    // sign 0 north, sign 1 south
                    sign = (byte)((0x80 & value[1]) >>7);

                    System.out.println("sign:"+sign);
                    result.setSign(sign);

                    int latitudeInt1 =    ((0x7f & value[1]) << (8*2)) & 0x00FF0000 ;
                    int latitudeInt2 =   (value[2] << (8*1)) & 0x0000FF00;
                    int latitudeInt3 =     (value[3] << (8*0)) & 0x000000FF;
                    int latitudeTotal = latitudeInt1 | latitudeInt2 | latitudeInt3;

                    System.out.println("latitudeTotalInt:"+latitudeTotal);
                    result.setLatitudeTotalInt(latitudeTotal);

                    latitudeDouble = latitudeTotal/((Math.pow(2,23))/90);

                    System.out.println("latitudeTotalDegress:"+latitudeDouble);
                    result.setLatitudeTotalDegress(latitudeDouble);

                    int longitudeInt1 =    (value[4] << (8*2)) & 0x00FF0000 ;
                    int longitudeInt2 =   (value[5] << (8*1)) & 0x0000FF00;
                    int longitudeInt3 =     (value[6] << (8*0)) & 0x000000FF;
                    int longitudeTotal = longitudeInt1 | longitudeInt2 | longitudeInt3;

                    System.out.println("longitudeTotalInt:"+longitudeTotal);
                    result.setLongitudeTotalInt(longitudeTotal);
                    longitudeDouble = longitudeTotal/((Math.pow(2,24))/360);

                    System.out.println("longitudeTotalDegress:"+longitudeDouble);
                    result.setLongitudeTotalDegress(longitudeDouble);


                    // altitude

                    directionAltitude = (byte) ((0x80 & value[7]) >> 7)  ;
                    System.out.println("directionAltitude:"+directionAltitude);
                    result.setDirectionAltitude(directionAltitude);

                    int altitudeInt1 =    (value[7] << (8*1)) & 0x00007F00 ;
                    int altitudeInt2 =   (value[8] << (8*0)) & 0x000000FF;
                    int altitudeTotal = altitudeInt1 | altitudeInt2;

                    altitude = altitudeTotal;

                    System.out.println("altitude:"+altitude);
                    result.setAltitude(altitude);

                    // uncertaintySemiMajor
                    unCertaintyCodeSemiMajor = (0x0000007f  & value[9]);

                    System.out.println("unCertaintyCodeSemiMajor:"+unCertaintyCodeSemiMajor);
                    result.setUnCertaintyCodeSemiMajor(unCertaintyCodeSemiMajor);
                    unCertaintyCodeSemiMajorMeter = 10*(Math.pow((1+0.1),unCertaintyCodeSemiMajor)-1);

                    System.out.println("unCertaintyCodeSemiMajorMeter:"+unCertaintyCodeSemiMajorMeter);
                    result.setUnCertaintyCodeSemiMajorMeter(unCertaintyCodeSemiMajorMeter);

                    // uncertaintySemiMajor
                    unCertaintyCodeSemiMinor = (0x0000007f  & value[10]);

                    System.out.println("unCertaintyCodeSemiMinor:"+unCertaintyCodeSemiMinor);
                    result.setUnCertaintyCodeSemiMinor(unCertaintyCodeSemiMinor);


                    unCertaintyCodeSemiMinorMeter = 10*(Math.pow((1+0.1),unCertaintyCodeSemiMinor)-1);

                    System.out.println("unCertaintyCodeSemiMinorMeter:"+unCertaintyCodeSemiMinorMeter);

                    result.setUnCertaintyCodeSemiMinorMeter(unCertaintyCodeSemiMinorMeter);

                    // orientationMajorAxis
                    orientationMajorAxis = 2*(0x000000ff & value[11]);

                    System.out.println("orientationMajorAxis:"+orientationMajorAxis);
                    result.setOrientationMajorAxis(orientationMajorAxis);



                    // uncertaintyAltitude
                    unCertaintyAltitude = (0x0000007f  & value[12]);

                    System.out.println("unCertaintyAltitude:"+unCertaintyAltitude);
                    result.setUnCertaintyAltitude(unCertaintyAltitude);

                    unCertaintyAltitudeMeter = 45*(Math.pow((1+0.025),unCertaintyAltitude)-1);

                    System.out.println("unCertaintyAltitudeMeter:"+unCertaintyAltitudeMeter);
                    result.setUnCertaintyAltitudeMeter(unCertaintyAltitudeMeter);

                    // confidence
                    confidence = (0x000000ff & value[13]);

                    System.out.println("confidence:"+confidence);
                    result.setConfidence(confidence);


                }// ellipsoidArc
                else if(typeOfshape ==10){
                    // sign 0 north, sign 1 south
                    sign = (byte)((0x80 & value[1]) >>7);

                    System.out.println("sign:"+sign);
                    result.setSign(sign);

                    int latitudeInt1 =    ((0x7f & value[1]) << (8*2)) & 0x00FF0000 ;
                    int latitudeInt2 =   (value[2] << (8*1)) & 0x0000FF00;
                    int latitudeInt3 =     (value[3] << (8*0)) & 0x000000FF;
                    int latitudeTotal = latitudeInt1 | latitudeInt2 | latitudeInt3;

                    System.out.println("latitudeTotalInt:"+latitudeTotal);
                    result.setLatitudeTotalInt(latitudeTotal);
                    latitudeDouble = latitudeTotal/((Math.pow(2,23))/90);

                    System.out.println("latitudeTotalDegress:"+latitudeDouble);

                    result.setLatitudeTotalDegress(latitudeDouble);
                    int longitudeInt1 =    (value[4] << (8*2)) & 0x00FF0000 ;
                    int longitudeInt2 =   (value[5] << (8*1)) & 0x0000FF00;
                    int longitudeInt3 =     (value[6] << (8*0)) & 0x000000FF;
                    int longitudeTotal = longitudeInt1 | longitudeInt2 | longitudeInt3;

                    System.out.println("longitudeTotalInt:"+longitudeTotal);


                    result.setLongitudeTotalInt(longitudeTotal);

                    longitudeDouble = longitudeTotal/((Math.pow(2,24))/360);

                    System.out.println("longitudeTotalDegress:"+longitudeDouble);

                    result.setLongitudeTotalDegress(longitudeDouble);


                    // inner radius

                    int innerRadiusInt1 =   (value[7] << (8*1)) & 0x0000FF00;
                    int innerRadiusInt2 =     (value[8] << (8*0)) & 0x000000FF;
                    int innerRadiusIntTotal = innerRadiusInt1 | innerRadiusInt2 ;

                    innerRadius = 5* innerRadiusIntTotal;

                    System.out.println("innerRadius:"+innerRadius);
                    result.setInnerRadius(innerRadius);


                    // uncertainty inner radius
                    unCertaintyInnerRadius = (0x0000007f  & value[9]);

                    System.out.println("unCertaintyInnerRadius:"+unCertaintyInnerRadius);

                    result.setUnCertaintyInnerRadius(unCertaintyInnerRadius);

                    unCertaintyInnerRadiusMeter = 10*(Math.pow((1+0.1),unCertaintyInnerRadius)-1);

                    System.out.println("unCertaintyInnerRadiusMeter:"+unCertaintyInnerRadiusMeter);
                    result.setUnCertaintyInnerRadiusMeter(unCertaintyInnerRadiusMeter);


                    // offset angle
                    offsetAngle = 2* (0x000000ff  & value[10]);

                    System.out.println("offsetAngle:"+offsetAngle);
                    result.setOffsetAngle(offsetAngle);

                    // included angle
                    includedAngle = 2* (0x000000ff  & value[11]);

                    System.out.println("includedAngle:"+includedAngle);
                    result.setIncludedAngle(includedAngle);
                    // confidence
                    confidence = (0x000000ff & value[12]);

                    System.out.println("confidence:"+confidence);
                    result.setConfidence(confidence);

                }







            }
        }


        System.out.println();


        try {
            System.out.println("decoding location estimate result:");
            System.out.println(((new ObjectMapper()).writerWithDefaultPrettyPrinter().writeValueAsString(result)));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        return  result;
    }



    /**
     * This method is used to convert/decode integer avp value, reverse back the value
     * where the value's byte was resversed on encode and convert it into plain text
     * @param input this is parameter to decodeIntegerAvpValue method
     * @return this return is integer value which already reversed back
     * @version 1.0
     * @since   2017-01-23
     */
    @Override
    public byte[] decodeIntegerAvpValue(byte[] input) {


        int intValue = 0;
        intValue = 0xff000000 & (intValue | input[0] << (8 * 3));

        int  intValue1 =0;
        intValue1 = 0x00ff0000 & (intValue1 | input[1] << (8 * 2));

        int intValue2 =0;
        intValue2 = 0x0000ff00 & (intValue2 | input[2] << (8 * 1));

        int  intValue3 =0;
        intValue3 =  0x000000ff &(intValue3 | input[3] << (8 * 0));

        int intValueTotal = intValue1 | intValue | intValue3 | intValue2;


        //Integer reversedInt = Integer.reverseBytes(intValueTotal);

        Integer reversedInt = intValueTotal;

        byte[] reversedByte = new byte[4];

        reversedByte[0] = (byte) ((0xff000000 & reversedInt) >>(8*3));
        reversedByte[1] = (byte) ((0x00ff0000 & reversedInt) >>(8*2));
        reversedByte[2] = (byte) ((0x0000ff00 & reversedInt) >>(8*1));
        reversedByte[3] = (byte) ((0x000000ff & reversedInt) >>(8*0));

        return  reversedByte;
    }

    /**
     * This method is used for convert avp ip address value into its numeric binary form
     * @param src this is first parameter to decodeIpAddressAvpValue method
     * @return this return is decoded ip address into plain text ip address
     * @version 1.0
     * @since   2017-01-23
     */
    @Override
    public byte[] decodeIpAddressAvpValue(byte[] src) {

        int ipFamilyValue = new BigInteger(Arrays.copyOfRange(src, 0, 2)).intValue();

        byte[] ipEncode = new byte[src.length - 2]; // 2 bits first for flag that ip is ipv4 or ipv6 (not main value)
        byte[] resultValue = new byte[ipEncode.length];

        for (int a = 2; a < src.length; a++) {
            ipEncode[a - 2] = src[a];
        }
        try {

            if (ipFamilyValue == 1) {
                Inet4Address ip4Address = (Inet4Address) Inet4Address.getByAddress(ipEncode);

                String ipaddress = ip4Address.getHostAddress();
                resultValue = ipaddress.getBytes();
            } else if (ipFamilyValue == 2) {
                Inet6Address inet6Address = (Inet6Address) Inet6Address.getByAddress(ipEncode);

                String ipaddress = inet6Address.getHostAddress();
                resultValue = ipaddress.getBytes();
            }

        }catch (Exception e){

        }

        return resultValue;
    }


}
