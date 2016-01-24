package com.sree.school;

import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;

@ManagedBean(name = "salaryView")
@ViewScoped
public class SalaryView implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private List<Salary> salaries;

  private double totalBasicSalary;
  private double totalFixedDA;
  private double totalHRA;
  private double totalConveyanceAll;
  private double totalGross;
  private double totalProfTaxDeduction;
  private double totalOtherDeductions;
  private double totalPFAmount;
  private double totalLoanAmount;
  private double totalDeductions;
  private double totalNetSalary;

  public SalaryView()
  {
    salaries = getAllSalaryStaff();
  }

  public List<Salary> getAllSalaryStaff()
  {
    java.sql.Connection conn;
    List<Salary> salaries = new ArrayList<>();

    try
    {
      conn = DBConnection.getConnection();
      ResultSet rs = conn.createStatement()
          .executeQuery("select employeeid, basicsalary, fixedda, hra, conveyanceall,"
              + "pfno, sbacno, pfrate, proftaxdeduction, otherdeduction, modeofpayment, iseligibleforpf, staff.firstname, staff.lastname,staff.categoryid,staff.isarchived,"
              + "pfamount, loanamount from salary INNER JOIN staff ON salary.employeeid=staff.Id");
      totalBasicSalary = 0;
      while (rs.next())
      {
        if (rs.getBoolean("staff.isarchived"))
        {
          continue;
        }
        Salary st = new Salary();
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
        st.setEmployeename(rs.getString("staff.firstname") + " " + rs.getString("staff.lastname"));
        String categoryId = rs.getString("staff.categoryid");
        st.setCategoryname(categoryId);
        st.setPfamount(rs.getDouble("pfamount"));
        st.setLoanamount(rs.getDouble("loanamount"));
        st.setModeofpayment(rs.getString("modeofpayment"));
        st.setIseligibleforpf(rs.getBoolean("iseligibleforpf"));
        if ("3".equals(categoryId))
        {
          st.setPfamount(0d);
        }
        salaries.add(st);
        totalBasicSalary += st.getBasicsalary();
        totalFixedDA += st.getFixedda();
        totalHRA += st.getHra();
        totalConveyanceAll += st.getConveyanceall();
        totalGross = totalGross + totalBasicSalary + totalFixedDA + totalHRA + totalConveyanceAll;
        totalProfTaxDeduction += st.getProftaxdeduction();
        totalOtherDeductions += st.getOtherdeduction();
        totalPFAmount += st.getPfamount();
        totalLoanAmount += st.getLoanamount();
        totalDeductions = totalDeductions + totalProfTaxDeduction + totalOtherDeductions + totalPFAmount + totalLoanAmount;
        totalNetSalary = totalGross - totalDeductions;
      }
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }

    return salaries;
  }

  public List<Salary> getSalaries()
  {
    return salaries;
  }

  public double getTotalBasicSalary()
  {
    return totalBasicSalary;
  }

  public double getTotalConveyanceAll()
  {
    return totalConveyanceAll;
  }

  public double getTotalDeductions()
  {
    return totalDeductions;
  }

  public double getTotalFixedDA()
  {
    return totalFixedDA;
  }

  public double getTotalGross()
  {
    return totalGross;
  }

  public double getTotalHRA()
  {
    return totalHRA;
  }

  public double getTotalLoanAmount()
  {
    return totalLoanAmount;
  }

  public double getTotalNetSalary()
  {
    return totalNetSalary;
  }

  public double getTotalOtherDeductions()
  {
    return totalOtherDeductions;
  }

  public double getTotalPFAmount()
  {
    return totalPFAmount;
  }

  public double getTotalProfTaxDeduction()
  {
    return totalProfTaxDeduction;
  }

  public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException
  {
    Document pdf = (Document) document;

    pdf.setMargins(-70, -70, 10, 10);
    pdf.setPageSize(PageSize.A4.rotate());

    pdf.setMarginMirroring(true);
    pdf.open();

    // ServletContext servletContext = (ServletContext)
    // FacesContext.getCurrentInstance().getExternalContext().getContext();
    // String logo = servletContext.getRealPath("") + File.separator +
    // "resources" + File.separator + "images" + File.separator + "logo.gif";
    //
    // Image instance = Image.getInstance(logo);
    // instance.setAlignment(Element.ALIGN_CENTER);
    // pdf.add(instance);
    // pdf.add(new Phrase("\n"));
  }

  public void setSalaries(List<Salary> salaries)
  {
    this.salaries = salaries;
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

  public void setTotalGross(double totalGross)
  {
    this.totalGross = totalGross;
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