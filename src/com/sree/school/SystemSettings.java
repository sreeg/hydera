package com.sree.school;

import java.io.Serializable;
import java.util.List;

public class SystemSettings implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String password;
  private String email;
  private boolean showwidget1;
  private boolean showwidget2;
  private String emailhostname;
  private String smtpport;
  private boolean disableemail;
  private List<String> designationsList;
  private List<String> designationsListStaff;
  private List<String> categoryListStaff;
  private List<String> banknamesList;
  private List<FeeDetails> feedetails;

  public List<String> getBanknamesList()
  {
    return banknamesList;
  }

  public List<String> getCategoryListStaff()
  {
    return categoryListStaff;
  }

  public List<String> getDesignationsList()
  {
    return designationsList;
  }

  public List<String> getDesignationsListStaff()
  {
    return designationsListStaff;
  }

  public boolean getDisableemail()
  {
    return disableemail;
  }

  public String getEmail()
  {
    return email;
  }

  public String getEmailhostname()
  {
    return emailhostname;
  }

  public List<FeeDetails> getFeedetails()
  {
    return feedetails;
  }

  public String getPassword()
  {
    return password;
  }

  public String getSmtpport()
  {
    return smtpport;
  }

  public boolean isShowwidget1()
  {
    return showwidget1;
  }

  public boolean isShowwidget2()
  {
    return showwidget2;
  }

  public void setBanknamesList(List<String> banknamesList)
  {
    this.banknamesList = banknamesList;
  }

  public void setCategoryListStaff(List<String> categoryListStaff)
  {
    this.categoryListStaff = categoryListStaff;
  }

  public void setDesignationsList(List<String> designationsList)
  {
    this.designationsList = designationsList;
  }

  public void setDesignationsListStaff(List<String> designationsListStaff)
  {
    this.designationsListStaff = designationsListStaff;
  }

  public void setDisableemail(boolean disableemail)
  {
    this.disableemail = disableemail;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public void setEmailhostname(String emailhostname)
  {
    this.emailhostname = emailhostname;
  }

  public void setFeedetails(List<FeeDetails> feedetails)
  {
    this.feedetails = feedetails;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public void setShowwidget1(boolean showwidget1)
  {
    this.showwidget1 = showwidget1;
  }

  public void setShowwidget2(boolean showwidget2)
  {
    this.showwidget2 = showwidget2;
  }

  public void setSmtpport(String smtpport)
  {
    this.smtpport = smtpport;
  }

}
