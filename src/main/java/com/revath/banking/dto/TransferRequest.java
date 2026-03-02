package com.revath.banking.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TransferRequest {
	
	@NotBlank(message="Sender Account Number is required")
	private String fromAccountNumber;
	
	@NotBlank(message="Receiver Account Number is required")
	private String toAccountNumber;
	
	@NotNull(message="Amount is required")
	@DecimalMin(value="0.01",message="Transfer amount must be greater than zero ")
	private BigDecimal amount;
	
	public String getFromAccountNumber()
	{
		return fromAccountNumber;
	}
	public void setFromAccountNumber(String fromAccountNumber)
	{
		this.fromAccountNumber=fromAccountNumber;
	}
	public String getToAccountNumber() 
	{
	     return toAccountNumber;
	}
	public void setToAccountNumber(String toAccountNumber) 
	{
	     this.toAccountNumber = toAccountNumber;
	}
	public BigDecimal getAmount() 
	{
	      return amount;
	}
	public void setAmount(BigDecimal amount) 
	{
	      this.amount = amount;
	}
	

}
