package model.ismscm;

public class LcsEpsClientName {

    private Integer lcsFormatIndicator;

    private String lcsNameString;

    public Integer getLcsFormatIndicator ()
    {
        return lcsFormatIndicator;
    }

    public void setLcsFormatIndicator (Integer lcsFormatIndicator)
    {
        this.lcsFormatIndicator = lcsFormatIndicator;
    }

    public String getLcsNameString ()
    {
        return lcsNameString;
    }

    public void setLcsNameString (String lcsNameString)
    {
        this.lcsNameString = lcsNameString;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [lcsFormatIndicator = "+lcsFormatIndicator+", lcsNameString = "+lcsNameString+"]";
    }
}
