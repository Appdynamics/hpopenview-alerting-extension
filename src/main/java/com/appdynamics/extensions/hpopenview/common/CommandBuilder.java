package com.appdynamics.extensions.hpopenview.common;


import com.appdynamics.extensions.hpopenview.Configuration;
import com.appdynamics.extensions.hpopenview.api.Alert;
import org.apache.commons.exec.CommandLine;

public class CommandBuilder {

    private static final String EQUALS_SEPARATOR = "=";

    public CommandLine buildCommand(Configuration config, Alert alert) {
        String pathToExecutable = config.getPathToExecutable();
        CommandLine command = new CommandLine(pathToExecutable);
        command.addArgument(CommandConstants.APPLICATION + EQUALS_SEPARATOR + alert.getApplication(),false);
        command.addArgument(CommandConstants.SEVERITY + EQUALS_SEPARATOR + alert.getSeverity(),false);
        command.addArgument(CommandConstants.OBJECT + EQUALS_SEPARATOR + alert.getObject(),false);
        command.addArgument(CommandConstants.MESSAGE_GROUP + EQUALS_SEPARATOR + alert.getMsgGroup(),false);
        command.addArgument(CommandConstants.MESSAGE_TEXT + EQUALS_SEPARATOR + "\"" + alert.getMsgText() + "\"",false);
        return command;
    }


}
