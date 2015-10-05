package com.sree.school;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
 
@ManagedBean(name="studentFilterView")
@ViewScoped
public class StudentFilterView implements Serializable {
     
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Student> students;
     
    private List<Student> filteredStudents;
    
    private Student selectedStudent;
     
    @ManagedProperty("#{studentService}")
    private StudentService service;
 
    public StudentFilterView() throws ClassNotFoundException, SQLException
    {
    	students = getAllAtudents();
    }
     
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean filterByPrice(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if(filterText == null||filterText.equals("")) {
            return true;
        }
         
        if(value == null) {
            return false;
        }
         
        return ((Comparable) value).compareTo(Integer.valueOf(filterText)) > 0;
    }

     
    public List<Student> getStudents() {
        return students;
    }
 
    public List<Student> getFilteredStudents() {
        return filteredStudents;
    }
 
    public void setFilteredStudents(List<Student> filteredStudents) {
        this.filteredStudents = filteredStudents;
    }
 
    public void setService(StudentService service) {
        this.service = service;
    }
    
	public List<Student> getAllAtudents() throws ClassNotFoundException, SQLException {
		java.sql.Connection conn = DBConnection.getConnection();
		ResultSet rs = conn.createStatement().executeQuery("select Id, FirstName, LastName, Class, Section,"
				+ "FatherName, FatherOccupation, Phone, DateOfBirth, DateOfJoining,"
				+ "MotherName, MotherOccupation, Gender, GaurdianName, Mobile, Email, ProfilePic, houseno, street, city, postalcode from student");

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
			st.setHouseno(rs.getString("houseno"));
			st.setStreet(rs.getString("street"));
			st.setCity(rs.getString("city"));
			st.setPostalCode(rs.getString("postalcode"));
			
			students.add(st);
		}
		return students;
	}

	public Student getSelectedStudent() {
		return selectedStudent;
	}

	public void setSelectedStudent(Student selectedStudent) {
		this.selectedStudent = selectedStudent;
	}
}
