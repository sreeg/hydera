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
		String result = UserDAO.login(uname, password);
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