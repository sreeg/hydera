package com.sree.hydera;

import java.sql.SQLException;
import java.util.Calendar;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.sree.hydera.dao.UserDAO;

@ManagedBean(name = "loginBean")
@SessionScoped

public class LoginBean {

	private String uname;
	private String password;
	private String staffid;
	private String lastlogindatettime;
	private String firstname;
	private String lastname;
	private String fullname;
	private String gender;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

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
		String result = "staff";//UserDAO.login(uname, password);
		HttpSession session = Util.getSession();
		session.setAttribute("username", "Sree");
		session.setAttribute("staffid", "1");
		session.setAttribute("lastlogindatettime", Calendar.getInstance().getTime());
		session.setAttribute("issuperuser", Boolean.TRUE);
		session.setAttribute("firstname", "Sreedhar");
		session.setAttribute("lastname", "Ganduri");
		session.setAttribute("gender", "Male");
		if (result.equals("staff")) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome!", uname));
			setUname(Util.getUserName());
			setStaffid(Util.getStaffid());
			setLastlogindatettime(Util.getLastLoginDateTime());
			setFullname(Util.getUserFullName());
			
			return "home";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Invalid Login!", "Please enter correct Username and Password!"));
			return "login";
		}
	}

	public String logout() {
		HttpSession session = Util.getSession();
		session.invalidate();
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
				.handleNavigation(FacesContext.getCurrentInstance(), null, "/login.xhtml?faces-redirect=true");
		return "login";
	}

	public String getStaffid() {
		return staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getLastlogindatettime() {
		return lastlogindatettime;
	}

	public void setLastlogindatettime(String lastlogindatettime) {
		this.lastlogindatettime = lastlogindatettime;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

}