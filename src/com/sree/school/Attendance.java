package com.sree.school;

public class Attendance extends Staff {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int dayspresent;
	private String month;
	private String year;

	public int getDayspresent() {
		return dayspresent;
	}

	public void setDayspresent(int dayspresent) {
		this.dayspresent = dayspresent;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

}
