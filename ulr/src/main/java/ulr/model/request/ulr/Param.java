package ulr.model.request.ulr;


public class Param  {

    private String mcc;
    private String mnc;
    private String sgsnNumber;
    private Integer ulrFlags;
    private Integer ratType;
    private String mmeNumberForMTSMS;

    protected String destinationRealm;

    protected String destinationHost;

    protected String msisdn;

    protected String imsi;
    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
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

    public String getSgsnNumber() {
        return sgsnNumber;
    }

    public void setSgsnNumber(String sgsnNumber) {
        this.sgsnNumber = sgsnNumber;
    }

    public Integer getUlrFlags() {
        return ulrFlags;
    }

    public String getMmeNumberForMTSMS() {
        return mmeNumberForMTSMS;
    }

    public void setMmeNumberForMTSMS(String mmeNumberForMTSMS) {
        this.mmeNumberForMTSMS = mmeNumberForMTSMS;
    }

    public void setUlrFlags(Integer ulrFlags) {
        this.ulrFlags = ulrFlags;
    }

    public Integer getRatType() {
        return ratType;
    }

    public void setRatType(Integer ratType) {
        this.ratType = ratType;
    }
}
