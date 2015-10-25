package com.sree.school;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Categories implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7664291519948701235L;
	private List<PaySlip> payslips = new ArrayList<>();
	private String name;
	private double totalBasicSalary;
	private double totalConveyanceAll;
	private double totalFixedDA;

	private double totalHRA;
	private double totalLoanAmount;
	private double totalOtherDeductions;

	private double totalPFAmount;

	private double totalProfTaxDeduction;

	public double getTotalBasicSalary() {
		for (PaySlip p : payslips) {
			totalBasicSalary += p.getBasicsalary();
		}
		return getDoubleValue(totalBasicSalary, 2);
	}

	public void setTotalBasicSalary(double totalBasicSalary) {
		this.totalBasicSalary = totalBasicSalary;
	}

	public double getTotalConveyanceAll() {
		for (PaySlip p : payslips) {
			totalConveyanceAll += p.getConveyanceall();
		}
		return getDoubleValue(totalConveyanceAll, 2);
	}

	public void setTotalConveyanceAll(double totalConveyanceAll) {
		this.totalConveyanceAll = totalConveyanceAll;
	}

	public double getTotalFixedDA() {
		for (PaySlip p : payslips) {
			totalFixedDA += p.getFixedda();
		}
		return getDoubleValue(totalFixedDA, 2);
	}

	public void setTotalFixedDA(double totalFixedDA) {
		this.totalFixedDA = totalFixedDA;
	}

	public double getTotalHRA() {
		for (PaySlip p : payslips) {
			totalHRA += p.getHra();
		}
		return getDoubleValue(totalHRA, 2);
	}

	public void setTotalHRA(double totalHRA) {
		this.totalHRA = totalHRA;
	}

	public double getTotalLoanAmount() {
		for (PaySlip p : payslips) {
			totalLoanAmount += p.getLoanamount();
		}
		return getDoubleValue(totalLoanAmount, 2);
	}

	public void setTotalLoanAmount(double totalLoanAmount) {
		this.totalLoanAmount = totalLoanAmount;
	}

	public double getTotalOtherDeductions() {
		for (PaySlip p : payslips) {
			totalOtherDeductions += p.getOtherdeduction();
		}
		return getDoubleValue(totalOtherDeductions, 2);
	}

	public void setTotalOtherDeductions(double totalOtherDeductions) {
		this.totalOtherDeductions = totalOtherDeductions;
	}

	public double getTotalPFAmount() {
		for (PaySlip p : payslips) {
			totalPFAmount += p.getPfamount();
		}
		return getDoubleValue(totalPFAmount, 2);
	}

	public void setTotalPFAmount(double totalPFAmount) {
		this.totalPFAmount = totalPFAmount;
	}

	public double getTotalProfTaxDeduction() {
		for (PaySlip p : payslips) {
			totalProfTaxDeduction += p.getProftaxdeduction();
		}
		return getDoubleValue(totalProfTaxDeduction, 2);
	}

	public void setTotalProfTaxDeduction(double totalProfTaxDeduction) {
		this.totalProfTaxDeduction = totalProfTaxDeduction;
	}

	public Categories(String name) {
		this.name = name;
	}

	public List<PaySlip> getTeams() {
		return payslips;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PaySlip> getPayslips() {
		return payslips;
	}

	public void setPayslips(List<PaySlip> payslips) {
		this.payslips = payslips;
	}

	public double getDoubleValue(double value, int scale) {
		return new BigDecimal(value).setScale(scale, RoundingMode.HALF_UP).doubleValue();
	}
}