package com.sree.school;

public class Attendance extends Staff
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private double dayspresent;
  private int daysinmonth;
  private String month;
  private String year;

  public int getDaysinmonth()
  {
    return daysinmonth;
  }

  public double getDayspresent()
  {
    return dayspresent;
  }

  public String getMonth()
  {
    return month;
  }

  public String getYear()
  {
    return year;
  }

  public void setDaysinmonth(int daysinmonth)
  {
    this.daysinmonth = daysinmonth;
  }

  public void setDayspresent(double dayspresent)
  {
    this.dayspresent = dayspresent;
  }

  public void setMonth(String month)
  {
    this.month = month;
  }

  public void setYear(String year)
  {
    this.year = year;
  }

}
