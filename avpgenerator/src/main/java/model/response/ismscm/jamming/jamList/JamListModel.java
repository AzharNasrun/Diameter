package model.response.ismscm.jamming.jamList;

public class JamListModel {

    private Param param;

    private String status;

    private String type;

    private String requestUuid;

    private String version;

    public Param getParam ()
    {
        return param;
    }

    public void setParam (Param param)
    {
        this.param = param;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getRequestUuid ()
    {
        return requestUuid;
    }

    public void setRequestUuid (String requestUuid)
    {
        this.requestUuid = requestUuid;
    }

    public String getVersion ()
    {
        return version;
    }

    public void setVersion (String version)
    {
        this.version = version;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [param = "+param+", status = "+status+", type = "+type+", requestUuid = "+requestUuid+", version = "+version+"]";
    }
}
