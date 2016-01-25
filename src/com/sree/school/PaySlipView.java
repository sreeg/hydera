package com.sree.school;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;

@ManagedBean(name = "payslipView")
@ViewScoped
public class PaySlipView implements Serializable
{

  private static LinkedHashMap<String, Integer> monthDaysMap;
  private static final String USED_DATE_FORMAT = "dd,MMMM,yyyy";
  private static LinkedHashMap<String, String> monthMap;
  private static LinkedHashMap<String, String> monthmapfromdb = new LinkedHashMap<String, String>();

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private static LinkedHashMap<String, String> yearMap = new LinkedHashMap<String, String>();

  static
  {
    monthMap = new LinkedHashMap<String, String>();
    monthMap.put("January", "January");
    monthMap.put("February", "February");
    monthMap.put("March", "March");
    monthMap.put("April", "April");
    monthMap.put("May", "May");
    monthMap.put("May", "June");
    monthMap.put("July", "July");
    monthMap.put("August", "August");
    monthMap.put("September", "September");
    monthMap.put("October", "October");
    monthMap.put("November", "November");
    monthMap.put("December", "December");
  }

  static
  {
    monthDaysMap = new LinkedHashMap<String, Integer>();
    monthDaysMap.put("January", 31);
    monthDaysMap.put("February", 28);
    monthDaysMap.put("March", 31);
    monthDaysMap.put("April", 30);
    monthDaysMap.put("May", 31);
    monthDaysMap.put("May", 30);
    monthDaysMap.put("July", 31);
    monthDaysMap.put("August", 31);
    monthDaysMap.put("September", 30);
    monthDaysMap.put("October", 31);
    monthDaysMap.put("November", 30);
    monthDaysMap.put("December", 31);
  }

  private static LinkedHashMap<String, Integer> monthIndexMap;

  static
  {
    monthIndexMap = new LinkedHashMap<String, Integer>();
    monthIndexMap.put("January", 1);
    monthIndexMap.put("February", 2);
    monthIndexMap.put("March", 3);
    monthIndexMap.put("April", 4);
    monthIndexMap.put("May", 5);
    monthIndexMap.put("June", 6);
    monthIndexMap.put("July", 7);
    monthIndexMap.put("August", 8);
    monthIndexMap.put("September", 9);
    monthIndexMap.put("October", 10);
    monthIndexMap.put("November", 11);
    monthIndexMap.put("December", 12);
  }

  private java.sql.Connection conn;
  SimpleDateFormat formatter = new SimpleDateFormat(USED_DATE_FORMAT);
  private String currentMonth;
  private String currentYear;
  private int daysincurrentmonth;

  private int daysinselectedmonth;
  private boolean disableGenerateButton;
  SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
  FacesMessage msg;
  private List<PaySlip> payslipDomestic;
  private List<PaySlip> payslipPermenant;
  private List<Categories> allPayslips;
  private List<Categories> pfPayslips;
  private List<Categories> nonpfPayslips;
  private List<PaySlip> payslips;
  private List<PaySlip> payslipTemporary;
  private String selectedmonth;

  private String selectedyear;
  private boolean showForm;
  private double totalBasicSalary;
  private double totalConveyanceAll;
  private double totalFixedDA;
  private double totalHRA;
  private double totalLoanAmount;
  private double totalOtherDeductions;

  private double totalPFAmount;
  private double totalProfTaxDeduction;

  private List<PaySlip> payslipWithPF;
  private List<PaySlip> payslipWithPFDomestic;
  private List<PaySlip> payslipWithoutPF;

  private List<PaySlip> payslipWithoutPFDomestic;

  private List<PaySlip> payslipWithoutPFPartTime;

  public PaySlipView()
  {
    Calendar now = Calendar.getInstance();
    setCurrentMonth(month_date.format(now.getTime()));
    setCurrentYear("" + now.get(Calendar.YEAR));
    setDaysincurrentmonth(now.getActualMaximum(Calendar.DATE));

    yearMap.put(currentYear, currentYear);
    setSelectedyear(getCurrentYear());
    setSelectedmonth(getCurrentMonth());
    setDaysinselectedmonth(monthDaysMap.get(getCurrentMonth()));
    disableGenerateButton = false;

    payslipPermenant = new ArrayList<>();
    payslipDomestic = new ArrayList<>();
    payslipTemporary = new ArrayList<>();
    allPayslips = new ArrayList<>();

    try
    {
      save();
    }
    catch (ClassNotFoundException | SQLException e)
    {
      e.printStackTrace();
    }
  }

  private boolean checkCorrectMonth()
  {
    if (Integer.parseInt(this.selectedyear) >= Integer.parseInt(this.currentYear))
    {
      if (monthIndexMap.get(selectedmonth) > monthIndexMap.get(currentMonth))
      {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot genrate salary sheets / payslips for future dates", "Select a correct year and month");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return false;
      }
    }
    return true;
  }

  public List<Categories> getAllPayslips()
  {
    return allPayslips;
  }

  public List<PaySlip> getAllSalaryStaffByMonthAndYear()
  {
    List<PaySlip> paySlips = new ArrayList<>();

    try
    {
      conn = DBConnection.getConnection();
      ResultSet rs = conn.createStatement()
          .executeQuery("select employeeid, basicsalary, fixedda, hra, conveyanceall," + "pfno, sbacno, pfrate, proftaxdeduction, otherdeduction,iseligibleforpf,"
              + "staff.firstname, staff.lastname,staff.categoryid,staff.designation,staff.DateOfJoining,staff.isarchived,"
              + "pfamount, loanamount, attendance.dayspresent, attendance.daysinmonth from salary "
              + "LEFT JOIN attendance ON salary.employeeid=attendance.staffid and attendance.year=" + "'" + getSelectedyear() + "'" + " and attendance.month = " + "'"
              + getSelectedmonth() + "'" + "LEFT JOIN staff ON salary.employeeid=staff.Id order by staff.firstname");

      totalBasicSalary = 0;
      totalFixedDA = 0;
      totalHRA = 0;
      totalConveyanceAll = 0;
      totalProfTaxDeduction = 0;
      totalOtherDeductions = 0;
      totalPFAmount = 0;
      totalLoanAmount = 0;

      payslipPermenant = new ArrayList<>();
      payslipDomestic = new ArrayList<>();
      payslipTemporary = new ArrayList<>();
      allPayslips = new ArrayList<>();

      pfPayslips = new ArrayList<>();
      nonpfPayslips = new ArrayList<>();
      payslipWithPF = new ArrayList<>();
      payslipWithoutPF = new ArrayList<>();
      payslipWithPFDomestic = new ArrayList<>();
      payslipWithoutPFDomestic = new ArrayList<>();
      payslipWithoutPFPartTime = new ArrayList<>();

      int pfpindex = 1;
      int pfdindex = 1;
      int nonpfpindex = 1;
      int nonpfdindex = 1;
      int nonpftindex = 1;
      while (rs.next())
      {
        if (rs.getBoolean("staff.isarchived"))
        {
          continue;
        }
        PaySlip ps = new PaySlip();
        ps.setEmployeeid(rs.getString("employeeid"));
        ps.setBasicsalary(rs.getDouble("basicsalary"));
        ps.setFixedda(rs.getDouble("fixedda"));
        ps.setHra(rs.getDouble("hra"));
        ps.setConveyanceall(rs.getDouble("conveyanceall"));
        ps.setPfno(rs.getString("pfno"));
        ps.setSbacno(rs.getString("sbacno"));
        ps.setPfrate(rs.getDouble("pfrate"));
        ps.setProftaxdeduction(rs.getDouble("proftaxdeduction"));
        ps.setOtherdeduction(rs.getDouble("otherdeduction"));
        ps.setEmployeename(rs.getString("staff.firstname") + " " + rs.getString("staff.lastname"));
        String categoryID = rs.getString("staff.categoryid");
        ps.setCategoryname(categoryID);
        ps.setPfamount(rs.getDouble("pfamount"));
        ps.setLoanamount(rs.getDouble("loanamount"));
        ps.setDaysinmonth(rs.getInt("daysinmonth"));
        ps.setDayspresent(rs.getDouble("dayspresent"));
        ps.setMonth(getSelectedmonth());
        ps.setYear(getSelectedyear());
        ps.setDesignation(rs.getString("designation"));
        ps.setDoj(rs.getDate("DateOfJoining"));
        ps.setIseligibleforpf(rs.getBoolean("iseligibleforpf"));

        double dayspresent = ps.getDayspresent();

        if (rs.getString("dayspresent") == null)
        {
          msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Attendance for " + ps.getEmployeename() + " for the month of " + selectedmonth + ", " + selectedyear + " is zero.",
              "Check if it is correct, otherwise fill attendance details and generate the payslips.");
          FacesContext.getCurrentInstance().addMessage(null, msg);
          // return paySlips;
        }

        Double factor = 0d;
        if (ps.getDaysinmonth() != 0)
          factor = (double) ((1.0f * dayspresent) / ps.getDaysinmonth());
        BigDecimal bd = new BigDecimal(factor);
        bd = bd.setScale(4, RoundingMode.HALF_UP);

        ps.setFactor(bd.doubleValue());
        paySlips.add(ps);

        totalBasicSalary += ps.getBasicsalary();
        totalFixedDA += ps.getFixedda();
        totalHRA += ps.getHra();
        totalConveyanceAll += ps.getConveyanceall();
        totalProfTaxDeduction += ps.getProftaxdeduction();
        totalOtherDeductions += ps.getOtherdeduction();
        totalPFAmount += ps.getPfamount();
        totalLoanAmount += ps.getLoanamount();

        if (categoryID.equals("1"))
        {
          payslipPermenant.add(ps);
          if (ps.getIseligibleforpf())
          {
            ps.setSlno(pfpindex);
            payslipWithPF.add(ps);
            pfpindex++;
          }
          else
          {
            ps.setSlno(nonpfpindex);
            payslipWithoutPF.add(ps);
            nonpfpindex++;
          }
        }
        if (categoryID.equals("2"))
        {
          payslipDomestic.add(ps);
          if (ps.getIseligibleforpf())
          {
            ps.setSlno(pfdindex);
            payslipWithPFDomestic.add(ps);
            pfdindex++;
          }
          else
          {
            ps.setSlno(nonpfdindex);
            payslipWithoutPFDomestic.add(ps);
            nonpfdindex++;
          }
        }
        if (categoryID.equals("3"))
        {
          ps.setSlno(nonpftindex);
          payslipTemporary.add(ps);
          payslipWithoutPFPartTime.add(ps);
          nonpftindex++;
        }

      }

      Categories per = new Categories("Permanent");
      per.setPayslips(payslipPermenant);
      Categories dom = new Categories("Domestic");
      dom.setPayslips(payslipDomestic);
      Categories tem = new Categories("Part Time");
      tem.setPayslips(payslipTemporary);

      allPayslips.add(per);
      allPayslips.add(dom);
      allPayslips.add(tem);

      Categories per1 = new Categories("Permanent");
      per1.setPayslips(payslipWithPF);
      Categories dom1 = new Categories("Domestic");
      dom1.setPayslips(payslipWithPFDomestic);

      pfPayslips.add(per1);
      pfPayslips.add(dom1);

      Categories per2 = new Categories("Permanent");
      per2.setPayslips(payslipWithoutPF);
      Categories dom2 = new Categories("Domestic");
      dom2.setPayslips(payslipWithoutPFDomestic);
      Categories tem2 = new Categories("Part Time");
      tem2.setPayslips(payslipWithoutPFPartTime);

      nonpfPayslips.add(per2);
      nonpfPayslips.add(dom2);
      nonpfPayslips.add(tem2);

      msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Succesfully generated payslips for " + selectedmonth + ", " + selectedyear, "Press 'print' for print outs.");
      FacesContext.getCurrentInstance().addMessage(null, msg);

    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }

    return paySlips;
  }

  public String getCurrentMonth()
  {
    return currentMonth;
  }

  public String getCurrentYear()
  {
    return currentYear;
  }

  public int getDaysincurrentmonth()
  {
    return daysincurrentmonth;
  }

  public int getDaysinselectedmonth()
  {
    return daysinselectedmonth;
  }

  public LinkedHashMap<String, String> getMonthMap()
  {
    return monthMap;
  }

  public LinkedHashMap<String, String> getMonthmapfromdb()
  {
    return monthmapfromdb;
  }

  public List<Categories> getNonpfPayslips()
  {
    return nonpfPayslips;
  }

  public List<PaySlip> getPayslipDomestic()
  {
    return payslipDomestic;
  }

  public List<PaySlip> getPayslipPermenant()
  {
    return payslipPermenant;
  }

  public List<PaySlip> getPayslips()
  {
    return payslips;
  }

  public List<PaySlip> getPayslipTemporary()
  {
    return payslipTemporary;
  }

  public List<PaySlip> getPayslipWithoutPF()
  {
    return payslipWithoutPF;
  }

  public List<PaySlip> getPayslipWithoutPFDomestic()
  {
    return payslipWithoutPFDomestic;
  }

  public List<PaySlip> getPayslipWithoutPFPartTime()
  {
    return payslipWithoutPFPartTime;
  }

  public List<PaySlip> getPayslipWithPF()
  {
    return payslipWithPF;
  }

  public List<PaySlip> getPayslipWithPFDomestic()
  {
    return payslipWithPFDomestic;
  }

  public List<Categories> getPfPayslips()
  {
    return pfPayslips;
  }

  public String getSelectedmonth()
  {
    return selectedmonth;
  }

  public String getSelectedyear()
  {
    return selectedyear;
  }

  public double getTotalBasicSalary()
  {
    return new BigDecimal(totalBasicSalary).setScale(2, RoundingMode.HALF_UP).doubleValue();
  }

  public double getTotalConveyanceAll()
  {
    return new BigDecimal(totalConveyanceAll).setScale(2, RoundingMode.HALF_UP).doubleValue();
  }

  public double getTotalFixedDA()
  {
    return new BigDecimal(totalFixedDA).setScale(2, RoundingMode.HALF_UP).doubleValue();
  }

  public double getTotalHRA()
  {
    return new BigDecimal(totalHRA).setScale(2, RoundingMode.HALF_UP).doubleValue();
  }

  public double getTotalLoanAmount()
  {
    return new BigDecimal(totalLoanAmount).setScale(2, RoundingMode.HALF_UP).doubleValue();
  }

  public double getTotalOtherDeductions()
  {
    return new BigDecimal(totalOtherDeductions).setScale(2, RoundingMode.HALF_UP).doubleValue();
  }

  public double getTotalPFAmount()
  {
    return new BigDecimal(totalPFAmount).setScale(2, RoundingMode.HALF_UP).doubleValue();
  }

  public double getTotalProfTaxDeduction()
  {
    return new BigDecimal(totalProfTaxDeduction).setScale(2, RoundingMode.HALF_UP).doubleValue();
  }

  public LinkedHashMap<String, String> getYearMap()
  {
    return yearMap;
  }

  public void insert()
  {
    FacesMessage msg = null;
    int i = 0;
    try
    {
      conn = DBConnection.getConnection();
      ResultSet rs1 = conn.createStatement().executeQuery("select * from PAYSLIP where month = " + "'" + getSelectedmonth() + "'" + " and year = " + "'" + getSelectedyear() + "'");
      rs1.setFetchSize(1);
      while (rs1.next())
      {
        i++;

      }
      if (i > 0)
      {
        conn.createStatement().executeUpdate("DELETE from PAYSLIP where month = " + "'" + getSelectedmonth() + "'" + " and year = " + "'" + getSelectedyear() + "'");
      }
    }
    catch (ClassNotFoundException e)
    {
      msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Something went wrong", "Please contant your system administrator.");
      e.printStackTrace();
    }
    catch (SQLException e)
    {
      msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Something went wrong", "Please contant your system administrator.");
      e.printStackTrace();
    }

    try
    {
      for (PaySlip p : payslips)
      {
        PreparedStatement ps = conn
            .prepareStatement("INSERT INTO PAYSLIP (employeeid, basicsalary, fixedda, hra, conveyanceall," + "pfno, sbacno, pfrate, proftaxdeduction, otherdeduction,"
                + "pfamount, loanamount, month, year, dayspresent, daysinmonth, iseligibleforpf, monthyeardate, createdatetime, updatedatetime)"
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, now(), now())");

        ps.setString(1, p.getEmployeeid());
        ps.setDouble(2, p.getBasicsalary());
        ps.setDouble(3, p.getFixedda());
        ps.setDouble(4, p.getHra());
        ps.setDouble(5, p.getConveyanceall());
        ps.setString(6, p.getPfno());
        ps.setString(7, p.getSbacno());
        ps.setDouble(8, p.getPfrate());
        ps.setDouble(9, p.getProftaxdeduction());
        ps.setDouble(10, p.getOtherdeduction());
        ps.setDouble(11, p.getPfamount());
        ps.setDouble(12, p.getLoanamount());
        ps.setString(13, getSelectedmonth());
        ps.setString(14, getSelectedyear());
        ps.setDouble(15, p.getDayspresent());
        ps.setInt(16, p.getDaysinmonth());
        ps.setBoolean(17, p.getIseligibleforpf());
        Calendar instance = Calendar.getInstance();
        instance.setTime(formatter.parse("01," + getSelectedmonth() + "," + getSelectedyear()));
        ps.setDate(18, new java.sql.Date(instance.getTime().getTime()));

        int rs = ps.executeUpdate();

        if (rs == 1)
        {
          msg = new FacesMessage("Payslips generated successfully", "");
          FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        else
        {
          msg = new FacesMessage("Something went wrong", "Please contant your system administrator.");
          FacesContext.getCurrentInstance().addMessage(null, msg);
        }
      }
    }
    catch (SQLException e)
    {
      msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Something went wrong", "Please contant your system administrator.");
      e.printStackTrace();
      FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    catch (ParseException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  public boolean isDisableGenerateButton()
  {
    return disableGenerateButton;
  }

  public boolean isShowForm()
  {
    return showForm;
  }

  public void onMonthChange() throws ClassNotFoundException, SQLException
  {
    if (selectedmonth != null && !selectedmonth.equals(""))
    {
      setDaysinselectedmonth(monthDaysMap.get(selectedmonth));
      disableGenerateButton = false;
    }
    else
    {
      disableGenerateButton = true;
      setShowForm(false);
    }
  }

  public void onYearChange()
  {
  }

  public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException
  {
    Document pdf = (Document) document;
    pdf.setMargins(-70, -70, 10, 10);
    pdf.setPageSize(PageSize.A4.rotate());

    pdf.setMarginMirroring(true);
    pdf.open();

    ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    String logo = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "images" + File.separator + "logo.gif";

    Image instance = Image.getInstance(logo);
    instance.setAlignment(Element.ALIGN_CENTER);
    pdf.add(instance);
    pdf.add(new Phrase("\n"));

    Paragraph paragraph = new Paragraph("Salary Proposals for the month of " + currentMonth + ", " + currentYear);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    paragraph.setFont(FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, Font.BOLD));
    pdf.add(paragraph);
    pdf.add(new Phrase("\n"));
  }

  public void save() throws ClassNotFoundException, SQLException
  {
    if (!checkCorrectMonth())
    {
      return;
    }

    payslips = getAllSalaryStaffByMonthAndYear();
    insert();
    setShowForm(true);
  }

  public void setAllPayslips(List<Categories> allPayslips)
  {
    this.allPayslips = allPayslips;
  }

  public void setCurrentMonth(String currentMonth)
  {
    this.currentMonth = currentMonth;
  }

  public void setCurrentYear(String currentYear)
  {
    this.currentYear = currentYear;
  }

  public void setDaysincurrentmonth(int daysincurrentmonth)
  {
    this.daysincurrentmonth = daysincurrentmonth;
  }

  public void setDaysinselectedmonth(int daysinselectedmonth)
  {
    this.daysinselectedmonth = daysinselectedmonth;
  }

  public void setDisableGenerateButton(boolean disableGenerateButton)
  {
    this.disableGenerateButton = disableGenerateButton;
  }

  public void setMonthMap(LinkedHashMap<String, String> monthMap)
  {
    PaySlipView.monthMap = monthMap;
  }

  public void setMonthmapfromdb(LinkedHashMap<String, String> monthmapfromdb)
  {
    PaySlipView.monthmapfromdb = monthmapfromdb;
  }

  public void setNonpfPayslips(List<Categories> nonpfPayslips)
  {
    this.nonpfPayslips = nonpfPayslips;
  }

  public void setPayslipDomestic(List<PaySlip> payslipDomestic)
  {
    this.payslipDomestic = payslipDomestic;
  }

  public void setPayslipPermenant(List<PaySlip> payslipPermenant)
  {
    this.payslipPermenant = payslipPermenant;
  }

  public void setPayslips(List<PaySlip> payslips)
  {
    this.payslips = payslips;
  }

  public void setPayslipTemporary(List<PaySlip> payslipTemporary)
  {
    this.payslipTemporary = payslipTemporary;
  }

  public void setPayslipWithoutPF(List<PaySlip> payslipWithoutPF)
  {
    this.payslipWithoutPF = payslipWithoutPF;
  }

  public void setPayslipWithoutPFDomestic(List<PaySlip> payslipWithoutPFDomestic)
  {
    this.payslipWithoutPFDomestic = payslipWithoutPFDomestic;
  }

  public void setPayslipWithoutPFPartTime(List<PaySlip> payslipWithoutPFPartTime)
  {
    this.payslipWithoutPFPartTime = payslipWithoutPFPartTime;
  }

  public void setPayslipWithPF(List<PaySlip> payslipWithPF)
  {
    this.payslipWithPF = payslipWithPF;
  }

  public void setPayslipWithPFDomestic(List<PaySlip> payslipWithPFDomestic)
  {
    this.payslipWithPFDomestic = payslipWithPFDomestic;
  }

  public void setPfPayslips(List<Categories> pfPayslips)
  {
    this.pfPayslips = pfPayslips;
  }

  public void setSelectedmonth(String selectedmonth)
  {
    this.selectedmonth = selectedmonth;
  }

  public void setSelectedyear(String selectedyear)
  {
    this.selectedyear = selectedyear;
  }

  public void setShowForm(boolean showForm)
  {
    this.showForm = showForm;
  }

  public void setTotalBasicSalary(double totalBasicSalary)
  {
    this.totalBasicSalary = totalBasicSalary;
  }

  public void setTotalConveyanceAll(double totalConveyanceAll)
  {
    this.totalConveyanceAll = totalConveyanceAll;
  }

  public void setTotalFixedDA(double totalFixedDA)
  {
    this.totalFixedDA = totalFixedDA;
  }

  public void setTotalHRA(double totalHRA)
  {
    this.totalHRA = totalHRA;
  }

  public void setTotalLoanAmount(double totalLoanAmount)
  {
    this.totalLoanAmount = totalLoanAmount;
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

  public void setYearMap(LinkedHashMap<String, String> yearMap)
  {
    PaySlipView.yearMap = yearMap;
  }

}