package com.sree.school;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;

@ManagedBean(name = "salaryView")
@ViewScoped
public class SalaryView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Salary> salaries;
	
	private double totalBasicSalary;
	private double totalFixedDA;
	private double totalHRA;
	private double totalConveyanceAll;
	private double totalProfTaxDeduction;
	private double totalOtherDeductions;
	private double totalPFAmount;
	private double totalLoanAmount;
	
	public SalaryView() {
		salaries = getAllSalaryStaff();
	}

	public List<Salary> getSalaries() {
		return salaries;
	}

	public void setSalaries(List<Salary> salaries) {
		this.salaries = salaries;
	}

	public List<Salary> getAllSalaryStaff() {
		java.sql.Connection conn;
		List<Salary> students = new ArrayList<>();

		try {
			conn = DBConnection.getConnection();
			ResultSet rs = conn.createStatement()
					.executeQuery("select employeeid, basicsalary, fixedda, hra, conveyanceall,"
							+ "pfno, sbacno, pfrate, proftaxdeduction, otherdeduction, modeofpayment, staff.firstname, staff.lastname,staff.categoryid,"
							+ "pfamount, loanamount from salary INNER JOIN staff ON salary.employeeid=staff.Id");
			totalBasicSalary =0;
			while (rs.next()) {
				Salary st = new Salary();
				st.setEmployeeid(rs.getString("employeeid"));
				st.setBasicsalary(rs.getDouble("basicsalary"));
				st.setFixedda(rs.getDouble("fixedda"));
				st.setHra(rs.getDouble("hra"));
				st.setConveyanceall(rs.getDouble("conveyanceall"));
				st.setPfno(rs.getString("pfno"));
				st.setSbacno(rs.getString("sbacno"));
				st.setPfrate(rs.getDouble("pfrate"));
				st.setProftaxdeduction(rs.getDouble("proftaxdeduction"));
				st.setOtherdeduction(rs.getDouble("otherdeduction"));
				st.setEmployeename(rs.getString("staff.firstname") + " " + rs.getString("staff.lastname"));
				st.setCategoryname(rs.getString("staff.categoryid"));
				st.setPfamount(rs.getDouble("pfamount"));
				st.setLoanamount(rs.getDouble("loanamount"));
				st.setModeofpayment(rs.getString("modeofpayment"));
				
				students.add(st);
				totalBasicSalary += st.getBasicsalary(); 
				totalFixedDA+= st.getFixedda();
				totalHRA += st.getHra();
				totalConveyanceAll += st.getConveyanceall();
				totalProfTaxDeduction += st.getProftaxdeduction();
				totalOtherDeductions += st.getOtherdeduction();
				totalPFAmount += st.getPfamount();
				totalLoanAmount += st.getLoanamount();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return students;
	}

	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
		Document pdf = (Document) document;

		pdf.setMargins(-70, -70, 10, 10);
		pdf.setPageSize(PageSize.A4.rotate());
		
		pdf.setMarginMirroring(true);
		pdf.open();

		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String logo = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "images"
				+ File.separator + "logo.gif";

		Image instance = Image.getInstance(logo);
		instance.setAlignment(Element.ALIGN_CENTER);
		pdf.add(instance);
		pdf.add(new Phrase("\n"));
	}

	public double getTotalBasicSalary() {
		return totalBasicSalary;
	}

	public void setTotalBasicSalary(double totalBasicSalary) {
		this.totalBasicSalary = totalBasicSalary;
	}

	public double getTotalHRA() {
		return totalHRA;
	}

	public void setTotalHRA(double totalHRA) {
		this.totalHRA = totalHRA;
	}

	public double getTotalFixedDA() {
		return totalFixedDA;
	}

	public void setTotalFixedDA(double totalFixedDA) {
		this.totalFixedDA = totalFixedDA;
	}

	public double getTotalConveyanceAll() {
		return totalConveyanceAll;
	}

	public void setTotalConveyanceAll(double totalConveyanceAll) {
		this.totalConveyanceAll = totalConveyanceAll;
	}

	public double getTotalProfTaxDeduction() {
		return totalProfTaxDeduction;
	}

	public void setTotalProfTaxDeduction(double totalProfTaxDeduction) {
		this.totalProfTaxDeduction = totalProfTaxDeduction;
	}

	public double getTotalOtherDeductions() {
		return totalOtherDeductions;
	}

	public void setTotalOtherDeductions(double totalOtherDeductions) {
		this.totalOtherDeductions = totalOtherDeductions;
	}

	public double getTotalPFAmount() {
		return totalPFAmount;
	}

	public void setTotalPFAmount(double totalPFAmount) {
		this.totalPFAmount = totalPFAmount;
	}

	public double getTotalLoanAmount() {
		return totalLoanAmount;
	}

	public void setTotalLoanAmount(double totalLoanAmount) {
		this.totalLoanAmount = totalLoanAmount;
	}

}