package com.sree.school;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

@ManagedBean(name = "studentFeeFilterView")
@ViewScoped
public class StudentFeeFilterView implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private static Connection conn;
  private static Collection<String> classnames;

  public static void setClassnames(Collection<String> classnames)
  {
    StudentFeeFilterView.classnames = classnames;
  }

  private List<String> selectedOptions;

  private List<StudentFee> students;
  private Map<String, StudentFee> studentMap;

  private List<Student> filteredStudents;
  private List<Student> selectedStudents;
  private Student selectedStudent;
  private String selectedStudentId;
  private boolean showForm;
  private boolean showPrintButton;
  private StudentFee studentfee;
  private List<StudentFee> feepayments;
  @ManagedProperty("#{studentService}")
  private StudentService service;
  private Date fromdate;
  private Date todate;
  private double totalFeevalue;

  public StudentFeeFilterView() throws ClassNotFoundException, SQLException
  {
    totalFeevalue = 0;
    students = getAllAtudents();
    classnames = Student.classes.values();
    feepayments = getAllFeePayments();
    setShowForm(false);
    setShowPrintButton(false);
  }

  public List<StudentFee> completeStudents(String query)
  {
    List<StudentFee> allStudents = students;
    List<StudentFee> filteredStudents = new ArrayList<StudentFee>();

    for (int i = 0; i < allStudents.size(); i++)
    {
      StudentFee stu = allStudents.get(i);
      if (stu.getFullname().toLowerCase().contains(query))
      {
        filteredStudents.add(stu);
      }
    }
    return filteredStudents;
  }

  public void filterByDate()
  {
    getAllFeePaymentsByFilter();
  }

  public List<StudentFee> getAllAtudents() throws ClassNotFoundException, SQLException
  {
    conn = DBConnection.getConnection();
    ResultSet rs = conn.createStatement()
        .executeQuery("select Id, FirstName, LastName, Class, Section," + "FatherName, FatherOccupation, Phone, DateOfBirth, DateOfJoining,"
            + "MotherName, MotherOccupation, Gender, GaurdianName, Mobile, mothermobile, Email, feereceiptid, amountpaid, paymentdate, paymentmode, term1, term2, term3, "
            + "ProfilePic, houseno, street, city, postalcode from student "
            + "LEFT JOIN feepayment ON feepayment.studentid=student.Id where student.isarchived = '0' order by Class, Section");

    List<StudentFee> students = new ArrayList<>();
    studentMap = new HashMap<>();
    while (rs.next())
    {
      StudentFee st = new StudentFee();
      st.setId(rs.getString("Id"));
      st.setFirstname(rs.getString("FirstName"));
      st.setLastname(rs.getString("Lastname"));
      st.setClassname(rs.getString("Class"));
      st.setSectionname(rs.getString("Section"));
      st.setFathername(rs.getString("FatherName"));
      st.setFatheroccupation(rs.getString("FatherOccupation"));
      st.setPhone(rs.getString("Phone"));
      st.setDob(rs.getDate("DateOfBirth"));
      st.setDoj(rs.getDate("DateOfJoining"));
      st.setMothername(rs.getString("MotherName"));
      st.setMotheroccupation(rs.getString("MotherOccupation"));
      st.setSex(rs.getString("Gender"));
      st.setGuardianname(rs.getString("GaurdianName"));
      st.setMobile(rs.getString("Mobile"));
      st.setMothermobile(rs.getString("mothermobile"));
      st.setEmail(rs.getString("Email"));
      st.setProfiepic(rs.getString("ProfilePic"));
      st.setHouseno(rs.getString("houseno"));
      st.setStreet(rs.getString("street"));
      st.setCity(rs.getString("city"));
      st.setPostalCode(rs.getString("postalcode"));
      st.setPaymentdate(rs.getDate("paymentdate"));
      st.setAmountPaid(rs.getDouble("amountpaid"));
      st.setReceiptid(rs.getString("feereceiptid"));
      st.setPaymentmode(rs.getString("paymentmode"));
      st.setTerm1(rs.getBoolean("term1"));
      st.setTerm2(rs.getBoolean("term2"));
      st.setTerm3(rs.getBoolean("term3"));

      if (st.isTerm1())
      {
        st.setTerm1amount(st.getAmountPaid());
        st.setTerm1paiddate(st.getPaymentdate());
      }

      if (studentMap.get(st.getId()) != null)
      {
        StudentFee s = studentMap.get(st.getId());
        if (st.isTerm1())
        {
          s.setTerm1(true);
          s.setTerm1amount(st.getAmountPaid());
          s.setTerm1paiddate(st.getPaymentdate());
        }
        if (st.isTerm2())
        {
          s.setTerm2(true);
          s.setTerm2amount(st.getAmountPaid());
          s.setTerm2paiddate(st.getPaymentdate());
        }
        if (st.isTerm3())
        {
          s.setTerm3(true);
          s.setTerm3amount(st.getAmountPaid());
          s.setTerm3paiddate(st.getPaymentdate());
        }
      }
      else
      {
        studentMap.put(st.getId(), st);
        students.add(st);
      }
    }
    return students;
  }

  private List<StudentFee> getAllFeePayments()
  {
    try
    {
      conn = DBConnection.getConnection();
      PreparedStatement ps = conn.prepareStatement(
          "select feereceiptid, paymentmode, amountpaid, term1, term2, term3, paymentdate, studentid, paymentdetails, bankname, receivedfrom, createdatetime from feepayment");
      ResultSet rs = ps.executeQuery();
      totalFeevalue = 0;
      feepayments = new ArrayList<>();
      while (rs.next())
      {
        StudentFee sf = new StudentFee();
        sf.setEmployeeid(rs.getString("studentid"));
        sf.setPaymentmode(rs.getString("paymentmode"));
        sf.setTerm1(rs.getBoolean("term1"));
        sf.setTerm2(rs.getBoolean("term2"));
        sf.setTerm3(rs.getBoolean("term3"));
        sf.setAmountPaid(rs.getDouble("amountpaid"));
        sf.setPaymentdate(rs.getDate("paymentdate"));
        sf.setPaymentdetails(rs.getString("paymentdetails"));
        sf.setReceiptid(rs.getString("feereceiptid"));
        sf.setBankname(rs.getString("bankname"));
        sf.setReceivedfrom(rs.getString("receivedfrom"));
        sf.setCreatedatetime(rs.getDate("createdatetime"));

        Student student = studentMap.get(sf.getEmployeeid());
        if (student != null)
        {
          sf.setFirstname(student.getFirstname());
          sf.setLastname(student.getLastname());
          sf.setClassname(student.getClassname());
          sf.setSectionname(student.getSectionname());
          sf.setEmail(student.getEmail());

          feepayments.add(sf);
          totalFeevalue = totalFeevalue + sf.getAmountPaid();
        }
      }
    }
    catch (ClassNotFoundException | SQLException e)
    {
      e.printStackTrace();
    }

    return feepayments;
  }

  private List<StudentFee> getAllFeePaymentsByFilter()
  {
    try
    {
      int size = selectedOptions.size();
      String options = "";
      if (size == 0)
      {
        options = "'Cheque', 'DD', 'Cash'";
      }
      for (int i = 0; i < size; i++)
      {
        options = options + "'" + selectedOptions.get(i) + "'";
        if (i != size - 1)
        {
          options = options + ", ";
        }
      }

      conn = DBConnection.getConnection();
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String string = "";
      if (todate != null && fromdate != null)
      {
        string = "select feereceiptid, paymentmode, amountpaid, term1, term2, term3, paymentdate, studentid, paymentdetails, bankname, receivedfrom, createdatetime from feepayment where paymentmode in ("
            + options + ") and DATE(createdatetime) <= " + "'" + simpleDateFormat.format(todate) + "'" + " and DATE(createdatetime) >= " + "'" + simpleDateFormat.format(fromdate)
            + "'";
      }
      else
      {
        string = "select feereceiptid, paymentmode, amountpaid, term1, term2, term3, paymentdate, studentid, paymentdetails, bankname, receivedfrom, createdatetime from feepayment where paymentmode in ("
            + options + ")";
      }

      PreparedStatement ps = conn.prepareStatement(string);
      ResultSet rs = ps.executeQuery();
      totalFeevalue = 0;
      feepayments = new ArrayList<>();
      while (rs.next())
      {
        StudentFee sf = new StudentFee();
        sf.setEmployeeid(rs.getString("studentid"));
        sf.setPaymentmode(rs.getString("paymentmode"));
        sf.setTerm1(rs.getBoolean("term1"));
        sf.setTerm2(rs.getBoolean("term2"));
        sf.setTerm3(rs.getBoolean("term3"));
        sf.setAmountPaid(rs.getDouble("amountpaid"));
        sf.setPaymentdate(rs.getDate("paymentdate"));
        sf.setPaymentdetails(rs.getString("paymentdetails"));
        sf.setReceiptid(rs.getString("feereceiptid"));
        sf.setBankname(rs.getString("bankname"));
        sf.setReceivedfrom(rs.getString("receivedfrom"));
        sf.setCreatedatetime(rs.getDate("createdatetime"));

        Student student = studentMap.get(sf.getEmployeeid());
        if (student != null)
        {
          sf.setFirstname(student.getFirstname());
          sf.setLastname(student.getLastname());
          sf.setClassname(student.getClassname());
          sf.setSectionname(student.getSectionname());
          sf.setEmail(student.getEmail());
          totalFeevalue = totalFeevalue + sf.getAmountPaid();
          feepayments.add(sf);
        }
      }
    }
    catch (ClassNotFoundException | SQLException e)
    {
      e.printStackTrace();
    }

    return feepayments;
  }

  public Collection<String> getClassnames()
  {
    return classnames;
  }

  public List<StudentFee> getFeepayments()
  {
    return feepayments;
  }

  public List<Student> getFilteredStudents()
  {
    return filteredStudents;
  }

  public Date getFromdate()
  {
    return fromdate;
  }

  public List<String> getSelectedOptions()
  {
    return selectedOptions;
  }

  public Student getSelectedStudent()
  {
    return selectedStudent;
  }

  public String getSelectedStudentId()
  {
    return selectedStudentId;
  }

  public List<Student> getSelectedStudents()
  {
    return selectedStudents;
  }

  public StudentFee getStudentfee()
  {
    return studentfee;
  }

  private StudentFee getStudentFee()
  {
    try
    {
      conn = DBConnection.getConnection();
      ResultSet rs = conn.createStatement().executeQuery("select studentid, term1paymentamount, term2paymentamount, term3paymentamount, feereceiptid, paymentmode, "
          + "term1paiddate, term2paiddate, term3paiddate, term1cheque, term2cheque, term3cheque " + "from feepayment where studentid = " + "'" + selectedStudentId + "'");

      studentfee = new StudentFee();
      while (rs.next())
      {
        studentfee.setEmployeeid(rs.getString("studentid"));
        studentfee.setTerm1amount(rs.getDouble("term1paymentamount"));
        studentfee.setTerm2amount(rs.getDouble("term2paymentamount"));
        studentfee.setTerm3amount(rs.getDouble("term3paymentamount"));
        studentfee.setTerm1paiddate(rs.getDate("term1paiddate"));
        studentfee.setTerm2paiddate(rs.getDate("term2paiddate"));
        studentfee.setTerm3paiddate(rs.getDate("term3paiddate"));
        studentfee.setTerm1cheque(rs.getString("term1cheque"));
        studentfee.setTerm2cheque(rs.getString("term2cheque"));
        studentfee.setTerm3cheque(rs.getString("term3cheque"));
        studentfee.setReceiptid(rs.getString("feereceiptid"));
        studentfee.setPaymentmode(rs.getString("paymentmode"));

        studentfee.setTerm1(false);
        studentfee.setTerm2(false);
        studentfee.setTerm3(false);

        studentfee.setAmountPaid(studentfee.getTerm1amount() + studentfee.getTerm2amount() + studentfee.getTerm3amount());
      }
    }
    catch (ClassNotFoundException | SQLException e)
    {
      e.printStackTrace();
    }

    return studentfee;
  }

  public Map<String, StudentFee> getStudentMap()
  {
    return studentMap;
  }

  public List<StudentFee> getStudents()
  {
    return students;
  }

  public Date getTodate()
  {
    return todate;
  }

  public double getTotalFeevalue()
  {
    return totalFeevalue;
  }

  public boolean isShowForm()
  {
    return showForm;
  }

  public boolean isShowPrintButton()
  {
    return showPrintButton;
  }

  public void onPaymentChange()
  {
    System.out.println(selectedOptions.toString());
    feepayments = getAllFeePaymentsByFilter();
  }

  public void onStudentFeeSelect(SelectEvent event)
  {
    selectedStudent = studentMap.get(selectedStudentId);
    studentfee = getStudentFee();
    setShowForm(true);
    setShowPrintButton(true);
  }

  public void onStudentSelect(SelectEvent event)
  {
    selectedStudent = studentMap.get(selectedStudentId);
    studentfee = getStudentFee();
    setShowForm(true);
    setShowPrintButton(false);
  }

  public void setFeepayments(List<StudentFee> feepayments)
  {
    this.feepayments = feepayments;
  }

  public void setFilteredStudents(List<Student> filteredStudents)
  {
    this.filteredStudents = filteredStudents;
  }

  public void setFromdate(Date fromdate)
  {
    this.fromdate = fromdate;
  }

  public void setSelectedOptions(List<String> selectedOptions)
  {
    this.selectedOptions = selectedOptions;
  }

  public void setSelectedStudent(Student selectedStudent)
  {
    this.selectedStudent = selectedStudent;
  }

  public void setSelectedStudentId(String selectedStudentId)
  {
    this.selectedStudentId = selectedStudentId;
  }

  public void setSelectedStudents(List<Student> selectedStudents)
  {
    this.selectedStudents = selectedStudents;
  }

  public void setService(StudentService service)
  {
    this.service = service;
  }

  public void setShowForm(boolean showForm)
  {
    this.showForm = showForm;
  }

  public void setShowPrintButton(boolean showPrintButton)
  {
    this.showPrintButton = showPrintButton;
  }

  public void setStudentfee(StudentFee studentfee)
  {
    this.studentfee = studentfee;
  }

  public void setStudentMap(Map<String, StudentFee> studentMap)
  {
    this.studentMap = studentMap;
  }

  public void setStudents(List<StudentFee> students)
  {
    this.students = students;
  }

  public void setTodate(Date todate)
  {
    this.todate = todate;
  }

  public void setTotalFeevalue(double totalFeevalue)
  {
    this.totalFeevalue = totalFeevalue;
  }

}
