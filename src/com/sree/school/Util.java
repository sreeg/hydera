package com.sree.school;

import java.text.SimpleDateFormat;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Util {

	static SimpleDateFormat dt = new SimpleDateFormat("dd MMMM, YYYY - hh:mm:ss aa");
	
	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public static String getUserName() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		return session.getAttribute("username").toString();
	}

	public static String getLastLoginDateTime() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		return dt.format((java.util.Date) session.getAttribute("lastlogindatettime"));
	}
	
	public static String getStaffid() {
		HttpSession session = getSession();
		if (session != null)
			return (String) session.getAttribute("staffid");
		else
			return null;
	}
}