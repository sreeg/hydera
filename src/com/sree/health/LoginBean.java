package com.sree.health;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.sree.health.dao.UserDAO;

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

	public String loginProject() {
		String result = UserDAO.login(uname, password);
		if (result.equals("customer")) {
			// get Http Session and store username
			HttpSession session = Util.getSession();
			session.setAttribute("username", uname);

			return "customer";
		} else if (result.equals("doctor")) {
			// get Http Session and store username
			HttpSession session = Util.getSession();
			session.setAttribute("username", uname);

			return "home";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Login!", "Please Try Again!"));
			return "login";
		}
	}

	public String logout() {
		HttpSession session = Util.getSession();
		session.setAttribute("username", null);
		session.invalidate();
		return "login";
	}
}