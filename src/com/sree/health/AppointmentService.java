package com.sree.health;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "appointmentService")
@ApplicationScoped
public class AppointmentService {

	public List<Appointment> createAppointments(int size) {
		List<Appointment> list = new ArrayList<Appointment>();
		for (int i = 0; i < size; i++) {
			list.add(new Appointment(Calendar.getInstance().getTime(), "Show prescription"));
		}

		return list;
	}
}