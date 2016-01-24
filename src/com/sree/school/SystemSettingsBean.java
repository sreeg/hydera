package com.sree.school;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "systemSettingsBean")
@SessionScoped
public class SystemSettingsBean implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private static Connection conn;
  public static String schoolname;
  public static String shortdescription;
  public static boolean disableemail;
  public static boolean peoplemanagement;
  public static boolean salarymanagement;
  public static boolean feemanagement;
  public static boolean charts;
  public static boolean reports;
  public static StreamedContent logo;

  private String currentPassword;
  private String newPassword1;
  private String newPassword2;
  private String designationItem;
  private String sectionNameItem;
  private String designationItemStaff;

  private String staffCategoryItem;
  private String bankNameItem;

  public SystemSettings systemSettings = new SystemSettings();

  PreparedStatement ps = null;

  public SystemSettingsBean()
  {
    try
    {
      getSystemSettingsFromDB();
    }
    catch (ClassNotFoundException | SQLException e)
    {
      e.printStackTrace();
    }
  }

  public String addBankNames()
  {
    Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    String type = params.get("type");
    FacesMessage msg;
    if (bankNameItem != null && !"".equals(bankNameItem))
    {
      try
      {
        conn = DBConnection.getConnection();
        conn.createStatement().executeUpdate("INSERT INTO lists (name, type) VALUES( '" + bankNameItem + "', '" + type + "')");
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
      catch (ClassNotFoundException e)
      {
        e.printStackTrace();
      }

      systemSettings.getBanknamesList().add(this.bankNameItem);
      this.bankNameItem = "";
      msg = new FacesMessage("Item Added");
    }
    else
    {
      msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Name cannot be empty", "");
    }
    FacesContext.getCurrentInstance().addMessage(null, msg);
    return null;
  }

  public String addDesignation()
  {
    Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    String type = params.get("type");
    FacesMessage msg;
    if (designationItem != null && !"".equals(designationItem))
    {
      try
      {
        conn = DBConnection.getConnection();
        conn.createStatement().executeUpdate("INSERT INTO lists (name, type) VALUES( '" + designationItem + "', '" + type + "')");
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
      catch (ClassNotFoundException e)
      {
        e.printStackTrace();
      }

      systemSettings.getDesignationsList().add(this.designationItem);
      this.designationItem = "";
      msg = new FacesMessage("Item Added");
    }
    else
    {
      msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Name cannot be empty", "");
    }
    FacesContext.getCurrentInstance().addMessage(null, msg);
    return null;
  }

  public String addDesignationStaff()
  {
    Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    String type = params.get("type");
    FacesMessage msg;
    if (designationItemStaff != null && !"".equals(designationItemStaff))
    {
      try
      {
        conn = DBConnection.getConnection();
        conn.createStatement().executeUpdate("INSERT INTO lists (name, type) VALUES( '" + designationItemStaff + "', '" + type + "')");
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
      catch (ClassNotFoundException e)
      {
        e.printStackTrace();
      }
      systemSettings.getDesignationsListStaff().add(this.designationItemStaff);
      this.designationItemStaff = "";
      msg = new FacesMessage("Item Added");
    }
    else
    {
      msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Name cannot be empty", "");
    }
    FacesContext.getCurrentInstance().addMessage(null, msg);
    return null;
  }

  public String addSectionNames()
  {
    Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    String type = params.get("type");
    FacesMessage msg;
    if (sectionNameItem != null && !"".equals(sectionNameItem))
    {
      try
      {
        conn = DBConnection.getConnection();
        conn.createStatement().executeUpdate("INSERT INTO lists (name, type) VALUES( '" + sectionNameItem + "', '" + type + "')");
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
      catch (ClassNotFoundException e)
      {
        e.printStackTrace();
      }

      systemSettings.getSectionList().add(this.sectionNameItem);
      this.sectionNameItem = "";
      msg = new FacesMessage("Item Added");
    }
    else
    {
      msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Name cannot be empty", "");
    }
    FacesContext.getCurrentInstance().addMessage(null, msg);
    return null;
  }

  private void deleteSystemSettings() throws ClassNotFoundException, SQLException
  {
    conn = DBConnection.getConnection();
    conn.createStatement().executeUpdate("DELETE from SYSTEMSETTINGS");
  }

  public String getBankNameItem()
  {
    return bankNameItem;
  }

  public String getCurrentPassword()
  {
    return currentPassword;
  }

  public String getDesignationItem()
  {
    return designationItem;
  }

  public String getDesignationItemStaff()
  {
    return designationItemStaff;
  }

  public StreamedContent getLogo()
  {
    return logo;
  }

  public String getNewPassword1()
  {
    return newPassword1;
  }

  public String getNewPassword2()
  {
    return newPassword2;
  }

  public String getSchoolname()
  {
    return schoolname;
  }

  public String getSectionNameItem()
  {
    return sectionNameItem;
  }

  public String getShortdescription()
  {
    return shortdescription;
  }

  public String getStaffCategoryItem()
  {
    return staffCategoryItem;
  }

  public SystemSettings getSystemSettings()
  {
    return systemSettings;
  }

  public void getSystemSettingsFromDB() throws ClassNotFoundException, SQLException
  {
    conn = DBConnection.getConnection();
    ResultSet rs = conn.createStatement().executeQuery("SELECT email, password, showwidget1, showwidget2, emailhost, smtpport, disableemail from SYSTEMSETTINGS");
    while (rs.next())
    {
      systemSettings.setEmail(rs.getString("email"));
      systemSettings.setPassword(rs.getString("password"));
      systemSettings.setShowwidget1(rs.getBoolean("showwidget1"));
      systemSettings.setShowwidget2(rs.getBoolean("showwidget2"));
      systemSettings.setEmailhostname(rs.getString("emailhost"));
      systemSettings.setSmtpport(rs.getString("smtpport"));
      systemSettings.setDisableemail(rs.getBoolean("disableemail"));
    }

    ArrayList<String> d = new ArrayList<>();
    ArrayList<String> dS = new ArrayList<>();
    ArrayList<String> banks = new ArrayList<>();
    ArrayList<String> sections = new ArrayList<>();
    rs = conn.createStatement().executeQuery("SELECT name,type,description from lists order by name");
    while (rs.next())
    {
      String dType = rs.getString("type");
      String list_name = rs.getString("name");
      if ("parent".equals(dType))
        d.add(list_name);
      else if ("staff".equals(dType))
        dS.add(list_name);
      else if ("banks".equals(dType))
        banks.add(list_name);
      else if ("section".equals(dType))
        sections.add(list_name);
    }
    systemSettings.setDesignationsList(d);
    systemSettings.setDesignationsListStaff(dS);
    systemSettings.setBanknamesList(banks);
    systemSettings.setSectionList(sections);

    ArrayList<FeeDetails> fd = new ArrayList<FeeDetails>();
    rs = conn.createStatement().executeQuery("SELECT classname, term1amount, term1amount, term2amount, term3amount, noofterms, termamount from feedetails order by row");
    while (rs.next())
    {
      FeeDetails f = new FeeDetails();
      f.setClassname(rs.getString("classname"));
      f.setTerm1amount(rs.getDouble("term1amount"));
      f.setTerm2amount(rs.getDouble("term2amount"));
      f.setTerm3amount(rs.getDouble("term3amount"));
      f.setTermamount(rs.getDouble("termamount"));
      f.setNoofterms(rs.getInt("noofterms"));
      fd.add(f);
    }
    systemSettings.setFeedetails(fd);
    Map<String, FeeDetails> map = new HashMap<String, FeeDetails>();
    for (FeeDetails f : fd)
      map.put(f.getClassname(), f);
    systemSettings.setFeedetailsByclass(map);
  }

  public boolean isCharts()
  {
    return charts;
  }

  public boolean isDisableemail()
  {
    return disableemail;
  }

  public boolean isFeemanagement()
  {
    return feemanagement;
  }

  public boolean isPeoplemanagement()
  {
    return peoplemanagement;
  }

  public boolean isReports()
  {
    return reports;
  }

  public boolean isSalarymanagement()
  {
    return salarymanagement;
  }

  public void onCancelDesignation(RowEditEvent event)
  {
    FacesMessage msg = new FacesMessage("Item Removed");
    FacesContext.getCurrentInstance().addMessage(null, msg);
    String object = (String) event.getObject();
    systemSettings.getDesignationsList().remove(object);
    systemSettings.getDesignationsListStaff().remove(object);
    try
    {
      conn = DBConnection.getConnection();
      conn.createStatement().executeUpdate("DELETE from lists where name = '" + object + "'");
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

  public void onCancelFeedetails(RowEditEvent event)
  {

  }

  public void onCancelSection(RowEditEvent event)
  {
    FacesMessage msg = new FacesMessage("Item Removed");
    FacesContext.getCurrentInstance().addMessage(null, msg);
    String object = (String) event.getObject();
    systemSettings.getSectionList().remove(object);
    try
    {
      conn = DBConnection.getConnection();
      conn.createStatement().executeUpdate("DELETE from lists where name = '" + object + "'");
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

  public void onEditDesignation(RowEditEvent event)
  {

  }

  public void onEditFeedetails(RowEditEvent event)
  {
    FeeDetails fd = (FeeDetails) event.getObject();
    try
    {
      conn = DBConnection.getConnection();
      conn.createStatement()
          .executeUpdate("UPDATE feedetails set term1amount = '" + fd.getTerm1amount() + "'" + ", term2amount = '" + fd.getTerm2amount() + "'" + ", term3amount = '"
              + fd.getTerm3amount() + "' , termamount = '" + fd.getTermamount() + "', noofterms = '" + fd.getNoofterms() + "' where classname = '" + fd.getClassname() + "'");
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    FacesMessage msg = new FacesMessage("Updated the record");
    FacesContext.getCurrentInstance().addMessage(null, msg);
  }

  public void save() throws ClassNotFoundException, SQLException
  {
    deleteSystemSettings();
    conn = DBConnection.getConnection();
    ps = conn.prepareStatement("INSERT INTO SYSTEMSETTINGS (email, password, showwidget1, showwidget2, emailhost, smtpport, disableemail)" + " VALUES (?,?,?,?,?,?,?)");

    ps.setString(1, systemSettings.getEmail());
    ps.setString(2, systemSettings.getPassword());
    ps.setBoolean(3, systemSettings.isShowwidget1());
    ps.setBoolean(4, systemSettings.isShowwidget2());
    ps.setString(5, systemSettings.getEmailhostname());
    ps.setString(6, systemSettings.getSmtpport());
    ps.setBoolean(7, systemSettings.getDisableemail());

    int rs = ps.executeUpdate();

    FacesMessage msg = null;
    if (rs == 1)
    {
      msg = new FacesMessage("Settings added successfully", "");
    }
    else
    {
      msg = new FacesMessage("Something went wrong", "Please contant your system administrator.");
    }
    FacesContext.getCurrentInstance().addMessage(null, msg);
  }

  public void setBankNameItem(String bankNameItem)
  {
    this.bankNameItem = bankNameItem;
  }

  public void setCharts(boolean charts)
  {
    SystemSettingsBean.charts = charts;
  }

  public void setCurrentPassword(String currentPassword)
  {
    this.currentPassword = currentPassword;
  }

  public void setDesignationItem(String designationItem)
  {
    this.designationItem = designationItem;
  }

  public void setDesignationItemStaff(String designationItemStaff)
  {
    this.designationItemStaff = designationItemStaff;
  }

  public void setDisableemail(boolean disableemail)
  {
    SystemSettingsBean.disableemail = disableemail;
  }

  public void setFeemanagement(boolean feemanagement)
  {
    SystemSettingsBean.feemanagement = feemanagement;
  }

  public void setLogo(StreamedContent logo)
  {
    SystemSettingsBean.logo = logo;
  }

  public void setNewPassword1(String newPassword1)
  {
    this.newPassword1 = newPassword1;
  }

  public void setNewPassword2(String newPassword2)
  {
    this.newPassword2 = newPassword2;
  }

  public void setPeoplemanagement(boolean peoplemanagement)
  {
    SystemSettingsBean.peoplemanagement = peoplemanagement;
  }

  public void setReports(boolean reports)
  {
    SystemSettingsBean.reports = reports;
  }

  public void setSalarymanagement(boolean salarymanagement)
  {
    SystemSettingsBean.salarymanagement = salarymanagement;
  }

  public void setSchoolname(String schoolname)
  {
    SystemSettingsBean.schoolname = schoolname;
  }

  public void setSectionNameItem(String sectionNameItem)
  {
    this.sectionNameItem = sectionNameItem;
  }

  public void setShortdescription(String shortdescription)
  {
    SystemSettingsBean.shortdescription = shortdescription;
  }

  public void setStaffCategoryItem(String staffCategoryItem)
  {
    this.staffCategoryItem = staffCategoryItem;
  }

  public void setSystemSettings(SystemSettings systemSettings)
  {
    this.systemSettings = systemSettings;
  }

  public void updatePassword()
  {
    RequestContext.getCurrentInstance().openDialog("updatePassword");
  }
}
