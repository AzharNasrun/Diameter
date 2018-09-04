package model;

/**
 * Created by gede on 10/04/18.
 */
public class DiameterFlag{

    private boolean transmitted;

    private boolean error;

    private boolean proxyable;

    private boolean request;

    public boolean isTransmitted() {
        return transmitted;
    }

    public void setTransmitted(boolean transmitted) {
        this.transmitted = transmitted;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public boolean isProxyable() {
        return proxyable;
    }

    public void setProxyable(boolean proxyable) {
        this.proxyable = proxyable;
    }

    public boolean isRequest() {
        return request;
    }

    public void setRequest(boolean request) {
        this.request = request;
    }

    @Override
    public String toString()
    {
        return "DiameterFlagMessage [transmitted = "+transmitted+", error = "+error+", proxyable = "+proxyable+", request = "+request+"]";
    }
}
