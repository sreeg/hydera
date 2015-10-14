package com.sree.school;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.PostConstruct;

public class Staff extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static LinkedHashMap<String, String> category, categoryInverse;
	private String serialnnumber;
	private double joiningsalary;
	private String designation;
	private List<String> subjectsTaught;
	private String spouseName;
	private String spouseOccupation;
	private String categoryid;
	@SuppressWarnings("unused")
	private String categoryName;

	static {
		category = new LinkedHashMap<String, String>();
		category.put("Permanent", "1");
		category.put("Domestic", "2");
		category.put("Part Time", "3");
	}
	static {
		categoryInverse = new LinkedHashMap<String, String>();
		categoryInverse.put("1","Permanent");
		categoryInverse.put("2","Domestic");
		categoryInverse.put("3","Part Time");
	}

	public String getSerialnumber() {
		return serialnnumber;
	}

	public void setSerialnumber(String registrationnumber) {
		this.serialnnumber = registrationnumber;
	}

	public double getJoiningsalary() {
		return joiningsalary;
	}

	public void setJoiningsalary(double joiningsalary) {
		this.joiningsalary = joiningsalary;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public List<String> getSubjectsTaught() {
		return subjectsTaught;
	}

	public void setSubjectsTaught(List<String> subjectsTaught) {
		this.subjectsTaught = subjectsTaught;
	}

	public String getSpouseName() {
		return spouseName;
	}

	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}

	public String getSpouseOccupation() {
		return spouseOccupation;
	}

	public void setSpouseOccupation(String spouseOccupation) {
		this.spouseOccupation = spouseOccupation;
	}

	public String getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
		setCategoryName(categoryInverse.get(this.categoryid));
	}

	public LinkedHashMap<String, String> getCategory() {
		return category;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
