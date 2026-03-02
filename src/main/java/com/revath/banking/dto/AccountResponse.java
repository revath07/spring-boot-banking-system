package com.revath.banking.dto;

import java.math.BigDecimal;

public class AccountResponse {
	private long id;
	private String accountNumber;
	private BigDecimal balance;
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id=id;
	}
	public String getAccountNumber()
	{
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber=accountNumber;
	}
	public BigDecimal getBalance()
	{
		return balance;
	}
	public void setBalance(BigDecimal balance)
	{
		this.balance=balance;
	}

}
