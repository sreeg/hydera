package com.sree.school;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.CellEditEvent;

@ManagedBean(name = "attendanceView")
@ViewScoped
public class AttendanceView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static LinkedHashMap<String, String> category;

	private static LinkedHashMap<String, String> monthMap;
	private Map<String, Staff> staffMap = new HashMap<>();
	private List<Attendance> staffs;

	private List<Attendance> staffsPermenant;
	private List<Attendance> staffsTemporary;
	private List<Attendance> staffsDomestic;

	private List<String> months;
	private List<String> years;

	private boolean showForm;

	private Connection conn;

	static {
		category = new LinkedHashMap<String, String>();
		category.put("Permanent", "Permanent");
		category.put("Domestic", "Domestic");
		category.put("Part Time", "Part Time");
	}

	static {
		monthMap = new LinkedHashMap<String, String>();
		monthMap.put("0", "January");
		monthMap.put("1", "February");
		monthMap.put("2", "March");
		monthMap.put("3", "April");
		monthMap.put("4", "May");
		monthMap.put("5", "June");
		monthMap.put("6", "July");
		monthMap.put("7", "August");
		monthMap.put("8", "September");
		monthMap.put("9", "October");
		monthMap.put("10", "November");
		monthMap.put("11", "December");
	}

	public AttendanceView() throws ClassNotFoundException, SQLException {
		staffsPermenant = new ArrayList<>();
		staffsTemporary = new ArrayList<>();
		staffsDomestic = new ArrayList<>();

		staffs = getAllStaff();
	}

	public List<Attendance> getStaffs() {
		return staffs;
	}

	public List<Attendance> getAllStaff() throws ClassNotFoundException, SQLException {
		java.sql.Connection conn = DBConnection.getConnection();
		ResultSet rs = conn.createStatement().executeQuery("select Id, FirstName, LastName, CategoryId, Designation,"
				+ "SpouseName, SpouseOccupation, Phone, DateOfBirth, DateOfJoining,"
				+ "JoiningSalary, Gender, Mobile, Email, ProfilePic, houseno, street, city, postalcode from staff where isarchived=0 ORDER BY CategoryId");

		List<Attendance> students = new ArrayList<>();
		while (rs.next()) {
			Attendance st = new Attendance();
			st.setId(rs.getString("Id"));
			st.setFirstname(rs.getString("FirstName"));
			st.setLastname(rs.getString("Lastname"));
			st.setCategoryid(rs.getString("CategoryId"));
			st.setDesignation(rs.getString("Designation"));
			st.setSpouseName(rs.getString("SpouseName"));
			st.setSpouseOccupation(rs.getString("SpouseOccupation"));
			st.setPhone(rs.getString("Phone"));
			st.setDob(rs.getDate("DateOfBirth"));
			st.setDoj(rs.getDate("DateOfJoining"));
			st.setJoiningsalary(rs.getDouble("JoiningSalary"));
			st.setSex(rs.getString("Gender"));
			st.setMobile(rs.getString("Mobile"));
			st.setEmail(rs.getString("Email"));
			st.setProfiepic(rs.getString("ProfilePic"));
			st.setHouseno(rs.getString("houseno"));
			st.setStreet(rs.getString("street"));
			st.setCity(rs.getString("city"));
			st.setPostalCode(rs.getString("postalcode"));

			students.add(st);
			if (st.getCategoryid().equals("1")) {
				staffsPermenant.add(st);
			}
			if (st.getCategoryid().equals("2")) {
				staffsDomestic.add(st);
			}
			if (st.getCategoryid().equals("3")) {
				staffsTemporary.add(st);
			}
		}
		return students;
	}

	private void getMonthsAndYear() {
		java.sql.Connection conn;
		try {
			conn = DBConnection.getConnection();
			ResultSet rs = conn.createStatement().executeQuery("select DISTINCT date from attendance");

			months = new ArrayList<>();
			years = new ArrayList<>();
			while (rs.next()) {
				Date date = rs.getDate("date");
				months.add(monthMap.get(date.getMonth()));
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<Staff> getStaffByCategory(String categoryID) throws ClassNotFoundException, SQLException {
		java.sql.Connection conn = DBConnection.getConnection();
		ResultSet rs = conn.createStatement()
				.executeQuery("select Id, FirstName, LastName, CategoryId, Designation,"
						+ "SpouseName, SpouseOccupation, Phone, DateOfBirth, DateOfJoining,"
						+ "JoiningSalary, Gender, Mobile, Email, ProfilePic, houseno, street, city, postalcode from staff where CategoryId = "
						+ "'" + categoryID + "'");

		List<Staff> students = new ArrayList<>();
		while (rs.next()) {
			Staff st = new Staff();
			st.setId(rs.getString("Id"));
			st.setFirstname(rs.getString("FirstName"));
			st.setLastname(rs.getString("Lastname"));
			st.setCategoryid(rs.getString("CategoryId"));
			st.setDesignation(rs.getString("Designation"));
			st.setSpouseName(rs.getString("SpouseName"));
			st.setSpouseOccupation(rs.getString("SpouseOccupation"));
			st.setPhone(rs.getString("Phone"));
			st.setDob(rs.getDate("DateOfBirth"));
			st.setDoj(rs.getDate("DateOfJoining"));
			st.setJoiningsalary(rs.getDouble("JoiningSalary"));
			st.setSex(rs.getString("Gender"));
			st.setMobile(rs.getString("Mobile"));
			st.setEmail(rs.getString("Email"));
			st.setProfiepic(rs.getString("ProfilePic"));
			st.setHouseno(rs.getString("houseno"));
			st.setStreet(rs.getString("street"));
			st.setCity(rs.getString("city"));
			st.setPostalCode(rs.getString("postalcode"));
			students.add(st);
			staffMap.put(st.getId(), st);
		}
		return students;
	}

	public LinkedHashMap<String, String> getCategory() {
		return category;
	}

	public static void setCategory(LinkedHashMap<String, String> category) {
		AttendanceView.category = category;
	}

	public boolean isShowForm() {
		return this.showForm;
	}

	public void setShowForm(boolean showForm) {
		this.showForm = showForm;
	}

	public List<Attendance> getStaffsPermenant() {
		return staffsPermenant;
	}

	public void setStaffsPermenant(List<Attendance> staffsPermenant) {
		this.staffsPermenant = staffsPermenant;
	}

	public List<Attendance> getStaffsTemporary() {
		return staffsTemporary;
	}

	public void setStaffsTemporary(List<Attendance> staffsTemporary) {
		this.staffsTemporary = staffsTemporary;
	}

	public List<Attendance> getStaffsDomestic() {
		return staffsDomestic;
	}

	public void setStaffsDomestic(List<Attendance> staffsDomestic) {
		this.staffsDomestic = staffsDomestic;
	}

	public List<String> getMonths() {
		return months;
	}

	public void setMonths(List<String> months) {
		this.months = months;
	}

	public List<String> getYears() {
		return years;
	}

	public void setYears(List<String> years) {
		this.years = years;
	}

	public void save() throws ClassNotFoundException, SQLException {
		conn = DBConnection.getConnection();

		for (Attendance staff : staffs) {
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO ATTENDANCE (staffid, date, dayspresent, daysinmonth, createdatetime, updatedatetime)"
							+ "VALUES (?,now(),?,?, now(), now())");

			ps.setString(1, staff.getId());
			ps.setInt(2, staff.getDayspresent());
			ps.setInt(3, 30);

			int rs = ps.executeUpdate();

			FacesMessage msg = null;
			if (rs == 1) {
				msg = new FacesMessage("Attendance added successfully", "");
				setShowForm(false);
			} else {
				msg = new FacesMessage("Something went wrong", "Please contant your system administrator.");
			}
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}
	
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

}
