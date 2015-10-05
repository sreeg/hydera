package com.sree.health;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "dtBasicView")
@ViewScoped
public class BasicView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Appointment> appointments;

	@ManagedProperty("#{appointmentService}")
	private AppointmentService service;

	@PostConstruct
	public void init() {
		appointments = service.createAppointments(5);
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setService(AppointmentService service) {
		this.service = service;
	}
}