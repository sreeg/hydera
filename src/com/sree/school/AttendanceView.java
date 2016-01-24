package com.sree.school;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.chart.DonutChartModel;

@ManagedBean(name = "attendanceView")
@ViewScoped
public class AttendanceView implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private static LinkedHashMap<String, String> category;
  private static LinkedHashMap<String, String> monthMap;
  private static LinkedHashMap<String, Integer> monthIndexMap;
  private static LinkedHashMap<String, String> yearMap = new LinkedHashMap<String, String>();
  private static LinkedHashMap<String, String> monthmapfromdb = new LinkedHashMap<String, String>();
  private static LinkedHashMap<String, Integer> monthDaysMap;

  static
  {
    category = new LinkedHashMap<String, String>();
    category.put("Permanent", "Permanent");
    category.put("Domestic", "Domestic");
    category.put("Part Time", "Part Time");
  }

  static
  {
    monthMap = new LinkedHashMap<String, String>();
    monthMap.put("January", "January");
    monthMap.put("February", "February");
    monthMap.put("March", "March");
    monthMap.put("April", "April");
    monthMap.put("May", "May");
    monthMap.put("June", "June");
    monthMap.put("July", "July");
    monthMap.put("August", "August");
    monthMap.put("September", "September");
    monthMap.put("October", "October");
    monthMap.put("November", "November");
    monthMap.put("December", "December");
  }

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

  static
  {
    monthDaysMap = new LinkedHashMap<String, Integer>();
    monthDaysMap.put("January", 31);
    monthDaysMap.put("February", 28);
    monthDaysMap.put("March", 31);
    monthDaysMap.put("April", 30);
    monthDaysMap.put("May", 31);
    monthDaysMap.put("June", 30);
    monthDaysMap.put("July", 31);
    monthDaysMap.put("August", 31);
    monthDaysMap.put("September", 30);
    monthDaysMap.put("October", 31);
    monthDaysMap.put("November", 30);
    monthDaysMap.put("December", 31);
  }

  public static void setCategory(LinkedHashMap<String, String> category)
  {
    AttendanceView.category = category;
  }

  private Map<String, Staff> staffMap = new HashMap<>();
  private Map<String, Staff> staffMapByEmployeeId = new HashMap<>();
  private Map<String, List<Attendance>> staffAttendanceMap = new HashMap<>();

  private Staff selectedStaff;

  private List<Attendance> staffs;
  private List<Staff> allStaffs;
  private List<Attendance> staffsPermenant;
  private List<Attendance> staffsTemporary;
  private List<Attendance> staffsDomestic;
  private List<Staff> filteredStaffs;
  private List<String> months;
  private List<String> years;

  private boolean showForm;
  private Connection conn;
  private String currentMonth;
  private String currentYear;
  private String selectedyear;
  private String selectedmonth;
  private int daysincurrentmonth;
  private int daysinselectedmonth;
  SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
  private List<Attendance> selectedStaffAttendance = new ArrayList<>();
  private DonutChartModel donutModel2;

  public AttendanceView() throws ClassNotFoundException, SQLException
  {
    staffsPermenant = new ArrayList<>();
    staffsTemporary = new ArrayList<>();
    staffsDomestic = new ArrayList<>();

    Calendar now = Calendar.getInstance();
    setCurrentMonth(month_date.format(now.getTime()));
    setCurrentYear("" + now.get(Calendar.YEAR));
    setDaysincurrentmonth(now.getActualMaximum(Calendar.DATE));
    setAllStaffs(getAllStaffsDetails());
    staffs = getAllStaffAttendance();
    yearMap.put(currentYear, currentYear);
    setSelectedyear(getCurrentYear());
    setSelectedmonth(getCurrentMonth());
    setDaysinselectedmonth(monthDaysMap.get(getCurrentMonth()));
    setShowForm(true);
    createDonutModels(new ArrayList<Attendance>());
    int y = Integer.parseInt(currentYear);
    if ((y % 400 == 0) || ((y % 4 == 0) && (y % 100 != 0)))
    {
      monthDaysMap.remove("February");
      monthDaysMap.put("February", 29);
    }
  }

  private boolean checkCorrectMonth()
  {
    if (Integer.parseInt(this.selectedyear) >= Integer.parseInt(this.currentYear))
    {
      if (monthIndexMap.get(selectedmonth) > monthIndexMap.get(currentMonth))
      {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot update attendance for future dates", "Select a correct year and month");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return false;
      }
    }
    return true;
  }

  private void createDonutModels(List<Attendance> selectedStaffAttendance2)
  {
    donutModel2 = initDonutModel(selectedStaffAttendance2);

    donutModel2.setLegendPosition("e");
    donutModel2.setSliceMargin(1);
    donutModel2.setShowDataLabels(true);
    donutModel2.setDataFormat("value");
    donutModel2.setShadow(false);
    donutModel2.setSeriesColors("1D9C73,dc4e44,33C7AB,A63F82,A30303");
  }

  public List<Attendance> getAllStaffAttendance()
  {
    ResultSet rs;
    List<Attendance> attendance = new ArrayList<>();

    try
    {
      java.sql.Connection conn = DBConnection.getConnection();

      // First get attendance by current month and year
      String query = "select staffid, dayspresent, month, year, daysinmonth from attendance  where month=" + "'" + currentMonth + "'" + " and year =" + "'" + currentYear + "'";
      rs = conn.createStatement().executeQuery(query);
      Map<String, Attendance> atMap = new HashMap<>();
      while (rs.next())
      {
        Attendance at = new Attendance();
        String staffID = rs.getString("staffid");
        at.setId(staffID);
        at.setDayspresent(rs.getInt("dayspresent"));
        at.setDaysinmonth(rs.getInt("daysinmonth"));
        at.setMonth(rs.getString("month"));
        at.setYear(rs.getString("year"));
        atMap.put(staffID, at);
      }

      for (Entry<String, Staff> entry : staffMapByEmployeeId.entrySet())
      {
        String staffid = entry.getKey();
        Staff s = entry.getValue();

        Attendance at = new Attendance();
        at.setId(staffid);
        at.setFirstname(s.getFirstname());
        at.setLastname(s.getLastname());
        at.setCategoryid(s.getCategoryid());
        at.setDesignation(s.getDesignation());
        at.setSpouseName(s.getSpouseName());
        at.setSpouseOccupation(s.getSpouseOccupation());
        at.setPhone(s.getPhone());
        at.setDob(s.getDob());
        at.setDoj(s.getDoj());
        at.setJoiningsalary(s.getJoiningsalary());
        at.setSex(s.getSex());
        at.setMobile(s.getMobile());
        at.setEmail(s.getEmail());
        at.setProfiepic(s.getProfiepic());
        at.setHouseno(s.getHouseno());
        at.setStreet(s.getStreet());
        at.setCity(s.getCity());
        at.setPostalCode(s.getPostalCode());

        if (atMap != null && !atMap.isEmpty())
        {
          Attendance atten = atMap.get(staffid);
          if (atten != null)
          {
            at.setDayspresent(atten.getDayspresent());
            at.setDaysinmonth(atten.getDaysinmonth());
            at.setMonth(atten.getMonth());
            at.setYear(atten.getYear());
          }
          else
          {
            at.setDayspresent(0);
            at.setDaysinmonth(0);
            at.setMonth(null);
            at.setYear(null);
          }
        }
        else
        {
          at.setDayspresent(0);
          at.setDaysinmonth(daysincurrentmonth);
          at.setMonth(currentMonth);
          at.setYear(currentYear);
        }
        String month = at.getMonth();
        String year = at.getYear();

        if ((month == null || month.equals(currentMonth)) && (year == null || year.equals(currentYear)))
          attendance.add(at);
      }

      // Now get all attendance
      String query2 = "select staffid, dayspresent, month, year, daysinmonth from attendance  order  by month, year";
      rs = conn.createStatement().executeQuery(query2);
      Map<String, Attendance> atMap2 = new HashMap<>();
      while (rs.next())
      {
        String staffid = rs.getString("staffid");
        Staff s = staffMapByEmployeeId.get(staffid);

        Attendance at = new Attendance();
        at.setId(staffid);
        at.setFirstname(s.getFirstname());
        at.setLastname(s.getLastname());
        at.setCategoryid(s.getCategoryid());
        at.setDesignation(s.getDesignation());
        at.setSpouseName(s.getSpouseName());
        at.setSpouseOccupation(s.getSpouseOccupation());
        at.setPhone(s.getPhone());
        at.setDob(s.getDob());
        at.setDoj(s.getDoj());
        at.setJoiningsalary(s.getJoiningsalary());
        at.setSex(s.getSex());
        at.setMobile(s.getMobile());
        at.setEmail(s.getEmail());
        at.setProfiepic(s.getProfiepic());
        at.setHouseno(s.getHouseno());
        at.setStreet(s.getStreet());
        at.setCity(s.getCity());
        at.setPostalCode(s.getPostalCode());

        at.setDayspresent(rs.getInt("dayspresent"));
        at.setDaysinmonth(rs.getInt("daysinmonth"));
        at.setMonth(rs.getString("month"));
        at.setYear(rs.getString("year"));
        atMap2.put(staffid, at);

        String month = at.getMonth();
        String year = at.getYear();

        if (year != null)
          yearMap.put(year, year);

        if (month != null)
          monthmapfromdb.put(month, month);

        if (at.getCategoryid().equals("1"))
        {
          staffsPermenant.add(at);
        }
        if (at.getCategoryid().equals("2"))
        {
          staffsDomestic.add(at);
        }
        if (at.getCategoryid().equals("3"))
        {
          staffsTemporary.add(at);
        }

        if (staffAttendanceMap.get(staffid) != null && staffAttendanceMap.get(staffid).size() != 0)
        {
          staffAttendanceMap.get(staffid).add(at);
        }
        else
        {
          ArrayList<Attendance> arrayList = new ArrayList<>();
          arrayList.add(at);
          staffAttendanceMap.put(staffid, arrayList);
        }
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    return attendance;
  }

  public List<Attendance> getAllStaffByMonthAndYear() throws ClassNotFoundException, SQLException
  {
    List<Attendance> attendance = new ArrayList<>();
    java.sql.Connection conn = DBConnection.getConnection();

    // First get attendance by current month and year
    String query = "select staffid, dayspresent, month, year, daysinmonth from attendance  where month=" + "'" + getSelectedmonth() + "'" + " and year =" + "'" + getSelectedyear()
        + "'";
    ResultSet rs = conn.createStatement().executeQuery(query);
    Map<String, Attendance> atMap = new HashMap<>();
    while (rs.next())
    {
      Attendance at = new Attendance();
      String staffID = rs.getString("staffid");
      at.setId(staffID);
      at.setDayspresent(rs.getInt("dayspresent"));
      at.setDaysinmonth(rs.getInt("daysinmonth"));
      at.setMonth(rs.getString("month"));
      at.setYear(rs.getString("year"));
      atMap.put(staffID, at);
    }

    for (Entry<String, Staff> entry : staffMapByEmployeeId.entrySet())
    {
      String staffid = entry.getKey();
      Staff s = entry.getValue();

      Attendance at = new Attendance();
      at.setId(staffid);
      at.setFirstname(s.getFirstname());
      at.setLastname(s.getLastname());
      at.setCategoryid(s.getCategoryid());
      at.setDesignation(s.getDesignation());
      at.setSpouseName(s.getSpouseName());
      at.setSpouseOccupation(s.getSpouseOccupation());
      at.setPhone(s.getPhone());
      at.setDob(s.getDob());
      at.setDoj(s.getDoj());
      at.setJoiningsalary(s.getJoiningsalary());
      at.setSex(s.getSex());
      at.setMobile(s.getMobile());
      at.setEmail(s.getEmail());
      at.setProfiepic(s.getProfiepic());
      at.setHouseno(s.getHouseno());
      at.setStreet(s.getStreet());
      at.setCity(s.getCity());
      at.setPostalCode(s.getPostalCode());

      if (atMap != null && !atMap.isEmpty())
      {
        Attendance atten = atMap.get(staffid);
        if (atten != null)
        {
          at.setDayspresent(atten.getDayspresent());
          at.setDaysinmonth(atten.getDaysinmonth());
          at.setMonth(atten.getMonth());
          at.setYear(atten.getYear());
        }
        else
        {
          at.setDayspresent(0);
          at.setDaysinmonth(0);
          at.setMonth(null);
          at.setYear(null);
        }
      }
      else
      {
        at.setDayspresent(0);
        at.setDaysinmonth(daysincurrentmonth);
        at.setMonth(currentMonth);
        at.setYear(currentYear);
      }
      String month = at.getMonth();
      String year = at.getYear();

      if ((month == null || month.equals(currentMonth)) && (year == null || year.equals(currentYear)))
        attendance.add(at);

      if (year != null)
        yearMap.put(year, year);

      if (month != null)
        monthmapfromdb.put(month, month);

      if (at.getCategoryid().equals("1"))
      {
        staffsPermenant.add(at);
      }
      if (at.getCategoryid().equals("2"))
      {
        staffsDomestic.add(at);
      }
      if (at.getCategoryid().equals("3"))
      {
        staffsTemporary.add(at);
      }
    }

    return attendance;
  }

  public List<Staff> getAllStaffs()
  {
    return allStaffs;
  }

  public List<Staff> getAllStaffsDetails()
  {
    java.sql.Connection conn;
    List<Staff> staffs = new ArrayList<>();
    try
    {
      conn = DBConnection.getConnection();

      ResultSet rs = conn.createStatement().executeQuery("select Id, FirstName, LastName, CategoryId, Designation, SpouseName, SpouseOccupation, Phone, DateOfBirth, DateOfJoining,"
          + "JoiningSalary, Gender, Mobile, Email, ProfilePic, houseno, street, city, postalcode from staff where isarchived=0");
      staffMapByEmployeeId = new HashMap<>();
      while (rs.next())
      {
        Staff st = new Staff();
        st.setId(rs.getString("Id"));
        st.setFirstname(rs.getString("FirstName"));
        st.setLastname(rs.getString("Lastname"));
        st.setCategoryid(rs.getString("CategoryId"));
        st.setDesignation(rs.getString("Designation"));
        st.setSpouseName(rs.getString("SpouseName"));
        st.setSpouseOccupation(rs.getString("SpouseOccupation"));
        st.setPhone(rs.getString("Phone"));
        st.setDob(rs.getDate("DateOfBirth"));
        st.setDoj(rs.getDate("DateOfJoining"));
        st.setJoiningsalary(rs.getDouble("JoiningSalary"));
        st.setSex(rs.getString("Gender"));
        st.setMobile(rs.getString("Mobile"));
        st.setEmail(rs.getString("Email"));
        st.setProfiepic(rs.getString("ProfilePic"));
        st.setHouseno(rs.getString("houseno"));
        st.setStreet(rs.getString("street"));
        st.setCity(rs.getString("city"));
        st.setPostalCode(rs.getString("postalcode"));

        staffs.add(st);
        staffMapByEmployeeId.put(st.getId(), st);
      }
    }
    catch (ClassNotFoundException | SQLException e)
    {
      e.printStackTrace();
    }
    return staffs;
  }

  public LinkedHashMap<String, String> getCategory()
  {
    return category;
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

  public DonutChartModel getDonutModel2()
  {
    return donutModel2;
  }

  public List<Staff> getFilteredStaffs()
  {
    return filteredStaffs;
  }

  public LinkedHashMap<String, String> getMonthMap()
  {
    return monthMap;
  }

  public LinkedHashMap<String, String> getMonthmapfromdb()
  {
    return monthmapfromdb;
  }

  public List<String> getMonths()
  {
    return months;
  }

  public String getSelectedmonth()
  {
    return selectedmonth;
  }

  public Staff getSelectedStaff()
  {
    return selectedStaff;
  }

  public List<Attendance> getSelectedStaffAttendance()
  {
    return selectedStaffAttendance;
  }

  public String getSelectedyear()
  {
    return selectedyear;
  }

  public Map<String, List<Attendance>> getStaffAttendanceMap()
  {
    return staffAttendanceMap;
  }

  public List<Staff> getStaffByCategory(String categoryID) throws ClassNotFoundException, SQLException
  {
    java.sql.Connection conn = DBConnection.getConnection();
    ResultSet rs = conn.createStatement()
        .executeQuery("select Id, FirstName, LastName, CategoryId, Designation," + "SpouseName, SpouseOccupation, Phone, DateOfBirth, DateOfJoining,"
            + "JoiningSalary, Gender, Mobile, Email, ProfilePic, houseno, street, city, postalcode from staff where isarchived=0 and CategoryId = " + "'" + categoryID + "'");

    List<Staff> students = new ArrayList<>();
    while (rs.next())
    {
      Staff st = new Staff();
      st.setId(rs.getString("Id"));
      st.setFirstname(rs.getString("FirstName"));
      st.setLastname(rs.getString("Lastname"));
      st.setCategoryid(rs.getString("CategoryId"));
      st.setDesignation(rs.getString("Designation"));
      st.setSpouseName(rs.getString("SpouseName"));
      st.setSpouseOccupation(rs.getString("SpouseOccupation"));
      st.setPhone(rs.getString("Phone"));
      st.setDob(rs.getDate("DateOfBirth"));
      st.setDoj(rs.getDate("DateOfJoining"));
      st.setJoiningsalary(rs.getDouble("JoiningSalary"));
      st.setSex(rs.getString("Gender"));
      st.setMobile(rs.getString("Mobile"));
      st.setEmail(rs.getString("Email"));
      st.setProfiepic(rs.getString("ProfilePic"));
      st.setHouseno(rs.getString("houseno"));
      st.setStreet(rs.getString("street"));
      st.setCity(rs.getString("city"));
      st.setPostalCode(rs.getString("postalcode"));
      students.add(st);
      staffMap.put(st.getId(), st);
    }
    return students;
  }

  public Map<String, Staff> getStaffMapByEmployeeId()
  {
    return staffMapByEmployeeId;
  }

  public List<Attendance> getStaffs()
  {
    return staffs;
  }

  public List<Attendance> getStaffsDomestic()
  {
    return staffsDomestic;
  }

  public List<Attendance> getStaffsPermenant()
  {
    return staffsPermenant;
  }

  public List<Attendance> getStaffsTemporary()
  {
    return staffsTemporary;
  }

  public LinkedHashMap<String, String> getYearMap()
  {
    return yearMap;
  }

  public List<String> getYears()
  {
    return years;
  }

  private DonutChartModel initDonutModel(List<Attendance> selectedStaffAttendance2)
  {
    DonutChartModel model = new DonutChartModel();
    int present = 0, total = 0;
    for (Attendance a : selectedStaffAttendance2)
    {
      present = present + a.getDayspresent();
      total = total + a.getDaysinmonth();
    }
    model.setTitle("Total no.of days : " + total);
    Map<String, Number> circle1 = new LinkedHashMap<String, Number>();
    circle1.put("Present", present);
    circle1.put("Absent", total - present);
    model.addCircle(circle1);

    return model;
  }

  public boolean isShowForm()
  {
    return this.showForm;
  }

  public void onCellEdit(CellEditEvent event)
  {
    Object oldValue = event.getOldValue();
    Object newValue = event.getNewValue();

    if (newValue != null && !newValue.equals(oldValue))
    {
      FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
      FacesContext.getCurrentInstance().addMessage(null, msg);
    }
  }

  public void onMonthChange() throws ClassNotFoundException, SQLException
  {
    if (selectedmonth != null && !selectedmonth.equals(""))
    {
      if (!checkCorrectMonth())
      {
        return;
      }
      setDaysinselectedmonth(monthDaysMap.get(selectedmonth));
      staffs = getAllStaffByMonthAndYear();
      setShowForm(true);
    }
  }

  public void onRowSelect(SelectEvent event)
  {
    selectedStaffAttendance = staffAttendanceMap.get(selectedStaff.getId());
    FacesMessage msg = new FacesMessage("Car Selected", selectedStaff.getFullname());
    FacesContext.getCurrentInstance().addMessage(null, msg);
    createDonutModels(selectedStaffAttendance);
  }

  public void onYearChange() throws ClassNotFoundException, SQLException
  {
    if (selectedmonth != null && !selectedmonth.equals(""))
    {
      int y = Integer.parseInt(selectedyear);
      if ((y % 400 == 0) || ((y % 4 == 0) && (y % 100 != 0)))
      {
        monthDaysMap.remove("February");
        monthDaysMap.put("February", 29);
      }
      else
      {
        monthDaysMap.remove("February");
        monthDaysMap.put("February", 28);
      }

      setDaysinselectedmonth(monthDaysMap.get(selectedmonth));
      staffs = getAllStaffByMonthAndYear();
      setShowForm(true);
    }
  }

  public void prefill()
  {
    for (Attendance a : staffs)
    {
      a.setDayspresent(getDaysinselectedmonth());
    }
  }

  public void save() throws ClassNotFoundException, SQLException
  {
    if (!checkCorrectMonth())
    {
      return;
    }
    conn = DBConnection.getConnection();

    for (Attendance staff : staffs)
    {
      String selectedmonth = getSelectedmonth();
      PreparedStatement ps;
      if (monthmapfromdb.get(selectedmonth) != null && staff.getMonth() != null && staff.getYear() != null)
      {
        ps = conn.prepareStatement("UPDATE ATTENDANCE  set dayspresent = ?, updatedatetime = now() " + "where staffid = ? and month = ? and year = ?");

        ps.setInt(1, staff.getDayspresent());
        ps.setString(2, staff.getId());
        ps.setString(3, selectedmonth);
        ps.setString(4, getSelectedyear());
      }
      else
      {
        ps = conn.prepareStatement(
            "INSERT INTO ATTENDANCE (staffid, date, dayspresent, daysinmonth, createdatetime, updatedatetime, month, year )" + "VALUES (?,now(),?,?, now(), now(),?,?)");

        ps.setString(1, staff.getId());
        ps.setInt(2, staff.getDayspresent());
        ps.setInt(3, daysinselectedmonth);
        ps.setString(4, selectedmonth);
        ps.setString(5, getSelectedyear());
      }

      int rs = ps.executeUpdate();

      FacesMessage msg = null;
      if (rs == 1)
      {
        msg = new FacesMessage("Attendance added successfully for " + staff.getFullname(), "");
        // setShowForm(false);
      }
      else
      {
        msg = new FacesMessage("Something went wrong", "When updating attendance for " + staff.getFullname());
      }
      FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    monthmapfromdb.put(selectedmonth, selectedmonth);
  }

  public void setAllStaffs(List<Staff> allStaffs)
  {
    this.allStaffs = allStaffs;
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

  public void setDonutModel2(DonutChartModel donutModel2)
  {
    this.donutModel2 = donutModel2;
  }

  public void setFilteredStaffs(List<Staff> filteredStaffs)
  {
    this.filteredStaffs = filteredStaffs;
  }

  public void setMonthMap(LinkedHashMap<String, String> monthMap)
  {
    AttendanceView.monthMap = monthMap;
  }

  public void setMonthmapfromdb(LinkedHashMap<String, String> monthmapfromdb)
  {
    monthmapfromdb = monthmapfromdb;
  }

  public void setMonths(List<String> months)
  {
    this.months = months;
  }

  public void setSelectedmonth(String selectedmonth)
  {
    this.selectedmonth = selectedmonth;
  }

  public void setSelectedStaff(Staff selectedStaff)
  {
    this.selectedStaff = selectedStaff;
  }

  public void setSelectedStaffAttendance(List<Attendance> selectedStaffAttendance)
  {
    this.selectedStaffAttendance = selectedStaffAttendance;
  }

  public void setSelectedyear(String selectedyear)
  {
    this.selectedyear = selectedyear;
  }

  public void setShowForm(boolean showForm)
  {
    this.showForm = showForm;
  }

  public void setStaffAttendanceMap(Map<String, List<Attendance>> staffAttendanceMap)
  {
    this.staffAttendanceMap = staffAttendanceMap;
  }

  public void setStaffMapByEmployeeId(Map<String, Staff> staffMapByEmployeeId)
  {
    this.staffMapByEmployeeId = staffMapByEmployeeId;
  }

  public void setStaffsDomestic(List<Attendance> staffsDomestic)
  {
    this.staffsDomestic = staffsDomestic;
  }

  public void setStaffsPermenant(List<Attendance> staffsPermenant)
  {
    this.staffsPermenant = staffsPermenant;
  }

  public void setStaffsTemporary(List<Attendance> staffsTemporary)
  {
    this.staffsTemporary = staffsTemporary;
  }

  public void setYearMap(LinkedHashMap<String, String> yearMap)
  {
    AttendanceView.yearMap = yearMap;
  }

  public void setYears(List<String> years)
  {
    this.years = years;
  }

}
