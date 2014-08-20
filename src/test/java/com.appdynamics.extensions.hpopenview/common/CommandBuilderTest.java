package com.appdynamics.extensions.hpopenview.common;


import com.appdynamics.extensions.hpopenview.Configuration;
import com.appdynamics.extensions.hpopenview.api.Alert;
import com.appdynamics.extensions.hpopenview.api.AlertDetails;
import com.appdynamics.extensions.hpopenview.api.AlertHeatlhRuleVioEventDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.exec.CommandLine;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class CommandBuilderTest {

    public static final String PATH_TO_EXEC = "/usr/local/opcmsg";
    public static final String APP = "app";
    public static final String CRITICAL = "CRITICAL";
    public static final String NODE = "node";
    CommandBuilder builder = new CommandBuilder();

    @Test
    public void canSuccessfullyBuildCommand() throws JsonProcessingException {
        CommandLine commandLine = builder.buildCommand(createConfiguration(),createAlert());
        System.out.print(commandLine.toString());
        String[] strings = commandLine.toStrings();
        List<String> commandList = Arrays.asList(strings);
        Assert.assertTrue(commandList.contains(PATH_TO_EXEC));
        Assert.assertTrue(commandList.contains("application="+ APP));
        Assert.assertTrue(commandList.contains("severity="+ CRITICAL));
        Assert.assertTrue(commandList.contains("object="+ NODE));
        Assert.assertTrue(commandList.contains("node="+ NODE));
        Assert.assertTrue(commandList.contains("'msg_text={\"Severity\":\"CRITICAL\",\"Name of Affected Entity\":\"node\",\"Evaluation Entities\":[]}'"));
    }

    private Configuration createConfiguration(){
        Configuration configuration = new Configuration();
        configuration.setTimeout(10);
        configuration.setPathToExecutable(PATH_TO_EXEC);
        return configuration;
    }

    private Alert createAlert() {
        Alert alert = new Alert();
        alert.setApplication(APP);
        alert.setObject(NODE);
        alert.setNode(NODE);
        alert.setDetails(createAlertDetails());
        return alert;
    }

    private AlertDetails createAlertDetails() {
        AlertHeatlhRuleVioEventDetails hrv = new AlertHeatlhRuleVioEventDetails();
        hrv.setSeverity(CRITICAL);
        hrv.setAffectedEntityName(NODE);
        return hrv;
    }


}
