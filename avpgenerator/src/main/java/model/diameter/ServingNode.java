package model.diameter;

public class ServingNode {
    private String mmeName;
    private String mmeRealm;
    private String mmeNumber;


    private String sgsnName;
    private String sgsnRealm;
    private String sgsnNumber;


    private String mscNumber;

    public String getMmeNumber() {
        return mmeNumber;
    }

    public void setMmeNumber(String mmeNumber) {
        this.mmeNumber = mmeNumber;
    }

    public String getSgsnName() {
        return sgsnName;
    }

    public void setSgsnName(String sgsnName) {
        this.sgsnName = sgsnName;
    }

    public String getSgsnRealm() {
        return sgsnRealm;
    }

    public void setSgsnRealm(String sgsnRealm) {
        this.sgsnRealm = sgsnRealm;
    }

    public String getSgsnNumber() {
        return sgsnNumber;
    }

    public void setSgsnNumber(String sgsnNumber) {
        this.sgsnNumber = sgsnNumber;
    }

    public String getMscNumber() {
        return mscNumber;
    }

    public void setMscNumber(String mscNumber) {
        this.mscNumber = mscNumber;
    }

    public ServingNode() {
    }

    public String getMmeName() {
        return mmeName;
    }

    public void setMmeName(String mmeName) {
        this.mmeName = mmeName;
    }

    public String getMmeRealm() {
        return mmeRealm;
    }

    public void setMmeRealm(String mmeRealm) {
        this.mmeRealm = mmeRealm;
    }
}
