package com.sree.school;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

@ManagedBean(name = "staffFilterView")
@ViewScoped
public class StaffFilterView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static LinkedHashMap<String, String> category;
	private Map<String, Staff> staffMap = new HashMap<>();
	private List<Staff> staffs;
	
	private List<Staff> staffsPermenant;
	private List<Staff> staffsTemporary;
	private List<Staff> staffsDomestic;
	
	private List<Staff> filteredStaffs;
	
	private List<Staff> filteredStaffsP;
	private List<Staff> filteredStaffsT;
	private List<Staff> filteredStaffsD;
	
	private List<Staff> filteredStaffsByCategory;
	private List<Staff> staffByCategory;

	private Staff selectedStaff;
	private Staff selectedStaffByCategory;
	private Staff staffbylogin;
	private String selectedCategoryId;
	private String selectedStaffIdByCategory;

	private boolean showForm;

	static {
		category = new LinkedHashMap<String, String>();
		category.put("Permanent", "1");
		category.put("Domestic", "2");
		category.put("Part Time", "3");
	}

	public StaffFilterView() throws ClassNotFoundException, SQLException {
		staffsPermenant = new ArrayList<>();
		staffsTemporary = new ArrayList<>();
		staffsDomestic = new ArrayList<>();
		
		staffs = getAllStaff();
	}

	public List<Staff> getStaffs() {
		return staffs;
	}

	public List<Staff> getFilteredStaffs() {
		return filteredStaffs;
	}

	public void setFilteredStaffs(List<Staff> filteredStaffs) {
		this.filteredStaffs = filteredStaffs;
	}

	public List<Staff> getAllStaff() throws ClassNotFoundException, SQLException {
		java.sql.Connection conn = DBConnection.getConnection();
		ResultSet rs = conn.createStatement().executeQuery("select Id, FirstName, LastName, CategoryId, Designation,"
				+ "SpouseName, SpouseOccupation, Phone, DateOfBirth, DateOfJoining,"
				+ "JoiningSalary, Gender, Mobile, Email, ProfilePic, houseno, street, city, postalcode from staff where isarchived=0");

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
			if(st.getCategoryid().equals("1"))
			{
				staffsPermenant.add(st);
			}
			if(st.getCategoryid().equals("2"))
			{
				staffsDomestic.add(st);
			}
			if(st.getCategoryid().equals("3"))
			{
				staffsTemporary.add(st);
			}
		}
		return students;
	}

	public Staff getStaffByLoginID() throws ClassNotFoundException, SQLException {
		java.sql.Connection conn = DBConnection.getConnection();
		ResultSet rs = conn.createStatement()
				.executeQuery("select Id, FirstName, LastName, CategoryId, Designation,"
						+ "SpouseName, SpouseOccupation, Phone, DateOfBirth, DateOfJoining,"
						+ "JoiningSalary, Gender, Mobile, Email, ProfilePic, houseno, street, city, postalcode from staff where Id = "
						+ "'" + Util.getStaffid() + "'");

		Staff st = new Staff();
		if (rs.next()) {
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
		}
		return st;
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

	public Staff getSelectedStaff() {
		return selectedStaff;
	}

	public void setSelectedStaff(Staff selectedStaff) {
		this.selectedStaff = selectedStaff;
	}

	public Staff getStaffbylogin() {
		if (staffbylogin != null && staffbylogin.getId() != null)
			return staffbylogin;
		else
			try {
				return getStaffByLoginID();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				return null;
			}
	}

	public void setStaffbylogin(Staff staffbylogin) {
		this.staffbylogin = staffbylogin;
	}

	public void viewMyProfile() {
		RequestContext.getCurrentInstance().openDialog("showmyprofile");
	}

	public List<Staff> getStaffByCategory() {
		if (staffByCategory != null && staffByCategory.size() > 0)
			return staffByCategory;
		else
			try {
				return getStaffByCategory(selectedCategoryId);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return null;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}

	}

	public void setStaffByCategory(List<Staff> staffByCategory) {
		this.staffByCategory = staffByCategory;
	}

	public String getSelectedCategoryId() {
		return selectedCategoryId;
	}

	public void setSelectedCategoryId(String selectedCategoryId) {
		this.selectedCategoryId = selectedCategoryId;
	}

	public void onCategoryChange() {
		filteredStaffsByCategory = getStaffByCategory();
		selectedStaffIdByCategory = null;
		setShowForm(false);
	}

	public void onStaffChange() {
		setShowForm(selectedStaffIdByCategory != null && !selectedStaffIdByCategory.equals("") && selectedCategoryId != null && !selectedCategoryId.equals(""));
		selectedStaffByCategory = staffMap.get(selectedStaffIdByCategory);
		getSalaryDetails(selectedStaffIdByCategory);
	}

	private void getSalaryDetails(String selectedStaffIdByCategory2) {

		ResultSet rs;
		Salary st = new Salary();
		try {
			java.sql.Connection conn = DBConnection.getConnection();
			rs = conn.createStatement().executeQuery("select employeeid, basicsalary, fixedda, hra, conveyanceall,"
					+ "pfno, sbacno, pfrate, proftaxdeduction, otherdeduction,"
					+ "pfamount, loanamount from salary where employeeid = " + "'" + selectedStaffIdByCategory2 + "'");
			if (rs.next()) {
				st.setEmployeeid(rs.getString("employeeid"));
				st.setBasicsalary(rs.getDouble("basicsalary"));
				st.setFixedda(rs.getDouble("fixedda"));
				st.setHra(rs.getDouble("hra"));
				st.setConveyanceall(rs.getDouble("conveyanceall"));
				st.setPfno(rs.getString("pfno"));
				st.setSbacno(rs.getString("sbacno"));
				st.setPfrate(rs.getDouble("pfrate"));
				st.setProftaxdeduction(rs.getDouble("proftaxdeduction"));
				st.setOtherdeduction(rs.getDouble("otherdeduction"));
				st.setPfamount(rs.getDouble("pfamount"));
				st.setLoanamount(rs.getDouble("loanamount"));

				FacesContext context = FacesContext.getCurrentInstance();
				SalaryBean bean = context.getApplication().evaluateExpressionGet(context, "#{salaryBean}", SalaryBean.class);
				
				bean.setSalary(st);
				bean.setAlreadyPresent(true);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<Staff> getFilteredStaffsByCategory() {
		return filteredStaffsByCategory;
	}

	public void setFilteredStaffsByCategory(List<Staff> filteredStaffsByCategory) {
		this.filteredStaffsByCategory = filteredStaffsByCategory;
	}

	public String getSelectedStaffIdByCategory() {
		return selectedStaffIdByCategory;
	}

	public void setSelectedStaffIdByCategory(String selectedStaffIdByCategory) {
		this.selectedStaffIdByCategory = selectedStaffIdByCategory;
	}

	public LinkedHashMap<String, String> getCategory() {
		return category;
	}

	public static void setCategory(LinkedHashMap<String, String> category) {
		StaffFilterView.category = category;
	}

	public boolean isShowForm() {
		return this.showForm;
	}

	public void setShowForm(boolean showForm) {
		this.showForm = showForm;
	}

	public Staff getSelectedStaffByCategory() {
		return selectedStaffByCategory;
	}

	public void setSelectedStaffByCategory(Staff selectedStaffByCategory) {
		this.selectedStaffByCategory = selectedStaffByCategory;
	}

	public List<Staff> getStaffsPermenant() {
		return staffsPermenant;
	}

	public void setStaffsPermenant(List<Staff> staffsPermenant) {
		this.staffsPermenant = staffsPermenant;
	}

	public List<Staff> getStaffsTemporary() {
		return staffsTemporary;
	}

	public void setStaffsTemporary(List<Staff> staffsTemporary) {
		this.staffsTemporary = staffsTemporary;
	}

	public List<Staff> getStaffsDomestic() {
		return staffsDomestic;
	}

	public void setStaffsDomestic(List<Staff> staffsDomestic) {
		this.staffsDomestic = staffsDomestic;
	}

	public List<Staff> getFilteredStaffsP() {
		return filteredStaffsP;
	}

	public void setFilteredStaffsP(List<Staff> filteredStaffsP) {
		this.filteredStaffsP = filteredStaffsP;
	}

	public List<Staff> getFilteredStaffsT() {
		return filteredStaffsT;
	}

	public void setFilteredStaffsT(List<Staff> filteredStaffsT) {
		this.filteredStaffsT = filteredStaffsT;
	}

	public List<Staff> getFilteredStaffsD() {
		return filteredStaffsD;
	}

	public void setFilteredStaffsD(List<Staff> filteredStaffsD) {
		this.filteredStaffsD = filteredStaffsD;
	}

}
