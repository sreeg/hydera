package com.sree.school;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

@ManagedBean(name = "studentFeeFilterView")
@ViewScoped
public class StudentFeeFilterView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Connection conn;
	private static Collection<String> classnames;
	
	private List<StudentFee> students;
	private Map<String, StudentFee> studentMap;
	private List<Student> filteredStudents;
	
	private Student selectedStudent;
	private String selectedStudentId;
	private boolean showForm;
	private boolean showPrintButton;
	private StudentFee studentfee;
	private boolean studentfound;

	@ManagedProperty("#{studentService}")
	private StudentService service;

	public StudentFeeFilterView() throws ClassNotFoundException, SQLException {
		students = getAllAtudents();
		classnames=Student.classes.values();
		studentfound = false;
		setShowForm(false);
		setShowPrintButton(false);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean filterByPrice(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim();
		if (filterText == null || filterText.equals("")) {
			return true;
		}

		if (value == null) {
			return false;
		}

		return ((Comparable) value).compareTo(Integer.valueOf(filterText)) > 0;
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

	public List<StudentFee> completeStudents(String query) {
		List<StudentFee> allStudents = students;
		List<StudentFee> filteredStudents = new ArrayList<StudentFee>();

		for (int i = 0; i < allStudents.size(); i++) {
			StudentFee stu = allStudents.get(i);
			if (stu.getFullname().toLowerCase().contains(query)) {
				filteredStudents.add(stu);
			}
		}
		return filteredStudents;
	}

	public List<StudentFee> getAllAtudents() throws ClassNotFoundException, SQLException {
		conn = DBConnection.getConnection();
		ResultSet rs = conn.createStatement().executeQuery("select Id, FirstName, LastName, Class, Section,"
				+ "FatherName, FatherOccupation, Phone, DateOfBirth, DateOfJoining,"
				+ "MotherName, MotherOccupation, Gender, GaurdianName, Mobile, Email, "
				+ "ProfilePic, houseno, street, city, postalcode, term1paiddate, term2paiddate, term3paiddate, term1paymentamount, term2paymentamount, term3paymentamount from student "
				+ "LEFT JOIN feepayment ON feepayment.studentid=student.Id order by Class, Section");

		List<StudentFee> students = new ArrayList<>();
		studentMap = new HashMap<>();
		while (rs.next()) {
			StudentFee st = new StudentFee();
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
			st.setTerm1paiddate(rs.getDate("term1paiddate"));
			st.setTerm2paiddate(rs.getDate("term2paiddate"));
			st.setTerm3paiddate(rs.getDate("term3paiddate"));
			st.setTerm1amount(rs.getDouble("term1paymentamount"));
			st.setTerm2amount(rs.getDouble("term2paymentamount"));
			st.setTerm3amount(rs.getDouble("term3paymentamount"));
			
			students.add(st);
			studentMap.put(rs.getString("Id"), st);
		}
		return students;
	}

	public Student getSelectedStudent() {
		return selectedStudent;
	}

	public void setSelectedStudent(Student selectedStudent) {
		this.selectedStudent = selectedStudent;
	}

	public void onStudentSelect(SelectEvent event) {
		selectedStudent = studentMap.get(selectedStudentId);
		studentfee = getStudentFee();
		setShowForm(true);
		setShowPrintButton(false);
	}
	
	public void onStudentFeeSelect(SelectEvent event) {
		selectedStudent = studentMap.get(selectedStudentId);
		studentfee = getStudentFee();
		setShowForm(true);
		setShowPrintButton(true);
	}

	private StudentFee getStudentFee() {
		try {
			conn = DBConnection.getConnection();
			ResultSet rs = conn.createStatement()
					.executeQuery("select studentid, term1paymentamount, term2paymentamount, term3paymentamount, "
							+ "term1paiddate, term2paiddate, term3paiddate, term1cheque, term2cheque, term3cheque "
							+ "from feepayment where studentid = " + "'" + selectedStudentId + "'");

			studentfee = new StudentFee();
			studentfound = false;
			while (rs.next()) {
				studentfee.setEmployeeid(rs.getString("studentid"));
				studentfee.setTerm1amount(rs.getDouble("term1paymentamount"));
				studentfee.setTerm2amount(rs.getDouble("term2paymentamount"));
				studentfee.setTerm3amount(rs.getDouble("term3paymentamount"));
				studentfee.setTerm1paiddate(rs.getDate("term1paiddate"));
				studentfee.setTerm2paiddate(rs.getDate("term2paiddate"));
				studentfee.setTerm3paiddate(rs.getDate("term3paiddate"));
				studentfee.setTerm1cheque(rs.getString("term1cheque"));
				studentfee.setTerm2cheque(rs.getString("term2cheque"));
				studentfee.setTerm3cheque(rs.getString("term3cheque"));

				studentfee.setTerm1(false);
				studentfee.setTerm2(false);
				studentfee.setTerm3(false);

				studentfee.setAmountPaid(
						studentfee.getTerm1amount() + studentfee.getTerm2amount() + studentfee.getTerm3amount());

				studentfound = true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return studentfee;
	}

	public void save() throws ClassNotFoundException, SQLException {
		conn = DBConnection.getConnection();
		Calendar c = Calendar.getInstance();
		PreparedStatement ps;
		if (studentfound) {
			ps = conn.prepareStatement(
					"UPDATE FEEPAYMENT set term1paymentamount = ?, term2paymentamount = ?, term3paymentamount = ?, "
							+ "term1paiddate = ?, term2paiddate = ?, term3paiddate = ?, "
							+ "term1cheque = ?, term2cheque = ?, term3cheque = ?, updatedatetime = now() where studentid = ?");
		} else {
			ps = conn.prepareStatement(
					"INSERT INTO FEEPAYMENT (term1paymentamount, term2paymentamount, term3paymentamount, "
							+ "term1paiddate, term2paiddate, term3paiddate, term1cheque, term2cheque, term3cheque, studentid, "
							+ "createdatetime, updatedatetime )" + "VALUES (?,?,?,?,?,?,?,?,?,?, now(), now())");
		}

		if (studentfee.isTerm1() && studentfee.isTerm2() && studentfee.isTerm3()) {
			studentfee.setTerm1amount(studentfee.getAmount());
			studentfee.setTerm2amount(0);
			studentfee.setTerm3amount(0);

			studentfee.setTerm1paiddate(c.getTime());
			studentfee.setTerm2paiddate(c.getTime());
			studentfee.setTerm3paiddate(c.getTime());

			studentfee.setTerm1cheque(studentfee.getCheque());
			studentfee.setTerm2cheque(studentfee.getCheque());
			studentfee.setTerm3cheque(studentfee.getCheque());

			ps.setDouble(1, studentfee.getTerm1amount());
			ps.setDouble(2, studentfee.getTerm2amount());
			ps.setDouble(3, studentfee.getTerm3amount());
			ps.setDate(4, new java.sql.Date(studentfee.getTerm1paiddate().getTime()));
			ps.setDate(5, new java.sql.Date(studentfee.getTerm2paiddate().getTime()));
			ps.setDate(6, new java.sql.Date(studentfee.getTerm3paiddate().getTime()));
			ps.setString(7, studentfee.getTerm1cheque());
			ps.setString(8, studentfee.getTerm2cheque());
			ps.setString(9, studentfee.getTerm3cheque());

		} else if (studentfee.isTerm1() && studentfee.isTerm2() && !studentfee.isTerm3()) {
			studentfee.setTerm1amount(studentfee.getAmount());
			studentfee.setTerm2amount(0);
			studentfee.setTerm3amount(0);

			studentfee.setTerm1paiddate(c.getTime());
			studentfee.setTerm2paiddate(c.getTime());

			studentfee.setTerm1cheque(studentfee.getCheque());
			studentfee.setTerm2cheque(studentfee.getCheque());

			ps.setDouble(1, studentfee.getTerm1amount());
			ps.setDouble(2, 0);
			ps.setDouble(3, 0);
			ps.setDate(4, new java.sql.Date(studentfee.getTerm1paiddate().getTime()));
			ps.setDate(5, new java.sql.Date(studentfee.getTerm2paiddate().getTime()));
			ps.setDate(6, null);
			ps.setString(7, studentfee.getTerm1cheque());
			ps.setString(8, studentfee.getTerm2cheque());
			ps.setString(9, null);
		} else if (studentfee.isTerm1() && !studentfee.isTerm2() && !studentfee.isTerm3()) {
			studentfee.setTerm1amount(studentfee.getAmount());
			studentfee.setTerm1paiddate(c.getTime());

			studentfee.setTerm1cheque(studentfee.getCheque());
			studentfee.setTerm2cheque(null);
			studentfee.setTerm3cheque(null);

			ps.setDouble(1, studentfee.getTerm1amount());
			ps.setDouble(2, 0);
			ps.setDouble(3, 0);
			ps.setDate(4, new java.sql.Date(studentfee.getTerm1paiddate().getTime()));
			ps.setDate(5, null);
			ps.setDate(6, null);
			ps.setString(7, studentfee.getTerm1cheque());
			ps.setString(8, null);
			ps.setString(9, null);
		} else if (!studentfee.isTerm1() && studentfee.isTerm2() && !studentfee.isTerm3()) {
			studentfee.setTerm2amount(studentfee.getAmount());
			studentfee.setTerm2paiddate(c.getTime());
			studentfee.setTerm2cheque(studentfee.getCheque());
			studentfee.setTerm3cheque(null);

			ps.setDouble(1, studentfee.getTerm1amount());
			ps.setDouble(2, studentfee.getTerm2amount());
			ps.setDouble(3, 0);
			ps.setDate(4, new java.sql.Date(studentfee.getTerm1paiddate().getTime()));
			ps.setDate(5, new java.sql.Date(studentfee.getTerm1paiddate().getTime()));
			ps.setDate(6, null);
			ps.setString(7, studentfee.getTerm1cheque());
			ps.setString(8, studentfee.getCheque());
			ps.setString(9, null);
		} else if (!studentfee.isTerm1() && !studentfee.isTerm2() && studentfee.isTerm3()) {
			studentfee.setTerm3amount(studentfee.getAmount());

			studentfee.setTerm3paiddate(c.getTime());

			studentfee.setTerm1cheque(studentfee.getTerm1cheque());
			studentfee.setTerm2cheque(studentfee.getTerm2cheque());
			studentfee.setTerm3cheque(studentfee.getCheque());

			ps.setDouble(1, studentfee.getTerm1amount());
			ps.setDouble(2, studentfee.getTerm2amount());
			ps.setDouble(3, studentfee.getTerm3amount());
			ps.setDate(4, new java.sql.Date(studentfee.getTerm1paiddate().getTime()));
			ps.setDate(5, new java.sql.Date(studentfee.getTerm2paiddate().getTime()));
			ps.setDate(6, new java.sql.Date(studentfee.getTerm3paiddate().getTime()));
			ps.setString(7, studentfee.getTerm1cheque());
			ps.setString(8, studentfee.getTerm2cheque());
			ps.setString(9, studentfee.getCheque());
		} else if (!studentfee.isTerm1() && studentfee.isTerm2() && studentfee.isTerm3()) {
			studentfee.setTerm2amount(studentfee.getAmount());
			studentfee.setTerm2paiddate(c.getTime());
			studentfee.setTerm3paiddate(c.getTime());
			studentfee.setTerm2cheque(studentfee.getCheque());

			ps.setDouble(1, studentfee.getTerm1amount());
			ps.setDouble(2, studentfee.getTerm2amount());
			ps.setDouble(3, studentfee.getTerm3amount());
			ps.setDate(4, new java.sql.Date(studentfee.getTerm1paiddate().getTime()));
			ps.setDate(5, new java.sql.Date(studentfee.getTerm2paiddate().getTime()));
			ps.setDate(6, new java.sql.Date(studentfee.getTerm3paiddate().getTime()));
			ps.setString(7, studentfee.getTerm1cheque());
			ps.setString(8, studentfee.getTerm2cheque());
			ps.setString(9, studentfee.getCheque());
		}
		ps.setString(10, selectedStudentId);

		int rs = ps.executeUpdate();
		FacesMessage msg = null;
		if (rs == 1) {
			msg = new FacesMessage("Student fee updated successfully", "");
			setShowPrintButton(true);
			setShowForm(true);
		} else {
			msg = new FacesMessage("Something went wrong", "Please contant your system administrator.");
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void setShowForm(boolean showForm) {
		this.showForm = showForm;
	}

	public boolean isShowForm() {
		return showForm;
	}

	public String getSelectedStudentId() {
		return selectedStudentId;
	}

	public void setSelectedStudentId(String selectedStudentId) {
		this.selectedStudentId = selectedStudentId;
	}

	public Map<String, StudentFee> getStudentMap() {
		return studentMap;
	}

	public void setStudentMap(Map<String, StudentFee> studentMap) {
		this.studentMap = studentMap;
	}

	public StudentFee getStudentfee() {
		return studentfee;
	}

	public void setStudentfee(StudentFee studentfee) {
		this.studentfee = studentfee;
	}

	public boolean isShowPrintButton() {
		return showPrintButton;
	}

	public void setShowPrintButton(boolean showPrintButton) {
		this.showPrintButton = showPrintButton;
	}

	public Collection<String> getClassnames() {
		return classnames;
	}

	public static void setClassnames(Collection<String> classnames) {
		StudentFeeFilterView.classnames = classnames;
	}

	public List<StudentFee> getStudents() {
		return students;
	}

	public void setStudents(List<StudentFee> students) {
		this.students = students;
	}

}