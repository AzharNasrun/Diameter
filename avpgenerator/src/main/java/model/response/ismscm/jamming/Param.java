package model.response.ismscm.jamming;

public class Param {
    private NetworkError networkError;

    private String hlrGt;

    private Integer result;

    private Integer status;

    private String statusDescription;

    private String lastKnownSgsnIp;

    private String lastKnownMscGt;

    private String msisdn;

    private String target;

    private String method;

    private String imsi;

    private String lastKnownSgsnGt;

    private String resultDescription;

    private String targetType;

    public NetworkError getNetworkError ()
    {
        return networkError;
    }

    public void setNetworkError (NetworkError networkError)
    {
        this.networkError = networkError;
    }

    public String getHlrGt ()
    {
        return hlrGt;
    }

    public void setHlrGt (String hlrGt)
    {
        this.hlrGt = hlrGt;
    }

    public Integer getResult ()
    {
        return result;
    }

    public void setResult (Integer result)
    {
        this.result = result;
    }

    public String getLastKnownSgsnIp ()
    {
        return lastKnownSgsnIp;
    }

    public void setLastKnownSgsnIp (String lastKnownSgsnIp)
    {
        this.lastKnownSgsnIp = lastKnownSgsnIp;
    }

    public String getLastKnownMscGt ()
    {
        return lastKnownMscGt;
    }

    public void setLastKnownMscGt (String lastKnownMscGt)
    {
        this.lastKnownMscGt = lastKnownMscGt;
    }

    public String getMsisdn ()
    {
        return msisdn;
    }

    public void setMsisdn (String msisdn)
    {
        this.msisdn = msisdn;
    }

    public String getTarget ()
    {
        return target;
    }

    public void setTarget (String target)
    {
        this.target = target;
    }

    public String getMethod ()
    {
        return method;
    }

    public void setMethod (String method)
    {
        this.method = method;
    }

    public String getImsi ()
    {
        return imsi;
    }

    public void setImsi (String imsi)
    {
        this.imsi = imsi;
    }

    public String getLastKnownSgsnGt ()
    {
        return lastKnownSgsnGt;
    }

    public void setLastKnownSgsnGt (String lastKnownSgsnGt)
    {
        this.lastKnownSgsnGt = lastKnownSgsnGt;
    }

    public String getResultDescription ()
    {
        return resultDescription;
    }

    public void setResultDescription (String resultDescription)
    {
        this.resultDescription = resultDescription;
    }

    public String getTargetType ()
    {
        return targetType;
    }

    public void setTargetType (String targetType)
    {
        this.targetType = targetType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [networkError = "+networkError+", hlrGt = "+hlrGt+", result = "+result+", lastKnownSgsnIp = "+lastKnownSgsnIp+", lastKnownMscGt = "+lastKnownMscGt+", msisdn = "+msisdn+", target = "+target+", method = "+method+", imsi = "+imsi+", lastKnownSgsnGt = "+lastKnownSgsnGt+", resultDescription = "+resultDescription+", targetType = "+targetType+"]";
    }}
