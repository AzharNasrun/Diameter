package model.response.ismscm.jamming.jamList;

public class JamList {
    private String statusDescription;

    private String status;

    private String target;

    private String targetType;

    public String getStatusDescription ()
    {
        return statusDescription;
    }

    public void setStatusDescription (String statusDescription)
    {
        this.statusDescription = statusDescription;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getTarget ()
    {
        return target;
    }

    public void setTarget (String target)
    {
        this.target = target;
    }

    public String getTargetType ()
    {
        return targetType;
    }

    public void setTargetType (String targetType)
    {
        this.targetType = targetType;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [statusDescription = "+statusDescription+", status = "+status+", target = "+target+", targetType = "+targetType+"]";
    }
}
