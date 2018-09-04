package model;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

public enum CommandCode{
    SRILCS(8388622),
    PSL(8388620),
    IDR(319),
    CER(257),
    SIR(319),
    UDR(306),
    ULR(316),
    DWR(280),
    SRR(8388647);

    private long commandCode;
    private static final Map<Long, CommandCode> COMANMDCODE_MAP = Collections.synchronizedMap(new Hashtable<Long, CommandCode>());


    static {
        for (CommandCode myEnum : values()) {
            COMANMDCODE_MAP.put(myEnum.getComandCode(), myEnum);
        }
    }
    public long getComandCode() {
        return commandCode;
    }

    CommandCode(long comandCode){
        this.commandCode= comandCode;
    }


    public static CommandCode getByCommandCode(long commandCode){
        return COMANMDCODE_MAP.get(commandCode);
    }

}
