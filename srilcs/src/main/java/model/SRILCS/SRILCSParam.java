package model.SRILCS;



public class SRILCSParam {

    private String destinationRealm;

    private String destinationHost;

    private String msisdn;

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

    private String imsi;

    private String gmlcNumber;

    public String getGmlcNumber() {
        return gmlcNumber;
    }

    public void setGmlcNumber(String gmlcNumber) {
        this.gmlcNumber = gmlcNumber;
    }
}
