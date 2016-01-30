package com.sree.school;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

@ManagedBean(name = "staffFilterView")
@ViewScoped
public class StaffFilterView implements Serializable
{

  private static LinkedHashMap<String, String> category;

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private static Connection conn;

  static
  {
    category = new LinkedHashMap<String, String>();
    category.put("Permanent", "1");
    category.put("Domestic", "2");
    category.put("Part Time", "3");
  }

  public static void setCategory(LinkedHashMap<String, String> category)
  {
    StaffFilterView.category = category;
  }

  private List<Staff> filteredStaffs;
  private List<Staff> filteredStaffsByCategory;
  private List<Staff> filteredStaffsD;

  private List<Staff> filteredStaffsP;

  private List<Staff> filteredStaffsT;
  private String selectedCategoryId;
  private Staff selectedStaff;

  private Staff selectedStaffByCategory;
  private String selectedStaffIdByCategory;

  private boolean showForm;
  private List<Staff> staffByCategory;
  private Staff staffbylogin;
  private Map<String, Staff> staffMap = new HashMap<>();
  private List<Staff> staffs;

  private List<Staff> staffsDomestic;

  private List<Staff> staffsPermenant;

  private List<Staff> staffsTemporary;

  public StaffFilterView() throws ClassNotFoundException, SQLException
  {
    staffsPermenant = new ArrayList<>();
    staffsTemporary = new ArrayList<>();
    staffsDomestic = new ArrayList<>();

    staffs = getAllStaff();
  }

  public String delete(Staff staff)
  {

    FacesMessage msg = null;

    try
    {
      conn = DBConnection.getConnection();
      conn.createStatement().executeUpdate("UPDATE staff set isarchived = '1' where id = " + "'" + staff.getId() + "'");
      msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Staff " + staff.getFullname() + " has been deleted.", "");
    }
    catch (ClassNotFoundException e)
    {
      msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Something went wrong", "Please contant your system administrator.");
      e.printStackTrace();
    }
    catch (SQLException e)
    {
      msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Something went wrong", "Please contant your system administrator.");
      e.printStackTrace();
    }

    return null;
  }

  public List<Staff> getAllStaff() throws ClassNotFoundException, SQLException
  {
    java.sql.Connection conn = DBConnection.getConnection();
    ResultSet rs = conn.createStatement()
        .executeQuery("select Id, FirstName, LastName, CategoryId, Designation," + "SpouseName, SpouseOccupation, Phone, DateOfBirth, DateOfJoining,"
            + "JoiningSalary, Gender, Mobile, Email, ProfilePic, houseno, street, city, postalcode from staff where isarchived=0");

    List<Staff> students = new ArrayList<>();
    while (rs.next())
    {
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
      if (st.getCategoryid().equals("1"))
      {
        staffsPermenant.add(st);
      }
      if (st.getCategoryid().equals("2"))
      {
        staffsDomestic.add(st);
      }
      if (st.getCategoryid().equals("3"))
      {
        staffsTemporary.add(st);
      }
    }
    return students;
  }

  public LinkedHashMap<String, String> getCategory()
  {
    return category;
  }

  public List<Staff> getFilteredStaffs()
  {
    return filteredStaffs;
  }

  public List<Staff> getFilteredStaffsByCategory()
  {
    return filteredStaffsByCategory;
  }

  public List<Staff> getFilteredStaffsD()
  {
    return filteredStaffsD;
  }

  public List<Staff> getFilteredStaffsP()
  {
    return filteredStaffsP;
  }

  public List<Staff> getFilteredStaffsT()
  {
    return filteredStaffsT;
  }

  private void getSalaryDetails(String selectedStaffIdByCategory2)
  {

    ResultSet rs;
    Salary st = new Salary();
    try
    {
      java.sql.Connection conn = DBConnection.getConnection();
      rs = conn.createStatement()
          .executeQuery("select employeeid, basicsalary, fixedda, hra, conveyanceall," + "pfno, sbacno, pfrate, proftaxdeduction, otherdeduction, modeofpayment,iseligibleforpf,"
              + "pfamount, loanamount from salary where employeeid = " + "'" + selectedStaffIdByCategory2 + "'");
      if (rs.next())
      {
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
        st.setModeofpayment(rs.getString("modeofpayment"));
        st.setIseligibleforpf(rs.getBoolean("iseligibleforpf"));

        FacesContext context = FacesContext.getCurrentInstance();
        SalaryBean bean = context.getApplication().evaluateExpressionGet(context, "#{salaryBean}", SalaryBean.class);

        bean.setSalary(st);
        bean.setSelectedmodeofpayment(rs.getString("modeofpayment"));
        bean.setAlreadyPresent(true);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
  }

  public String getSelectedCategoryId()
  {
    return selectedCategoryId;
  }

  public Staff getSelectedStaff()
  {
    return selectedStaff;
  }

  public Staff getSelectedStaffByCategory()
  {
    return selectedStaffByCategory;
  }

  public String getSelectedStaffIdByCategory()
  {
    return selectedStaffIdByCategory;
  }

  public List<Staff> getStaffByCategory()
  {
    if (staffByCategory != null && staffByCategory.size() > 0)
      return staffByCategory;
    else
      try
      {
        return getStaffByCategory(selectedCategoryId);
      }
      catch (ClassNotFoundException e)
      {
        e.printStackTrace();
        return null;
      }
      catch (SQLException e)
      {
        e.printStackTrace();
        return null;
      }

  }

  public List<Staff> getStaffByCategory(String categoryID) throws ClassNotFoundException, SQLException
  {
    java.sql.Connection conn = DBConnection.getConnection();
    ResultSet rs = conn.createStatement()
        .executeQuery("select Id, FirstName, LastName, CategoryId, Designation," + "SpouseName, SpouseOccupation, Phone, DateOfBirth, DateOfJoining,"
            + "JoiningSalary, Gender, Mobile, Email, ProfilePic, houseno, street, city, postalcode from staff where isarchived=0 and CategoryId = " + "'" + categoryID + "'");

    List<Staff> students = new ArrayList<>();
    while (rs.next())
    {
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

  public Staff getStaffbylogin()
  {
    if (staffbylogin != null && staffbylogin.getId() != null)
      return staffbylogin;
    else
      try
      {
        return getStaffByLoginID();
      }
      catch (ClassNotFoundException | SQLException e)
      {
        e.printStackTrace();
        return null;
      }
  }

  public Staff getStaffByLoginID() throws ClassNotFoundException, SQLException
  {
    java.sql.Connection conn = DBConnection.getConnection();
    ResultSet rs = conn.createStatement()
        .executeQuery("select Id, FirstName, LastName, CategoryId, Designation," + "SpouseName, SpouseOccupation, Phone, DateOfBirth, DateOfJoining,"
            + "JoiningSalary, Gender, Mobile, Email, ProfilePic, houseno, street, city, postalcode from staff where isarchived=0 and Id = " + "'" + Util.getStaffid() + "'");

    Staff st = new Staff();
    if (rs.next())
    {
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

  public List<Staff> getStaffs()
  {
    return staffs;
  }

  public List<Staff> getStaffsDomestic()
  {
    return staffsDomestic;
  }

  public List<Staff> getStaffsPermenant()
  {
    return staffsPermenant;
  }

  public List<Staff> getStaffsTemporary()
  {
    return staffsTemporary;
  }

  public boolean isShowForm()
  {
    return this.showForm;
  }

  public void onCategoryChange()
  {
    filteredStaffsByCategory = getStaffByCategory();
    selectedStaffIdByCategory = null;
    setShowForm(false);
  }

  public void onStaffChange()
  {
    setShowForm(selectedStaffIdByCategory != null && !selectedStaffIdByCategory.equals("") && selectedCategoryId != null && !selectedCategoryId.equals(""));
    selectedStaffByCategory = staffMap.get(selectedStaffIdByCategory);
    getSalaryDetails(selectedStaffIdByCategory);
  }

  public String saveSuccess(Staff staff)
  {

    try
    {
      conn = DBConnection.getConnection();

      PreparedStatement ps = conn.prepareStatement(
          "UPDATE STAFF set FirstName = ?, LastName = ?, CategoryId = ?, Designation = ?," + "SpouseName = ?, SpouseOccupation = ?, Phone = ?, DateOfBirth = ?, DateOfJoining = ?,"
              + "JoiningSalary = ?, Gender = ?, Mobile = ?, Email = ?, ProfilePic = ?, houseno = ?, street = ?, city = ?, postalcode = ?, updatedatetime = now() where Id = ?");

      ps.setString(1, staff.getFirstname());
      ps.setString(2, staff.getLastname());
      ps.setString(3, staff.getCategoryid());
      ps.setString(4, staff.getDesignation());
      ps.setString(5, staff.getSpouseName());
      ps.setString(6, staff.getSpouseOccupation());
      String phone = staff.getPhone();
      if (phone == null)
        ps.setString(7, "");
      else
        ps.setString(7, phone);
      Date dob = staff.getDob();
      if (dob == null)
      {
        ps.setDate(8, null);
      }
      else
      {
        ps.setDate(8, new java.sql.Date(dob.getTime()));
      }
      ps.setDate(9, new java.sql.Date(staff.getDoj().getTime()));
      ps.setDouble(10, staff.getJoiningsalary());
      ps.setString(11, staff.getSex());
      ps.setString(12, staff.getMobile());
      ps.setString(13, staff.getEmail());
      ps.setString(14, staff.getProfiepic());
      ps.setString(15, staff.getHouseno());
      ps.setString(16, staff.getStreet());
      ps.setString(17, staff.getCity());
      ps.setString(18, staff.getPostalCode());
      ps.setString(19, staff.getId());

      int rs = ps.executeUpdate();

      FacesMessage msg = null;
      if (rs == 1)
      {
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Student " + staff.getFullname() + " has been saved", null);
      }
      else
      {
        msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Something went wrong", "Please contant your system administrator.");
      }
      FacesContext.getCurrentInstance().addMessage(null, msg);

    }
    catch (ClassNotFoundException | SQLException e)
    {
      e.printStackTrace();
    }

    return null;
  }

  public void setFilteredStaffs(List<Staff> filteredStaffs)
  {
    this.filteredStaffs = filteredStaffs;
  }

  public void setFilteredStaffsByCategory(List<Staff> filteredStaffsByCategory)
  {
    this.filteredStaffsByCategory = filteredStaffsByCategory;
  }

  public void setFilteredStaffsD(List<Staff> filteredStaffsD)
  {
    this.filteredStaffsD = filteredStaffsD;
  }

  public void setFilteredStaffsP(List<Staff> filteredStaffsP)
  {
    this.filteredStaffsP = filteredStaffsP;
  }

  public void setFilteredStaffsT(List<Staff> filteredStaffsT)
  {
    this.filteredStaffsT = filteredStaffsT;
  }

  public void setSelectedCategoryId(String selectedCategoryId)
  {
    this.selectedCategoryId = selectedCategoryId;
  }

  public void setSelectedStaff(Staff selectedStaff)
  {
    this.selectedStaff = selectedStaff;
  }

  public void setSelectedStaffByCategory(Staff selectedStaffByCategory)
  {
    this.selectedStaffByCategory = selectedStaffByCategory;
  }

  public void setSelectedStaffIdByCategory(String selectedStaffIdByCategory)
  {
    this.selectedStaffIdByCategory = selectedStaffIdByCategory;
  }

  public void setShowForm(boolean showForm)
  {
    this.showForm = showForm;
  }

  public void setStaffByCategory(List<Staff> staffByCategory)
  {
    this.staffByCategory = staffByCategory;
  }

  public void setStaffbylogin(Staff staffbylogin)
  {
    this.staffbylogin = staffbylogin;
  }

  public void setStaffsDomestic(List<Staff> staffsDomestic)
  {
    this.staffsDomestic = staffsDomestic;
  }

  public void setStaffsPermenant(List<Staff> staffsPermenant)
  {
    this.staffsPermenant = staffsPermenant;
  }

  public void setStaffsTemporary(List<Staff> staffsTemporary)
  {
    this.staffsTemporary = staffsTemporary;
  }

  public void viewMyProfile()
  {
    RequestContext.getCurrentInstance().openDialog("showmyprofile");
  }
}
