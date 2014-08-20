package com.appdynamics.extensions.hpopenview.api;

public class Alert {
	
	private String application;
	
	private String msg_grp;
	
	private String object;
	
	private AlertDetails details;
	
	private String node;

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getMsg_grp() {
		return msg_grp;
	}

	public void setMsg_grp(String msg_grp) {
		this.msg_grp = msg_grp;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

    public AlertDetails getDetails() {
		return details;
	}

	public void setDetails(AlertDetails details) {
		this.details = details;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}	
	
}
