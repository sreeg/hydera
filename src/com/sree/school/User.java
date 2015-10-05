package com.sree.school;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import org.primefaces.model.UploadedFile;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String firstname;
	private String lastname;
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

	private static HashMap<String, String> gender;

	static {
		gender = new HashMap<String, String>();
		gender.put("Male", "Male");
		gender.put("Female", "Female");
	}

	public Integer getAge() {
		return age;
	}

	public Date getDoj() {
		return doj;
	}

	public String getCity() {
		return city;
	}

	public Date getDob() {
		return dob;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getInfo() {
		return info;
	}

	public String getLastname() {
		return lastname;
	}

	public String getPhone() {
		return phone;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getSex() {
		return sex;
	}

	public String getStreet() {
		return street;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setDoj(Date doj) {
		this.doj = doj;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouseno() {
		return houseno;
	}

	public void setHouseno(String houseno) {
		this.houseno = houseno;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public HashMap<String, String> getGender() {
		return gender;
	}

	public String getProfiepic() {
		return profiepic;
	}

	public void setProfiepic(String profiepic) {
		this.profiepic = profiepic;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}
}
