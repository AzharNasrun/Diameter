package main;

import Decode.DecodeImplements;
import Decode.DecodeInterface;
import Encode.EncodeImplements;
import Encode.EncodeInterface;
import Utils.Util;
import constant.Constants;
import model.AVP;
import model.CommandCode;
import model.Diameter;
import model.DiameterFlag;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by gede on 15/01/18.
 */
/**
 * This class is used for start the process the encode and decode diameter
 * Created by gede on 15/01/18.
 * @version 1.0
 * @since  23-01-2018
 */


public class DMRMain {

    private static final Logger logger = Logger.getLogger(Constants.ENCODER_LOG);


    static EncodeInterface  encoder = new EncodeImplements();
    static DecodeInterface decoder = new DecodeImplements();


    public static List<Byte> ObjectToDiameterMessage(Diameter input){

        List<Byte> result = new ArrayList<Byte>();

        try {



            // uuid
            String uuid = input.getUuid();
            logger.info("uuid:"+uuid);

            // version
            int version = input.getVersion();
            logger.info("version:"+version);


            int flags=0x00;

            //flags
            DiameterFlag flag = input.getFlags();
            if(flag!=null) {


                boolean request = flag.isRequest();
                if (request) {
                    flags = 0x80;
                }
                logger.info("request:" + request);



                boolean proxyable = flag.isProxyable();

                if (proxyable) {
                    flags = flags | 0x40;
                }

                logger.info("proxyable:" + proxyable);



                boolean error = flag.isError();

                if (error) {
                    flags = flags | 0x20;
                }

                logger.info("error:" + error);

                boolean transmitted = flag.isTransmitted();

                if (transmitted) {
                    flags = flags | 0x10;
                }

                logger.info("transmitted:" + transmitted);
                logger.info("flags hexa:" + Util.toHexadecimal(flags));
                logger.info("flags char:" + (char) flags);

            }


            // commandCode

            int commandCode = (int)input.getCommandCode();

            logger.info("commandcode:"+commandCode);


            // applicationid


            int applicationId = (int)input.getApplicationId();
            logger.info("applicationId:"+applicationId);


            // hopByhop

            Random rand  = new Random();
            int radomInt = Math.abs(rand.nextInt());
            int randomInt2 = Math.abs(rand.nextInt());


            int hop;
            if(input.getHopByHopId() != 0) {
                hop = (int)input.getHopByHopId();
                logger.info("hopbyhop:" + hop);
            }else{
                hop =radomInt;
            }
            // end


            long end;
            if(input.getEndToEndId() != 0) {
                end = input.getEndToEndId();
                logger.info("endtoend:" + end);
            }else{

                if((commandCode ==257)||(commandCode==280) ) {
                    end = randomInt2;
                }else{
                    end = Math.abs(rand.nextInt());
                }
            }




            // avps


             List<AVP> avpList  = input.getAvpList();
            List<Byte> avpMessage = new ArrayList<Byte>();

             for(AVP item : avpList){
                 avpMessage.addAll(encoder. encodeAvpObject(item));

             }



            // + 20 is length for diameter header.
            List<Byte>  diameterHeaderMessage =  encoder.encodeDiameterHeader((char)version,(avpMessage.size()+20),(char)flags,commandCode,applicationId,hop,end);


            result.addAll(diameterHeaderMessage);
            result.addAll(avpMessage);


        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }


        return result;
    }






    public static Diameter diameterMessageToObject(byte[] input){

        Diameter result = new Diameter();



        try {




            int index=0;

            // diameter version

            int version = input[index++];
            result.setVersion(version);

            // diameter length
            int length = 0;
            length = 0x00ff0000 & (length | input[index++] << (8 * 2));

            int length1 =0;
            length1 = 0x0000ff00 & (length1 | input[index++] << (8 * 1));

            int length2=0;
            length2 = 0x000000ff & (length2 | input[index++] << (8 * 0));

            int lengthTotal = length | length1 |  length2;

            long lengthLong = 0;
            lengthLong = 0x00000000ffffffffl & lengthTotal ;

           System.out.println("panjang byte di message:"+lengthLong);

            DiameterFlag diameterFlag = new DiameterFlag();



            // flag
            byte flagByte =  input[index++];
            boolean request = false;
            if((flagByte & 0x80)== 0x80){
                request = true;
            }

            diameterFlag.setRequest(request);

            boolean proxyable = false;
            if((flagByte & 0x40)== 0x40){
                proxyable = true;
            }

            diameterFlag.setProxyable(proxyable);

            boolean error = false;
            if((flagByte & 0x20)== 0x20){
                error = true;
            }

            diameterFlag.setError(error);


            boolean transmitted = false;
            if((flagByte & 0x10)== 0x10){
                transmitted = true;
            }


            diameterFlag.setTransmitted(transmitted);
            result.setFlags(diameterFlag);

            // command code

            int commandcode = 0;
            commandcode = 0x00ff0000 & (commandcode | input[index++] << (8 * 2));

            int commandcode1 = 0;
            commandcode1 = 0x0000ff00 & (commandcode1 | input[index++] << (8 * 1));

            int commandcode2= 0;
            commandcode2 = 0x000000ff & (commandcode2 | input[index++] << (8 * 0));

            int commandcodeTotal = commandcode | commandcode1 | commandcode2;


            long commandcodeLong = 0;
            commandcodeLong = 0x00000000ffffffffl & commandcodeTotal ;

            result.setCommandCode(commandcodeLong);
            result.setType(CommandCode.getByCommandCode(commandcodeLong).toString());

            // application id
            int applicationId = 0;
            applicationId = 0xff000000 & (applicationId | input[index++] << (8 * 3));

            int applicationId1 = 0;
            applicationId1 = 0x00ff0000 & (applicationId1 | input[index++] << (8 * 2));

            int applicationId2 = 0;
            applicationId2 = 0x0000ff00 & (applicationId2 | input[index++] << (8 * 1));

            int applicationId3 = 0;
            applicationId3 = 0x000000ff & (applicationId3 | input[index++] << (8 * 0));

            int applicationIdTotal = applicationId | applicationId1 | applicationId2 | applicationId3;

            long applicationIdLong = 0;
            applicationIdLong = 0x00000000ffffffffl & applicationIdTotal ;

            result.setApplicationId(applicationIdLong);


            // hopByhop id
            int hopbyhopId = 0;
            hopbyhopId = 0xff000000 & (hopbyhopId | input[index++] << (8 * 3));

            int hopbyhopId1 =0;
            hopbyhopId1 = 0x00ff0000 & (hopbyhopId1 | input[index++] << (8 * 2));

            int hopbyhopId2=0;
            hopbyhopId2 = 0x0000ff00 & (hopbyhopId2 | input[index++] << (8 * 1));

            int hopbyhopId3=0;
            hopbyhopId3 = 0x000000ff & (hopbyhopId3 | input[index++] << (8 * 0));


            int hopbyhopIdTotal = hopbyhopId | hopbyhopId1 |  hopbyhopId2 | hopbyhopId3;


            long hopbyhopIdLong = 0;
            hopbyhopIdLong = 0x00000000ffffffffl & hopbyhopIdTotal ;



            result.setHopByHopId(hopbyhopIdLong);
            // endtoend id



            long endToEndId = 0;
            endToEndId = 0xff000000 & (endToEndId | input[index++] << (8 * 3));

            long  endToEndId1 =0;
            endToEndId1 = 0x00ff0000 & (endToEndId1 | input[index++] << (8 * 2));

            long endToEndId2 =0;
            endToEndId2 = 0x0000ff00 & (endToEndId2 | input[index++] << (8 * 1));

            long  endToEndId3 =0;
            endToEndId3 =  0x000000ff &(endToEndId3 | input[index++] << (8 * 0));

            long endToEndIdTotal = endToEndId | endToEndId1 | endToEndId2 | endToEndId3;

            //System.out.println("hex value:"+Integer.toHexString(endToEndIdTotal));


            long endToEndIdLong = 0;
            endToEndIdLong = 0x00000000ffffffffl & endToEndIdTotal ;


            result.setEndToEndId(endToEndIdLong);





            List<AVP> avpList = new ArrayList<AVP>();

            decoder.decodeAvpObject(avpList,input,index,lengthLong);

/*

            //sorting
            avpsArray =  Utils.Util.reversedArrayNode(avpsArray,mapper);

  */

            result.setAvpList(avpList);

            logger.info("constructed Json:"+result.toString());


        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return result;
    }











}
