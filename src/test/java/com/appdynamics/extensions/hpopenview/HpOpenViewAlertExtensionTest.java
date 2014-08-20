package com.appdynamics.extensions.hpopenview;


import com.appdynamics.extensions.alerts.customevents.EventBuilder;
import com.appdynamics.extensions.hpopenview.api.Alert;
import com.appdynamics.extensions.hpopenview.api.AlertBuilder;
import com.appdynamics.extensions.hpopenview.common.CommandExecutor;
import com.appdynamics.extensions.hpopenview.common.CommandExecutorException;
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
        alertExtension.processAnEvent(eventArgs.getHealthRuleViolationEventWithMultipleEvalEntityAndMultipleTriggerBaseline());
    }


    @Test
    public void canPostOtherEvent() throws FileNotFoundException, CommandExecutorException {
        HpOpenViewAlertExtension alertExtension = getHpOpenViewAlertExtension();
        alertExtension.processAnEvent(eventArgs.getOtherEvent());
    }

    @Test
    public void canPostHRViolationEventWithMultipleEvalEntityAndTriggerMultipleBaselineNoDetails() throws FileNotFoundException, CommandExecutorException {
        HpOpenViewAlertExtension alertExtension = getHpOpenViewAlertExtension();
        alertExtension.processAnEvent(eventArgs.getHealthRuleViolationEventWithMultipleEvalEntityAndMultipleTriggerBaseline());
    }


    @Test
    public void canPostOtherEventWithNoDetails() throws FileNotFoundException, CommandExecutorException {
        HpOpenViewAlertExtension alertExtension = getHpOpenViewAlertExtension();
        alertExtension.processAnEvent(eventArgs.getOtherEvent());
    }

    private HpOpenViewAlertExtension getHpOpenViewAlertExtension() throws CommandExecutorException {
        CommandExecutor commandExecutor = mock(CommandExecutor.class);
        Configuration config = createConfiguration();
        when(commandExecutor.execute((Configuration)any(),(Alert)any())).thenReturn(true);
        return new HpOpenViewAlertExtension(config,new EventBuilder(),new AlertBuilder(),commandExecutor);
    }

    private Configuration createConfiguration(){
        Configuration configuration = new Configuration();
        configuration.setTimeout(10);
        configuration.setPathToExecutable("/usr/local/opcmsg");
        return configuration;
    }

}
