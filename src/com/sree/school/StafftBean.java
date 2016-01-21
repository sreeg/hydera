package com.sree.school;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedHashMap;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

@ManagedBean(name = "staffBean")
@ViewScoped
public class StafftBean implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private static Connection conn;
  private static LinkedHashMap<String, String> modeofpayment;

  static
  {
    modeofpayment = new LinkedHashMap<String, String>();
    modeofpayment.put("Cash", "1");
    modeofpayment.put("Cheque", "2");
    modeofpayment.put("Online", "3");
  }

  private Staff staff = new Staff();

  private Salary salary = new Salary();

  private boolean showForm = true;

  private String selectedmodeofpayment;

  PreparedStatement ps = null;

  public LinkedHashMap<String, String> getModeofpayment()
  {
    return modeofpayment;
  }

  public Salary getSalary()
  {
    return salary;
  }

  public String getSelectedmodeofpayment()
  {
    return selectedmodeofpayment;
  }

  public Staff getStaff()
  {
    return staff;
  }

  @PostConstruct
  public void init()
  {
    getStaff().setDoj(Calendar.getInstance().getTime());
    getStaff().setCity("Hyderabad");
  }

  public boolean isShowForm()
  {
    return showForm;
  }

  public void save() throws ClassNotFoundException, SQLException
  {
    conn = DBConnection.getConnection();
    ResultSet rs1 = conn.createStatement().executeQuery("select * from staff");

    int i = 0;
    while (rs1.next())
    {
      i++;
    }

    ps = conn.prepareStatement("INSERT INTO STAFF (Id, FirstName, LastName, CategoryId, Designation," + "SpouseName, SpouseOccupation, Phone, DateOfBirth, DateOfJoining,"
        + "JoiningSalary, Gender, Mobile, Email, ProfilePic, houseno, street, city, postalcode, createdatetime, updatedatetime)"
        + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, now(), now())");

    String staffID = "STAFF_ID" + (i + 1);
    ps.setString(1, staffID);
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
    if (rs == 1)
    {
      msg = new FacesMessage("Staff added successfully", "");
      setShowForm(false);
    }
    else
    {
      msg = new FacesMessage("Something went wrong", "Please contant your system administrator.");
    }
    FacesContext.getCurrentInstance().addMessage(null, msg);

    try
    {
      ps = conn.prepareStatement("INSERT INTO SALARY (employeeid, basicsalary, fixedda, hra, conveyanceall," + "pfno, sbacno, pfrate, proftaxdeduction, otherdeduction,"
          + "pfamount, loanamount, modeofpayment, iseligibleforpf, createdatetime, updatedatetime) " + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),now())");

      ps.setString(1, staffID);
      ps.setDouble(2, salary.getBasicsalary());
      ps.setDouble(3, salary.getFixedda());
      ps.setDouble(4, salary.getHra());
      ps.setDouble(5, salary.getConveyanceall());
      ps.setString(6, salary.getPfno());
      ps.setString(7, salary.getSbacno());
      ps.setDouble(8, salary.getPfrate());
      ps.setDouble(9, salary.getProftaxdeduction());
      ps.setDouble(10, salary.getOtherdeduction());
      ps.setDouble(11, salary.getPfamount());
      ps.setDouble(12, salary.getLoanamount());
      ps.setString(13, selectedmodeofpayment);
      ps.setBoolean(14, salary.getIseligibleforpf());

      rs = ps.executeUpdate();

      if (rs == 1)
      {
        msg = new FacesMessage("Salary added successfully", "");
        setShowForm(false);
      }
      else
      {
        msg = new FacesMessage("Something went wrong", "Please contant your system administrator.");
      }
      FacesContext.getCurrentInstance().addMessage(null, msg);

    }
    catch (SQLException e)
    {
      msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Something went wrong", "Please contant your system administrator.");
      e.printStackTrace();
      FacesContext.getCurrentInstance().addMessage(null, msg);

    }

  }

  public void setModeofpayment(LinkedHashMap<String, String> modeofpayment)
  {
    StafftBean.modeofpayment = modeofpayment;
  }

  public void setSalary(Salary salary)
  {
    this.salary = salary;
  }

  public void setSelectedmodeofpayment(String selectedmodeofpayment)
  {
    this.selectedmodeofpayment = selectedmodeofpayment;
  }

  public void setShowForm(boolean showForm)
  {
    this.showForm = showForm;
  }

  public void setStaff(Staff staff)
  {
    this.staff = staff;
  }
}
