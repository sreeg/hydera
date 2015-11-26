package com.sree.school;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.chart.DonutChartModel;

@ManagedBean(name = "attendanceView")
@ViewScoped
public class AttendanceView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static LinkedHashMap<String, String> category;

	private static LinkedHashMap<String, String> monthMap;
	private static LinkedHashMap<String, String> yearMap = new LinkedHashMap<String, String>();
	private static LinkedHashMap<String, String> monthmapfromdb = new LinkedHashMap<String, String>();

	private static LinkedHashMap<String, Integer> monthDaysMap;
	private Map<String, Staff> staffMap = new HashMap<>();
	private Map<String, List<Attendance>> staffAttendanceMap = new HashMap<>();
	private List<Attendance> staffs;
	private List<Staff> allStaffs;
	private Staff selectedStaff;

	private List<Attendance> staffsPermenant;
	private List<Attendance> staffsTemporary;
	private List<Attendance> staffsDomestic;
	private List<Staff> filteredStaffs;

	private List<String> months;
	private List<String> years;

	private boolean showForm;

	private Connection conn;
	private String currentMonth;
	private String currentYear;
	private String selectedyear;
	private String selectedmonth;
	private int daysincurrentmonth;
	private int daysinselectedmonth;

	SimpleDateFormat month_date = new SimpleDateFormat("MMMM");

	private List<Attendance> selectedStaffAttendance = new ArrayList<>();

	private DonutChartModel donutModel2;

	static {
		category = new LinkedHashMap<String, String>();
		category.put("Permanent", "Permanent");
		category.put("Domestic", "Domestic");
		category.put("Part Time", "Part Time");
	}

	static {
		monthMap = new LinkedHashMap<String, String>();
		monthMap.put("January", "January");
		monthMap.put("February", "February");
		monthMap.put("March", "March");
		monthMap.put("April", "April");
		monthMap.put("May", "May");
		monthMap.put("June", "June");
		monthMap.put("July", "July");
		monthMap.put("August", "August");
		monthMap.put("September", "September");
		monthMap.put("October", "October");
		monthMap.put("November", "November");
		monthMap.put("December", "December");
	}

	static {
		monthDaysMap = new LinkedHashMap<String, Integer>();
		monthDaysMap.put("January", 31);
		monthDaysMap.put("February", 28);
		monthDaysMap.put("March", 31);
		monthDaysMap.put("April", 30);
		monthDaysMap.put("May", 31);
		monthDaysMap.put("June", 30);
		monthDaysMap.put("July", 31);
		monthDaysMap.put("August", 31);
		monthDaysMap.put("September", 30);
		monthDaysMap.put("October", 31);
		monthDaysMap.put("November", 30);
		monthDaysMap.put("December", 31);
	}

	public AttendanceView() throws ClassNotFoundException, SQLException {
		staffsPermenant = new ArrayList<>();
		staffsTemporary = new ArrayList<>();
		staffsDomestic = new ArrayList<>();

		Calendar now = Calendar.getInstance();
		setCurrentMonth(month_date.format(now.getTime()));
		setCurrentYear("" + now.get(Calendar.YEAR));
		setDaysincurrentmonth(now.getActualMaximum(Calendar.DATE));
		staffs = getAllStaffAttendance();
		setAllStaffs(getAllStaffsDetails());
		yearMap.put(currentYear, currentYear);
		setSelectedyear(getCurrentYear());
		setSelectedmonth(getCurrentMonth());
		setDaysinselectedmonth(monthDaysMap.get(getCurrentMonth()));
		setShowForm(true);
		createDonutModels(new ArrayList<Attendance>());
	}

	public List<Attendance> getStaffs() {
		return staffs;
	}

	public List<Attendance> getAllStaffAttendance() {
		ResultSet rs;
		List<Attendance> attendance = new ArrayList<>();

		try {
			java.sql.Connection conn = DBConnection.getConnection();

			rs = conn.createStatement()
					.executeQuery("select Id, FirstName, LastName, CategoryId, Designation,"
							+ "SpouseName, SpouseOccupation, Phone, DateOfBirth, DateOfJoining,"
							+ "JoiningSalary, Gender, Mobile, Email, ProfilePic, houseno, street, city, postalcode, "
							+ "attendance.dayspresent, attendance.month, attendance.year, attendance.daysinmonth from attendance "
							+ "LEFT JOIN staff ON staff.Id=attendance.staffid ORDER BY staff.CategoryId");

			while (rs.next()) {
				Attendance at = new Attendance();
				String staffID = rs.getString("Id");
				at.setId(staffID);
				at.setFirstname(rs.getString("FirstName"));
				at.setLastname(rs.getString("Lastname"));
				at.setCategoryid(rs.getString("CategoryId"));
				at.setDesignation(rs.getString("Designation"));
				at.setSpouseName(rs.getString("SpouseName"));
				at.setSpouseOccupation(rs.getString("SpouseOccupation"));
				at.setPhone(rs.getString("Phone"));
				at.setDob(rs.getDate("DateOfBirth"));
				at.setDoj(rs.getDate("DateOfJoining"));
				at.setJoiningsalary(rs.getDouble("JoiningSalary"));
				at.setSex(rs.getString("Gender"));
				at.setMobile(rs.getString("Mobile"));
				at.setEmail(rs.getString("Email"));
				at.setProfiepic(rs.getString("ProfilePic"));
				at.setHouseno(rs.getString("houseno"));
				at.setStreet(rs.getString("street"));
				at.setCity(rs.getString("city"));
				at.setPostalCode(rs.getString("postalcode"));
				at.setDayspresent(rs.getInt("dayspresent"));
				at.setDaysinmonth(rs.getInt("daysinmonth"));
				String month = rs.getString("month");
				at.setMonth(month);
				String year = rs.getString("year");
				at.setYear(year);
				if (month.equals(currentMonth))
					attendance.add(at);

				if (year != null)
					yearMap.put(year, year);

				if (month != null)
					monthmapfromdb.put(month, month);

				if (at.getCategoryid().equals("1")) {
					staffsPermenant.add(at);
				}
				if (at.getCategoryid().equals("2")) {
					staffsDomestic.add(at);
				}
				if (at.getCategoryid().equals("3")) {
					staffsTemporary.add(at);
				}

				if (staffAttendanceMap.get(staffID) != null && staffAttendanceMap.get(staffID).size() != 0) {
					staffAttendanceMap.get(staffID).add(at);
				} else {
					ArrayList<Attendance> arrayList = new ArrayList<>();
					arrayList.add(at);
					staffAttendanceMap.put(staffID, arrayList);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return attendance;
	}

	public List<Staff> getAllStaffsDetails() {
		java.sql.Connection conn;
		List<Staff> staffs = new ArrayList<>();

		try {
			conn = DBConnection.getConnection();

			ResultSet rs = conn.createStatement()
					.executeQuery("select Id, FirstName, LastName, CategoryId, Designation,"
							+ "SpouseName, SpouseOccupation, Phone, DateOfBirth, DateOfJoining,"
							+ "JoiningSalary, Gender, Mobile, Email, ProfilePic, houseno, street, city, postalcode from staff where isarchived=0");

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

				staffs.add(st);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return staffs;
	}

	private void createDonutModels(List<Attendance> selectedStaffAttendance2) {
		donutModel2 = initDonutModel(selectedStaffAttendance2);
		
		donutModel2.setLegendPosition("e");
		donutModel2.setSliceMargin(1);
		donutModel2.setShowDataLabels(true);
		donutModel2.setDataFormat("value");
		donutModel2.setShadow(false);
		donutModel2.setSeriesColors("1D9C73,dc4e44,33C7AB,A63F82,A30303");
	}

	private DonutChartModel initDonutModel(List<Attendance> selectedStaffAttendance2) {
		DonutChartModel model = new DonutChartModel();
		int present = 0, total = 0;
		for (Attendance a : selectedStaffAttendance2) {
			present = present + a.getDayspresent();
			total = total + a.getDaysinmonth();
		}
		model.setTitle("Total no.of days : "+total);
		Map<String, Number> circle1 = new LinkedHashMap<String, Number>();
		circle1.put("Present", present);
		circle1.put("Absent", total - present);
		model.addCircle(circle1);

		return model;
	}

	public List<Attendance> getAllStaffByMonthAndYear() throws ClassNotFoundException, SQLException {
		java.sql.Connection conn = DBConnection.getConnection();
		ResultSet rs = conn.createStatement().executeQuery("select Id, FirstName, LastName, CategoryId, Designation,"
				+ "SpouseName, SpouseOccupation, Phone, DateOfBirth, DateOfJoining,"
				+ "JoiningSalary, Gender, Mobile, Email, ProfilePic, houseno, street, city, postalcode, "
				+ "attendance.dayspresent, attendance.month, attendance.year, attendance.daysinmonth from staff "
				+ "LEFT JOIN attendance ON staff.Id=attendance.staffid and attendance.year=" + "'" + getSelectedyear()
				+ "'" + " and attendance.month = " + "'" + getSelectedmonth() + "'" + " ORDER BY staff.CategoryId");

		List<Attendance> attendance = new ArrayList<>();
		while (rs.next()) {
			Attendance at = new Attendance();
			at.setId(rs.getString("Id"));
			at.setFirstname(rs.getString("FirstName"));
			at.setLastname(rs.getString("Lastname"));
			at.setCategoryid(rs.getString("CategoryId"));
			at.setDesignation(rs.getString("Designation"));
			at.setSpouseName(rs.getString("SpouseName"));
			at.setSpouseOccupation(rs.getString("SpouseOccupation"));
			at.setPhone(rs.getString("Phone"));
			at.setDob(rs.getDate("DateOfBirth"));
			at.setDoj(rs.getDate("DateOfJoining"));
			at.setJoiningsalary(rs.getDouble("JoiningSalary"));
			at.setSex(rs.getString("Gender"));
			at.setMobile(rs.getString("Mobile"));
			at.setEmail(rs.getString("Email"));
			at.setProfiepic(rs.getString("ProfilePic"));
			at.setHouseno(rs.getString("houseno"));
			at.setStreet(rs.getString("street"));
			at.setCity(rs.getString("city"));
			at.setPostalCode(rs.getString("postalcode"));
			at.setDayspresent(rs.getInt("dayspresent"));
			at.setDaysinmonth(rs.getInt("daysinmonth"));
			String month = rs.getString("month");

			at.setMonth(month);
			String year = rs.getString("year");
			at.setYear(year);
			attendance.add(at);

			if (year != null)
				yearMap.put(year, year);

			if (month != null)
				monthmapfromdb.put(month, month);

			if (at.getCategoryid().equals("1")) {
				staffsPermenant.add(at);
			}
			if (at.getCategoryid().equals("2")) {
				staffsDomestic.add(at);
			}
			if (at.getCategoryid().equals("3")) {
				staffsTemporary.add(at);
			}
		}
		return attendance;
	}

	public String getCurrentMonth() {
		return currentMonth;
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
			String selectedmonth = getSelectedmonth();
			PreparedStatement ps;
			if (monthmapfromdb.get(selectedmonth) != null) {
				ps = conn.prepareStatement(
						"UPDATE ATTENDANCE  set dayspresent = ?, updatedatetime = now() " + "where staffid = ? and month = ? and year = ?");

				ps.setInt(1, staff.getDayspresent());
				ps.setString(2, staff.getId());
				ps.setString(3,selectedmonth);
				ps.setString(4,getSelectedyear());
			} else {
				ps = conn.prepareStatement(
						"INSERT INTO ATTENDANCE (staffid, date, dayspresent, daysinmonth, createdatetime, updatedatetime, month, year )"
								+ "VALUES (?,now(),?,?, now(), now(),?,?)");

				ps.setString(1, staff.getId());
				ps.setInt(2, staff.getDayspresent());
				ps.setInt(3, daysinselectedmonth);
				ps.setString(4, selectedmonth);
				ps.setString(5, getSelectedyear());
			}

			int rs = ps.executeUpdate();

			FacesMessage msg = null;
			if (rs == 1) {
				msg = new FacesMessage("Attendance added successfully", "");
				// setShowForm(false);
			} else {
				msg = new FacesMessage("Something went wrong", "Please contant your system administrator.");
			}
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		monthmapfromdb.put(selectedmonth, selectedmonth);
	}

	public void onRowSelect(SelectEvent event) {
		selectedStaffAttendance = staffAttendanceMap.get(selectedStaff.getId());
		FacesMessage msg = new FacesMessage("Car Selected", selectedStaff.getFullname());
		FacesContext.getCurrentInstance().addMessage(null, msg);
		createDonutModels(selectedStaffAttendance);
	}

	public void prefill() {
		for (Attendance a : staffs) {
			a.setDayspresent(getDaysinselectedmonth());
		}
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed",
					"Old: " + oldValue + ", New:" + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void setCurrentMonth(String currentMonth) {
		this.currentMonth = currentMonth;
	}

	public String getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(String currentYear) {
		this.currentYear = currentYear;
	}

	public int getDaysincurrentmonth() {
		return daysincurrentmonth;
	}

	public void setDaysincurrentmonth(int daysincurrentmonth) {
		this.daysincurrentmonth = daysincurrentmonth;
	}

	public LinkedHashMap<String, String> getYearMap() {
		return yearMap;
	}

	public void setYearMap(LinkedHashMap<String, String> yearMap) {
		AttendanceView.yearMap = yearMap;
	}

	public String getSelectedyear() {
		return selectedyear;
	}

	public void setSelectedyear(String selectedyear) {
		this.selectedyear = selectedyear;
	}

	public String getSelectedmonth() {
		return selectedmonth;
	}

	public void setSelectedmonth(String selectedmonth) {
		this.selectedmonth = selectedmonth;
	}

	public LinkedHashMap<String, String> getMonthMap() {
		return monthMap;
	}

	public void setMonthMap(LinkedHashMap<String, String> monthMap) {
		AttendanceView.monthMap = monthMap;
	}

	public void onYearChange() {
		System.out.println(selectedyear);
	}

	public void onMonthChange() throws ClassNotFoundException, SQLException {
		if (selectedmonth != null && !selectedmonth.equals("")) {
			setDaysinselectedmonth(monthDaysMap.get(selectedmonth));
			staffs = getAllStaffByMonthAndYear();
			setShowForm(true);
		}
	}

	public LinkedHashMap<String, String> getMonthmapfromdb() {
		return monthmapfromdb;
	}

	public void setMonthmapfromdb(LinkedHashMap<String, String> monthmapfromdb) {
		monthmapfromdb = monthmapfromdb;
	}

	public int getDaysinselectedmonth() {
		return daysinselectedmonth;
	}

	public void setDaysinselectedmonth(int daysinselectedmonth) {
		this.daysinselectedmonth = daysinselectedmonth;
	}

	public Map<String, List<Attendance>> getStaffAttendanceMap() {
		return staffAttendanceMap;
	}

	public void setStaffAttendanceMap(Map<String, List<Attendance>> staffAttendanceMap) {
		this.staffAttendanceMap = staffAttendanceMap;
	}

	public Staff getSelectedStaff() {
		return selectedStaff;
	}

	public void setSelectedStaff(Staff selectedStaff) {
		this.selectedStaff = selectedStaff;
	}

	public List<Staff> getAllStaffs() {
		return allStaffs;
	}

	public void setAllStaffs(List<Staff> allStaffs) {
		this.allStaffs = allStaffs;
	}

	public List<Staff> getFilteredStaffs() {
		return filteredStaffs;
	}

	public void setFilteredStaffs(List<Staff> filteredStaffs) {
		this.filteredStaffs = filteredStaffs;
	}

	public List<Attendance> getSelectedStaffAttendance() {
		return selectedStaffAttendance;
	}

	public void setSelectedStaffAttendance(List<Attendance> selectedStaffAttendance) {
		this.selectedStaffAttendance = selectedStaffAttendance;
	}

	public DonutChartModel getDonutModel2() {
		return donutModel2;
	}

	public void setDonutModel2(DonutChartModel donutModel2) {
		this.donutModel2 = donutModel2;
	}

}
