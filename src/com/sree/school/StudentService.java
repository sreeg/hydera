package com.sree.school;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
 
@ManagedBean(name = "studentService")
@ApplicationScoped
public class StudentService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 644779773719132823L;

	public List<Student> getAllAtudents() throws ClassNotFoundException, SQLException {
		java.sql.Connection conn = DBConnection.getConnection();
		ResultSet rs = conn.createStatement().executeQuery("select (Id, FirstName, LastName, Class, Section,"
				+ "FatherName, FatherOccupation, Phone, DateOfBirth, DateOfJoining,"
				+ "MotherName, MotherOccupation, Gender, GaurdianName, Mobile, Email, ProfilePic) from student");

		List<Student> students = new ArrayList<>();
		while (rs.next()) {
			Student st = new Student();
			st.setId(rs.getString("Id"));
			st.setFirstname(rs.getString("FirstName"));
			st.setLastname(rs.getString("Lastname"));
			st.setClassname(rs.getString("Class"));
			st.setSectionname(rs.getString("Section"));
			st.setFathername(rs.getString("FatherName"));
			st.setFatheroccupation(rs.getString("FatherOccupation"));
			st.setPhone(rs.getString("Phone"));
			st.setDob(rs.getDate("DateOfBirth"));
			st.setDoj(rs.getDate("DateOfJoining"));
			st.setMothername(rs.getString("MotherName"));
			st.setMotheroccupation(rs.getString("MotherOccupation"));
			st.setSex(rs.getString("Gender"));
			st.setGuardianname(rs.getString("GaurdianName"));
			st.setMobile(rs.getString("Mobile"));
			st.setEmail(rs.getString("Email"));
			st.setProfiepic(rs.getString("ProfilePic"));
			
			students.add(st);
		}
		return students;
	}
}