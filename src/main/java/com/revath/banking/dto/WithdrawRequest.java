package com.revath.banking.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Positive;

public class WithdrawRequest {
	
	@Positive
	private BigDecimal amount;
	
	private String pin;
	
	public BigDecimal getAmount()
	{
		return amount;
	}
	public void setAmount(BigDecimal amount)
	{
		this.amount=amount;
	}
	
	public String getPin()
	{
		return pin;
	}
	public void setPin(String pin)
	{
		this.pin=pin;
	}

}
