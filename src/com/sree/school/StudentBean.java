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

@ManagedBean(name = "studentBean")
@ViewScoped
public class StudentBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Connection conn;
	private Student student = new Student();
	private boolean showForm = true;
	
	PreparedStatement ps = null;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

    @PostConstruct
    public void init(){
    	this.student.setDoj(Calendar.getInstance().getTime());
    	this.student.setCity("Hyderabad");
    }
    
    
	public void save() throws ClassNotFoundException, SQLException {
		conn = DBConnection.getConnection();
		ResultSet rs1 = conn.createStatement().executeQuery("select * from student");

		int i = 0;
		while (rs1.next()) {
			i++;
		}

		ps = conn.prepareStatement("INSERT INTO STUDENT (Id, FirstName, LastName, Class, Section,"
				+ "FatherName, FatherOccupation, Phone, DateOfBirth, DateOfJoining,"
				+ "MotherName, MotherOccupation, Gender, GaurdianName, Mobile, Email, ProfilePic, houseno, street, city, postalcode, createdatetime, updatedatetime)"
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, now(), now())");

		ps.setString(1, "STUDENT_ID" + (i + 1));
		ps.setString(2, student.getFirstname());
		ps.setString(3, student.getLastname());
		ps.setString(4, student.getClassname());
		ps.setString(5, student.getSectionname());
		ps.setString(6, student.getFathername());
		ps.setString(7, student.getFatheroccupation());
		ps.setString(8, student.getPhone());
		ps.setDate(9, new java.sql.Date(student.getDob().getTime()));
		ps.setDate(10, new java.sql.Date(student.getDoj().getTime()));
		ps.setString(11, student.getMothername());
		ps.setString(12, student.getMotheroccupation());
		ps.setString(13, student.getSex());
		ps.setString(14, student.getGuardianname());
		ps.setString(15, student.getMobile());
		ps.setString(16, student.getEmail());
		ps.setString(17, student.getProfiepic());
		ps.setString(18, student.getHouseno());
		ps.setString(19, student.getStreet());
		ps.setString(20, student.getCity());
		ps.setString(21, student.getPostalCode());

		int rs = ps.executeUpdate();

		FacesMessage msg = null;
		if (rs == 1) {
			msg = new FacesMessage("Student added successfully", "");
			setShowForm(false);
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Something went wrong", "Please contant your system administrator.");
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
