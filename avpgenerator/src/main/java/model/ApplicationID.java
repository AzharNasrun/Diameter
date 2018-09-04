package model;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

public enum  ApplicationID {
    SRILCS(16777291),
    PSL(16777255),
    IDR(16777251),
    CER(0),
    SIR(16777251),
    UDR(16777217),
    ULR(16777251),
    SRR(16777312);


    private long applicationID;

    public long getApplicationID() {
        return applicationID;
    }

    ApplicationID(long applicationID){
        this.applicationID= applicationID;
    }

    private static final Map<Long, ApplicationID> APPLICATIONID_MAP = Collections.synchronizedMap(new Hashtable<Long, ApplicationID>());

    public static ApplicationID getApplicationID(long applicationID){
        return APPLICATIONID_MAP.get(applicationID);
    }
}
