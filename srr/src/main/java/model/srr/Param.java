package model.srr;


public class Param {


    public String scAddress;

    public Integer smRpMti;

    public String smRpSmea;

    public Integer srrFlags;

    private String destinationRealm;

    private String destinationHost;

    private String msisdn;

    private String imsi;


    public Integer getSrrFlags() {
        return srrFlags;
    }

    public void setSrrFlags(Integer srrFlags) {
        this.srrFlags = srrFlags;
    }

    public String getSmRpSmea() {
        return smRpSmea;
    }

    public void setSmRpSmea(String smRpSmea) {
        this.smRpSmea = smRpSmea;
    }

    public Integer getSmRpMti() {
        return smRpMti;
    }

    public void setSmRpMti(Integer smRpMti) {
        this.smRpMti = smRpMti;
    }

    public String getScAddress() {
        return scAddress;
    }

    public void setScAddress(String scAddress) {
        this.scAddress = scAddress;
    }

    public String getDestinationRealm() {
        return destinationRealm;
    }

    public void setDestinationRealm(String destinationRealm) {
        this.destinationRealm = destinationRealm;
    }

    public String getDestinationHost() {
        return destinationHost;
    }

    public void setDestinationHost(String destinationHost) {
        this.destinationHost = destinationHost;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }
}
