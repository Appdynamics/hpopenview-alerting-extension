package com.appdynamics.extensions.hpopenview.common;


public class CommandExecutorException extends Exception {

	private static final long serialVersionUID = 5636130921046622436L;
	
	public CommandExecutorException(String message) {
		super(message);
	}
	
	public CommandExecutorException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CommandExecutorException(Throwable cause) {
		super(cause);
	}


}
