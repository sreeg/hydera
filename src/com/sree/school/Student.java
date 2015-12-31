package com.sree.school;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Student extends User
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public static LinkedHashMap<String, String> classes;

  static
  {
    classes = new LinkedHashMap<String, String>();
    classes.put("Class X", "Class X");
    classes.put("Class IX", "Class IX");
    classes.put("Class VIII", "Class VIII");
    classes.put("Class VII", "Class VII");
    classes.put("Class VI", "Class VI");
    classes.put("Class V", "Class V");
    classes.put("Class IV", "Class IV");
    classes.put("Class III", "Class III");
    classes.put("Class II", "Class II");
    classes.put("Class I", "Class I");
    classes.put("UKG", "UKG");
    classes.put("LKG", "LKG");
    classes.put("Nursery", "Nursery");
  }

  private static LinkedHashMap<String, String> sections;

  static
  {
    sections = new LinkedHashMap<String, String>();
    sections.put("Section A", "Section-A");
    sections.put("Section B", "Section-B");
    sections.put("Section C", "Section-C");
  }

  private String fathername;
  private String mothername;
  private String guardianname;
  private String classname;
  private String sectionname;

  private String fatheroccupation;

  private String fatheroccupationdetails;

  private String motheroccupation;

  private String motheroccupationdetails;

  private String registrationnumber;

  public LinkedHashMap<String, String> getClasses()
  {
    return classes;
  }

  public String getClassname()
  {
    return classname;
  }

  public String getFathername()
  {
    return fathername;
  }

  public String getFatheroccupation()
  {
    return fatheroccupation;
  }

  public String getFatheroccupationdetails()
  {
    return fatheroccupationdetails;
  }

  public String getGuardianname()
  {
    return guardianname;
  }

  public String getMothername()
  {
    return mothername;
  }

  public String getMotheroccupation()
  {
    return motheroccupation;
  }

  public String getMotheroccupationdetails()
  {
    return motheroccupationdetails;
  }

  public String getRegistrationnumber()
  {
    return registrationnumber;
  }

  public String getSectionname()
  {
    return sectionname;
  }

  public HashMap<String, String> getSections()
  {
    return sections;
  }

  public void setClassname(String classname)
  {
    this.classname = classname;
  }

  public void setFathername(String fathername)
  {
    this.fathername = fathername;
  }

  public void setFatheroccupation(String fatheroccupation)
  {
    this.fatheroccupation = fatheroccupation;
  }

  public void setFatheroccupationdetails(String fatheroccupationdetails)
  {
    this.fatheroccupationdetails = fatheroccupationdetails;
  }

  public void setGuardianname(String gaurdianname)
  {
    this.guardianname = gaurdianname;
  }

  public void setMothername(String mothername)
  {
    this.mothername = mothername;
  }

  public void setMotheroccupation(String motheroccupation)
  {
    this.motheroccupation = motheroccupation;
  }

  public void setMotheroccupationdetails(String motheroccupationdetails)
  {
    this.motheroccupationdetails = motheroccupationdetails;
  }

  public void setRegistrationnumber(String registrationnumber)
  {
    this.registrationnumber = registrationnumber;
  }

  public void setSectionname(String sectionname)
  {
    this.sectionname = sectionname;
  }
}
