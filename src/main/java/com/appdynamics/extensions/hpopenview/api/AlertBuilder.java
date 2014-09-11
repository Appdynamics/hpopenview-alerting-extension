package com.appdynamics.extensions.hpopenview.api;

import com.appdynamics.extensions.alerts.customevents.Event;
import com.appdynamics.extensions.alerts.customevents.EventSummary;
import com.appdynamics.extensions.alerts.customevents.HealthRuleViolationEvent;
import com.appdynamics.extensions.alerts.customevents.OtherEvent;
import com.appdynamics.extensions.hpopenview.Configuration;
import org.apache.log4j.Logger;

/**
 * Builds an Alert from Health Rule violation event.
 */

public class AlertBuilder {

    private static Logger logger = Logger.getLogger(AlertBuilder.class);
    public static final String COLON_SEPARATOR = ":";
    public static final String POLICY_CLOSE = "POLICY_CLOSE";

    public Alert buildAlertFromHealthRuleViolationEvent(HealthRuleViolationEvent violationEvent, Configuration config) {
        if(violationEvent != null && config != null){
            Alert alert = new Alert();
            alert.setApplication(getApplication(violationEvent));
            alert.setObject(violationEvent.getHealthRuleName());
            alert.setSeverity(getSeverity(violationEvent.getEventType(),violationEvent.getSeverity()));
            alert.setMsgText(getMessageText(violationEvent));
            alert.setMsgGroup(config.getMsgGroup());
            return alert;
        }
        return null;
    }


    public Alert buildAlertFromOtherEvent(OtherEvent otherEvent, Configuration config) {
        if (otherEvent != null && config != null) {
            Alert alert = new Alert();
            alert.setApplication(getApplication(otherEvent));
            alert.setObject(otherEvent.getEventNotificationName());
            alert.setSeverity(getSeverity(null,otherEvent.getSeverity()));
            alert.setMsgText(getMessageText(otherEvent));
            alert.setMsgGroup(config.getMsgGroup());
            return alert;
        }
        return null;
    }

    private String getMessageText(OtherEvent otherEvent) {
        StringBuilder builder = new StringBuilder("");
        if(otherEvent.getEventSummaries() != null) {
            for (EventSummary eventSummary : otherEvent.getEventSummaries()) {
                builder.append(eventSummary.getEventSummaryString());
                builder.append(" ");
                builder.append(otherEvent.getDeepLinkUrl() + eventSummary.getEventSummaryId());
                builder.append(" ");
                builder.append(eventSummary.getEventSummaryId());
            }
        }
        return builder.toString();
    }

    private String getApplication(Event event) {
        StringBuilder builder = new StringBuilder("");
        builder.append(event.getAppName());
        builder.append(COLON_SEPARATOR);
        builder.append(event.getPriority());
        builder.append(COLON_SEPARATOR);
        if(event instanceof HealthRuleViolationEvent){
            HealthRuleViolationEvent hrve = (HealthRuleViolationEvent)event;
            builder.append(hrve.getAffectedEntityName());
        }
        else{
            OtherEvent oe = (OtherEvent)event;
            builder.append(oe.getEventNotificationName());
        }
        return builder.toString();
    }


    private String getMessageText(HealthRuleViolationEvent violationEvent) {
        StringBuilder builder = new StringBuilder("");
        builder.append(violationEvent.getSummaryMessage());
        return builder.toString();
    }


    private String getSeverity(String eventType,String severity){
        if(eventType != null && severity != null) {
            if (eventType.equalsIgnoreCase(POLICY_CLOSE)) {
                return "NORMAL";
            }
            if (severity.equalsIgnoreCase("WARN")) {
                return "WARNING";
            } else if (severity.equalsIgnoreCase("ERROR")) {
                return "CRITICAL";
            } else if (severity.equalsIgnoreCase("INFO")) {
                return "MINOR";
            }
        }
        return "MINOR";
    }

}
