package com.sree.school;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

@ManagedBean(name = "salaryBean")
@ViewScoped
public class SalaryBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Connection conn;
	private Salary salary = new Salary();
	private boolean showForm = true;
	private boolean alreadyPresent = false;
	private static LinkedHashMap<String, String> modeofpayment;
	private String selectedmodeofpayment;
	PreparedStatement ps = null;

	public Salary getSalary() {
		return salary;
	}

	public void setSalary(Salary salary) {
		this.salary = salary;
	}

	@PostConstruct
	public void init() {
		this.salary.setLoanamount(Double.parseDouble("0"));
	}

	static {
		modeofpayment = new LinkedHashMap<String, String>();
		modeofpayment.put("Cash", "1");
		modeofpayment.put("Cheque", "2");
		modeofpayment.put("Online", "3");
	}
	public void save() {
		FacesMessage msg = null;
		int i = 0;
		try {
			conn = DBConnection.getConnection();
			ResultSet rs1 = conn.createStatement()
					.executeQuery("select * from salary where employeeid = " + "'" + salary.getEmployeeid() + "'");

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
		if (i > 0) {
			updates();
		} else {
			try {
				ps = conn.prepareStatement("INSERT INTO SALARY (employeeid, basicsalary, fixedda, hra, conveyanceall,"
						+ "pfno, sbacno, pfrate, proftaxdeduction, otherdeduction,"
						+ "pfamount, loanamount, createdatetime, updatedatetime, modeofpayment) "
						+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?, now(), now(), ?)");

				ps.setString(1, salary.getEmployeeid());
				ps.setDouble(2, salary.getBasicsalary());
				ps.setDouble(3, salary.getFixedda());
				ps.setDouble(4, salary.getHra());
				ps.setDouble(5, salary.getConveyanceall());
				ps.setString(6, salary.getPfno());
				ps.setString(7, salary.getSbacno());
				ps.setDouble(8, salary.getPfrate());
				ps.setDouble(9, salary.getProftaxdeduction());
				ps.setDouble(10, salary.getOtherdeduction());
				ps.setDouble(11, salary.getPfamount());
				ps.setDouble(12, salary.getLoanamount());
				ps.setString(13, selectedmodeofpayment);
				
				int rs = ps.executeUpdate();

				if (rs == 1) {
					msg = new FacesMessage("Salary added successfully", "");
					setShowForm(false);
				} else {
					msg = new FacesMessage("Something went wrong", "Please contant your system administrator.");
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

	public void updates() {
		FacesMessage msg = null;
		try {
			conn = DBConnection.getConnection();
			ps = conn.prepareStatement("UPDATE SALARY set basicsalary = ?, fixedda = ?, hra = ?, conveyanceall = ?,"
					+ "pfno = ?, sbacno = ?, pfrate = ?, proftaxdeduction = ?, otherdeduction = ?,"
					+ "pfamount = ?, loanamount = ?, modeofpayment = ?, updatedatetime = now() where employeeid = ?");

			ps.setDouble(1, salary.getBasicsalary());
			ps.setDouble(2, salary.getFixedda());
			ps.setDouble(3, salary.getHra());
			ps.setDouble(4, salary.getConveyanceall());
			ps.setString(5, salary.getPfno());
			ps.setString(6, salary.getSbacno());
			ps.setDouble(7, salary.getPfrate());
			ps.setDouble(8, salary.getProftaxdeduction());
			ps.setDouble(9, salary.getOtherdeduction());
			ps.setDouble(10, ((salary.getBasicsalary() + salary.getFixedda()) *salary.getPfrate())/100d);
			ps.setDouble(11, salary.getLoanamount());
			ps.setString(12, selectedmodeofpayment);
			ps.setString(13, salary.getEmployeeid());
			
			
			int rs = ps.executeUpdate();

			if (rs == 1) {
				msg = new FacesMessage("Salary updated successfully", "");
				setShowForm(false);
			} else {
				msg = new FacesMessage("Something went wrong", "Please contant your system administrator.");
			}
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (ClassNotFoundException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Something went wrong",
					"Please contant your system administrator.");
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (SQLException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Something went wrong",
					"Please contant your system administrator.");
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} 

	}

	public boolean isShowForm() {
		return showForm;
	}

	public void setShowForm(boolean showForm) {
		this.showForm = showForm;
	}

	public boolean isAlreadyPresent() {
		return alreadyPresent;
	}

	public void setAlreadyPresent(boolean alreadyPresent) {
		this.alreadyPresent = alreadyPresent;
	}

	public LinkedHashMap<String, String> getModeofpayment() {
		return modeofpayment;
	}
	
	public String getSelectedmodeofpayment() {
		return selectedmodeofpayment;
	}

	public void setSelectedmodeofpayment(String selectedmodeofpayment) {
		this.selectedmodeofpayment = selectedmodeofpayment;
	}

}
