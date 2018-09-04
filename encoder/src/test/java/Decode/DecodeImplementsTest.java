package Decode;

import Constant.Constant;
import Encode.EncodeImplements;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by aji on 23/01/18.
 */
public class DecodeImplementsTest{

    EncodeImplements encode = new EncodeImplements();
    DecodeImplements decode = new DecodeImplements();
    Constant cons = new Constant();


    public byte[] getValue(List<Byte> source){
        int lengHeader = 8; // note length header 8 if flag parameter value is (char) 1
        int valueSize = source.size()-lengHeader;
        byte[] value= new byte[valueSize];

        for(int x=lengHeader;x<source.size();x++){
            value[x-lengHeader] = source.get(x);
        }
        return value;
    }

    @Test
    public void decodeAvpsJson() throws Exception {
    }

    @Test
    public void decodeByteStringValue() throws Exception {

        String plaintext="Aji Noor";

        List<Byte> encodeByteString = encode.encodeAvpByteStringValue(263, (char) 1, 0, plaintext.toCharArray());
        byte[] value = getValue(encodeByteString);
        byte[] stringValue = decode.decodeByteStringValue(value);

        String resultDecodeByteString = new String(stringValue);
        Assert.assertEquals(plaintext, resultDecodeByteString);

        System.out.println(plaintext+"---"+resultDecodeByteString);
    }

    @Test
    public void decodeString() throws Exception {

        String plaintext="Bli Gede";

        List<Byte> encodeString = encode.encodeAvpStringValue(263, (char) 1, 0, plaintext.toCharArray());
        byte[] value = getValue(encodeString);
        byte[] stringValue = decode.decodeString(value);

        String resultDecodeString = new String(stringValue);
        Assert.assertEquals(plaintext, resultDecodeString );

        System.out.println(plaintext+"---"+resultDecodeString );
    }

    @Test
    public void decodeIsdnAvpValue() throws Exception {
        String plaintext="628984093644";

        List<Byte> encodeIsdn = encode.encodeAvpIsdnValue(784, (char)1, 0, plaintext.toCharArray());

        byte[] value = getValue(encodeIsdn);

        byte[] stringValue = decode.decodeIsdnAvpValue(value);

        StringBuffer resultString =  new StringBuffer();

        for(int x=0;x<stringValue.length - 2;x++){

            resultString.append(  (char)   (((stringValue[x] &  0xf0 ) >> 4 ) +48) );

            if( !((stringValue[x] & 0x0f) == 0x0f) ) {
                resultString.append((char) ((stringValue[x] & 0x0f) + 48));
            }
        }

        Assert.assertEquals(plaintext, resultString.toString());

        System.out.println(plaintext+"---"+resultString.toString());

    }

    @Test
    public void decodeIntegerAvpValue() throws Exception {

        int plaintext= 6879876;

        List<Byte> encodeInteger = encode.encodeAvpIntegerValue(784, (char)1, 0, plaintext);

        byte[] value = getValue(encodeInteger);

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

    @Test
    public void decodeIpAddressAvpValue() throws Exception {

        String plaintext= "172.217.160.36";
        String result = "";

        List<Byte> encodeIpAddress = encode.encodeAvpIpAddressValue(701, (char) 1, 0, cons.IP_ADDRESS_FAMILY_IPV4, plaintext.toCharArray());
        byte[] value = getValue(encodeIpAddress);
        int num = new BigInteger(Arrays.copyOfRange(value,0,2)).intValue();

        byte[] ipEncode = new byte[value.length - Arrays.copyOfRange(value,0,2).length-2];

        for(int a=2; a<value.length-2;a++){
            ipEncode[a-2] = value[a];
        }

        if(num == 1){
            Inet4Address ip4Address = (Inet4Address) Inet4Address.getByAddress(ipEncode);

            String ipaddress = ip4Address.getHostAddress();
            result = ipaddress;
        }
        else if(num == 2){
            Inet6Address inet6Address = (Inet6Address) Inet6Address.getByAddress(ipEncode);

            String ipaddress = inet6Address.getHostAddress();
            result = ipaddress;
        }
        Assert.assertEquals(plaintext,result);

        System.out.println(plaintext+"---"+result);
    }
}