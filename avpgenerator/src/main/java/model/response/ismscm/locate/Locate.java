package model.response.ismscm.locate;

import java.io.Serializable;

public class Locate implements Serializable{


    private Param param;

    private Integer status;

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

    public Integer getStatus ()
    {
        return status;
    }

    public void setStatus (Integer status)
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
