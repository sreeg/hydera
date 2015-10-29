package com.sree.school;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Student extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fathername;
	private String mothername;
	private String guardianname;
	private String classname;
	private String sectionname;
	private String fatheroccupation;
	private String motheroccupation;
	private String registrationnumber;

	public static LinkedHashMap<String, String> classes;

	static {
		classes = new LinkedHashMap<String, String>();
		classes.put("Class X", "Class-X");
		classes.put("Class IX", "Class-IX");
		classes.put("Class VIII", "Class-VIII");
		classes.put("Class VII", "Class-VII");
		classes.put("Class VI", "Class-VI");
		classes.put("Class V", "Class-V");
		classes.put("Class IV", "Class-IV");
		classes.put("Class III", "Class-III");
		classes.put("Class II", "Class-II");
		classes.put("Class I", "Class-I");
		classes.put("Class UKG", "Class-UKG");
		classes.put("Class LKG", "Class-LKG");
		classes.put("Class NURSERY", "Class-NURSERY");
	}

	private static LinkedHashMap<String, String> sections;

	static {
		sections = new LinkedHashMap<String, String>();
		sections.put("Section A", "Section-A");
		sections.put("Section B", "Section-B");
		sections.put("Section C", "Section-C");
	}

	public String getClassname() {
		return classname;
	}

	public String getFathername() {
		return fathername;
	}

	public String getFatheroccupation() {
		return fatheroccupation;
	}

	public String getGuardianname() {
		return guardianname;
	}

	public String getMothername() {
		return mothername;
	}

	public String getMotheroccupation() {
		return motheroccupation;
	}

	public String getSectionname() {
		return sectionname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public void setFathername(String fathername) {
		this.fathername = fathername;
	}

	public void setFatheroccupation(String fatheroccupation) {
		this.fatheroccupation = fatheroccupation;
	}

	public void setGuardianname(String gaurdianname) {
		this.guardianname = gaurdianname;
	}

	public void setMothername(String mothername) {
		this.mothername = mothername;
	}

	public void setMotheroccupation(String motheroccupation) {
		this.motheroccupation = motheroccupation;
	}

	public void setSectionname(String sectionname) {
		this.sectionname = sectionname;
	}

	public String getRegistrationnumber() {
		return registrationnumber;
	}

	public void setRegistrationnumber(String registrationnumber) {
		this.registrationnumber = registrationnumber;
	}

	public LinkedHashMap<String, String> getClasses() {
		return classes;
	}

	public HashMap<String, String> getSections() {
		return sections;
	}
}
