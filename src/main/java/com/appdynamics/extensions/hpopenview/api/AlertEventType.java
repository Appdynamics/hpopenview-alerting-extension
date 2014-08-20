package com.appdynamics.extensions.hpopenview.api;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlertEventType {

    @JsonProperty("Event Type")
    private String eventType;

    @JsonProperty("Event Type Number")
    private String eventTypeNum;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventTypeNum() {
        return eventTypeNum;
    }

    public void setEventTypeNum(String eventTypeNum) {
        this.eventTypeNum = eventTypeNum;
    }
}
