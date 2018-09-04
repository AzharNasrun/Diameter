package model;

/**
 * Created by gede on 10/04/18.
 */
public class AVPFlag {


    private boolean protect;

    private boolean mandatory;

    private boolean vendorSpecific;


    public boolean isProtect() {
        return protect;
    }

    public void setProtect(boolean protect) {
        this.protect = protect;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public boolean isVendorSpecific() {
        return vendorSpecific;
    }

    public void setVendorSpecific(boolean vendorSpecific) {
        this.vendorSpecific = vendorSpecific;
    }

    @Override
    public String toString()
    {
        return "AVPFlagMessage [protected = "+protect+", mandatory = "+mandatory+", vendorSpecific = "+vendorSpecific+"]";
    }
}
