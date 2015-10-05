package com.sree.school;

import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.sree.school.dao.UserDAO;

@ManagedBean(name = "loginBean")
@SessionScoped

public class LoginBean {

	private String uname;
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String loginProject() throws SQLException {
		String result = UserDAO.login(uname, password);
		if (result.equals("customer")) {
			// get Http Session and store username
			HttpSession session = Util.getSession();
			session.setAttribute("username", uname);
			return "customer";
		} else if (result.equals("staff")) {
			// get Http Session and store username
			HttpSession session = Util.getSession();
			session.setAttribute("username", uname);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome!", uname));
			return "home";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Login!", "Please enter correct Username and Password!"));
			return "login";
		}
	}

	public String logout() {
		HttpSession session = Util.getSession();
		session.invalidate();
		FacesContext
		.getCurrentInstance()
		.getApplication()
		.getNavigationHandler()
		.handleNavigation(FacesContext.getCurrentInstance(), null,
		"/login.xhtml?faces-redirect=true");
		return "login";
	}
}