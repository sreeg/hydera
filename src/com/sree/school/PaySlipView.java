package com.sree.school;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
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

@ManagedBean(name = "payslipView")
@ViewScoped
public class PaySlipView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double totalBasicSalary;
	private double totalFixedDA;
	private double totalHRA;
	private double totalConveyanceAll;
	private double totalProfTaxDeduction;
	private double totalOtherDeductions;
	private double totalPFAmount;
	private double totalLoanAmount;
	private List<PaySlip> payslips;
	private boolean showForm;
	private boolean disableGenerateButton;

	private static LinkedHashMap<String, String> monthMap;
	private static LinkedHashMap<String, String> yearMap = new LinkedHashMap<String, String>();
	private static LinkedHashMap<String, String> monthmapfromdb = new LinkedHashMap<String, String>();

	private static LinkedHashMap<String, Integer> monthDaysMap;

	private String currentMonth;
	private String currentYear;
	private String selectedyear;
	private String selectedmonth;
	private int daysincurrentmonth;
	private int daysinselectedmonth;
	SimpleDateFormat month_date = new SimpleDateFormat("MMMM");

	static {
		monthMap = new LinkedHashMap<String, String>();
		monthMap.put("January", "January");
		monthMap.put("February", "February");
		monthMap.put("March", "March");
		monthMap.put("April", "April");
		monthMap.put("May", "May");
		monthMap.put("May", "June");
		monthMap.put("July", "July");
		monthMap.put("August", "August");
		monthMap.put("September", "September");
		monthMap.put("October", "October");
		monthMap.put("November", "November");
		monthMap.put("December", "December");
	}

	static {
		monthDaysMap = new LinkedHashMap<String, Integer>();
		monthDaysMap.put("January", 31);
		monthDaysMap.put("February", 28);
		monthDaysMap.put("March", 31);
		monthDaysMap.put("April", 30);
		monthDaysMap.put("May", 31);
		monthDaysMap.put("May", 30);
		monthDaysMap.put("July", 31);
		monthDaysMap.put("August", 31);
		monthDaysMap.put("September", 30);
		monthDaysMap.put("October", 31);
		monthDaysMap.put("November", 30);
		monthDaysMap.put("December", 31);
	}

	public PaySlipView() {
		Calendar now = Calendar.getInstance();
		setCurrentMonth(month_date.format(now.getTime()));
		setCurrentYear("" + now.get(Calendar.YEAR));
		setDaysincurrentmonth(now.getActualMaximum(Calendar.DATE));

		yearMap.put(currentYear, currentYear);
		setSelectedyear(getCurrentYear());
		setSelectedmonth(getCurrentMonth());
		setDaysinselectedmonth(monthDaysMap.get(getCurrentMonth()));
		disableGenerateButton = false;
	}

	public List<PaySlip> getAllSalaryStaffByMonthAndYear() {
		java.sql.Connection conn;
		List<PaySlip> paySlips = new ArrayList<>();

		try {
			conn = DBConnection.getConnection();
			ResultSet rs = conn.createStatement()
					.executeQuery("select employeeid, basicsalary, fixedda, hra, conveyanceall,"
							+ "pfno, sbacno, pfrate, proftaxdeduction, otherdeduction,staff.firstname, staff.lastname,staff.categoryid,staff.designation,staff.DateOfJoining,"
							+ "pfamount, loanamount, attendance.dayspresent, attendance.daysinmonth from salary "
							+ "LEFT JOIN attendance ON salary.employeeid=attendance.staffid and attendance.year=" + "'"
							+ getSelectedyear() + "'" + " and attendance.month = " + "'" + getSelectedmonth() + "'"
							+ "LEFT JOIN staff ON salary.employeeid=staff.Id");
			totalBasicSalary = 0;
			while (rs.next()) {
				PaySlip ps = new PaySlip();
				ps.setEmployeeid(rs.getString("employeeid"));
				ps.setBasicsalary(rs.getDouble("basicsalary"));
				ps.setFixedda(rs.getDouble("fixedda"));
				ps.setHra(rs.getDouble("hra"));
				ps.setConveyanceall(rs.getDouble("conveyanceall"));
				ps.setPfno(rs.getString("pfno"));
				ps.setSbacno(rs.getString("sbacno"));
				ps.setPfrate(rs.getDouble("pfrate"));
				ps.setProftaxdeduction(rs.getDouble("proftaxdeduction"));
				ps.setOtherdeduction(rs.getDouble("otherdeduction"));
				ps.setEmployeename(rs.getString("staff.firstname") + " " + rs.getString("staff.lastname"));
				ps.setCategoryname(rs.getString("staff.categoryid"));
				ps.setPfamount(rs.getDouble("pfamount"));
				ps.setLoanamount(rs.getDouble("loanamount"));
				ps.setDaysinmonth(rs.getInt("daysinmonth"));
				ps.setDayspresent(rs.getInt("dayspresent"));
				ps.setMonth(getSelectedmonth());
				ps.setYear(getSelectedyear());
				ps.setDesignation(rs.getString("designation"));
				ps.setDoj(rs.getDate("DateOfJoining"));
				int dayspresent = ps.getDayspresent();

				if (dayspresent == 0) {
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Cannot generate payslips for " + selectedmonth + ", " + selectedyear,
							"Enter attendance details first.");
					FacesContext.getCurrentInstance().addMessage(null, msg);
					return paySlips;
				}

				Double factor = (double) ((1.0f * dayspresent) / ps.getDaysinmonth());
				BigDecimal bd = new BigDecimal(factor);
				bd = bd.setScale(4, RoundingMode.HALF_UP);

				ps.setFactor(bd.doubleValue());
				paySlips.add(ps);

				totalBasicSalary += ps.getBasicsalary();
				totalFixedDA += ps.getFixedda();
				totalHRA += ps.getHra();
				totalConveyanceAll += ps.getConveyanceall();
				totalProfTaxDeduction += ps.getProftaxdeduction();
				totalOtherDeductions += ps.getOtherdeduction();
				totalPFAmount += ps.getPfamount();
				totalLoanAmount += ps.getLoanamount();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return paySlips;
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

	public String getCurrentMonth() {
		return currentMonth;
	}

	public void setCurrentMonth(String currentMonth) {
		this.currentMonth = currentMonth;
	}

	public String getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(String currentYear) {
		this.currentYear = currentYear;
	}

	public int getDaysincurrentmonth() {
		return daysincurrentmonth;
	}

	public void setDaysincurrentmonth(int daysincurrentmonth) {
		this.daysincurrentmonth = daysincurrentmonth;
	}

	public LinkedHashMap<String, String> getYearMap() {
		return yearMap;
	}

	public void setYearMap(LinkedHashMap<String, String> yearMap) {
		PaySlipView.yearMap = yearMap;
	}

	public String getSelectedyear() {
		return selectedyear;
	}

	public void setSelectedyear(String selectedyear) {
		this.selectedyear = selectedyear;
	}

	public String getSelectedmonth() {
		return selectedmonth;
	}

	public void setSelectedmonth(String selectedmonth) {
		this.selectedmonth = selectedmonth;
	}

	public LinkedHashMap<String, String> getMonthMap() {
		return monthMap;
	}

	public void setMonthMap(LinkedHashMap<String, String> monthMap) {
		PaySlipView.monthMap = monthMap;
	}

	public void onYearChange() {
		System.out.println(selectedyear);
	}

	public void onMonthChange() throws ClassNotFoundException, SQLException {
		if (selectedmonth != null && !selectedmonth.equals("")) {
			setDaysinselectedmonth(monthDaysMap.get(selectedmonth));
			disableGenerateButton = false;
		} else {
			disableGenerateButton = true;
			setShowForm(false);
		}
	}

	public LinkedHashMap<String, String> getMonthmapfromdb() {
		return monthmapfromdb;
	}

	public void setMonthmapfromdb(LinkedHashMap<String, String> monthmapfromdb) {
		PaySlipView.monthmapfromdb = monthmapfromdb;
	}

	public int getDaysinselectedmonth() {
		return daysinselectedmonth;
	}

	public void setDaysinselectedmonth(int daysinselectedmonth) {
		this.daysinselectedmonth = daysinselectedmonth;
	}

	public void save() throws ClassNotFoundException, SQLException {
		payslips = getAllSalaryStaffByMonthAndYear();
		setShowForm(true);

	}

	public List<PaySlip> getPayslips() {
		return payslips;
	}

	public void setPayslips(List<PaySlip> payslips) {
		this.payslips = payslips;
	}

	public boolean isShowForm() {
		return showForm;
	}

	public void setShowForm(boolean showForm) {
		this.showForm = showForm;
	}

	public boolean isDisableGenerateButton() {
		return disableGenerateButton;
	}

	public void setDisableGenerateButton(boolean disableGenerateButton) {
		this.disableGenerateButton = disableGenerateButton;
	}

}