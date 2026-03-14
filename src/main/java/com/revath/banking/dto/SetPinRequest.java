package com.revath.banking.dto;

import jakarta.validation.constraints.Pattern;

public class SetPinRequest {
	@Pattern(regexp="\\d{4}",message="PIN must be exactly 4 digits")
	private String pin;
	
	public String getPin()
	{
		return pin;
	}
	public void setPin(String pin)
	{
		this.pin=pin;
	}

}
