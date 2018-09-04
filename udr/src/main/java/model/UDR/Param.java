package model.UDR;


import model.ismscm.UserIdentity;

public class Param  {
    private Integer requestedNode;

    private Integer requestedDomain;

    private UserIdentity userIdentity;

    private Integer drmp;

    private Integer[] dataReference;

    private String serverName;

    private Integer currentLocation;

    private String destinationRealm;

    private String destinationHost;

    private String msisdn;

    private String imsi;

    public Integer getRequestedNode() {
        return requestedNode;
    }

    public void setRequestedNode(Integer requestedNode) {
        this.requestedNode = requestedNode;
    }

    public Integer getRequestedDomain() {
        return requestedDomain;
    }

    public void setRequestedDomain(Integer requestedDomain) {
        this.requestedDomain = requestedDomain;
    }

    public UserIdentity getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(UserIdentity userIdentity) {
        this.userIdentity = userIdentity;
    }


    public Integer[] getDataReference() {
        return dataReference;
    }

    public void setDataReference(Integer[] dataReference) {
        this.dataReference = dataReference;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public Integer getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Integer currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Integer getDrmp() {
        return drmp;
    }

    public void setDrmp(Integer drmp) {
        this.drmp = drmp;
    }

    public String getDestinationRealm() {
        return destinationRealm;
    }

    public void setDestinationRealm(String destinationRealm) {
        this.destinationRealm = destinationRealm;
    }

    public String getDestinationHost() {
        return destinationHost;
    }

    public void setDestinationHost(String destinationHost) {
        this.destinationHost = destinationHost;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    @Override
    public String toString() {
        return "ClassPojo [requestedNode = " + requestedNode + ", requestedDomain = " + requestedDomain + ", userIdentity = " + userIdentity + ", destinationRealm = " + destinationRealm + ", destinationHost = " + destinationHost + ", dataReference = " + dataReference + ", serverName = " + serverName + ", currentLocation = " + currentLocation + "]";
    }
}
