package com.revath.banking.dto;

import java.math.BigDecimal;

public class DepositRequest {
	
	
	private BigDecimal amount;
	public BigDecimal getAmount()
	{
		return amount;
	}
	public void setAmount(BigDecimal amount)
	{
		this.amount=amount;
	}

}
