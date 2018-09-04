package model.ismscm;



public class BaseRequestModel {
    protected String uuid;

    protected String type;

    protected String key;

    protected String async;

    protected String version;

    protected String url;

    public BaseParamRequestModel param;

    protected String pushKey;

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

    public BaseParamRequestModel getParam() {
        return param;
    }

    public void setParam(BaseParamRequestModel param) {
        this.param = param;
    }

    public void setPushKey(String pushKey) {
        this.pushKey = pushKey;
    }
}
