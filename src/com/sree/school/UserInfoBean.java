package com.sree.school;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

@ManagedBean(name = "userInfoBean")
@ViewScoped
public class UserInfoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Connection conn;
	private UserInfo userinfo = new UserInfo();
	private boolean showForm = true;
	
	PreparedStatement ps = null;

	public UserInfo getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}

	public void save() throws ClassNotFoundException, SQLException {
		conn = DBConnection.getConnection();
		ps = conn.prepareStatement("INSERT INTO USERINFO (username, email, password, staffid, createdatetime)"
				+ "VALUES (?,?,?,?,now())");

		ps.setString(1, userinfo.getUsername());
		ps.setString(2, userinfo.getEmail());
		ps.setString(3, userinfo.getPassword());
		ps.setString(4, userinfo.getStaffid());

		int rs = ps.executeUpdate();

		FacesMessage msg = null;
		if (rs == 1) {
			msg = new FacesMessage("User added successfully", "");
			setShowForm(false);
		} else {
			msg = new FacesMessage("Something went wrong", "Please contant your system administrator.");
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
		
	}

	public boolean isShowForm() {
		return showForm;
	}

	public void setShowForm(boolean showForm) {
		this.showForm = showForm;
	}
}
