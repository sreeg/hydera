package com.sree.hydera;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class User implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private static HashMap<String, String> gender;

  static
  {
    gender = new HashMap<String, String>();
    gender.put("Male", "Male");
    gender.put("Female", "Female");
  }

  private String firstname;
  private String lastname;
  private String fullname;
  private String sex;

  private Date dob;

  private String Id;
  private Integer age;
  private String houseno;
  private String street;
  private String city;
  private String postalCode;
  private String info;
  private String email;
  private String phone;
  private String mobile;
  private Date doj;

  private String profiepic;

  private boolean isarchived;

  public Integer getAge()
  {
    return age;
  }

  public String getCity()
  {
    return city;
  }

  public Date getDob()
  {
    return dob;
  }

  public Date getDoj()
  {
    return doj;
  }

  public String getEmail()
  {
    return email;
  }

  public String getFirstname()
  {
    return firstname;
  }

  public String getFullname()
  {
    return firstname + " " + lastname;
  }

  public HashMap<String, String> getGender()
  {
    return gender;
  }

  public String getHouseno()
  {
    return houseno;
  }

  public String getId()
  {
    return Id;
  }

  public String getInfo()
  {
    return info;
  }

  public String getLastname()
  {
    return lastname;
  }

  public String getMobile()
  {
    return mobile;
  }

  public String getPhone()
  {
    return phone;
  }

  public String getPostalCode()
  {
    return postalCode;
  }

  public String getProfiepic()
  {
    return profiepic;
  }

  public String getSex()
  {
    return sex;
  }

  public String getStreet()
  {
    return street;
  }

  public boolean isIsarchived()
  {
    return isarchived;
  }

  public void setAge(Integer age)
  {
    this.age = age;
  }

  public void setCity(String city)
  {
    this.city = city;
  }

  public void setDob(Date dob)
  {
    this.dob = dob;
  }

  public void setDoj(Date doj)
  {
    this.doj = doj;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public void setFirstname(String firstname)
  {
    this.firstname = firstname;
  }

  public void setFullname(String fullname)
  {
    this.fullname = fullname;
  }

  public void setHouseno(String houseno)
  {
    this.houseno = houseno;
  }

  public void setId(String id)
  {
    Id = id;
  }

  public void setInfo(String info)
  {
    this.info = info;
  }

  public void setIsarchived(boolean isarchived)
  {
    this.isarchived = isarchived;
  }

  public void setLastname(String lastname)
  {
    this.lastname = lastname;
  }

  public void setMobile(String mobile)
  {
    this.mobile = mobile;
  }

  public void setPhone(String phone)
  {
    this.phone = phone;
  }

  public void setPostalCode(String postalCode)
  {
    this.postalCode = postalCode;
  }

  public void setProfiepic(String profiepic)
  {
    this.profiepic = profiepic;
  }

  public void setSex(String sex)
  {
    this.sex = sex;
  }

  public void setStreet(String street)
  {
    this.street = street;
  }
}
