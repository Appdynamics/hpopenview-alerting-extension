/*
 * Copyright 2014. AppDynamics LLC and its affiliates.
 *  All Rights Reserved.
 *  This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 *  The copyright notice above does not evidence any actual or intended publication of such source code.
 */

package com.appdynamics.extensions.hpopenview.common;


import com.appdynamics.extensions.hpopenview.Configuration;
import com.appdynamics.extensions.hpopenview.api.Alert;
import org.apache.commons.exec.CommandLine;
import org.apache.log4j.Logger;

public class CommandBuilder {
    private static Logger logger = Logger.getLogger(CommandBuilder.class);

    private static final String EQUALS_SEPARATOR = "=";
    public static final String QUOTE_STR = "\"";

    public CommandLine buildCommand(Configuration config, Alert alert) {
        String pathToExecutable = config.getPathToExecutable();
        CommandLine command = new CommandLine(pathToExecutable);
        command.addArgument(CommandConstants.APPLICATION + EQUALS_SEPARATOR + QUOTE_STR + alert.getApplication() + QUOTE_STR,false);
        command.addArgument(CommandConstants.SEVERITY + EQUALS_SEPARATOR + alert.getSeverity(),false);
        // object - health rule/event notification name
        command.addArgument(CommandConstants.OBJECT + EQUALS_SEPARATOR + QUOTE_STR + alert.getObject() + QUOTE_STR,false);
        // msgGroup from config.yml
        command.addArgument(CommandConstants.MESSAGE_GROUP + EQUALS_SEPARATOR + alert.getMsgGroup(),false);
        // HR summary message + modified deeplink url
        command.addArgument(CommandConstants.MESSAGE_TEXT + EQUALS_SEPARATOR + QUOTE_STR + alert.getMsgText() + QUOTE_STR,false);
        if (alert.getNode() != null) {
            command.addArgument("-option");
            command.addArgument("var1" + EQUALS_SEPARATOR + QUOTE_STR + alert.getNode() + QUOTE_STR, false);
        } else {
            logger.warn("Affected entity is null, hence omitting options to the opcmsg command");
        }
        return command;
    }


}
