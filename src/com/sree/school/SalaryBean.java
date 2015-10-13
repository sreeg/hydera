package com.sree.school;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

	PreparedStatement ps = null;

	public Salary getSalary() {
		return salary;
	}

	public void setSalary(Salary salary) {
		this.salary = salary;
	}

	@PostConstruct
	public void init() {
		this.salary.setLoanamount(0f);
	}

	public void save() {
		FacesMessage msg = null;
		try {
			conn = DBConnection.getConnection();
			ps = conn.prepareStatement("INSERT INTO SALARY (employeeid, basicsalary, fixedda, hra, conveyanceall,"
					+ "pfno, sbacno, pfrate, proftaxdeduction, otherdeduction,"
					+ "pfamount, loanamount, createdatetime, updatedatetime)"
					+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?, now(), now())");

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

			int rs = ps.executeUpdate();

			if (rs == 1) {
				msg = new FacesMessage("Salary added successfully", "");
				setShowForm(false);
			} else {
				msg = new FacesMessage("Something went wrong", "Please contant your system administrator.");
			}
		} catch (ClassNotFoundException e) {
			msg = new FacesMessage("Something went wrong", "Please contant your system administrator.");
			e.printStackTrace();
		} catch (SQLException e) {
			msg = new FacesMessage("Something went wrong", "Please contant your system administrator.");
			e.printStackTrace();
		} finally {
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	public boolean isShowForm() {
		return showForm;
	}

	public void setShowForm(boolean showForm) {
		this.showForm = showForm;
	}
}
