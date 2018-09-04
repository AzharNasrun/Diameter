package model;

/**
 * Created by gede on 10/04/18.
 */
public class AVP {



    private AVPFlag flags;

    private String description;

    private Object value;

    private String valueType;

    private long code;

    private long vendorId;


    public AVPFlag getFlags() {
        return flags;
    }

    public void setFlags(AVPFlag flags) {
        this.flags = flags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public long getVendorId() {
        return vendorId;
    }

    public void setVendorId(long vendorId) {
        this.vendorId = vendorId;
    }

    @Override
    public String toString()
    {
        return "AVPMessage [flags = "+flags+", description = "+description+", value = "+value+", valueType = "+valueType+", code = "+code+", vendorId = "+vendorId+"]";
    }
}
