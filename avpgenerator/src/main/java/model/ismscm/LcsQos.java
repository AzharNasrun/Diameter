package model.ismscm;

public class LcsQos {

    private Integer verticalRequested;

    private Integer lcsQosClass;

    private Integer horizontalAccuracy;

    private Integer verticalAccuracy;

    private Integer responseTime;

    public Integer getVerticalRequested ()
    {
        return verticalRequested;
    }

    public void setVerticalRequested (Integer verticalRequested)
    {
        this.verticalRequested = verticalRequested;
    }

    public Integer getLcsQosClass ()
    {
        return lcsQosClass;
    }

    public void setLcsQosClass (Integer lcsQosClass)
    {
        this.lcsQosClass = lcsQosClass;
    }

    public Integer getHorizontalAccuracy ()
    {
        return horizontalAccuracy;
    }

    public void setHorizontalAccuracy (Integer horizontalAccuracy)
    {
        this.horizontalAccuracy = horizontalAccuracy;
    }

    public Integer getVerticalAccuracy ()
    {
        return verticalAccuracy;
    }

    public void setVerticalAccuracy (Integer verticalAccuracy)
    {
        this.verticalAccuracy = verticalAccuracy;
    }

    public Integer getResponseTime ()
    {
        return responseTime;
    }

    public void setResponseTime (Integer responseTime)
    {
        this.responseTime = responseTime;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [verticalRequested = "+verticalRequested+", lcsQosClass = "+lcsQosClass+", horizontalAccuracy = "+horizontalAccuracy+", verticalAccuracy = "+verticalAccuracy+", responseTime = "+responseTime+"]";
    }
}
