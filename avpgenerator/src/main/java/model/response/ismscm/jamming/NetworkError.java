package model.response.ismscm.jamming;

public class NetworkError {

    private Integer mapId;

    private Integer refuseReason;

    private String provErrorDescription;

    private Integer provError;

    private String userErrorDescription;

    private Integer userError;

    private String mapDescription;

    public Integer getMapId ()
    {
        return mapId;
    }

    public void setMapId (Integer mapId)
    {
        this.mapId = mapId;
    }

    public Integer getRefuseReason ()
    {
        return refuseReason;
    }

    public void setRefuseReason (Integer refuseReason)
    {
        this.refuseReason = refuseReason;
    }

    public String getProvErrorDescription ()
    {
        return provErrorDescription;
    }

    public void setProvErrorDescription (String provErrorDescription)
    {
        this.provErrorDescription = provErrorDescription;
    }

    public Integer getProvError ()
    {
        return provError;
    }

    public void setProvError (Integer provError)
    {
        this.provError = provError;
    }

    public String getUserErrorDescription ()
    {
        return userErrorDescription;
    }

    public void setUserErrorDescription (String userErrorDescription)
    {
        this.userErrorDescription = userErrorDescription;
    }

    public Integer getUserError ()
    {
        return userError;
    }

    public void setUserError (Integer userError)
    {
        this.userError = userError;
    }

    public String getMapDescription ()
    {
        return mapDescription;
    }

    public void setMapDescription (String mapDescription)
    {
        this.mapDescription = mapDescription;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [mapId = "+mapId+", refuseReason = "+refuseReason+", provErrorDescription = "+provErrorDescription+", provError = "+provError+", userErrorDescription = "+userErrorDescription+", userError = "+userError+", mapDescription = "+mapDescription+"]";
    }
}
