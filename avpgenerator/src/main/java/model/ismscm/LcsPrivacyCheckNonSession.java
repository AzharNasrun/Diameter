package model.ismscm;

public class LcsPrivacyCheckNonSession {

    private Integer lcsPrivacyCheck;

    public Integer getLcsPrivacyCheck ()
    {
        return lcsPrivacyCheck;
    }

    public void setLcsPrivacyCheck (Integer lcsPrivacyCheck)
    {
        this.lcsPrivacyCheck = lcsPrivacyCheck;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [lcsPrivacyCheck = "+lcsPrivacyCheck+"]";
    }
}
