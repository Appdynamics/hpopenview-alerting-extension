package com.appdynamics.extensions.hpopenview.api;

public class Alert {
	
	private String application;
	
	private String severity;
	
	private String msg_grp;
	
	private String object;
	
	private String msg_text;
	
	private String node;

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
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

	public String getMsg_text() {
		return msg_text;
	}

	public void setMsg_text(String msg_text) {
		this.msg_text = msg_text;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}	
	
}
