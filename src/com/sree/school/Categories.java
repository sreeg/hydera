package com.sree.school;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Categories implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = -7664291519948701235L;
  private List<PaySlip> payslips = new ArrayList<>();
  private String name;
  private double totalBasicSalary;
  private double totalGrossSalary;
  private double totalDeductions;
  private double totalNetSalary;
  private double totalConveyanceAll;
  private double totalFixedDA;

  private double totalHRA;
  private double totalLoanAmount;
  private double totalOtherDeductions;

  private double totalPFAmount;

  private double totalProfTaxDeduction;

  public Categories(String name)
  {
    this.name = name;
  }

  public double getDoubleValue(double value, int scale)
  {
    return new BigDecimal(value).setScale(scale, RoundingMode.HALF_UP).doubleValue();
  }

  public String getName()
  {
    return name;
  }

  public List<PaySlip> getPayslips()
  {
    return payslips;
  }

  public List<PaySlip> getTeams()
  {
    return payslips;
  }

  public double getTotalBasicSalary()
  {
    for (PaySlip p : payslips)
    {
      totalBasicSalary += p.getBasicsalary();
    }
    return getDoubleValue(totalBasicSalary, 2);
  }

  public double getTotalConveyanceAll()
  {
    for (PaySlip p : payslips)
    {
      totalConveyanceAll += p.getConveyanceall();
    }
    return getDoubleValue(totalConveyanceAll, 2);
  }

  public double getTotalDeductions()
  {
    for (PaySlip p : payslips)
    {
      totalDeductions += p.getTotaldeductions();
    }
    return getDoubleValue(totalDeductions, 2);
  }

  public double getTotalFixedDA()
  {
    for (PaySlip p : payslips)
    {
      totalFixedDA += p.getFixedda();
    }
    return getDoubleValue(totalFixedDA, 2);
  }

  public double getTotalGrossSalary()
  {
    for (PaySlip p : payslips)
    {
      totalGrossSalary += p.getGrosssalary();
    }
    return getDoubleValue(totalGrossSalary, 2);
  }

  public double getTotalHRA()
  {
    for (PaySlip p : payslips)
    {
      totalHRA += p.getHra();
    }
    return getDoubleValue(totalHRA, 2);
  }

  public double getTotalLoanAmount()
  {
    for (PaySlip p : payslips)
    {
      totalLoanAmount += p.getLoanamount();
    }
    return getDoubleValue(totalLoanAmount, 2);
  }

  public double getTotalNetSalary()
  {
    for (PaySlip p : payslips)
    {
      totalNetSalary += p.getNetsalary();
    }
    return getDoubleValue(totalNetSalary, 2);
  }

  public double getTotalOtherDeductions()
  {
    for (PaySlip p : payslips)
    {
      totalOtherDeductions += p.getOtherdeduction();
    }
    return getDoubleValue(totalOtherDeductions, 2);
  }

  public double getTotalPFAmount()
  {
    for (PaySlip p : payslips)
    {
      totalPFAmount += p.getPfamount();
    }
    return getDoubleValue(totalPFAmount, 2);
  }

  public double getTotalProfTaxDeduction()
  {
    for (PaySlip p : payslips)
    {
      totalProfTaxDeduction += p.getProftaxdeduction();
    }
    return getDoubleValue(totalProfTaxDeduction, 2);
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public void setPayslips(List<PaySlip> payslips)
  {
    this.payslips = payslips;
  }

  public void setTotalBasicSalary(double totalBasicSalary)
  {
    this.totalBasicSalary = totalBasicSalary;
  }

  public void setTotalConveyanceAll(double totalConveyanceAll)
  {
    this.totalConveyanceAll = totalConveyanceAll;
  }

  public void setTotalDeductions(double totalDeductions)
  {
    this.totalDeductions = totalDeductions;
  }

  public void setTotalFixedDA(double totalFixedDA)
  {
    this.totalFixedDA = totalFixedDA;
  }

  public void setTotalGrossSalary(double totalGrossSalary)
  {
    this.totalGrossSalary = totalGrossSalary;
  }

  public void setTotalHRA(double totalHRA)
  {
    this.totalHRA = totalHRA;
  }

  public void setTotalLoanAmount(double totalLoanAmount)
  {
    this.totalLoanAmount = totalLoanAmount;
  }

  public void setTotalNetSalary(double totalNetSalary)
  {
    this.totalNetSalary = totalNetSalary;
  }

  public void setTotalOtherDeductions(double totalOtherDeductions)
  {
    this.totalOtherDeductions = totalOtherDeductions;
  }

  public void setTotalPFAmount(double totalPFAmount)
  {
    this.totalPFAmount = totalPFAmount;
  }

  public void setTotalProfTaxDeduction(double totalProfTaxDeduction)
  {
    this.totalProfTaxDeduction = totalProfTaxDeduction;
  }
}