package com.appdynamics.extensions.hpopenview.common;


import java.io.File;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.log4j.Logger;

import com.appdynamics.extensions.hpopenview.Configuration;
import com.appdynamics.extensions.hpopenview.api.Alert;
import com.appdynamics.extensions.hpopenview.api.AlertConstants;

public class CommandLineExecutor {

	private static final String EXE = "opcmsg";
	private static Logger logger = Logger.getLogger(CommandLineExecutor.class);
    private static final String EQUALS_SEPARATOR = "=";

	public void execute(Configuration config, Alert alert) throws CommandLineExecutorException {
    	
    	CommandLine command = buildCommand(config, alert);
		//CommandLine command = testPing(config, alert);
    	logger.debug("HPOV command to be executed is " + command.toString());
    	DefaultExecutor executor = new DefaultExecutor();
    	try {
    		int exitValue = executor.execute(command);
    		logger.debug("Exit Value" + exitValue);
		} catch (ExecuteException e) {
			logger.error("Execution failed with exit value:" + e.getExitValue());
			throw new CommandLineExecutorException("Execution failed with exit value:" + e.getExitValue(), e);
		} catch(Exception e) {
			logger.error("Execution failed with message "+e.getMessage(), e);
			throw new CommandLineExecutorException("Execution failed with message "+e.getMessage(), e);
		}
    }

	private CommandLine buildCommand(Configuration config, Alert alert) {
		
		String pathToExecutable = config.getPathToExecutable();
		if(!pathToExecutable.endsWith(File.separator)) {
			pathToExecutable += pathToExecutable + File.separator;
		}
		String line = pathToExecutable + EXE;
    	CommandLine command = new CommandLine(line);
    	command.addArgument(AlertConstants.APPLICATION + EQUALS_SEPARATOR + alert.getApplication());
    	command.addArgument(AlertConstants.SEVERITY + EQUALS_SEPARATOR + alert.getDetails().getSeverity());
    	command.addArgument(AlertConstants.OBJECT + EQUALS_SEPARATOR + alert.getObject());
    	command.addArgument(AlertConstants.NODE + EQUALS_SEPARATOR + alert.getNode());
    	command.addArgument(AlertConstants.MESSAGE_TEXT + EQUALS_SEPARATOR + alert.getDetails());
    	
		return command;
	}
	private CommandLine testPing(Configuration config, Alert alert) {
		String line = "ping";
    	CommandLine command = new CommandLine(line);
    	command.addArgument("localhost");
		return command;
	}
}
