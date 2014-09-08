package com.appdynamics.extensions.hpopenview.common;


import com.appdynamics.extensions.hpopenview.Configuration;
import com.appdynamics.extensions.hpopenview.api.Alert;
import org.apache.commons.exec.CommandLine;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandExecutor {

    private CommandBuilder commandBuilder = new CommandBuilder();

    private static Logger logger = Logger.getLogger(CommandExecutor.class);


    public boolean execute(Configuration config, Alert alert) throws CommandExecutorException {
        CommandLine command = null;
        command = commandBuilder.buildCommand(config, alert);
        StringBuilder commBuilder = new StringBuilder();
        for(String comm : command.toStrings()){
            commBuilder.append(comm + " ");
        }
        logger.info("Command to be executed is :: " + commBuilder.toString());

       /* DefaultExecutor executor = new DefaultExecutor();
        executor.setExitValue(0); //set 0 as the success value
        ExecuteWatchdog watchdog = new ExecuteWatchdog(config.getTimeout() * 1000);
        executor.setWatchdog(watchdog);
        try {
            int exitValue = executor.execute(command);
            logger.debug("Exit Value" + exitValue);
            if(exitValue != 0){
                logger.error("Unable to generate alert. ExitValue = " + exitValue);
                return false;
            }
        } catch (ExecuteException e) {
            logger.error("Execution failed with exit value:" + e.getExitValue());
            throw new CommandExecutorException("Execution failed with exit value:" + e.getExitValue(), e);
        } catch(Exception e) {
            logger.error("Execution failed with message "+e.getMessage(), e);
            throw new CommandExecutorException("Execution failed with message "+e.getMessage(), e);
        }*/

        Runtime rt = Runtime.getRuntime();
        Process p = null;
        try {
            p = rt.exec(commBuilder.toString());
            if(logger.isDebugEnabled()){
                logDebugProcessExecution(p);
            }
            int exitVal = p.waitFor();
            if(exitVal != 0){
                logger.error("Unable to generate alert. ExitValue = " + exitVal);
                return false;
            }
        } catch (IOException e) {
            logger.error("Error in executing the command " + e);
            throw new CommandExecutorException("Execution failed with message "+ e.getMessage(), e);
        } catch (InterruptedException e) {
            logger.error("Execution of command got interrupted " + e);
            throw new CommandExecutorException("Execution of command got interrupted  "+ e.getMessage(), e);
        }
        return true;
    }

    private void logDebugProcessExecution(Process p){

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        // read the output from the command
        logger.debug("Output from the command:\n");
        String s;
        try {
            while ((s = stdInput.readLine()) != null) {
                logger.info(s);
            }
        } catch (IOException e) {
            logger.error("Error in accessing the stdInput stream " + e);
        }
        // read any errors from the attempted command
        logger.info("Error stream from the command:\n");
        try {
            while ((s = stdError.readLine()) != null) {
                logger.info(s);
            }
        } catch (IOException e) {
            logger.error("Error in accessing the error stream " + e);
        }
    }


}
