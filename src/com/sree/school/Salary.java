package com.sree.school;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class Salary  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3784360127530452986L;
	private static LinkedHashMap<String, String> categoryInverse;
	private String employeeid;
	private Double basicsalary;
	private Double fixedda;
	private Double hra;
	private Double conveyanceall;
	private String pfno;
	private String sbacno;
	private Double pfrate;
	private Double proftaxdeduction;
	private Double otherdeduction;
	private Double pfamount;
	private Double loanamount;
	private String employeename;
	private String categoryname;
	private Double grosssalary;
	private Double totaldeductions;
	private Double netsalary;
	private double factor = 1.0f;
	
	static {
		categoryInverse = new LinkedHashMap<String, String>();
		categoryInverse.put("1","Permanent");
		categoryInverse.put("2","Domestic");
		categoryInverse.put("3","Part Time");
	}
	
	public Double getHra() {
		return hra;
	}

	public void setHra(Double hra) {
		this.hra = hra;
	}

	public Double getConveyanceall() {
		return conveyanceall == null ? null : getFactor() * conveyanceall;
	}

	public void setConveyanceall(Double conveyanceall) {
		this.conveyanceall = conveyanceall;
	}

	public String getPfno() {
		return pfno;
	}

	public void setPfno(String pfno) {
		this.pfno = pfno;
	}

	public String getSbacno() {
		return sbacno;
	}

	public void setSbacno(String sbacno) {
		this.sbacno = sbacno;
	}

	public Double getPfrate() {
		return pfrate;
	}

	public void setPfrate(Double pfrate) {
		this.pfrate = pfrate;
	}

	public Double getProftaxdeduction() {
		return proftaxdeduction;
	}

	public void setProftaxdeduction(Double proftaxdeduction) {
		this.proftaxdeduction = proftaxdeduction;
	}

	public Double getOtherdeduction() {
		return otherdeduction;
	}

	public void setOtherdeduction(Double otherdeduction) {
		this.otherdeduction = otherdeduction;
	}

	public Double getPfamount() {
		return pfamount;
	}

	public void setPfamount(Double pfamount) {
		this.pfamount = pfamount;
	}

	public Double getLoanamount() {
		return loanamount;
	}

	public void setLoanamount(Double loanamount) {
		this.loanamount = loanamount;
	}

	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}

	public Double getBasicsalary() {
		return basicsalary == null ? null : getFactor() * basicsalary;
	}

	public void setBasicsalary(Double basicsalary) {
		this.basicsalary = basicsalary;
	}

	public Double getFixedda() {
		return fixedda == null ? null : getFactor() * fixedda;
	}

	public void setFixedda(Double fixedda) {
		this.fixedda = fixedda;
	}

	public String getEmployeename() {
		return employeename;
	}

	public void setEmployeename(String employeename) {
		this.employeename = employeename;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryInverse.get(categoryname);
	}

	public Double getGrosssalary() {
		grosssalary = getBasicsalary() + getFixedda() + getHra() + getConveyanceall();
		return grosssalary;
	}

	public void setGrosssalary(Double grosssalary) {
		this.grosssalary = grosssalary;
	}

	public Double getTotaldeductions() {
		totaldeductions = getPfamount() + getOtherdeduction() + getLoanamount() + getProftaxdeduction();
		return totaldeductions;
	}

	public void setTotaldeductions(Double totaldeductions) {
		this.totaldeductions = totaldeductions;
	}

	public Double getNetsalary() {
		netsalary = getGrosssalary() - getTotaldeductions();
		return netsalary;
	}

	public void setNetsalary(Double netsalary) {
		this.netsalary = netsalary;
	}

	public double getFactor() {
		return factor;
	}

	public void setFactor(double factor) {
		this.factor = factor;
	}
}
