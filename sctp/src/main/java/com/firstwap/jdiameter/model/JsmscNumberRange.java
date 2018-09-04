package com.firstwap.jdiameter.model;


import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsmscNumberRange {

    Integer id;
    String lowerRange;
    String upperRange;
    String response;
    Integer callbackDelay;
    Integer commandCode;
    String type;

    public String getLowerRange() {
        return lowerRange;
    }

    public void setLowerRange(String lowerRange) {
        this.lowerRange = lowerRange;
    }

    public String getUpperRange() {
        return upperRange;
    }

    public void setUpperRange(String upperRange) {
        this.upperRange = upperRange;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Integer getCallbackDelay() {
        return callbackDelay;
    }

    public void setCallbackDelay(Integer callbackDelay) {
        this.callbackDelay = callbackDelay;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommanCode() {
        return commandCode;
    }

    public void setCommanCode(Integer commandCode) {
        this.commandCode = commandCode;
    }
}
