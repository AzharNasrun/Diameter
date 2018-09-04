package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gede on 10/04/18.
 */
public class Diameter {


    private long commandCode;

    private DiameterFlag flags;

    private List<AVP> avpList = new ArrayList<AVP>();

    private long applicationId;

    private long endToEndId;

    private long hopByHopId;



    private String uuid;

    private String type;

    private int version;



    public long getCommandCode() {
        return commandCode;
    }

    public void setCommandCode(long commandCode) {
        this.commandCode = commandCode;
    }

    public DiameterFlag getFlags() {
        return flags;
    }

    public void setFlags(DiameterFlag flags) {
        this.flags = flags;
    }

    public List<AVP> getAvpList() {
        return avpList;
    }

    public void setAvpList(List<AVP> avpList) {
        this.avpList = avpList;
    }

    public long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(long applicationId) {
        this.applicationId = applicationId;
    }

    public long getEndToEndId() {
        return endToEndId;
    }

    public void setEndToEndId(long endToEndId) {
        this.endToEndId = endToEndId;
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public long getHopByHopId() {
        return hopByHopId;
    }

    public void setHopByHopId(long hopByHopId) {
        this.hopByHopId = hopByHopId;
    }

    @Override
    public String toString()
    {
        return "DiameterMessage [commandCode = "+commandCode+", flags = "+flags+", avpList.size = "+avpList.size()+", applicationId = "+applicationId+", endToEndId = "+endToEndId+", uuid = "+uuid+", type = "+type+", version = "+version+"]";
    }

}
