package com.sree.school;

import java.io.Serializable;
import java.util.Date;

public class StudentFee extends Student implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -906906131662631254L;
	private String employeeid;
	private double amount;
	private double amountPaid;
	private String cheque;
	private double term1amount;

	private String term1cheque;

	private Date term1paiddate;

	private double term2amount;

	private String term2cheque;

	private Date term2paiddate;

	private double term3amount;

	private String term3cheque;

	private Date term3paiddate;
	
    private boolean term1;  
    private boolean term2;
    private boolean term3;
    
	public boolean isTerm1() {
		return term1;
	}

	public void setTerm1(boolean term1) {
		this.term1 = term1;
	}

	public boolean isTerm2() {
		return term2;
	}

	public void setTerm2(boolean term2) {
		this.term2 = term2;
	}

	public boolean isTerm3() {
		return term3;
	}

	public void setTerm3(boolean term3) {
		this.term3 = term3;
	}

	public String getEmployeeid() {
		return employeeid;
	}

	public double getTerm1amount() {
		return term1amount;
	}

	public String getTerm1cheque() {
		return term1cheque;
	}

	public Date getTerm1paiddate() {
		return term1paiddate;
	}

	public double getTerm2amount() {
		return term2amount;
	}

	public String getTerm2cheque() {
		return term2cheque;
	}

	public Date getTerm2paiddate() {
		return term2paiddate;
	}

	public double getTerm3amount() {
		return term3amount;
	}

	public String getTerm3cheque() {
		return term3cheque;
	}

	public Date getTerm3paiddate() {
		return term3paiddate;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}
	public void setTerm1amount(double term1amount) {
		this.term1amount = term1amount;
	}
	public void setTerm1cheque(String term1cheque) {
		this.term1cheque = term1cheque;
	}
	public void setTerm1paiddate(Date term1paiddate) {
		this.term1paiddate = term1paiddate;
	}

	public void setTerm2amount(double term2amount) {
		this.term2amount = term2amount;
	}
	public void setTerm2cheque(String term2cheque) {
		this.term2cheque = term2cheque;
	}
	public void setTerm2paiddate(Date term2paiddate) {
		this.term2paiddate = term2paiddate;
	}

	public void setTerm3amount(double term3amount) {
		this.term3amount = term3amount;
	}
	public void setTerm3cheque(String term3cheque) {
		this.term3cheque = term3cheque;
	}
	public void setTerm3paiddate(Date term3paiddate) {
		this.term3paiddate = term3paiddate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCheque() {
		return cheque;
	}

	public void setCheque(String cheque) {
		this.cheque = cheque;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}
}
