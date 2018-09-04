package Decode;

import model.AVP;

import java.util.List;

public interface DecodeInterface {


    public void decodeAvpObject(List<AVP> avpList, byte[] input, int index, long diameterLength);

    public byte[] decodeIsdnAvpValue(byte[] src);
    public byte[] decodeIntegerAvpValue(byte[] src);
    public byte[] decodeIpAddressAvpValue(byte[] src);

}
