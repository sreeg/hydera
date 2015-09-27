package com.sree.health;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Appointment {

	public String appointmentName;
	public Date appointmentTime;
	public String appointment;

	public String getAppointment() {
		return appointment;
	}

	public void setAppointment(String appointment) {
		this.appointment = appointment;
	}

	public DateFormat dateFormatter;

	public Appointment(Date d, String app) {
		this.appointmentTime = d;
		this.appointmentName = app;
		dateFormatter = new SimpleDateFormat("MMM dd YYYY, HH:mm aaa");
		this.appointment = dateFormatter.format(appointmentTime);
	}

	public String getAppointmentName() {
		return appointmentName;
	}

	public Date getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentName(String appointmentName) {
		this.appointmentName = appointmentName;
	}

	public void setAppointmentTime(Date appointmentTime) {
		this.appointmentTime = appointmentTime;
	}
}
