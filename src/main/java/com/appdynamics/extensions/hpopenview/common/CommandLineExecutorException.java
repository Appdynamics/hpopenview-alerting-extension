package com.appdynamics.extensions.hpopenview.common;


public class CommandLineExecutorException extends Exception {

	private static final long serialVersionUID = 5636130921046622436L;
	
	public CommandLineExecutorException(String message) {
		super(message);
	}
	
	public CommandLineExecutorException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CommandLineExecutorException(Throwable cause) {
		super(cause);
	}


}
