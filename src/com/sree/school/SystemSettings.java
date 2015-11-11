package com.sree.school;

import java.io.Serializable;

public class SystemSettings implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String password;
	private String email;
	private boolean showwidget1;
	private boolean showwidget2;
	private String emailhostname;
	private String smtpport;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isShowwidget1() {
		return showwidget1;
	}

	public void setShowwidget1(boolean showwidget1) {
		this.showwidget1 = showwidget1;
	}

	public boolean isShowwidget2() {
		return showwidget2;
	}

	public void setShowwidget2(boolean showwidget2) {
		this.showwidget2 = showwidget2;
	}

	public String getEmailhostname() {
		return emailhostname;
	}

	public void setEmailhostname(String emailhostname) {
		this.emailhostname = emailhostname;
	}

	public String getSmtpport() {
		return smtpport;
	}

	public void setSmtpport(String smtpport) {
		this.smtpport = smtpport;
	}

}
