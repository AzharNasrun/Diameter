package Encode;



import com.fasterxml.jackson.databind.JsonNode;
import model.AVP;

import java.net.UnknownHostException;
import java.util.List;

public interface EncodeInterface {

    List<Byte> encodeDiameterHeader(char version, int length, char flags, int commandCode, int applicationId, int hopByHopId, long endToEndId);

    List<Byte> encodeAvp(int avpCode, char avpFlag, int avpVendorId, List<Byte> avpValue);



    List<Byte> encodeAvpIpAddressValue(int avpCode, char avpFlag, int avpVendorId, short ipAddressFamily, char[] ipAddress) throws Exception;
    List<Byte>  encodeAvpStringValue(int avpCode, char avpFlag, int avpVendorId ,char[] stringValue) ;
    List<Byte>  encodeAvpIntegerValue(int avpCode, char avpFlag, int avpVendorId, int avpValue);
    List<Byte>  encodeAvpByteStringValue(int avpCode, char avpFlag, int avpVendorId, char[] hexString);
    List<Byte>  encodeAvpIsdnValue(int avpCode, char avpFlag, int avpVendorId, char[] isdn);

/*
    long encodeDiameterJson(char[] dest, long maxLen, int hopByHopId, int endToEndId, char[] jsonString) ;
*/


    public List<Byte> encodeAvpObject(AVP avp);





}
