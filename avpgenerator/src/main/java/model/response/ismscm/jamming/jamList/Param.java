package model.response.ismscm.jamming.jamList;

public class Param {

    private String method;

    private JamList[] jamList;

    public String getMethod ()
    {
        return method;
    }

    public void setMethod (String method)
    {
        this.method = method;
    }

    public JamList[] getJamList ()
    {
        return jamList;
    }

    public void setJamList (JamList[] jamList)
    {
        this.jamList = jamList;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [method = "+method+", jamList = "+jamList+"]";
    }
}
