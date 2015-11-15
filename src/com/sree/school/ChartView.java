package com.sree.school;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.PieChartModel;

@ManagedBean(name = "chartView")
@RequestScoped
public class ChartView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3768405787440247712L;
	private static final String USED_DATE_FORMAT = "MMMM,yyyy";
	private BarChartModel barModel;
	private Connection conn;
	private BarChartModel cumBarModel;

	private Double cumilativeDom;
	private Double cumilativePer;
	private Double cumilativeTem;
	private Map<Date, Double> domestic;
	SimpleDateFormat formatter = new SimpleDateFormat(USED_DATE_FORMAT);
	private HorizontalBarChartModel horizontalBarModel;
	private Map<Date, Double> parttime;
	private Map<Date, Double> permanant;
	private PieChartModel pieModel1;
	private Map<Date, Double> total;

	public ChartView() {
		permanant = new TreeMap<>();
		domestic = new TreeMap<>();
		parttime = new TreeMap<>();
		total = new TreeMap<>();
		cumilativePer = 0d;
		cumilativeDom = 0d;
		cumilativeTem = 0d;
		getAllPayslips();
	}

	private void createBarModel() {
		barModel = initBarModel(false);
		barModel.setShowPointLabels(true);
		barModel.setDatatipFormat("&#8377; %2$s");
		barModel.setLegendCols(2);
		barModel.setLegendPosition("nw");

		Axis xAxis = barModel.getAxis(AxisType.X);
		xAxis.setLabel("Month");

		Axis yAxis = barModel.getAxis(AxisType.Y);
		yAxis.setLabel("Total Net Salary Paid");
		yAxis.setTickFormat("%'d");
		// yAxis.setMin(0);
		// yAxis.setMax(100);
	}

	private void createCumBarModel() {
		cumBarModel = initBarModel(true);
		cumBarModel.setShowPointLabels(true);
		cumBarModel.setDatatipFormat("&#8377; %2$s");
		cumBarModel.setLegendCols(3);
		cumBarModel.setLegendPosition("nw");

		Axis xAxis = cumBarModel.getAxis(AxisType.X);
		xAxis.setLabel("Month");

		Axis yAxis = cumBarModel.getAxis(AxisType.Y);
		yAxis.setLabel("Total Net Salary Paid");
		yAxis.setTickFormat("%'d");
		// yAxis.setMin(0);
		// yAxis.setMax(100);
		cumBarModel.setStacked(true);
	}

	private void createPieModel1() {
		pieModel1 = new PieChartModel();
		pieModel1.set("Permanant", cumilativePer);
		pieModel1.set("Domestic", cumilativeDom);
		pieModel1.set("Part Time", cumilativeTem);

		pieModel1.setLegendPosition("w");
		pieModel1.setSliceMargin(5);
		pieModel1.setShowDataLabels(true);
		pieModel1.setSeriesColors("337AB7,5CB85C,4C3973,A63F82,A30303,F74A4A");
	}

	private void createStaffCompositionModel() {
		horizontalBarModel = new HorizontalBarChartModel();

		ChartSeries staff = new ChartSeries();
		staff.set("Permanent", permanant.size());
		staff.set("Domestic", domestic.size());
		staff.set("Part Time", parttime.size());

		horizontalBarModel.addSeries(staff);
		horizontalBarModel.setShowPointLabels(true);
		horizontalBarModel.setSeriesColors("F74A4A,337AB7,5CB85C,4C3973,A63F82,A30303");
		
		Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
		xAxis.setLabel("No. of Staff");
		xAxis.setTickCount(10);
		xAxis.setMax(50);
		
		Axis yAxis = horizontalBarModel.getAxis(AxisType.Y);
		yAxis.setLabel("Staff Type");
		yAxis.setTickFormat("%d");

	}

	public List<PaySlip> getAllPayslips() {
		List<PaySlip> paySlips = new ArrayList<>();

		try {
			conn = DBConnection.getConnection();
			ResultSet rs = conn.createStatement()
					.executeQuery("select employeeid, basicsalary, fixedda, hra, conveyanceall,"
							+ "pfno, sbacno, pfrate, proftaxdeduction, otherdeduction, CategoryId,"
							+ "pfamount, loanamount, month, year, iseligibleforpf,monthyeardate from payslip LEFT JOIN staff ON payslip.employeeid=staff.Id order by monthyeardate");

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
				ps.setPfamount(rs.getDouble("pfamount"));
				ps.setLoanamount(rs.getDouble("loanamount"));
				ps.setMonth(rs.getString("month"));
				ps.setYear(rs.getString("year"));
				ps.setIseligibleforpf(rs.getBoolean("iseligibleforpf"));
				ps.setMonthyearDate(rs.getDate("monthyeardate"));

				String cat = rs.getString("CategoryId");
				paySlips.add(ps);
				Date monthAndYear = ps.getMonthyearDate();// ps.getMonth() + ",
															// " + ps.getYear();
				// months.add(monthAndYear);

				if (cat.equals("1") && permanant.get(monthAndYear) == null) {
					permanant.put(monthAndYear, ps.getNetsalary());
				} else if (cat.equals("1")) {
					Double netsalary = permanant.get(monthAndYear);
					netsalary += ps.getNetsalary();
					permanant.put(monthAndYear, netsalary);
				}

				if (cat.equals("2") && domestic.get(monthAndYear) == null) {
					domestic.put(monthAndYear, ps.getNetsalary());
				} else if (cat.equals("2")) {
					Double netsalary = domestic.get(monthAndYear);
					netsalary += ps.getNetsalary();
					domestic.put(monthAndYear, netsalary);
				}

				if (cat.equals("3") && parttime.get(monthAndYear) == null) {
					parttime.put(monthAndYear, ps.getNetsalary());
				} else if (cat.equals("3")) {
					Double netsalary = parttime.get(monthAndYear);
					netsalary += ps.getNetsalary();
					parttime.put(monthAndYear, netsalary);
				}

				if (total.get(monthAndYear) == null) {
					total.put(monthAndYear, ps.getNetsalary());
				} else {
					Double netsalary = total.get(monthAndYear);
					netsalary += ps.getNetsalary();
					total.put(monthAndYear, netsalary);
				}

			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return paySlips;
	}

	public BarChartModel getBarModel() {
		return barModel;
	}

	public BarChartModel getCumBarModel() {
		return cumBarModel;
	}

	public HorizontalBarChartModel getHorizontalBarModel() {
		return horizontalBarModel;
	}

	public PieChartModel getPieModel1() {
		return pieModel1;
	}

	@PostConstruct
	public void init() {
		createBarModel();
		createCumBarModel();
		createPieModel1();
		createStaffCompositionModel();
	}

	private BarChartModel initBarModel(boolean isCum) {
		BarChartModel model = new BarChartModel();

		ChartSeries per = new ChartSeries();
		per.setLabel("Permanent ");
		for (Entry<Date, Double> entry : permanant.entrySet()) {
			Date key = entry.getKey();
			per.set(formatter.format(key), entry.getValue());
			cumilativePer += entry.getValue();
		}

		ChartSeries dom = new ChartSeries();
		dom.setLabel("Domestic ");
		for (Map.Entry<Date, Double> entry : domestic.entrySet()) {
			dom.set(formatter.format(entry.getKey()), entry.getValue());
			cumilativeDom += entry.getValue();
		}

		ChartSeries part = new ChartSeries();
		part.setLabel("Part Time ");
		for (Map.Entry<Date, Double> entry : parttime.entrySet()) {
			part.set(formatter.format(entry.getKey()), entry.getValue());
			cumilativeTem += entry.getValue();
		}

		ChartSeries tot = new ChartSeries();
		tot.setLabel("Total");
		for (Map.Entry<Date, Double> entry : total.entrySet()) {
			tot.set(formatter.format(entry.getKey()), entry.getValue());
		}

		model.addSeries(per);
		model.addSeries(dom);
		model.addSeries(part);
		if (!isCum)
			model.addSeries(tot);

		model.setSeriesColors("337AB7,5CB85C,4C3973,A63F82,A30303,F74A4A");
		model.setAnimate(true);
		return model;
	}

	public Date parseToDate(String monthYearString) throws ParseException {
		return formatter.parse("01," + monthYearString);
	}

	public void setCumBarModel(BarChartModel cumBarModel) {
		this.cumBarModel = cumBarModel;
	}

	public void setHorizontalBarModel(HorizontalBarChartModel horizontalBarModel) {
		this.horizontalBarModel = horizontalBarModel;
	}

	public void setPieModel1(PieChartModel pieModel1) {
		this.pieModel1 = pieModel1;
	}
}