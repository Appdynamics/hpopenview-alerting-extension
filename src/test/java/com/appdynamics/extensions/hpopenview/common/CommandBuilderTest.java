package com.appdynamics.extensions.hpopenview.common;


import com.appdynamics.extensions.hpopenview.Configuration;
import com.appdynamics.extensions.hpopenview.api.Alert;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.util.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class CommandBuilderTest {

    public static final String PATH_TO_EXEC = "C:\\HP\\bin\\opcmsg";
    public static final String EQUALS_SEPARATOR = "=";
    public static final String QUOTE_STRING = "";
    CommandBuilder builder = new CommandBuilder();
    ConfigUtil<Configuration> configUtil = new ConfigUtil<Configuration>();

    @Test
    public void canSuccessfullyBuildCommand() throws FileNotFoundException {
        Configuration config = configUtil.readConfig(this.getClass().getResource("/conf/config.windows.yaml").getFile(),Configuration.class);
        Alert alert = createAlert();
        CommandLine commandLine = builder.buildCommand(config,alert);
        String[] strings = commandLine.toStrings();
        List<String> commandList = Arrays.asList(strings);
        Assert.assertTrue(commandList.contains(StringUtils.fixFileSeparatorChar(PATH_TO_EXEC)));
        Assert.assertTrue(commandList.contains(CommandConstants.APPLICATION + EQUALS_SEPARATOR + QUOTE_STRING + alert.getApplication() + QUOTE_STRING));
        Assert.assertTrue(commandList.contains(CommandConstants.OBJECT + EQUALS_SEPARATOR + QUOTE_STRING + alert.getObject() + QUOTE_STRING));
        Assert.assertTrue(commandList.contains(CommandConstants.MESSAGE_GROUP + EQUALS_SEPARATOR + alert.getMsgGroup()));
        Assert.assertTrue(commandList.contains(CommandConstants.MESSAGE_TEXT + EQUALS_SEPARATOR + QUOTE_STRING + alert.getMsgText() + QUOTE_STRING));
        Assert.assertTrue(commandList.contains(CommandConstants.SEVERITY + EQUALS_SEPARATOR + alert.getSeverity()));
    }

    private Alert createAlert() {
        Alert alert = new Alert();
        alert.setApplication("APP_NAME:PRIORITY:AFFECTED_ENTITY_NAME");
        alert.setObject("HEALTH_RULE_NAME");
        alert.setMsgGroup("AppDynamics");
        alert.setMsgText("This is a messageText");
        alert.setSeverity("CRITICAL");
        return alert;
    }




}
