package com.sree.hydera;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

@ManagedBean(name = "areaChartView")
@RequestScoped

public class AreaChartView {

	private LineChartModel lineModel1;
	private LineChartModel lineModel2;

	@PostConstruct
	public void init() {
		createLineModels();
	}

	public LineChartModel getLineModel1() {
		return lineModel1;
	}

	public LineChartModel getLineModel2() {
		return lineModel2;
	}

	private void createLineModels() {

		lineModel2 = initCategoryModel();
		// lineModel2.setTitle("Category Chart");
		lineModel2.setLegendPosition("ne");
		lineModel2.setShowPointLabels(true);
		lineModel2.setAnimate(true);
		lineModel2.getAxes().put(AxisType.X, new CategoryAxis("Years"));
		lineModel2.setSeriesColors("27AAE1,E91E63,48936E,F74A4A,F52F2F,A30303");

		Axis yAxis = lineModel2.getAxis(AxisType.Y);
		yAxis.setLabel("No. of Students");
		yAxis.setMin(0);
		yAxis.setMax(200);
	}

	private LineChartModel initCategoryModel() {
		LineChartModel model = new LineChartModel();

		LineChartSeries boys = new LineChartSeries();
		//boys.setFill(true);
		boys.setLabel("Boys");
		boys.set("2011", 120);
		boys.set("2012", 100);
		boys.set("2013", 44);
		boys.set("2014", 150);
		boys.set("2015", 125);

		LineChartSeries girls = new LineChartSeries();
		//girls.setFill(true);
		girls.setLabel("Girls");
		girls.set("2011", 52);
		girls.set("2012", 60);
		girls.set("2013", 110);
		girls.set("2014", 90);
		girls.set("2015", 120);

		model.addSeries(boys);
		model.addSeries(girls);

		return model;
	}

}