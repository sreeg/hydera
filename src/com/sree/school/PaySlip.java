package com.sree.school;

import java.io.Serializable;
import java.util.Date;

public class PaySlip extends Salary implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = -8645764245616466123L;
  private String month;
  private String Year;
  private String designation;
  private Date doj;
  private int slno;

  private double dayspresent;
  private int daysinmonth;
  private double factor = 1.0f;

  public int getDaysinmonth()
  {
    return daysinmonth;
  }

  public double getDayspresent()
  {
    return dayspresent;
  }

  public String getDesignation()
  {
    return designation;
  }

  public Date getDoj()
  {
    return doj;
  }

  @Override
  public double getFactor()
  {
    return factor;
  }

  public String getMonth()
  {
    return month;
  }

  public int getSlno()
  {
    return slno;
  }

  public String getYear()
  {
    return Year;
  }

  public void setDaysinmonth(int daysinmonth)
  {
    this.daysinmonth = daysinmonth;
  }

  public void setDayspresent(double dayspresent)
  {
    this.dayspresent = dayspresent;
  }

  public void setDesignation(String designation)
  {
    this.designation = designation;
  }

  public void setDoj(Date doj)
  {
    this.doj = doj;
  }

  @Override
  public void setFactor(double factor)
  {
    this.factor = factor;
  }

  public void setMonth(String month)
  {
    this.month = month;
  }

  public void setSlno(int slno)
  {
    this.slno = slno;
  }

  public void setYear(String year)
  {
    Year = year;
  }
}
