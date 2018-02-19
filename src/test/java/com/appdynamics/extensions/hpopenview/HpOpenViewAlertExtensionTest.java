/*
 * Copyright 2014. AppDynamics LLC and its affiliates.
 *  All Rights Reserved.
 *  This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 *  The copyright notice above does not evidence any actual or intended publication of such source code.
 */

package com.appdynamics.extensions.hpopenview;


import com.appdynamics.extensions.alerts.customevents.EventBuilder;
import com.appdynamics.extensions.hpopenview.api.Alert;
import com.appdynamics.extensions.hpopenview.api.AlertBuilder;
import com.appdynamics.extensions.hpopenview.common.CommandExecutor;
import com.appdynamics.extensions.hpopenview.common.CommandExecutorException;
import com.appdynamics.extensions.hpopenview.common.ConfigUtil;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HpOpenViewAlertExtensionTest {

    EventArgs eventArgs = new EventArgs();

    @Test
    public void canPostHRViolationEventWithMultipleEntityAndTriggerMultipleBaseline() throws FileNotFoundException, CommandExecutorException {
        HpOpenViewAlertExtension alertExtension = getHpOpenViewAlertExtension();
        Assert.assertTrue(alertExtension.processAnEvent(eventArgs.getHealthRuleViolationEventWithMultipleEvalEntityAndMultipleTriggerBaseline()));
    }


    @Test
    public void canPostOtherEvent() throws FileNotFoundException, CommandExecutorException {
        HpOpenViewAlertExtension alertExtension = getHpOpenViewAlertExtension();
        Assert.assertTrue(alertExtension.processAnEvent(eventArgs.getOtherEvent()));
    }

    @Test
    public void canPostHRViolationEventWithMultipleEvalEntityAndTriggerMultipleBaselineNoDetails() throws FileNotFoundException, CommandExecutorException {
        HpOpenViewAlertExtension alertExtension = getHpOpenViewAlertExtension();
        Assert.assertTrue(alertExtension.processAnEvent(eventArgs.getHealthRuleViolationEventWithMultipleEvalEntityAndMultipleTriggerBaseline()));
    }

    /*    @Test
    public void integrationTest() throws FileNotFoundException {
        ConfigUtil<Configuration> configUtil = new ConfigUtil<Configuration>();
        Configuration config = configUtil.readConfig(this.getClass().getResource("/conf/config.windows.yaml").getFile(),Configuration.class);
            HpOpenViewAlertExtension extension = new HpOpenViewAlertExtension(config,new EventBuilder(),new AlertBuilder(),new CommandExecutor());
        Assert.assertTrue(extension.processAnEvent(eventArgs.getHealthRuleViolationEventWithMultipleEvalEntityAndMultipleTriggerBaseline()));
    }*/

    @Test
    public void canPostOtherEventWithNoDetails() throws FileNotFoundException, CommandExecutorException {
        HpOpenViewAlertExtension alertExtension = getHpOpenViewAlertExtension();
        Assert.assertTrue(alertExtension.processAnEvent(eventArgs.getOtherEvent()));
    }

    private HpOpenViewAlertExtension getHpOpenViewAlertExtension() throws CommandExecutorException {
        CommandExecutor commandExecutor = mock(CommandExecutor.class);
        Configuration config = createConfiguration();
        when(commandExecutor.execute((Configuration)any(),(Alert)any())).thenReturn(true);
        return new HpOpenViewAlertExtension(config,new EventBuilder(),new AlertBuilder(),commandExecutor);
    }

    private Configuration createConfiguration(){
        Configuration configuration = new Configuration();
        configuration.setPathToExecutable("/usr/local/opcmsg");
        configuration.setMsgGroup("AppDynamics");
        return configuration;
    }

}
