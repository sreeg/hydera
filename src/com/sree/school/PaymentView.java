package com.sree.school;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "paymentView")
@ViewScoped
public class PaymentView implements Serializable {

	private java.sql.Connection conn;

	private static LinkedHashMap<String, String> monthmapfromdb = new LinkedHashMap<String, String>();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static LinkedHashMap<String, String> yearMap = new LinkedHashMap<String, String>();

	private String currentMonth;
	private String currentYear;
	private boolean disableGenerateButton;

	SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
	FacesMessage msg;
	private List<PaySlip> payslipCheque;
	private List<PaySlip> payslipCash;
	private List<Categories> allPayslips;
	private List<PaySlip> payslips;
	private List<PaySlip> payslipOnline;
	private String selectedmonth;
	private String selectedyear;
	private boolean showForm;

	private double totalBasicSalary;
	private double totalConveyanceAll;
	private double totalFixedDA;
	private double totalHRA;
	private double totalLoanAmount;
	private double totalOtherDeductions;
	private double totalPFAmount;
	private double totalProfTaxDeduction;
	
	private double payslipCashTotal;
	private double payslipChequeTotal;
	private double payslipOnlineTotal;
	private List<Integer> profTaxList;

	public PaymentView() throws ClassNotFoundException, SQLException {
		Calendar now = Calendar.getInstance();
		setCurrentMonth(month_date.format(now.getTime()));
		setCurrentYear("" + now.get(Calendar.YEAR));

		yearMap.put(currentYear, currentYear);
		setSelectedyear(getCurrentYear());
		setSelectedmonth(getCurrentMonth());
		disableGenerateButton = false;

		payslipCash = new ArrayList<>();
		payslipCheque = new ArrayList<>();
		payslipOnline = new ArrayList<>();
		allPayslips = new ArrayList<>();
		
		//save();
	}

	public List<PaySlip> getAllSalaryStaffByMonthAndYear() {
		List<PaySlip> paySlips = new ArrayList<>();

		try {
			conn = DBConnection.getConnection();
			ResultSet rs = conn.createStatement()
					.executeQuery("select employeeid, basicsalary, fixedda, hra, conveyanceall,"
							+ "pfno, sbacno, pfrate, proftaxdeduction, otherdeduction,modeofpayment,iseligibleforpf,"
							+ "staff.firstname, staff.lastname,staff.categoryid,staff.designation,staff.DateOfJoining,"
							+ "pfamount, loanamount, attendance.dayspresent, attendance.daysinmonth from salary "
							+ "LEFT JOIN attendance ON salary.employeeid=attendance.staffid and attendance.year=" + "'"
							+ getSelectedyear() + "'" + " and attendance.month = " + "'" + getSelectedmonth() + "'"
							+ "LEFT JOIN staff ON salary.employeeid=staff.Id order by staff.categoryid");

			totalBasicSalary = 0;
			totalFixedDA = 0;
			totalHRA = 0;
			totalConveyanceAll = 0;
			totalProfTaxDeduction = 0;
			totalOtherDeductions = 0;
			totalPFAmount = 0;
			totalLoanAmount = 0;

			payslipCash = new ArrayList<>();
			payslipCheque = new ArrayList<>();
			payslipOnline = new ArrayList<>();
			allPayslips = new ArrayList<>();

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
				String categoryID = rs.getString("staff.categoryid");
				String modeofpayment = rs.getString("modeofpayment");
				ps.setModeofpayment(modeofpayment);
				ps.setIseligibleforpf(rs.getBoolean("iseligibleforpf"));
				ps.setCategoryname(categoryID);
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
					msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Cannot generate payslips for " + selectedmonth + ", " + selectedyear + ".",
							"Enter attendance details first.");
					FacesContext.getCurrentInstance().addMessage(null, msg);
					setShowForm(false);
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

				if (modeofpayment.equals("1")) {
					payslipCash.add(ps);
					payslipCashTotal = payslipCashTotal + ps.getNetsalary();
				}
				if (modeofpayment.equals("2")) {
					payslipCheque.add(ps);
					payslipChequeTotal = payslipChequeTotal + ps.getNetsalary();
				}
				if (modeofpayment.equals("3")) {
					payslipOnline.add(ps);
					payslipOnlineTotal = payslipOnlineTotal + ps.getNetsalary();
				}
			}

			msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Succesfully generated payments for " + selectedmonth + ", " + selectedyear,
					"Press 'print' for print outs.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			setShowForm(true);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return paySlips;
	}


	public String getCurrentMonth() {
		return currentMonth;
	}

	public String getCurrentYear() {
		return currentYear;
	}

	public LinkedHashMap<String, String> getMonthMap() {
		return Commons.monthMap;
	}

	public LinkedHashMap<String, String> getMonthmapfromdb() {
		return monthmapfromdb;
	}

	public List<PaySlip> getPayslipCheque() {
		return payslipCheque;
	}

	public List<PaySlip> getPayslipCash() {
		return payslipCash;
	}

	public List<PaySlip> getPayslips() {
		return payslips;
	}

	public List<PaySlip> getPayslipOnline() {
		return payslipOnline;
	}

	public String getSelectedmonth() {
		return selectedmonth;
	}

	public String getSelectedyear() {
		return selectedyear;
	}

	public double getTotalBasicSalary() {
		return new BigDecimal(totalBasicSalary).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

	public double getTotalConveyanceAll() {
		return new BigDecimal(totalConveyanceAll).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

	public double getTotalFixedDA() {
		return new BigDecimal(totalFixedDA).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

	public double getTotalHRA() {
		return new BigDecimal(totalHRA).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

	public double getTotalLoanAmount() {
		return new BigDecimal(totalLoanAmount).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

	public double getTotalOtherDeductions() {
		return new BigDecimal(totalOtherDeductions).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

	public double getTotalPFAmount() {
		return new BigDecimal(totalPFAmount).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

	public double getTotalProfTaxDeduction() {
		return new BigDecimal(totalProfTaxDeduction).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

	public LinkedHashMap<String, String> getYearMap() {
		return yearMap;
	}

	public boolean isDisableGenerateButton() {
		return disableGenerateButton;
	}

	public boolean isShowForm() {
		return showForm;
	}

	public void onMonthChange() {
		if (selectedmonth != null && !selectedmonth.equals("")) {
			disableGenerateButton = false;
		} else {
			disableGenerateButton = true;
		}
		setShowForm(false);
	}

	public void onYearChange() {
	}

	public void save() {
		payslips = getAllSalaryStaffByMonthAndYear();
	}

	public void setCurrentMonth(String currentMonth) {
		this.currentMonth = currentMonth;
	}

	public void setCurrentYear(String currentYear) {
		this.currentYear = currentYear;
	}

	public void setDisableGenerateButton(boolean disableGenerateButton) {
		this.disableGenerateButton = disableGenerateButton;
	}
	
	public void setMonthmapfromdb(LinkedHashMap<String, String> monthmapfromdb) {
		PaymentView.monthmapfromdb = monthmapfromdb;
	}

	public void setPayslipCheque(List<PaySlip> payslipCheque) {
		this.payslipCheque = payslipCheque;
	}

	public void setPayslipCash(List<PaySlip> payslipCash) {
		this.payslipCash = payslipCash;
	}

	public void setPayslips(List<PaySlip> payslips) {
		this.payslips = payslips;
	}

	public void setPayslipOnline(List<PaySlip> payslipOnline) {
		this.payslipOnline = payslipOnline;
	}

	public void setSelectedmonth(String selectedmonth) {
		this.selectedmonth = selectedmonth;
	}

	public void setSelectedyear(String selectedyear) {
		this.selectedyear = selectedyear;
	}

	public void setShowForm(boolean showForm) {
		this.showForm = showForm;
	}

	public void setTotalBasicSalary(double totalBasicSalary) {
		this.totalBasicSalary = totalBasicSalary;
	}

	public void setTotalConveyanceAll(double totalConveyanceAll) {
		this.totalConveyanceAll = totalConveyanceAll;
	}

	public void setTotalFixedDA(double totalFixedDA) {
		this.totalFixedDA = totalFixedDA;
	}

	public void setTotalHRA(double totalHRA) {
		this.totalHRA = totalHRA;
	}

	public void setTotalLoanAmount(double totalLoanAmount) {
		this.totalLoanAmount = totalLoanAmount;
	}

	public void setTotalOtherDeductions(double totalOtherDeductions) {
		this.totalOtherDeductions = totalOtherDeductions;
	}

	public void setTotalPFAmount(double totalPFAmount) {
		this.totalPFAmount = totalPFAmount;
	}

	public void setTotalProfTaxDeduction(double totalProfTaxDeduction) {
		this.totalProfTaxDeduction = totalProfTaxDeduction;
	}

	public void setYearMap(LinkedHashMap<String, String> yearMap) {
		PaymentView.yearMap = yearMap;
	}

	public List<Categories> getAllPayslips() {
		return allPayslips;
	}

	public void setAllPayslips(List<Categories> allPayslips) {
		this.allPayslips = allPayslips;
	}

	public double getPayslipCashTotal() {
		return payslipCashTotal;
	}

	public void setPayslipCashTotal(double payslipCashTotal) {
		this.payslipCashTotal = payslipCashTotal;
	}

	public double getPayslipChequeTotal() {
		return payslipChequeTotal;
	}

	public void setPayslipChequeTotal(double payslipChequeTotal) {
		this.payslipChequeTotal = payslipChequeTotal;
	}

	public double getPayslipOnlineTotal() {
		return payslipOnlineTotal;
	}

	public void setPayslipOnlineTotal(double payslipOnlineTotal) {
		this.payslipOnlineTotal = payslipOnlineTotal;
	}

	public List<Integer> getProfTaxList() {
		int slab1 = 0, slab2 = 0,slab3 = 0,slab4 = 0,slab5 = 0,slab6 = 0,slab7 = 0,slab8 = 0, slab9 = 0;
		for(PaySlip p : payslips)
		{
			Double netsalary = p.getNetsalary();
			if(netsalary <= 2000)
				slab1++;
			else if(netsalary > 2000 && netsalary <=3000 )
				slab2++;
			else if(netsalary > 3000 && netsalary <=4000 )
				slab3++;
			else if(netsalary > 4000 && netsalary <=5000 )
				slab4++;
			else if(netsalary > 5000 && netsalary <=6000 )
				slab5++;
			else if(netsalary > 6000 && netsalary <=10000 )
				slab6++;
			else if(netsalary > 10000 && netsalary <=15000 )
				slab7++;
			else if(netsalary > 15000 && netsalary <=20000 )
				slab8++;
			else if(netsalary > 20000 )
				slab9++;
		}
		profTaxList = new ArrayList<Integer>();
		profTaxList.add(slab1);
		profTaxList.add(slab2);
		profTaxList.add(slab3);
		profTaxList.add(slab4);
		profTaxList.add(slab5);
		profTaxList.add(slab6);
		profTaxList.add(slab7);
		profTaxList.add(slab8);
		profTaxList.add(slab9);
		return profTaxList;
	}

	public void setProfTaxList(List<Integer> profTaxList) {
		this.profTaxList = profTaxList;
	}

}