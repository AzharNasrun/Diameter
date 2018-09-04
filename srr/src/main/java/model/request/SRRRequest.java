package model.request;

import model.srr.Param;

public class SRRRequest {
    private String uuid;

    private String type;

    private String key;

    private String async;

    private String version;

    private String url;

    private String pushKey;

    private Param param;

    public Param getParam() {
        return param;
    }

    public void setParam(Param param) {
        this.param = param;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [param = "+param+", uuid = "+uuid+", type = "+type+", async = "+async+", url = "+url+", pushKey = "+pushKey+", version = "+version+"]";
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAsync() {
        return async;
    }

    public void setAsync(String async) {
        this.async = async;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPushKey() {
        return pushKey;
    }

    public void setPushKey(String pushKey) {
        this.pushKey = pushKey;
    }
}
