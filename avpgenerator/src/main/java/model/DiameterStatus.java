package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author Azhar
 * this class is for Diameter Status Dictionary
 */

public enum DiameterStatus {

    /**
     * Request processed Successfully
     */
    SUCCESS(2001),

    /**
     * to indicate that the user is unknown
     */
    ERROR_USER_UNKNOWN(5001),

    /**
     * to indicate that the requesting GMLC's network is not authorized to request
     */
    ERROR_UNAUTHORIZED_REQUESTING_NETWORK(5490),

    /**
     * to indicate that the user could not be reached in order to perform positioning procedure
     */
    ERROR_UNREACHABLE_USER(4221),

    /**
     * to indicate that the user is suspended in the MME
     */
    ERROR_SUSPENDED_USER(4222),

    /**
     * to indicate that the user is detached in the MME
     */
    ERROR_DETACHED_USER(4223),

    /**
     * to indicate that the positioning procedure was denied
     */
    ERROR_POSITIONING_DENIED(4224),

    /**
     * to indicate that the positioning procedure failed
     */
    ERROR_POSITIONING_FAILED(4225),

    /**
     * to indicate that the LCS Client was not known or could not be reached
     */
    ERROR_UNKNOWN_UNREACHABLE_LCS_CLIENT(4226),

    /**
     * Message rejected because of unspecified reasons.
     */
    DIAMETER_UNABLE_TO_COMPLY(5012),

    /**
     * Message can’t be delivered because there is no Host with Diameter URI present in Destination-Host AVP in associated Realm.
     */

    DIAMETER_UNABLE_TO_DELIVER(3002),

    /**
     * Intended Realm is not recognized
     */
    DIAMETER_REALM_NOT_SERVED(3003),

    /**
     *Shall return by server only when server unable to provide requested service, where all the pre-requisites are also met. Client should also send the request to alternate peer.
     */
    DIAMETER_TOO_BUSY(3004),

    /**
     * Request is processed but some more processing is required by Server to provide access to user
     */
    DIAMETER_LIMITED_SUCCESS(2002),

    /**
     *DIAMETER_LOOP_DETECTED
     */
    DIAMETER_LOOP_DETECTED(3005),

    /**
     * DIAMETER_APPLICATION_UNSUPPORTED
     */
    DIAMETER_APPLICATION_UNSUPPORTED(3007),

    /**
     *DIAMETER_ERROR_ABSENT_USER
     */
    DIAMETER_ERROR_ABSENT_USER(4201),

    /**
     * if server network down should return this error
     */

    ERROR_SERVER_NETWORK_DOWN(1);

    private static final Map<Integer, DiameterStatus> MY_MAP = Collections.synchronizedMap(new Hashtable<Integer, DiameterStatus>());

    static {
        for (DiameterStatus myEnum : values()) {
            MY_MAP.put(myEnum.getCode(), myEnum);
        }
    }

    private int code;


    DiameterStatus(int code) {
        this.code = code;
    }

    /**
     * To Get DiameterStatus Object By Code
     *
     * @param code
     * @return
     */
    public static DiameterStatus getByCode(int code) {
        return MY_MAP.get(code);
    }

    public int getCode() {
        return code;
    }

}
