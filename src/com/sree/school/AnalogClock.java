package com.sree.school;

import java.awt.Color;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.extensions.component.analogclock.model.AnalogClockColorModel;
import org.primefaces.extensions.component.analogclock.model.DefaultAnalogClockColorModel;

@ManagedBean(name = "analogClock")
@RequestScoped
public class AnalogClock implements Serializable {

	private static final long serialVersionUID = -5427668306657486626L;

	private AnalogClockColorModel customTheme = new DefaultAnalogClockColorModel();

	public AnalogClock() {

		Color color = new Color(51, 122, 183);
		customTheme.setBorder(color);
		customTheme.setFace(color);
		customTheme.setHourHand(Color.WHITE);
		customTheme.setHourSigns(Color.WHITE);
		customTheme.setMinuteHand(Color.WHITE);
		customTheme.setPin(Color.WHITE);
		customTheme.setSecondHand(Color.WHITE);
	}

	public AnalogClockColorModel getCustomTheme() {
		return customTheme;
	}

}