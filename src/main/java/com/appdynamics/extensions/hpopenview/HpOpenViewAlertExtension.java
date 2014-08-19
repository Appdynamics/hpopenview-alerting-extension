package com.appdynamics.extensions.hpopenview;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.log4j.Logger;

import com.appdynamics.extensions.alerts.customevents.EventBuilder;
import com.appdynamics.extensions.hpopenview.common.ConfigUtil;

public class HpOpenViewAlertExtension {
	
	public static final String CONFIG_FILENAME =  "." + File.separator + "conf" + File.separator + "config.yaml";
	private static Logger logger = Logger.getLogger(HpOpenViewAlertExtension.class);
	
	final EventBuilder eventBuilder = new EventBuilder();
	final static ConfigUtil<Configuration> configUtil = new ConfigUtil<Configuration>();
	private Configuration config;
	
	public HpOpenViewAlertExtension(Configuration config){
        String msg = "HpOpenViewAlertExtension Version ["+getImplementationTitle()+"]";
        logger.info(msg);
        System.out.println(msg);
        this.config = config;
    }
	
    public static void main( String[] args ) {
    	if(args == null || args.length == 0){
            logger.error("No arguments passed to the extension, exiting the program.");
            return;
        }
        Configuration config = null;
        try {
            config = configUtil.readConfig(CONFIG_FILENAME, Configuration.class);
            HpOpenViewAlertExtension alertExtension = new HpOpenViewAlertExtension(config);
            boolean status = alertExtension.processAnEvent(args);
            if(status){
                logger.info( "HpOpenView Alerting Extension completed successfully.");
                return;
            }

        } catch (FileNotFoundException e) {
            logger.error( "Config file not found." + e);
        } catch(Exception e){
            logger.error( "Error processing an event" + e);
        }
        logger.error( "HpOpenView Alerting Extension completed with errors");
    }
    
    private boolean processAnEvent(String[] args) {
		return false;
	}
    
	private String getImplementationTitle(){
        return this.getClass().getPackage().getImplementationTitle();
    }
    
    
}
