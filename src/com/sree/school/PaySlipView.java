package com.sree.school;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
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
import javax.servlet.ServletContext;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;

@ManagedBean(name = "payslipView")
@ViewScoped
public class PaySlipView implements Serializable {

	private static LinkedHashMap<String, Integer> monthDaysMap;
	private java.sql.Connection conn;

	private static LinkedHashMap<String, String> monthMap;
	private static LinkedHashMap<String, String> monthmapfromdb = new LinkedHashMap<String, String>();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static LinkedHashMap<String, String> yearMap = new LinkedHashMap<String, String>();

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

	private String currentMonth;
	private String currentYear;
	private int daysincurrentmonth;
	private int daysinselectedmonth;
	private boolean disableGenerateButton;

	SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
	FacesMessage msg;
	private List<PaySlip> payslipDomestic;
	private List<PaySlip> payslipPermenant;
	private List<Categories> allPayslips;
	private List<PaySlip> payslips;
	private List<PaySlip> payslipTemporary;
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

		payslipPermenant = new ArrayList<>();
		payslipDomestic = new ArrayList<>();
		payslipTemporary = new ArrayList<>();
		allPayslips = new ArrayList<>();
	}

	public List<PaySlip> getAllSalaryStaffByMonthAndYear() {
		List<PaySlip> paySlips = new ArrayList<>();

		try {
			conn = DBConnection.getConnection();
			ResultSet rs = conn.createStatement()
					.executeQuery("select employeeid, basicsalary, fixedda, hra, conveyanceall,"
							+ "pfno, sbacno, pfrate, proftaxdeduction, otherdeduction,"
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

			payslipPermenant = new ArrayList<>();
			payslipDomestic = new ArrayList<>();
			payslipTemporary = new ArrayList<>();
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
					msg = new FacesMessage(FacesMessage.SEVERITY_FATAL,
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

				if (categoryID.equals("1")) {
					payslipPermenant.add(ps);
				}
				if (categoryID.equals("2")) {
					payslipDomestic.add(ps);
				}
				if (categoryID.equals("3")) {
					payslipTemporary.add(ps);
				}
			}

			Categories per = new Categories("Permanent");
			per.setPayslips(payslipPermenant);
			Categories dom = new Categories("Domestic");
			dom.setPayslips(payslipDomestic);
			Categories tem = new Categories("Temporary");
			tem.setPayslips(payslipTemporary);

			allPayslips.add(per);
			allPayslips.add(dom);
			allPayslips.add(tem);

			msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Succesfully generated payslips for " + selectedmonth + ", " + selectedyear,
					"Press 'print' for print outs.");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return paySlips;
	}

	public void insert() {
		FacesMessage msg = null;
		int i = 0;
		try {
			conn = DBConnection.getConnection();
			ResultSet rs1 = conn.createStatement().executeQuery("select * from PAYSLIP where month = " + "'"
					+ getCurrentMonth() + "'" + " and year = " + "'" + getCurrentYear() + "'");

			while (rs1.next()) {
				i++;
			}
		} catch (ClassNotFoundException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Something went wrong",
					"Please contant your system administrator.");
			e.printStackTrace();
		} catch (SQLException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Something went wrong",
					"Please contant your system administrator.");
			e.printStackTrace();
		}
		if (i <= 0) {

			try {
				for (PaySlip p : payslips) {
					PreparedStatement ps = conn.prepareStatement(
							"INSERT INTO PAYSLIP (employeeid, basicsalary, fixedda, hra, conveyanceall,"
									+ "pfno, sbacno, pfrate, proftaxdeduction, otherdeduction,"
									+ "pfamount, loanamount, month, year, dayspresent, daysinmonth, createdatetime, updatedatetime)"
									+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, now(), now())");

					ps.setString(1, p.getEmployeeid());
					ps.setDouble(2, p.getBasicsalary());
					ps.setDouble(3, p.getFixedda());
					ps.setDouble(4, p.getHra());
					ps.setDouble(5, p.getConveyanceall());
					ps.setString(6, p.getPfno());
					ps.setString(7, p.getSbacno());
					ps.setDouble(8, p.getPfrate());
					ps.setDouble(9, p.getProftaxdeduction());
					ps.setDouble(10, p.getOtherdeduction());
					ps.setDouble(11, p.getPfamount());
					ps.setDouble(12, p.getLoanamount());
					ps.setString(13, getCurrentMonth());
					ps.setString(14, getCurrentYear());
					ps.setInt(15, p.getDayspresent());
					ps.setInt(16, p.getDaysinmonth());

					int rs = ps.executeUpdate();

					if (rs == 1) {
						msg = new FacesMessage("Payslips generated successfully", "");
					} else {
						msg = new FacesMessage("Something went wrong", "Please contant your system administrator.");
					}
				}
			} catch (SQLException e) {
				msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Something went wrong",
						"Please contant your system administrator.");
				e.printStackTrace();
			} finally {
				
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}

	}

	public String getCurrentMonth() {
		return currentMonth;
	}

	public String getCurrentYear() {
		return currentYear;
	}

	public int getDaysincurrentmonth() {
		return daysincurrentmonth;
	}

	public int getDaysinselectedmonth() {
		return daysinselectedmonth;
	}

	public LinkedHashMap<String, String> getMonthMap() {
		return monthMap;
	}

	public LinkedHashMap<String, String> getMonthmapfromdb() {
		return monthmapfromdb;
	}

	public List<PaySlip> getPayslipDomestic() {
		return payslipDomestic;
	}

	public List<PaySlip> getPayslipPermenant() {
		return payslipPermenant;
	}

	public List<PaySlip> getPayslips() {
		return payslips;
	}

	public List<PaySlip> getPayslipTemporary() {
		return payslipTemporary;
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

	public void onMonthChange() throws ClassNotFoundException, SQLException {
		if (selectedmonth != null && !selectedmonth.equals("")) {
			setDaysinselectedmonth(monthDaysMap.get(selectedmonth));
			disableGenerateButton = false;
		} else {
			disableGenerateButton = true;
			setShowForm(false);
		}
	}

	public void onYearChange() {
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

		Paragraph paragraph = new Paragraph("Salary Proposals for the month of " + currentMonth + ", " + currentYear);
		paragraph.setAlignment(Element.ALIGN_CENTER);
		paragraph.setFont(FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, Font.BOLD));
		pdf.add(paragraph);
		pdf.add(new Phrase("\n"));
	}

	public void save() throws ClassNotFoundException, SQLException {
		payslips = getAllSalaryStaffByMonthAndYear();
		insert();
		setShowForm(true);
	}

	public void setCurrentMonth(String currentMonth) {
		this.currentMonth = currentMonth;
	}

	public void setCurrentYear(String currentYear) {
		this.currentYear = currentYear;
	}

	public void setDaysincurrentmonth(int daysincurrentmonth) {
		this.daysincurrentmonth = daysincurrentmonth;
	}

	public void setDaysinselectedmonth(int daysinselectedmonth) {
		this.daysinselectedmonth = daysinselectedmonth;
	}

	public void setDisableGenerateButton(boolean disableGenerateButton) {
		this.disableGenerateButton = disableGenerateButton;
	}

	public void setMonthMap(LinkedHashMap<String, String> monthMap) {
		PaySlipView.monthMap = monthMap;
	}

	public void setMonthmapfromdb(LinkedHashMap<String, String> monthmapfromdb) {
		PaySlipView.monthmapfromdb = monthmapfromdb;
	}

	public void setPayslipDomestic(List<PaySlip> payslipDomestic) {
		this.payslipDomestic = payslipDomestic;
	}

	public void setPayslipPermenant(List<PaySlip> payslipPermenant) {
		this.payslipPermenant = payslipPermenant;
	}

	public void setPayslips(List<PaySlip> payslips) {
		this.payslips = payslips;
	}

	public void setPayslipTemporary(List<PaySlip> payslipTemporary) {
		this.payslipTemporary = payslipTemporary;
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
		PaySlipView.yearMap = yearMap;
	}

	public List<Categories> getAllPayslips() {
		return allPayslips;
	}

	public void setAllPayslips(List<Categories> allPayslips) {
		this.allPayslips = allPayslips;
	}

}