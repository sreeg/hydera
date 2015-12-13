package com.sree.school;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

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
  public static StreamedContent logo;

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

  private void deleteSystemSettings() throws ClassNotFoundException, SQLException
  {
    conn = DBConnection.getConnection();
    conn.createStatement().executeUpdate("DELETE from SYSTEMSETTINGS");
  }

  public StreamedContent getLogo()
  {
    return logo;
  }

  public String getSchoolname()
  {
    return schoolname;
  }

  public String getShortdescription()
  {
    return shortdescription;
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

  public boolean isSalarymanagement()
  {
    return salarymanagement;
  }

  public void save() throws ClassNotFoundException, SQLException
  {
    deleteSystemSettings();
    conn = DBConnection.getConnection();
    ps = conn.prepareStatement("INSERT INTO SYSTEMSETTINGS (email, password, showwidget1, showwidget2, emailhost, smtpport, disableemail)" + "VALUES (?,?,?,?,?,?,?)");

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

  public void setCharts(boolean charts)
  {
    SystemSettingsBean.charts = charts;
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

  public void setPeoplemanagement(boolean peoplemanagement)
  {
    SystemSettingsBean.peoplemanagement = peoplemanagement;
  }

  public void setSalarymanagement(boolean salarymanagement)
  {
    SystemSettingsBean.salarymanagement = salarymanagement;
  }

  public void setSchoolname(String schoolname)
  {
    SystemSettingsBean.schoolname = schoolname;
  }

  public void setShortdescription(String shortdescription)
  {
    SystemSettingsBean.shortdescription = shortdescription;
  }

  public void setSystemSettings(SystemSettings systemSettings)
  {
    this.systemSettings = systemSettings;
  }
}
