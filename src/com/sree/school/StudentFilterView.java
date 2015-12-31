package com.sree.school;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.primefaces.component.outputpanel.OutputPanel;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@ManagedBean(name = "studentFilterView")
@ViewScoped
public class StudentFilterView implements Serializable
{

  private static Font underline18 = new Font(Font.HELVETICA, 18, Font.UNDERLINE);
  private static Font smallBold = new Font(Font.HELVETICA, 12, Font.BOLD);
  private static Font subFont = new Font(Font.HELVETICA, 14, Font.NORMAL);
  private static Font smallestItalic = new Font(Font.HELVETICA, 10, Font.ITALIC);

  private static Collection<String> classnames;
  private static Connection conn;
  private static String FILE = "FirstPdf.pdf";
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** Definition of two columns */
  public static final float[][] COLUMNS = { { 36, 36, 296, 806 }, { 299, 36, 559, 806 } };

  private static void addEmptyLine(Paragraph paragraph, int number)
  {
    for (int i = 0; i < number; i++)
    {
      paragraph.add(new Paragraph(" "));
    }
  }

  public static void setClassnames(Collection<String> classnames)
  {
    StudentFilterView.classnames = classnames;
  }

  private List<String> selectedTermOptions;
  private OutputPanel feePanel;
  private StreamedContent file;
  private List<Student> filteredStudents;
  private Student selectedStudent;
  private List<Student> selectedStudents;
  private int noofselecteditems = 0;
  private String selectedStudentId;
  private List<String> selectedStudentIds;
  @ManagedProperty("#{studentService}")
  private StudentService service;
  private Map<String, String> studentbyclass;
  private boolean showForm;
  private boolean showPrintButton;
  private StudentFee studentfee;
  private List<StudentFee> studentfees;
  private List<StudentFee> studentfeeList;
  private boolean studentfound;
  private Map<String, Student> studentMap;
  private List<Student> students;
  private List<Student> studentsWithFee;
  private String selectedClass;
  private double termamount;

  public StudentFilterView() throws ClassNotFoundException, SQLException
  {
    students = getAllAtudents();
    classnames = Student.classes.values();
    studentfound = false;
    setShowForm(false);
    setShowPrintButton(false);
    studentbyclass = new HashMap<>();
  }

  public List<Student> completeStudents(String query)
  {
    List<Student> allStudents = students;
    List<Student> filteredStudents = new ArrayList<Student>();

    for (int i = 0; i < allStudents.size(); i++)
    {
      Student stu = allStudents.get(i);
      if (stu.getFullname().toLowerCase().contains(query))
      {
        filteredStudents.add(stu);
      }
    }
    return filteredStudents;
  }

  public String delete(Student student)
  {
    FacesMessage msg = null;
    try
    {
      conn = DBConnection.getConnection();
      conn.createStatement().executeUpdate("UPDATE student set isarchived = '1' where id = " + "'" + student.getId() + "'");
      msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Student " + student.getFullname() + " has been deleted.", "");
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

    return null;
  }

  public void emailFeeReceipt()
  {
    Document document = new Document();
    ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    String logo = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "images" + File.separator + "logo.gif";
    // Create the attachment
    EmailAttachment attachment = new EmailAttachment();
    attachment.setPath(FILE);
    attachment.setDisposition(EmailAttachment.ATTACHMENT);
    attachment.setDescription("fee recipt");
    attachment.setName("fee.pdf");

    SystemSettingsBean sys = new SystemSettingsBean();

    // Create the email message
    HtmlEmail email = new HtmlEmail();
    email.setHostName(sys.getSystemSettings().getEmailhostname());
    email.setSmtpPort(Integer.parseInt(sys.getSystemSettings().getSmtpport()));
    email.setAuthenticator(new DefaultAuthenticator(sys.getSystemSettings().getEmail(), sys.getSystemSettings().getPassword()));
    email.setSSLOnConnect(true);
    try
    {
      FileOutputStream fileOutputStream = new FileOutputStream(FILE);
      PdfWriter writer = PdfWriter.getInstance(document, fileOutputStream);
      document.open();
      document.addTitle("Fee receipt");
      document.addSubject("Chaitanya Vidyalaya");
      document.addAuthor("Chaitanya Vidyalaya");
      document.addCreator("Chaitanya Vidyalaya");

      Paragraph header = new Paragraph();
      header.setAlignment(Element.ALIGN_CENTER);
      header.setIndentationLeft(20);
      header.setIndentationRight(20);
      header.add(Image.getInstance(logo));
      addEmptyLine(header, 2);

      Paragraph paragraph = new Paragraph();
      paragraph.setAlignment(Element.ALIGN_CENTER);
      paragraph.setFont(underline18);
      paragraph.add(new Chunk("Fee Receipt :"));
      header.add(paragraph);

      addEmptyLine(header, 1);
      String headerString = "Payment of " + studentfee.getAmountPaid() + "  has been received as part of term payment ";
      if (studentfee.getTerm1paiddate() != null && studentfee.getTerm2paiddate() != null && studentfee.getTerm3paiddate() != null)
      {
        headerString += "1, 2 and 3 from ";
      }
      else if (studentfee.getTerm1paiddate() != null && studentfee.getTerm2paiddate() != null && studentfee.getTerm3paiddate() == null)
      {
        headerString += "1 and 2 from ";
      }
      else if (studentfee.getTerm1paiddate() != null && studentfee.getTerm2paiddate() == null && studentfee.getTerm3paiddate() == null)
      {
        headerString += "1 from ";
      }

      Paragraph paragraph2 = new Paragraph(headerString, subFont);
      paragraph2.setAlignment(Element.ALIGN_CENTER);
      header.add(paragraph2);
      addEmptyLine(header, 1);
      document.add(header);

      Paragraph center = new Paragraph();
      center.setAlignment(Element.ALIGN_JUSTIFIED);
      center.setIndentationLeft(50);
      center.setIndentationRight(50);
      center.add(new Chunk("Student Name \t\t\t", smallBold));
      center.add(new Chunk(selectedStudent.getFullname(), subFont));
      addEmptyLine(center, 1);
      center.add(new Chunk("Class Name \t\t\t", smallBold));
      center.add(new Chunk(selectedStudent.getClassname(), subFont));
      addEmptyLine(center, 1);
      center.add(new Chunk("Section Name \t\t\t", smallBold));
      center.add(new Chunk(selectedStudent.getSectionname(), subFont));
      addEmptyLine(center, 1);

      String dateOfReceipt = "Payment of " + studentfee.getAmountPaid() + "  has been received as part of term payment ";
      if (studentfee.getTerm1paiddate() != null && studentfee.getTerm2paiddate() != null && studentfee.getTerm3paiddate() != null)
      {
        dateOfReceipt = studentfee.getTerm1paiddate() + ", " + studentfee.getTerm2paiddate() + ", " + studentfee.getTerm3paiddate();
      }
      else if (studentfee.getTerm1paiddate() != null && studentfee.getTerm2paiddate() != null && studentfee.getTerm3paiddate() == null)
      {
        dateOfReceipt = studentfee.getTerm1paiddate() + ", " + studentfee.getTerm2paiddate();
      }
      else if (studentfee.getTerm1paiddate() != null && studentfee.getTerm2paiddate() == null && studentfee.getTerm3paiddate() == null)
      {
        dateOfReceipt = studentfee.getTerm1paiddate() + "";
      }

      center.add(new Chunk("Date of receipt \t\t\t", smallBold));
      center.add(new Chunk(dateOfReceipt, subFont));
      addEmptyLine(center, 1);

      String chequeDetails = "Payment of " + studentfee.getAmountPaid() + "  has been received as part of term payment ";
      if (studentfee.getTerm1paiddate() != null && studentfee.getTerm2paiddate() != null && studentfee.getTerm3paiddate() != null)
      {
        chequeDetails = studentfee.getTerm1cheque() + ", " + studentfee.getTerm2cheque() + ", " + studentfee.getTerm3cheque();
      }
      else if (studentfee.getTerm1paiddate() != null && studentfee.getTerm2paiddate() != null && studentfee.getTerm3paiddate() == null)
      {
        chequeDetails = studentfee.getTerm1cheque() + ", " + studentfee.getTerm2cheque();
      }
      else if (studentfee.getTerm1paiddate() != null && studentfee.getTerm2paiddate() == null && studentfee.getTerm3paiddate() == null)
      {
        chequeDetails = studentfee.getTerm1cheque() + "";
      }

      center.add(new Chunk("Cheque details \t\t\t", smallBold));
      center.add(new Chunk(chequeDetails, subFont));
      addEmptyLine(center, 4);
      document.add(center);

      Paragraph footer = new Paragraph();
      footer.setAlignment(Element.ALIGN_CENTER);
      footer.setIndentationLeft(20);
      footer.setIndentationRight(20);
      footer.add(new Chunk("Please Note : This is a system generated receipt and does not require a signature.", smallestItalic));
      document.add(footer);

      PdfContentByte canvas = writer.getDirectContent();
      Rectangle rect = new Rectangle(36, 36, 559, 806);
      rect.setBorder(Rectangle.BOX);
      rect.setBorderWidth(1);
      canvas.rectangle(rect);

      // document.newPage();
      document.close();

      setFile(new DefaultStreamedContent(new FileInputStream(new File(FILE)), "application/pdf"));

      email.addTo(selectedStudent.getEmail());
      email.setFrom("chaitanyavidyalaya@gmail.com", "Mail from Chaitanya Vidyalaya");
      email.setSubject("Fee Receipt of " + selectedStudent.getFullname());
      email.setMsg("Please find the fee receipt attached.");

      email.setHtmlMsg("Please find the fee receipt attached.");
      email.attach(attachment);
      email.send();
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Email sent succesfully to " + selectedStudent.getEmail(), ""));
    }
    catch (EmailException e)
    {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage(), ""));
      e.printStackTrace();
    }
    catch (FileNotFoundException e)
    {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage(), ""));
      e.printStackTrace();
    }
    catch (DocumentException e)
    {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage(), ""));
      e.printStackTrace();
    }
    catch (MalformedURLException e)
    {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage(), ""));
      e.printStackTrace();
    }
    catch (IOException e)
    {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage(), ""));
      e.printStackTrace();
    }
  }

  public void emailfees()
  {
    if (studentfees == null || studentfees.isEmpty())
    {
      generatefees();
      if (studentfees == null || studentfees.isEmpty())
      {
        return;
      }
    }

    ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    String logo = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "images" + File.separator + "logo.gif";
    // Create the attachment
    EmailAttachment attachment = new EmailAttachment();
    attachment.setPath(FILE);
    attachment.setDisposition(EmailAttachment.ATTACHMENT);
    attachment.setDescription("fee recipt");
    attachment.setName("fee.pdf");

    SystemSettingsBean sys = new SystemSettingsBean();

    // Create the email message
    String emailhostname = sys.getSystemSettings().getEmailhostname();
    int smtpPort = Integer.parseInt(sys.getSystemSettings().getSmtpport());
    DefaultAuthenticator defaultAuthenticator = new DefaultAuthenticator(sys.getSystemSettings().getEmail(), sys.getSystemSettings().getPassword());

    try
    {
      for (StudentFee sf : studentfees)
      {
        FileOutputStream fileOutputStream = new FileOutputStream(FILE);
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, fileOutputStream);
        document.open();
        document.addTitle("Fee receipt");
        document.addSubject("Chaitanya Vidyalaya");
        document.addAuthor("Chaitanya Vidyalaya");
        document.addCreator("Chaitanya Vidyalaya");

        Paragraph header = new Paragraph();
        header.setAlignment(Element.ALIGN_CENTER);
        header.setIndentationLeft(20);
        header.setIndentationRight(20);
        header.add(Image.getInstance(logo));
        addEmptyLine(header, 2);

        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setFont(underline18);
        paragraph.add(new Chunk("Fee Receipt :"));
        header.add(paragraph);

        addEmptyLine(header, 1);
        String headerString = "Payment of " + sf.getAmountPaid() + "  has been received as part of term payment ";
        if (sf.getTerm1paiddate() != null && sf.getTerm2paiddate() != null && sf.getTerm3paiddate() != null)
        {
          headerString += "1, 2 and 3 from ";
        }
        else if (sf.getTerm1paiddate() != null && sf.getTerm2paiddate() != null && sf.getTerm3paiddate() == null)
        {
          headerString += "1 and 2 from ";
        }
        else if (sf.getTerm1paiddate() != null && sf.getTerm2paiddate() == null && sf.getTerm3paiddate() == null)
        {
          headerString += "1 from ";
        }

        Paragraph paragraph2 = new Paragraph(headerString, subFont);
        paragraph2.setAlignment(Element.ALIGN_CENTER);
        header.add(paragraph2);
        addEmptyLine(header, 1);
        document.add(header);

        Paragraph center = new Paragraph();
        center.setAlignment(Element.ALIGN_JUSTIFIED);
        center.setIndentationLeft(50);
        center.setIndentationRight(50);
        center.add(new Chunk("Student Name \t\t\t", smallBold));
        center.add(new Chunk(sf.getFullname(), subFont));
        addEmptyLine(center, 1);
        center.add(new Chunk("Class Name \t\t\t", smallBold));
        center.add(new Chunk(sf.getClassname(), subFont));
        addEmptyLine(center, 1);
        center.add(new Chunk("Section Name \t\t\t", smallBold));
        center.add(new Chunk(sf.getSectionname(), subFont));
        addEmptyLine(center, 1);

        String dateOfReceipt = "Payment of " + sf.getAmountPaid() + "  has been received as part of term payment ";
        if (sf.getTerm1paiddate() != null && sf.getTerm2paiddate() != null && sf.getTerm3paiddate() != null)
        {
          dateOfReceipt = sf.getTerm1paiddate() + ", " + sf.getTerm2paiddate() + ", " + sf.getTerm3paiddate();
        }
        else if (sf.getTerm1paiddate() != null && sf.getTerm2paiddate() != null && sf.getTerm3paiddate() == null)
        {
          dateOfReceipt = sf.getTerm1paiddate() + ", " + sf.getTerm2paiddate();
        }
        else if (sf.getTerm1paiddate() != null && sf.getTerm2paiddate() == null && sf.getTerm3paiddate() == null)
        {
          dateOfReceipt = sf.getTerm1paiddate() + "";
        }

        center.add(new Chunk("Date of receipt \t\t\t", smallBold));
        center.add(new Chunk(dateOfReceipt, subFont));
        addEmptyLine(center, 1);

        String chequeDetails = "Payment of " + sf.getAmountPaid() + "  has been received as part of term payment ";
        if (sf.getTerm1paiddate() != null && sf.getTerm2paiddate() != null && sf.getTerm3paiddate() != null)
        {
          chequeDetails = sf.getTerm1cheque() + ", " + sf.getTerm2cheque() + ", " + sf.getTerm3cheque();
        }
        else if (sf.getTerm1paiddate() != null && sf.getTerm2paiddate() != null && sf.getTerm3paiddate() == null)
        {
          chequeDetails = sf.getTerm1cheque() + ", " + sf.getTerm2cheque();
        }
        else if (sf.getTerm1paiddate() != null && sf.getTerm2paiddate() == null && sf.getTerm3paiddate() == null)
        {
          chequeDetails = sf.getTerm1cheque() + "";
        }

        center.add(new Chunk("Cheque details \t\t\t", smallBold));
        center.add(new Chunk(chequeDetails, subFont));
        addEmptyLine(center, 4);
        document.add(center);

        Paragraph footer = new Paragraph();
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setIndentationLeft(20);
        footer.setIndentationRight(20);
        footer.add(new Chunk("Please Note : This is a system generated receipt and does not require a signature.", smallestItalic));
        document.add(footer);

        PdfContentByte canvas = writer.getDirectContent();
        Rectangle rect = new Rectangle(36, 36, 559, 806);
        rect.setBorder(Rectangle.BOX);
        rect.setBorderWidth(1);
        canvas.rectangle(rect);

        // document.newPage();
        document.close();

        setFile(new DefaultStreamedContent(new FileInputStream(new File(FILE)), "application/pdf"));
        HtmlEmail email = new HtmlEmail();
        email.setHostName(emailhostname);
        email.setSmtpPort(smtpPort);
        email.setAuthenticator(defaultAuthenticator);
        email.setSSLOnConnect(true);
        email.addTo(sf.getEmail());
        email.setFrom("chaitanyavidyalaya@gmail.com", "Mail from Chaitanya Vidyalaya");
        email.setSubject("Fee Receipt of " + sf.getFullname());
        email.setMsg("Please find the fee receipt attached.");

        email.setHtmlMsg("Please find the fee receipt attached.");
        email.attach(attachment);
        email.send();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Email sent succesfully to " + sf.getEmail(), ""));
      }

    }
    catch (EmailException e)
    {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage(), ""));
      e.printStackTrace();
    }
    catch (FileNotFoundException e)
    {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage(), ""));
      e.printStackTrace();
    }
    catch (DocumentException e)
    {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage(), ""));
      e.printStackTrace();
    }
    catch (MalformedURLException e)
    {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage(), ""));
      e.printStackTrace();
    }
    catch (IOException e)
    {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage(), ""));
      e.printStackTrace();
    }
  }

  public void generatefees()
  {
    if (selectedStudents == null || selectedStudents.isEmpty())
    {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No students selected", "Select students and then check for receipts."));
      return;
    }
    String p = "";
    selectedStudentIds = new ArrayList<>();
    int size = selectedStudents.size();
    for (int i = 0; i < size; i++)
    {
      selectedStudentIds.add(selectedStudents.get(i).getId());
      p = p + "'" + selectedStudents.get(i).getId() + "'";
      if (i != size - 1)
      {
        p = p + ", ";
      }
    }
    getStudentFees(p);
  }

  public List<Student> getAllAtudents() throws ClassNotFoundException, SQLException
  {
    conn = DBConnection.getConnection();
    ResultSet rs = conn.createStatement().executeQuery("select Id, FirstName, LastName, Class, Section," + "FatherName, FatherOccupation, Phone, DateOfBirth, DateOfJoining,"
        + "MotherName, MotherOccupation, Gender, GaurdianName, Mobile, Email, ProfilePic, houseno, street, city, postalcode, fatherdetails, motherdetails from student where isarchived = '0' order by Class, Section");

    List<Student> students = new ArrayList<>();
    studentMap = new HashMap<>();
    while (rs.next())
    {
      Student st = new Student();
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
      st.setEmail(rs.getString("Email"));
      st.setProfiepic(rs.getString("ProfilePic"));
      st.setHouseno(rs.getString("houseno"));
      st.setStreet(rs.getString("street"));
      st.setCity(rs.getString("city"));
      st.setPostalCode(rs.getString("postalcode"));
      st.setFatheroccupationdetails(rs.getString("fatherdetails"));
      st.setMotheroccupationdetails(rs.getString("motherdetails"));
      students.add(st);

      studentMap.put(rs.getString("Id"), st);
    }
    return students;
  }

  public Collection<String> getClassnames()
  {
    return classnames;
  }

  private String getClassTobePromoted()
  {
    if (selectedClass.equals("Class IX"))
      return "Class X";
    if (selectedClass.equals("Class VIII"))
      return "Class IX";
    if (selectedClass.equals("Class VII"))
      return "Class VIII";
    if (selectedClass.equals("Class VI"))
      return "Class VII";
    if (selectedClass.equals("Class V"))
      return "Class VI";
    if (selectedClass.equals("Class IV"))
      return "Class V";
    if (selectedClass.equals("Class III"))
      return "Class IV";
    if (selectedClass.equals("Class II"))
      return "Class III";
    if (selectedClass.equals("Class I"))
      return "Class II";
    if (selectedClass.equals("UKG"))
      return "Class I";
    if (selectedClass.equals("LKG"))
      return "UKG";
    if (selectedClass.equals("Nursery"))
      return "LKG";
    else
      return "";
  }

  public OutputPanel getFeePanel()
  {
    return feePanel;
  }

  public StreamedContent getFile()
  {
    return file;
  }

  public List<Student> getFilteredStudents()
  {
    return filteredStudents;
  }

  public int getNoofselecteditems()
  {
    if (selectedStudents == null || selectedStudents.isEmpty())
    {
      return 0;
    }
    else
    {
      return selectedStudents.size();
    }
  }

  public String getSelectedClass()
  {
    return selectedClass;
  }

  public Student getSelectedStudent()
  {
    return selectedStudent;
  }

  public String getSelectedStudentId()
  {
    return selectedStudentId;
  }

  public List<String> getSelectedStudentIds()
  {
    return selectedStudentIds;
  }

  public List<Student> getSelectedStudents()
  {
    return selectedStudents;
  }

  public List<String> getSelectedTermOptions()
  {
    return selectedTermOptions;
  }

  public Map<String, String> getStudentbyclass()
  {
    return studentbyclass;
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
      ResultSet rs = conn.createStatement().executeQuery(
          "select feereceiptid, paymentmode, amountpaid, term1, term2, term3, paymentdate, studentid, paymentdetails, bankname, receivedfrom from feepayment where studentid = "
              + "'" + selectedStudentId + "'");

      studentfeeList = new ArrayList<>();
      studentfound = false;
      studentfee = new StudentFee();

      while (rs.next())
      {
        StudentFee sfee = new StudentFee();
        sfee.setReceiptid(rs.getString("feereceiptid"));
        sfee.setEmployeeid(rs.getString("studentid"));
        sfee.setPaymentmode(rs.getString("paymentmode"));
        sfee.setTerm1(rs.getBoolean("term1"));
        sfee.setTerm2(rs.getBoolean("term2"));
        sfee.setTerm3(rs.getBoolean("term3"));
        sfee.setAmountPaid(rs.getDouble("amountpaid"));
        sfee.setPaymentdate(rs.getDate("paymentdate"));
        sfee.setPaymentdetails(rs.getString("paymentdetails"));
        sfee.setBankname(rs.getString("bankname"));
        sfee.setReceivedfrom(rs.getString("receivedfrom"));

        studentfound = true;
        studentfeeList.add(sfee);
        if (sfee.isTerm1())
        {
          studentfee.setTerm1(true);
        }
        if (sfee.isTerm2())
        {
          studentfee.setTerm2(true);
        }
        if (sfee.isTerm3())
        {
          studentfee.setTerm3(true);
        }
      }

      studentfee.setAmount(termamount);
      studentfee.setEmployeeid(selectedStudentId);
      studentfee.setPaymentdate(Calendar.getInstance().getTime());
    }
    catch (ClassNotFoundException | SQLException e)
    {
      e.printStackTrace();
    }
    return studentfee;
  }

  public List<StudentFee> getStudentfeeList()
  {
    return studentfeeList;
  }

  public List<StudentFee> getStudentfees()
  {
    return studentfees;
  }

  private List<StudentFee> getStudentFees(String parameters)
  {
    try
    {
      conn = DBConnection.getConnection();
      PreparedStatement ps = conn.prepareStatement(
          "select feereceiptid, paymentmode, amountpaid, term1, term2, term3, paymentdate, studentid, paymentdetails, bankname, receivedfrom from feepayment where studentid in ("
              + parameters + ") ");
      ResultSet rs = ps.executeQuery();

      studentfees = new ArrayList<>();
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

        Student student = studentMap.get(sf.getEmployeeid());
        sf.setFirstname(student.getFirstname());
        sf.setLastname(student.getLastname());
        sf.setClassname(student.getClassname());
        sf.setSectionname(student.getSectionname());
        sf.setEmail(student.getEmail());

        studentfees.add(sf);
      }
    }
    catch (ClassNotFoundException | SQLException e)
    {
      e.printStackTrace();
    }

    return studentfees;
  }

  public Map<String, Student> getStudentMap()
  {
    return studentMap;
  }

  public List<Student> getStudents()
  {
    return students;
  }

  public List<Student> getStudentsWithFee()
  {
    return studentsWithFee;
  }

  public PdfPTable getTable() throws SQLException, DocumentException, IOException
  {
    PdfPTable table = new PdfPTable(new float[] { 2, 1, 2, 5, 1 });
    table.setWidthPercentage(100f);
    table.getDefaultCell().setUseAscender(true);
    table.getDefaultCell().setUseDescender(true);
    table.getDefaultCell().setBackgroundColor(Color.LIGHT_GRAY);
    for (int i = 0; i < 2; i++)
    {
      table.addCell("Location");
      table.addCell("Time");
      table.addCell("Run Length");
      table.addCell("Title");
      table.addCell("Year");
    }
    table.getDefaultCell().setBackgroundColor(null);
    table.setHeaderRows(2);
    table.setFooterRows(1);

    return table;
  }

  public boolean isShowForm()
  {
    return showForm;
  }

  public boolean isShowPrintButton()
  {
    return showPrintButton;
  }

  public void onClassChange()
  {
    studentbyclass = new HashMap<>();
    for (Student s : studentMap.values())
    {
      if (s.getClassname().equalsIgnoreCase(selectedClass))
      {
        studentbyclass.put(s.getFullname(), s.getId());
      }
    }
    SystemSettingsBean sys = new SystemSettingsBean();
    SystemSettings systemSettings = sys.getSystemSettings();
    Map<String, FeeDetails> feedetailsByclass = systemSettings.getFeedetailsByclass();
    FeeDetails feeDetails = feedetailsByclass.get(selectedClass);
    termamount = feeDetails.getTermamount();
  }

  public void onClassChangePromotion()
  {
    filteredStudents = new ArrayList<>();
    for (Student s : studentMap.values())
    {
      if (s.getClassname().equalsIgnoreCase(selectedClass))
      {
        filteredStudents.add(s);
      }
    }
  }

  public void onStudentChange()
  {
    selectedStudent = studentMap.get(selectedStudentId);
    studentfee = getStudentFee();
    setShowForm(true);
    setShowPrintButton(false);
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

  public void onTermOptionsChange()
  {
    double amount = studentfee.getAmount();
    amount = selectedTermOptions.size() * termamount;
    studentfee.setAmount(amount);
  }

  public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException
  {
    Document pdf = (Document) document;
    pdf.open();
    pdf.setPageSize(PageSize.A4);

    ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    String logo = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "demo" + File.separator + "images" + File.separator + "prime_logo.png";
  }

  public void promote()
  {
    FacesMessage msg = null;
    String p = "";
    int size = selectedStudents.size();
    for (int i = 0; i < size; i++)
    {
      p = p + "'" + selectedStudents.get(i).getId() + "'";
      if (i != size - 1)
      {
        p = p + ", ";
      }
    }
    String newClass = getClassTobePromoted();
    try
    {
      conn = DBConnection.getConnection();
      if (!selectedClass.equals("Class X"))
        conn.createStatement().executeUpdate("UPDATE student set class = " + "'" + newClass + "'" + ", updatedatetime = now() where id in (" + p + ") ");
      else
        conn.createStatement().executeUpdate("UPDATE student set isarchived = '1', updatedatetime = now() where id in (" + p + ") ");

      msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Promotions Done", "");

      selectedStudents = new ArrayList<>();
      getAllAtudents();
      onClassChangePromotion();
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
    FacesContext.getCurrentInstance().addMessage(null, msg);
  }

  public void save() throws ClassNotFoundException, SQLException
  {
    conn = DBConnection.getConnection();
    PreparedStatement ps;

    ps = conn.prepareStatement(
        "INSERT INTO FEEPAYMENT (paymentmode, amountpaid, term1, term2, term3, paymentdate, studentid, paymentdetails, bankname, receivedfrom, createdatetime, updatedatetime )"
            + "VALUES (?,?,?,?,?,?,?,?,?,?, now(), now())");

    studentfee.setTerm1(false);
    studentfee.setTerm2(false);
    studentfee.setTerm3(false);

    for (int i = 0; i < selectedTermOptions.size(); i++)
    {
      if (selectedTermOptions.get(i).equals("term 1"))
        studentfee.setTerm1(true);
      else if (selectedTermOptions.get(i).equals("term 2"))
        studentfee.setTerm2(true);
      else if (selectedTermOptions.get(i).equals("term 3"))
        studentfee.setTerm3(true);
    }

    ps.setString(1, studentfee.getPaymentmode());
    ps.setDouble(2, studentfee.getAmount());
    ps.setBoolean(3, studentfee.isTerm1());
    ps.setBoolean(4, studentfee.isTerm2());
    ps.setBoolean(5, studentfee.isTerm3());
    ps.setDate(6, new java.sql.Date(studentfee.getPaymentdate().getTime()));
    ps.setString(7, studentfee.getEmployeeid());
    ps.setString(8, studentfee.getPaymentdetails());
    ps.setString(9, studentfee.getBankname());
    ps.setString(10, studentfee.getReceivedfrom());

    int rs = ps.executeUpdate();
    FacesMessage msg = null;
    if (rs == 1)
    {
      msg = new FacesMessage("Student fee updated successfully", "");
      setShowPrintButton(true);
      setShowForm(true);
    }
    else
    {
      msg = new FacesMessage("Something went wrong", "Please contant your system administrator.");
    }
    FacesContext.getCurrentInstance().addMessage(null, msg);

    ResultSet r = conn.createStatement().executeQuery("select MAX(feereceiptid) as feereceiptid from FEEPAYMENT");
    while (r.next())
    {
      studentfee.setReceiptid("" + r.getInt("feereceiptid"));
    }
  }

  public String saveSuccess(Student student)
  {

    try
    {
      conn = DBConnection.getConnection();
      PreparedStatement ps = conn.prepareStatement("UPDATE student set FirstName = ?, LastName = ?, Class = ?, " + "Section = ?, FatherName = ?, FatherOccupation = ?, "
          + "Phone = ?, DateOfBirth = ?, DateOfJoining = ?, " + "MotherName = ?, MotherOccupation = ?, Gender = ?, " + "GaurdianName = ?, Mobile = ?, Email = ?, "
          + "ProfilePic = ?, houseno = ?, street = ?, " + "city = ?, postalcode = ?, " + "updatedatetime = now() where Id = ?");

      ps.setString(1, student.getFirstname());
      ps.setString(2, student.getLastname());
      ps.setString(3, student.getClassname());
      ps.setString(4, student.getSectionname());
      ps.setString(5, student.getFathername());
      ps.setString(6, student.getFatheroccupation());
      ps.setString(7, student.getPhone());
      ps.setDate(8, new java.sql.Date(student.getDob().getTime()));
      ps.setDate(9, new java.sql.Date(student.getDoj().getTime()));
      ps.setString(10, student.getMothername());
      ps.setString(11, student.getMotheroccupation());
      ps.setString(12, student.getSex());
      ps.setString(13, student.getGuardianname());
      ps.setString(14, student.getMobile());
      ps.setString(15, student.getEmail());
      ps.setString(16, student.getProfiepic());
      ps.setString(17, student.getHouseno());
      ps.setString(18, student.getStreet());
      ps.setString(19, student.getCity());
      ps.setString(20, student.getPostalCode());
      ps.setString(21, student.getId());

      int rs = ps.executeUpdate();

      FacesMessage msg = null;
      if (rs == 1)
      {
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Student " + student.getFullname() + " has been saved", null);
      }
      else
      {
        msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Something went wrong", "Please contant your system administrator.");
      }
      FacesContext.getCurrentInstance().addMessage(null, msg);
      RequestContext.getCurrentInstance().update("form:allStudents");

    }
    catch (ClassNotFoundException | SQLException e)
    {
      e.printStackTrace();
    }

    return null;
  }

  public void setFeePanel(OutputPanel feePanel)
  {
    this.feePanel = feePanel;
  }

  public void setFile(StreamedContent file)
  {
    this.file = file;
  }

  public void setFilteredStudents(List<Student> filteredStudents)
  {
    this.filteredStudents = filteredStudents;
  }

  public void setNoofselecteditems(int noofselecteditems)
  {
    this.noofselecteditems = noofselecteditems;
  }

  public void setSelectedClass(String selectedClass)
  {
    this.selectedClass = selectedClass;
  }

  public void setSelectedStudent(Student selectedStudent)
  {
    this.selectedStudent = selectedStudent;
  }

  public void setSelectedStudentId(String selectedStudentId)
  {
    this.selectedStudentId = selectedStudentId;
  }

  public void setSelectedStudentIds(List<String> selectedStudentIds)
  {
    this.selectedStudentIds = selectedStudentIds;
  }

  public void setSelectedStudents(List<Student> selectedStudents)
  {
    this.selectedStudents = selectedStudents;
  }

  public void setSelectedTermOptions(List<String> selectedTermOptions)
  {
    this.selectedTermOptions = selectedTermOptions;
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

  public void setStudentbyclass(Map<String, String> studentbyclass)
  {
    this.studentbyclass = studentbyclass;
  }

  public void setStudentfee(StudentFee studentfee)
  {
    this.studentfee = studentfee;
  }

  public void setStudentfeeList(List<StudentFee> studentfeeList)
  {
    this.studentfeeList = studentfeeList;
  }

  public void setStudentfees(List<StudentFee> studentfees)
  {
    this.studentfees = studentfees;
  }

  public void setStudentMap(Map<String, Student> studentMap)
  {
    this.studentMap = studentMap;
  }

  public void setStudentsWithFee(List<Student> studentsWithFee)
  {
    this.studentsWithFee = studentsWithFee;
  }
}
