package model.response.ismscm.api;

public class Param
{

    private Integer userError;
    private Integer deliveryError;
    private Integer networkError;
    private Integer providerError;
    private Integer result;

    private String target;

    private String imsi;

    private String resultDescription;

    private String msc;

    public Integer getDeliveryError ()
    {
        return deliveryError;
    }

    public void setDeliveryError (Integer deliveryError)
    {
        this.deliveryError = deliveryError;
    }

    public Integer getNetworkError ()
    {
        return networkError;
    }

    public void setNetworkError (Integer networkError)
    {
        this.networkError = networkError;
    }

    public Integer getResult ()
    {
        return result;
    }

    public void setResult (Integer result)
    {
        this.result = result;
    }

    public String getTarget ()
    {
        return target;
    }

    public void setTarget (String target)
    {
        this.target = target;
    }

    public Integer getUserError ()
    {
        return userError;
    }

    public void setUserError (Integer userError)
    {
        this.userError = userError;
    }

    public String getImsi ()
    {
        return imsi;
    }

    public void setImsi (String imsi)
    {
        this.imsi = imsi;
    }

    public Integer getProviderError ()
    {
        return providerError;
    }

    public void setProviderError (Integer providerError)
    {
        this.providerError = providerError;
    }

    public String getResultDescription ()
    {
        return resultDescription;
    }

    public void setResultDescription (String resultDescription)
    {
        this.resultDescription = resultDescription;
    }

    public String getMsc ()
    {
        return msc;
    }

    public void setMsc (String msc)
    {
        this.msc = msc;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [deliveryError = "+deliveryError+", networkError = "+networkError+", result = "+result+", target = "+target+", userError = "+userError+", imsi = "+imsi+", providerError = "+providerError+", resultDescription = "+resultDescription+", msc = "+msc+"]";
    }
}
