package com.sree.school;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.PieChartModel;

@ManagedBean(name = "chartView")
@RequestScoped
public class ChartView implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 3768405787440247712L;
  private static final String USED_DATE_FORMAT = "MMMM,yyyy";
  private BarChartModel barModel;
  private BarChartModel cashModel;
  private Connection conn;

  private BarChartModel cumBarModel;
  private Double cumilativeDom;
  private Double cumilativePer;
  private Double cumilativeTem;
  private Map<Date, Double> domestic;

  SimpleDateFormat formatter = new SimpleDateFormat(USED_DATE_FORMAT);
  private HorizontalBarChartModel horizontalBarModel;
  private HorizontalBarChartModel parentOccupationModel;
  private HorizontalBarChartModel motherOccupationModel;
  private Map<Date, Double> parttime;
  private Map<Date, Double> permanant;
  private PieChartModel pieModel1;
  private Map<Date, Double> total;
  private Double totalFeeCollected;
  private Double totalSalarySpent;
  private int totalNumberOfPerm;
  private int totalNumberOfDom;
  private int totalNumberOfTemp;

  private Set<String> staffsDomestic;

  private Set<String> staffsPermenant;

  private Set<String> staffsTemporary;

  Map<String, Integer> occupation;
  Map<String, Integer> occupationMother;

  public ChartView()
  {
    permanant = new TreeMap<>();
    domestic = new TreeMap<>();
    parttime = new TreeMap<>();
    total = new TreeMap<>();
    cumilativePer = 0d;
    cumilativeDom = 0d;
    cumilativeTem = 0d;
    totalFeeCollected = 0d;
    totalSalarySpent = 0d;
    totalNumberOfPerm = 0;
    totalNumberOfDom = 0;
    totalNumberOfTemp = 0;
    staffsPermenant = new HashSet<>();
    staffsDomestic = new HashSet<>();
    staffsTemporary = new HashSet<>();
    getAllPayslips();
    getFeePaymentDetails();
    getStaffCount();
    getParentOccupation();
    getMotherOccupation();
  }

  private void createBarModel()
  {
    barModel = initBarModel(false);
    barModel.setShowPointLabels(true);
    barModel.setDatatipFormat("&#8377; %2$s");
    barModel.setLegendCols(2);
    barModel.setLegendPosition("nw");

    Axis xAxis = barModel.getAxis(AxisType.X);
    xAxis.setLabel("Month");

    Axis yAxis = barModel.getAxis(AxisType.Y);
    yAxis.setLabel("Total Net Salary Paid");
    yAxis.setTickFormat("%'d");
    // yAxis.setMin(0);
    // yAxis.setMax(100);
  }

  private void createCashModel()
  {
    cashModel = new BarChartModel();

    ChartSeries cash = new ChartSeries();
    cash.setLabel("Cash in-flow");
    cash.set(" ", totalFeeCollected);

    ChartSeries cashOut = new ChartSeries();
    cashOut.setLabel("Cash out-flow ");
    cashOut.set(" ", totalSalarySpent);

    ChartSeries diff = new ChartSeries();
    diff.setLabel("Difference");
    diff.set(" ", (totalFeeCollected - totalSalarySpent));

    cashModel.addSeries(cash);
    cashModel.addSeries(cashOut);
    cashModel.addSeries(diff);

    cashModel.setShowPointLabels(true);
    cashModel.setSeriesColors("005BAD,F74A4A,33C7AB,5CB85C,A63F82,A30303");
    cashModel.setDatatipFormat("&#8377; %2$s");

    cashModel.setLegendCols(3);
    cashModel.setLegendPosition("ne");

    Axis xAxis = cashModel.getAxis(AxisType.X);
    xAxis.setLabel("Cash flows");
    Axis yAxis = cashModel.getAxis(AxisType.Y);
    yAxis.setLabel("Amount");
    yAxis.setTickFormat("%'d");

    cashModel.setAnimate(true);

  }

  private void createCumBarModel()
  {
    cumBarModel = initBarModel(true);
    cumBarModel.setShowPointLabels(true);
    cumBarModel.setDatatipFormat("&#8377; %2$s");
    cumBarModel.setLegendCols(3);
    cumBarModel.setLegendPosition("nw");

    Axis xAxis = cumBarModel.getAxis(AxisType.X);
    xAxis.setLabel("Month");

    Axis yAxis = cumBarModel.getAxis(AxisType.Y);
    yAxis.setLabel("Total Net Salary Paid");
    yAxis.setTickFormat("%'d");
    // yAxis.setMin(0);
    // yAxis.setMax(100);
    cumBarModel.setStacked(true);
    cumBarModel.setAnimate(true);
  }

  private void createMotherOccupationModel()
  {
    motherOccupationModel = new HorizontalBarChartModel();
    // motherOccupationModel.setExtender("chartExtender");
    ChartSeries s = new ChartSeries();

    for (Entry<String, Integer> entry : occupationMother.entrySet())
    {
      String key = entry.getKey();
      Integer value = entry.getValue();
      s.set(key, value.intValue());
    }

    motherOccupationModel.addSeries(s);
    motherOccupationModel.setShowPointLabels(true);
    motherOccupationModel.setSeriesColors("337AB7,5CB85C,4C3973,A63F82");
    motherOccupationModel.setShowPointLabels(true);
    motherOccupationModel.setAnimate(true);

    Axis xAxis = motherOccupationModel.getAxis(AxisType.X);
    xAxis.setLabel("No. of Students");

    xAxis.setTickCount(1);
    xAxis.setTickInterval("1");
    xAxis.setTickFormat("%d");

    Axis yAxis = motherOccupationModel.getAxis(AxisType.Y);
    yAxis.setLabel("Occupation");
    yAxis.setTickFormat("%s");
  }

  private void createParentOccupationModel()
  {
    parentOccupationModel = new HorizontalBarChartModel();
    // parentOccupationModel.setExtender("chartExtender");
    ChartSeries s = new ChartSeries();

    for (Entry<String, Integer> entry : occupation.entrySet())
    {
      String key = entry.getKey();
      Integer value = entry.getValue();
      s.set(key, value.intValue());
    }

    parentOccupationModel.addSeries(s);
    parentOccupationModel.setShowPointLabels(true);
    parentOccupationModel.setSeriesColors("337AB7,5CB85C,4C3973,A63F82");
    parentOccupationModel.setShowPointLabels(true);
    parentOccupationModel.setAnimate(true);

    Axis xAxis = parentOccupationModel.getAxis(AxisType.X);
    xAxis.setLabel("No. of Students");

    xAxis.setTickCount(1);
    xAxis.setTickInterval("1");
    xAxis.setTickFormat("%d");

    Axis yAxis = parentOccupationModel.getAxis(AxisType.Y);
    yAxis.setLabel("Occupation");
    yAxis.setTickFormat("%s");
  }

  private void createPieModel1()
  {
    pieModel1 = new PieChartModel();
    pieModel1.set("Permanant", cumilativePer);
    pieModel1.set("Domestic", cumilativeDom);
    pieModel1.set("Part Time", cumilativeTem);

    pieModel1.setLegendPosition("w");
    pieModel1.setSliceMargin(5);
    pieModel1.setShowDataLabels(true);
    pieModel1.setSeriesColors("337AB7,5CB85C,4C3973,A63F82,A30303,F74A4A");
  }

  private void createStaffCompositionModel()
  {
    horizontalBarModel = new HorizontalBarChartModel();

    ChartSeries staff = new ChartSeries();
    staff.set(" ", totalNumberOfPerm);
    staff.setLabel("Permanent");

    ChartSeries staffD = new ChartSeries();
    staffD.set(" ", totalNumberOfDom);
    staffD.setLabel("Domestic");

    ChartSeries staffT = new ChartSeries();
    staffT.set(" ", totalNumberOfTemp);
    staffT.setLabel("Part Time");

    horizontalBarModel.addSeries(staff);
    horizontalBarModel.addSeries(staffD);
    horizontalBarModel.addSeries(staffT);

    horizontalBarModel.setShowPointLabels(true);
    horizontalBarModel.setSeriesColors("5CB85C, F74A4A,337AB7,5CB85C,4C3973,A63F82");

    horizontalBarModel.setLegendCols(1);
    horizontalBarModel.setLegendPosition("ne");
    horizontalBarModel.setDatatipFormat("%1$s");
    horizontalBarModel.setAnimate(true);

    Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
    xAxis.setLabel("No. of Staff");
    xAxis.setTickFormat("%d");

    Axis yAxis = horizontalBarModel.getAxis(AxisType.Y);
    yAxis.setLabel("Staff Type");

  }

  public List<PaySlip> getAllPayslips()
  {
    List<PaySlip> paySlips = new ArrayList<>();

    try
    {
      conn = DBConnection.getConnection();
      ResultSet rs = conn.createStatement()
          .executeQuery("select employeeid, basicsalary, fixedda, hra, conveyanceall," + "pfno, sbacno, pfrate, proftaxdeduction, otherdeduction, CategoryId,"
              + "pfamount, loanamount, month, year, iseligibleforpf,monthyeardate from payslip LEFT OUTER JOIN staff ON payslip.employeeid=staff.Id order by monthyeardate");

      while (rs.next())
      {
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
        ps.setPfamount(rs.getDouble("pfamount"));
        ps.setLoanamount(rs.getDouble("loanamount"));
        ps.setMonth(rs.getString("month"));
        ps.setYear(rs.getString("year"));
        ps.setIseligibleforpf(rs.getBoolean("iseligibleforpf"));
        ps.setMonthyearDate(rs.getDate("monthyeardate"));

        String cat = rs.getString("CategoryId");
        paySlips.add(ps);
        Date monthAndYear = ps.getMonthyearDate();// ps.getMonth() + ",
                                                  // " + ps.getYear();
        // months.add(monthAndYear);

        if (cat.equals("1") && permanant.get(monthAndYear) == null)
        {
          permanant.put(monthAndYear, ps.getNetsalary());
          totalNumberOfPerm++;
          staffsPermenant.add(ps.getEmployeeid());
        }
        else if (cat.equals("1"))
        {
          Double netsalary = permanant.get(monthAndYear);
          netsalary += ps.getNetsalary();
          permanant.put(monthAndYear, netsalary);
          totalNumberOfPerm++;
          staffsPermenant.add(ps.getEmployeeid());
        }

        if (cat.equals("2") && domestic.get(monthAndYear) == null)
        {
          domestic.put(monthAndYear, ps.getNetsalary());
          totalNumberOfDom++;
          staffsDomestic.add(ps.getEmployeeid());
        }
        else if (cat.equals("2"))
        {
          Double netsalary = domestic.get(monthAndYear);
          netsalary += ps.getNetsalary();
          domestic.put(monthAndYear, netsalary);
          totalNumberOfDom++;
          staffsDomestic.add(ps.getEmployeeid());
        }

        if (cat.equals("3") && parttime.get(monthAndYear) == null)
        {
          parttime.put(monthAndYear, ps.getNetsalary());
          totalNumberOfTemp++;
          staffsTemporary.add(ps.getEmployeeid());
        }
        else if (cat.equals("3"))
        {
          Double netsalary = parttime.get(monthAndYear);
          netsalary += ps.getNetsalary();
          parttime.put(monthAndYear, netsalary);
          totalNumberOfTemp++;
          staffsTemporary.add(ps.getEmployeeid());
        }

        if (total.get(monthAndYear) == null)
        {
          total.put(monthAndYear, ps.getNetsalary());
          totalSalarySpent += ps.getNetsalary();
        }
        else
        {
          Double netsalary = total.get(monthAndYear);
          netsalary += ps.getNetsalary();
          total.put(monthAndYear, netsalary);
          totalSalarySpent += ps.getNetsalary();
        }

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

    return paySlips;
  }

  public BarChartModel getBarModel()
  {
    return barModel;
  }

  public BarChartModel getCashModel()
  {
    return cashModel;
  }

  public BarChartModel getCumBarModel()
  {
    return cumBarModel;
  }

  private void getFeePaymentDetails()
  {
    try
    {
      conn = DBConnection.getConnection();
      ResultSet rs = conn.createStatement().executeQuery("select studentid, amountpaid from feepayment");

      while (rs.next())
      {
        totalFeeCollected += rs.getDouble("amountpaid");

      }
    }
    catch (ClassNotFoundException | SQLException e)
    {
      e.printStackTrace();
    }
  }

  public HorizontalBarChartModel getHorizontalBarModel()
  {
    return horizontalBarModel;
  }

  private void getMotherOccupation()
  {
    occupationMother = new HashMap<>();
    try
    {
      conn = DBConnection.getConnection();
      ResultSet rs = conn.createStatement().executeQuery("SELECT motheroccupation, count(*) as 'Cnt' from school.student GROUP BY motheroccupation");

      while (rs.next())
      {
        String occ = rs.getString("motheroccupation");
        if (occ == null || "".equals(occ))
        {
          occ = "None";
        }
        occupationMother.put(occ, Integer.valueOf(rs.getInt("Cnt")));
      }
    }
    catch (ClassNotFoundException | SQLException e)
    {
      e.printStackTrace();
    }
  }

  public HorizontalBarChartModel getMotherOccupationModel()
  {
    return motherOccupationModel;
  }

  private void getParentOccupation()
  {
    occupation = new HashMap<>();
    try
    {
      conn = DBConnection.getConnection();
      ResultSet rs = conn.createStatement().executeQuery("SELECT fatheroccupation, count(*) as 'Cnt' from school.student GROUP BY fatheroccupation");

      while (rs.next())
      {
        String occ = rs.getString("fatheroccupation");
        if (occ == null || "".equals(occ))
        {
          occ = "None";
        }
        occupation.put(occ, Integer.valueOf(rs.getInt("Cnt")));
      }
    }
    catch (ClassNotFoundException | SQLException e)
    {
      e.printStackTrace();
    }
  }

  public HorizontalBarChartModel getParentOccupationModel()
  {
    return parentOccupationModel;
  }

  public PieChartModel getPieModel1()
  {
    return pieModel1;
  }

  private void getStaffCount()
  {

    try
    {
      conn = DBConnection.getConnection();
      ResultSet rs = conn.createStatement().executeQuery("SELECT CategoryId, count(*) as 'Cnt' FROM school.staff GROUP BY CategoryId");

      while (rs.next())
      {
        if ("1".equals(rs.getString("CategoryId")))
        {
          totalNumberOfPerm = Integer.parseInt(rs.getString("Cnt"));
        }
        else if ("2".equals(rs.getString("CategoryId")))
        {
          totalNumberOfDom = Integer.parseInt(rs.getString("Cnt"));
        }
        else if ("3".equals(rs.getString("CategoryId")))
        {
          totalNumberOfTemp = Integer.parseInt(rs.getString("Cnt"));
        }

      }
    }
    catch (ClassNotFoundException | SQLException e)
    {
      e.printStackTrace();
    }
  }

  public Set<String> getStaffsDomestic()
  {
    return staffsDomestic;
  }

  public Set<String> getStaffsPermenant()
  {
    return staffsPermenant;
  }

  public Set<String> getStaffsTemporary()
  {
    return staffsTemporary;
  }

  public Double getTotalFeeCollected()
  {
    return totalFeeCollected;
  }

  public int getTotalNumberOfDom()
  {
    return totalNumberOfDom;
  }

  public int getTotalNumberOfPerm()
  {
    return totalNumberOfPerm;
  }

  public int getTotalNumberOfTemp()
  {
    return totalNumberOfTemp;
  }

  public Double getTotalSalarySpent()
  {
    return totalSalarySpent;
  }

  @PostConstruct
  public void init()
  {
    createBarModel();
    createCumBarModel();
    createPieModel1();
    createStaffCompositionModel();
    createCashModel();
    createParentOccupationModel();
    createMotherOccupationModel();
  }

  private BarChartModel initBarModel(boolean isCum)
  {
    BarChartModel model = new BarChartModel();

    ChartSeries per = new ChartSeries();
    per.setLabel("Permanent ");
    for (Entry<Date, Double> entry : permanant.entrySet())
    {
      Date key = entry.getKey();
      per.set(formatter.format(key), entry.getValue());
      cumilativePer += entry.getValue();
    }

    ChartSeries dom = new ChartSeries();
    dom.setLabel("Domestic ");
    for (Map.Entry<Date, Double> entry : domestic.entrySet())
    {
      dom.set(formatter.format(entry.getKey()), entry.getValue());
      cumilativeDom += entry.getValue();
    }

    ChartSeries part = new ChartSeries();
    part.setLabel("Part Time ");
    for (Map.Entry<Date, Double> entry : parttime.entrySet())
    {
      part.set(formatter.format(entry.getKey()), entry.getValue());
      cumilativeTem += entry.getValue();
    }

    ChartSeries tot = new ChartSeries();
    tot.setLabel("Total");
    for (Map.Entry<Date, Double> entry : total.entrySet())
    {
      tot.set(formatter.format(entry.getKey()), entry.getValue());
    }

    model.addSeries(per);
    model.addSeries(dom);
    model.addSeries(part);
    if (!isCum)
      model.addSeries(tot);

    model.setSeriesColors("337AB7,5CB85C,4C3973,A63F82,A30303,F74A4A");
    model.setAnimate(true);
    return model;
  }

  public Date parseToDate(String monthYearString) throws ParseException
  {
    return formatter.parse("01," + monthYearString);
  }

  public void setCashModel(HorizontalBarChartModel cashModel)
  {
    this.cashModel = cashModel;
  }

  public void setCumBarModel(BarChartModel cumBarModel)
  {
    this.cumBarModel = cumBarModel;
  }

  public void setHorizontalBarModel(HorizontalBarChartModel horizontalBarModel)
  {
    this.horizontalBarModel = horizontalBarModel;
  }

  public void setMotherOccupationModel(HorizontalBarChartModel motherOccupationModel)
  {
    this.motherOccupationModel = motherOccupationModel;
  }

  public void setParentOccupationModel(HorizontalBarChartModel parentOccupationModel)
  {
    this.parentOccupationModel = parentOccupationModel;
  }

  public void setPieModel1(PieChartModel pieModel1)
  {
    this.pieModel1 = pieModel1;
  }

  public void setStaffsDomestic(Set<String> staffsDomestic)
  {
    this.staffsDomestic = staffsDomestic;
  }

  public void setStaffsPermenant(Set<String> staffsPermenant)
  {
    this.staffsPermenant = staffsPermenant;
  }

  public void setStaffsTemporary(Set<String> staffsTemporary)
  {
    this.staffsTemporary = staffsTemporary;
  }

  public void setTotalFeeCollected(Double totalFeeCollected)
  {
    this.totalFeeCollected = totalFeeCollected;
  }

  public void setTotalNumberOfDom(int totalNumberOfDom)
  {
    this.totalNumberOfDom = totalNumberOfDom;
  }

  public void setTotalNumberOfPerm(int totalNumberOfPerm)
  {
    this.totalNumberOfPerm = totalNumberOfPerm;
  }

  public void setTotalNumberOfTemp(int totalNumberOfTemp)
  {
    this.totalNumberOfTemp = totalNumberOfTemp;
  }

  public void setTotalSalarySpent(Double totalSalarySpent)
  {
    this.totalSalarySpent = totalSalarySpent;
  }
}