package model.ismscm;

public class UserIdentity
{
    private String msisdn;
    private String publicIdentity;

    public String getMsisdn ()
    {
        return msisdn;
    }

    public void setMsisdn (String msisdn)
    {
        this.msisdn = msisdn;
    }

    public String getPublicIdentity() {
        return publicIdentity;
    }

    public void setPublicIdentity(String publicIdentity) {
        this.publicIdentity = publicIdentity;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [msisdn = "+msisdn+"]";
    }
}
