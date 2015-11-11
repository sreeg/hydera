package com.sree.school;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "systemSettingsBean")
@SessionScoped
public class SystemSettingsBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Connection conn;
	public SystemSettings systemSettings = new SystemSettings();

	PreparedStatement ps = null;

	public SystemSettingsBean() {
		try {
			getSystemSettingsFromDB();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public SystemSettings getSystemSettings() {
		return systemSettings;
	}

	public void setSystemSettings(SystemSettings systemSettings) {
		this.systemSettings = systemSettings;
	}

	public void getSystemSettingsFromDB() throws ClassNotFoundException, SQLException {
		conn = DBConnection.getConnection();
		ResultSet rs = conn.createStatement().executeQuery(
				"SELECT email, password, showwidget1, showwidget2, emailhost, smtpport from SYSTEMSETTINGS");
		while (rs.next()) {
			systemSettings.setEmail(rs.getString("email"));
			systemSettings.setPassword(rs.getString("password"));
			systemSettings.setShowwidget1(rs.getBoolean("showwidget1"));
			systemSettings.setShowwidget2(rs.getBoolean("showwidget2"));
			systemSettings.setEmailhostname(rs.getString("emailhost"));
			systemSettings.setSmtpport(rs.getString("smtpport"));
		}
	}

	public void save() throws ClassNotFoundException, SQLException {
		deleteSystemSettings();
		conn = DBConnection.getConnection();
		ps = conn.prepareStatement(
				"INSERT INTO SYSTEMSETTINGS (email, password, showwidget1, showwidget2, emailhost, smtpport)"
						+ "VALUES (?,?,?,?,?,?)");

		ps.setString(1, systemSettings.getEmail());
		ps.setString(2, systemSettings.getPassword());
		ps.setBoolean(3, systemSettings.isShowwidget1());
		ps.setBoolean(4, systemSettings.isShowwidget2());
		ps.setString(5, systemSettings.getEmailhostname());
		ps.setString(6, systemSettings.getSmtpport());
		int rs = ps.executeUpdate();

		FacesMessage msg = null;
		if (rs == 1) {
			msg = new FacesMessage("Settings added successfully", "");
		} else {
			msg = new FacesMessage("Something went wrong", "Please contant your system administrator.");
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	private void deleteSystemSettings() throws ClassNotFoundException, SQLException {
		conn = DBConnection.getConnection();
		conn.createStatement().executeUpdate("DELETE from SYSTEMSETTINGS");
	}

}
