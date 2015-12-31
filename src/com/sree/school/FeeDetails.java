package com.sree.school;

import java.io.Serializable;

public class FeeDetails implements Serializable
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private String classname;
  private double term1amount;
  private double term2amount;
  private double term3amount;
  private double totalamount;
  private double termamount;
  private int noofterms;

  public String getClassname()
  {
    return classname;
  }

  public int getNoofterms()
  {
    return noofterms;
  }

  public double getTerm1amount()
  {
    return term1amount;
  }

  public double getTerm2amount()
  {
    return term2amount;
  }

  public double getTerm3amount()
  {
    return term3amount;
  }

  public double getTermamount()
  {
    return termamount;
  }

  public double getTotalamount()
  {
    totalamount = noofterms * termamount;
    return totalamount;
  }

  public void setClassname(String classname)
  {
    this.classname = classname;
  }

  public void setNoofterms(int noofterms)
  {
    this.noofterms = noofterms;
  }

  public void setTerm1amount(double term1amount)
  {
    this.term1amount = term1amount;
  }

  public void setTerm2amount(double term2amount)
  {
    this.term2amount = term2amount;
  }

  public void setTerm3amount(double term3amount)
  {
    this.term3amount = term3amount;
  }

  public void setTermamount(double termamount)
  {
    this.termamount = termamount;
  }

  public void setTotalamount(double totalamount)
  {
    this.totalamount = totalamount;
  }
}
