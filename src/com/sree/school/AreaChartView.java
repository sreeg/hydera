package com.sree.health;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

@ManagedBean
public class AreaChartView implements Serializable {

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
		lineModel2.getAxes().put(AxisType.X, new CategoryAxis("Duration"));
		lineModel2.setSeriesColors("27AAE1,E91E63,F74A4A,F52F2F,A30303");

		Axis yAxis = lineModel2.getAxis(AxisType.Y);
		yAxis.setLabel("No. of Visits");
		yAxis.setMin(0);
		yAxis.setMax(200);
	}

	private LineChartModel initCategoryModel() {
		LineChartModel model = new LineChartModel();

		LineChartSeries boys = new LineChartSeries();
		boys.setFill(true);
		boys.setLabel("Men");
		boys.set("April", 120);
		boys.set("May", 100);
		boys.set("June", 44);
		boys.set("July", 150);
		boys.set("August", 25);

		LineChartSeries girls = new LineChartSeries();
		girls.setFill(true);
		girls.setLabel("Women");
		girls.set("April", 52);
		girls.set("May", 60);
		girls.set("June", 110);
		girls.set("July", 90);
		girls.set("August", 120);

		model.addSeries(boys);
		model.addSeries(girls);

		return model;
	}

}