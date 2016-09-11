package com.sree.school;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

@ManagedBean(name = "mapBean")
@SessionScoped
public class MapBean {
	private MapModel model = new DefaultMapModel();

	public MapBean() {

		Marker marker = new Marker(new LatLng(17.470210, 78.540004), "Hospital Location");
		marker.setIcon("resources/images/hospital48.png");
		model.addOverlay(marker);
		model.addOverlay(new Marker(new LatLng(17.472011, 78.542215), "Person2"));
		model.addOverlay(new Marker(new LatLng(17.470906, 78.543137), "Person3"));
		model.addOverlay(new Marker(new LatLng(17.472216, 78.538932), "Person4"));
		// more overlays
	}

	public MapModel getModel() {
		return this.model;
	}
}