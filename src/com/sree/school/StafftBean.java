package com.sree.school;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

@ManagedBean(name = "staffBean")
@ViewScoped
public class StafftBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Connection conn;
	private Staff staff = new Staff();
	private boolean showForm = true;
	
	PreparedStatement ps = null;

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

    @PostConstruct
    public void init(){
    	getStaff().setDoj(Calendar.getInstance().getTime());
    	getStaff().setCity("Hyderabad");
    }
    
	public void save() throws ClassNotFoundException, SQLException {
		conn = DBConnection.getConnection();
		ResultSet rs1 = conn.createStatement().executeQuery("select * from staff");

		int i = 0;
		while (rs1.next()) {
			i++;
		}

		ps = conn.prepareStatement("INSERT INTO STAFF (Id, FirstName, LastName, CategoryId, Designation,"
				+ "SpouseName, SpouseOccupation, Phone, DateOfBirth, DateOfJoining,"
				+ "JoiningSalary, Gender, Mobile, Email, ProfilePic, houseno, street, city, postalcode, createdatetime, updatedatetime)"
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, now(), now())");

		ps.setString(1, "STAFF_ID" + (i + 1));
		ps.setString(2, staff.getFirstname());
		ps.setString(3, staff.getLastname());
		ps.setString(4, staff.getCategoryid());
		ps.setString(5, staff.getDesignation());
		ps.setString(6, staff.getSpouseName());
		ps.setString(7, staff.getSpouseOccupation());
		ps.setString(8, staff.getPhone());
		ps.setDate(9, new java.sql.Date(staff.getDob().getTime()));
		ps.setDate(10, new java.sql.Date(staff.getDoj().getTime()));
		ps.setDouble(11, staff.getJoiningsalary());
		ps.setString(12, staff.getSex());
		ps.setString(13, staff.getMobile());
		ps.setString(14, staff.getEmail());
		ps.setString(15, staff.getProfiepic());
		ps.setString(16, staff.getHouseno());
		ps.setString(17, staff.getStreet());
		ps.setString(18, staff.getCity());
		ps.setString(19, staff.getPostalCode());

		int rs = ps.executeUpdate();

		FacesMessage msg = null;
		if (rs == 1) {
			msg = new FacesMessage("Staff added successfully", "");
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
