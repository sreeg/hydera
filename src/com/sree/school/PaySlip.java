package com.sree.school;

import java.io.Serializable;
import java.util.Date;

public class PaySlip extends Salary  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8645764245616466123L;
	private String month;
	private String Year;
	private String designation;
	private Date doj;

	private int dayspresent;
	private int daysinmonth;
	private double factor = 1.0f;
	
	public int getDaysinmonth() {
		return daysinmonth;
	}

	public int getDayspresent() {
		return dayspresent;
	}

	public String getMonth() {
		return month;
	}

	public String getYear() {
		return Year;
	}

	public void setDaysinmonth(int daysinmonth) {
		this.daysinmonth = daysinmonth;
	}

	public void setDayspresent(int dayspresent) {
		this.dayspresent = dayspresent;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public void setYear(String year) {
		Year = year;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Date getDoj() {
		return doj;
	}

	public void setDoj(Date doj) {
		this.doj = doj;
	}

	public double getFactor() {
		return factor;
	}

	public void setFactor(double factor) {
		this.factor = factor;
	}
}
