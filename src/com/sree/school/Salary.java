package com.sree.school;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;

public class Salary implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 3784360127530452986L;
  private static LinkedHashMap<String, String> categoryInverse;

  static
  {
    categoryInverse = new LinkedHashMap<String, String>();
    categoryInverse.put("1", "Permanent");
    categoryInverse.put("2", "Domestic");
    categoryInverse.put("3", "Part Time");
  }

  private String employeeid;
  private Double basicsalary;
  private Double fixedda;
  private Double hra;
  private Double conveyanceall;
  private String pfno;
  private String sbacno;
  private Double pfrate = 0.12d;
  private Double proftaxdeduction;
  private Double otherdeduction;
  private Double pfamount;
  private Double loanamount;
  private String employeename;
  private String categoryname;
  private Double grosssalary;
  private Double totaldeductions;
  private Double netsalary;
  private double factor = 1.0f;
  private String modeofpayment;
  private Boolean iseligibleforpf;

  private Date monthyearDate;

  public Double getBasicsalary()
  {
    return basicsalary == null ? null : getFactor() * basicsalary;
  }

  public String getCategoryname()
  {
    return categoryname;
  }

  public Double getConveyanceall()
  {
    return conveyanceall == null ? null : getFactor() * conveyanceall;
  }

  public String getEmployeeid()
  {
    return employeeid;
  }

  public String getEmployeename()
  {
    return employeename;
  }

  public double getFactor()
  {
    return factor;
  }

  public Double getFixedda()
  {
    return fixedda == null ? null : getFactor() * fixedda;
  }

  public Double getGrosssalary()
  {
    grosssalary = getBasicsalary() + getFixedda() + getHra() + getConveyanceall();
    return grosssalary;
  }

  public Double getHra()
  {
    return hra == null ? null : getFactor() * hra;
  }

  public Boolean getIseligibleforpf()
  {
    if (iseligibleforpf == null || "Part Time".equalsIgnoreCase(categoryname))
      return false;
    else
      return iseligibleforpf;
  }

  public Double getLoanamount()
  {
    if (loanamount == null)
      return 0d;
    return loanamount;
  }

  public String getModeofpayment()
  {
    return modeofpayment;
  }

  public Date getMonthyearDate()
  {
    return monthyearDate;
  }

  public Double getNetsalary()
  {
    netsalary = getGrosssalary() - getTotaldeductions();
    Long l = Math.round(netsalary);
    Integer.valueOf(l.intValue());
    return new Double(Integer.valueOf(l.intValue()));
  }

  public Double getOtherdeduction()
  {
    return otherdeduction;
  }

  public Double getPfamount()
  {
    if (iseligibleforpf != null && !iseligibleforpf)
    {
      return 0d;
    }
    else
    {
      return ((getBasicsalary() + getFixedda()) * getPfrate());
    }
  }

  public String getPfno()
  {
    return pfno;
  }

  public Double getPfrate()
  {
    return 0.12d;
  }

  public Double getProftaxdeduction()
  {
    return proftaxdeduction;
  }

  public String getSbacno()
  {
    return sbacno;
  }

  public Double getTotaldeductions()
  {
    totaldeductions = getPfamount() + getOtherdeduction() + getLoanamount() + getProftaxdeduction();
    return totaldeductions;
  }

  public void setBasicsalary(Double basicsalary)
  {
    this.basicsalary = basicsalary;
  }

  public void setCategoryname(String categoryname)
  {
    this.categoryname = categoryInverse.get(categoryname);
  }

  public void setConveyanceall(Double conveyanceall)
  {
    this.conveyanceall = conveyanceall;
  }

  public void setEmployeeid(String employeeid)
  {
    this.employeeid = employeeid;
  }

  public void setEmployeename(String employeename)
  {
    this.employeename = employeename;
  }

  public void setFactor(double factor)
  {
    this.factor = factor;
  }

  public void setFixedda(Double fixedda)
  {
    this.fixedda = fixedda;
  }

  public void setGrosssalary(Double grosssalary)
  {
    this.grosssalary = grosssalary;
  }

  public void setHra(Double hra)
  {
    this.hra = hra;
  }

  public void setIseligibleforpf(Boolean iseligibleforpf)
  {
    this.iseligibleforpf = iseligibleforpf;
  }

  public void setLoanamount(Double loanamount)
  {
    this.loanamount = loanamount;
  }

  public void setModeofpayment(String modeofpayment)
  {
    this.modeofpayment = modeofpayment;
  }

  public void setMonthyearDate(Date monthyearDate)
  {
    this.monthyearDate = monthyearDate;
  }

  public void setNetsalary(Double netsalary)
  {
    this.netsalary = netsalary;
  }

  public void setOtherdeduction(Double otherdeduction)
  {
    this.otherdeduction = otherdeduction;
  }

  public void setPfamount(Double pfamount)
  {
    this.pfamount = pfamount;
  }

  public void setPfno(String pfno)
  {
    this.pfno = pfno;
  }

  public void setPfrate(Double pfrate)
  {
    this.pfrate = pfrate;
  }

  public void setProftaxdeduction(Double proftaxdeduction)
  {
    this.proftaxdeduction = proftaxdeduction;
  }

  public void setSbacno(String sbacno)
  {
    this.sbacno = sbacno;
  }

  public void setTotaldeductions(Double totaldeductions)
  {
    this.totaldeductions = totaldeductions;
  }
}
