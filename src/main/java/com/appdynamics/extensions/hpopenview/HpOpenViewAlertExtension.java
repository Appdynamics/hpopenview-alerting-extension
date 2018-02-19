/*
 * Copyright 2014. AppDynamics LLC and its affiliates.
 *  All Rights Reserved.
 *  This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 *  The copyright notice above does not evidence any actual or intended publication of such source code.
 */

package com.appdynamics.extensions.hpopenview;

import com.appdynamics.extensions.alerts.customevents.Event;
import com.appdynamics.extensions.alerts.customevents.EventBuilder;
import com.appdynamics.extensions.alerts.customevents.HealthRuleViolationEvent;
import com.appdynamics.extensions.alerts.customevents.OtherEvent;
import com.appdynamics.extensions.hpopenview.api.Alert;
import com.appdynamics.extensions.hpopenview.api.AlertBuilder;
import com.appdynamics.extensions.hpopenview.common.CommandExecutor;
import com.appdynamics.extensions.hpopenview.common.CommandExecutorException;
import com.appdynamics.extensions.hpopenview.common.ConfigUtil;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;

public class HpOpenViewAlertExtension {

    // configuration unmarshalled
    private Configuration config;

    // builder to  de-serialize arguments passed to extension into JAVA POJO
    private EventBuilder eventBuilder;

    // builder to build alert object specific to the needs of integrated product
    private AlertBuilder alertBuilder;

    // an executor that runs system commands
    private CommandExecutor commandExecutor;

	private final static ConfigUtil<Configuration> configUtil = new ConfigUtil<Configuration>();

    private static Logger logger = Logger.getLogger(HpOpenViewAlertExtension.class);

    private static final String CONFIG_FILENAME =  "." + File.separator + "conf" + File.separator + "config.yaml";



	public HpOpenViewAlertExtension(Configuration config,EventBuilder eventBuilder,AlertBuilder alertBuilder,CommandExecutor commandExecutor){
        String msg = "HpOpenViewAlertExtension Version ["+getImplementationTitle()+"]";
        logger.info(msg);
        System.out.println(msg);
        this.config = config;
        this.eventBuilder = eventBuilder;
        this.alertBuilder = alertBuilder;
        this.commandExecutor = commandExecutor;
    }
	
    public static void main( String[] args ) {
    	if(args == null || args.length == 0){
            logger.error("No arguments passed to the extension, exiting the program.");
            return;
        }
        Configuration config = null;
        try {
            config = configUtil.readConfig(CONFIG_FILENAME, Configuration.class);
            HpOpenViewAlertExtension alertExtension = new HpOpenViewAlertExtension(config,new EventBuilder(),new AlertBuilder(),new CommandExecutor());
            boolean status = alertExtension.processAnEvent(args);
            if(status){
                logger.info("HpOpenView Alerting Extension completed successfully.");
                return;
            }

        } catch (FileNotFoundException e) {
            logger.error( "Config file not found." + e);
        } catch(Exception e){
            logger.error( "Error processing an event" + e);
        }
        logger.error( "HpOpenView Alerting Extension completed with errors");
    }
    
    public boolean processAnEvent(String[] args) {
        Event event = eventBuilder.build(args);
        if (event != null) {
            Alert alert = null;
            if (event instanceof HealthRuleViolationEvent) {
                HealthRuleViolationEvent violationEvent = (HealthRuleViolationEvent) event;
                alert = alertBuilder.buildAlertFromHealthRuleViolationEvent(violationEvent, config);
            } else {
                OtherEvent otherEvent = (OtherEvent) event;
                alert = alertBuilder.buildAlertFromOtherEvent(otherEvent, config);
            }
            if (alert != null) {
                try {
                    return commandExecutor.execute(config, alert);
                } catch (CommandExecutorException e) {
                    logger.error("Executing command opcmsg failed " + e);
                }
            }
        }
		return false;
	}
    
	private String getImplementationTitle(){
        return this.getClass().getPackage().getImplementationTitle();
    }
    
    
}
