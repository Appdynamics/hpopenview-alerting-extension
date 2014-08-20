package com.appdynamics.extensions.hpopenview.common;


import com.appdynamics.extensions.hpopenview.Configuration;
import com.appdynamics.extensions.hpopenview.api.Alert;
import com.appdynamics.extensions.hpopenview.api.AlertConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.exec.CommandLine;

public class CommandBuilder {

    private static final String EQUALS_SEPARATOR = "=";

    public CommandLine buildCommand(Configuration config, Alert alert) throws JsonProcessingException {
        String pathToExecutable = config.getPathToExecutable();
        CommandLine command = new CommandLine(pathToExecutable);
        command.addArgument(AlertConstants.APPLICATION + EQUALS_SEPARATOR + alert.getApplication());
        command.addArgument(AlertConstants.SEVERITY + EQUALS_SEPARATOR + alert.getDetails().getSeverity());
        command.addArgument(AlertConstants.OBJECT + EQUALS_SEPARATOR + alert.getObject());
        command.addArgument(AlertConstants.NODE + EQUALS_SEPARATOR + alert.getNode());
        ObjectMapper om = new ObjectMapper();
        command.addArgument(AlertConstants.MESSAGE_TEXT + EQUALS_SEPARATOR + om.writeValueAsString(alert.getDetails()));
        return command;
    }
}
