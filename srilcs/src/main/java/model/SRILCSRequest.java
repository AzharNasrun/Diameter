package model;

import model.SRILCS.SRILCSParam;

import java.io.Serializable;

public class SRILCSRequest {

    private String uuid;

    private String type;

    private String key;

    private String async;

    private String version;

    private String url;

    private SRILCSParam param ;

    private String pushKey;


    public SRILCSParam getParam() {
        return param;
    }

    public void setParam(SRILCSParam param) {
        this.param = param;
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
