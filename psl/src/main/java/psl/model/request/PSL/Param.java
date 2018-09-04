package psl.model.request.PSL;


import model.ismscm.LcsEpsClientName;
import model.ismscm.LcsPrivacyCheckNonSession;
import model.ismscm.LcsQos;

public class Param {

    private Integer lcsSupportedGadShapes;

    private Integer slgLocationType;

    private LcsEpsClientName lcsEpsClientName;

    private LcsPrivacyCheckNonSession lcsPrivacyCheckNonSession;

    private Integer lcsClientType;

    private Integer lcsPriority;

    private LcsQos lcsQos;

    private String mscNumber;

    private String destinationRealm;

    private String destinationHost;

    private String msisdn;

    private String imsi;

    public Integer getLcsSupportedGadShapes ()
    {
        return lcsSupportedGadShapes;
    }

    public void setLcsSupportedGadShapes (Integer lcsSupportedGadShapes)
    {
        this.lcsSupportedGadShapes = lcsSupportedGadShapes;
    }

    public Integer getSlgLocationType ()
    {
        return slgLocationType;
    }

    public void setSlgLocationType (Integer slgLocationType)
    {
        this.slgLocationType = slgLocationType;
    }

    public LcsEpsClientName getLcsEpsClientName ()
    {
        return lcsEpsClientName;
    }

    public void setLcsEpsClientName (LcsEpsClientName lcsEpsClientName)
    {
        this.lcsEpsClientName = lcsEpsClientName;
    }

    public LcsPrivacyCheckNonSession getLcsPrivacyCheckNonSession ()
    {
        return lcsPrivacyCheckNonSession;
    }

    public void setLcsPrivacyCheckNonSession (LcsPrivacyCheckNonSession lcsPrivacyCheckNonSession)
    {
        this.lcsPrivacyCheckNonSession = lcsPrivacyCheckNonSession;
    }

    public Integer getLcsClientType ()
    {
        return lcsClientType;
    }

    public void setLcsClientType (Integer lcsClientType)
    {
        this.lcsClientType = lcsClientType;
    }

    public Integer getLcsPriority ()
    {
        return lcsPriority;
    }

    public void setLcsPriority (Integer lcsPriority)
    {
        this.lcsPriority = lcsPriority;
    }

    public LcsQos getLcsQos ()
    {
        return lcsQos;
    }

    public void setLcsQos (LcsQos lcsQos)
    {
        this.lcsQos = lcsQos;
    }

    public String getMscNumber() {
        return mscNumber;
    }

    public void setMscNumber(String mscNumber) {
        this.mscNumber = mscNumber;
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

    @Override
    public String toString()
    {
        return "ClassPojo [lcsSupportedGadShapes = "+lcsSupportedGadShapes+", slgLocationType = "+slgLocationType+", lcsEpsClientName = "+lcsEpsClientName+", lcsPrivacyCheckNonSession = "+lcsPrivacyCheckNonSession+", lcsClientType = "+lcsClientType+", lcsPriority = "+lcsPriority+", lcsQos = "+lcsQos+", imsi = "+imsi+"]";
    }
}
