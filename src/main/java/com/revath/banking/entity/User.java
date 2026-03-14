package com.revath.banking.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	private Long id;
	private String name;
	private String email;
	@JsonIgnore private String password;
	private String transactionPin;
	private int failedPinAttempts;
	private LocalDateTime pinLockedUntil;
	private BigDecimal dailyTransferLimit=new BigDecimal("100000");
	private BigDecimal dailyTransferUsed=BigDecimal.ZERO;
	private LocalDate lastTransferDate;
	private BigDecimal dailyWithdrawalLimit = new BigDecimal("40000");
	private BigDecimal dailyWithdrawalUsed = BigDecimal.ZERO;
	private LocalDate lastWithdrawalDate;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore private List<Account> accounts;
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id=id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email=email;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password=password;
	}
	public List<Account> getAccounts()
	{
		return accounts;
	}
	public void setAccounts(List<Account> accounts)
	{
		this.accounts=accounts;
	}
	public String getTransactionPin()
	{
		return transactionPin;
	}
	public void setTransactionPin(String pin)
	{
		this.transactionPin=pin;
	}
	public int getFailedPinAttempts() {
	    return failedPinAttempts;
	}

	public void setFailedPinAttempts(int failedPinAttempts) {
	    this.failedPinAttempts = failedPinAttempts;
	}

	public LocalDateTime getPinLockedUntil() {
	    return pinLockedUntil;
	}

	public void setPinLockedUntil(LocalDateTime pinLockedUntil) {
	    this.pinLockedUntil = pinLockedUntil;
	}
	public void setDailyTransferLimit(BigDecimal dailyTransferLimit)
	{
		this.dailyTransferLimit=dailyTransferLimit;
	}
	public BigDecimal getDailyTransferLimit()
	{
		return dailyTransferLimit;
	}
	public void setDailyTransferUsed(BigDecimal dailyTransferUsed)
	{
		this.dailyTransferUsed=dailyTransferUsed;
	}
	public BigDecimal getDailyTransferUsed()
	{
		return dailyTransferUsed;
	}
	public LocalDate getLastTransferDate()
	{
		return lastTransferDate;
	}
	public void setLastTransferDate(LocalDate lastTransferDate)
	{
		this.lastTransferDate=lastTransferDate;
	}
	public BigDecimal getDailyWithdrawalLimit() {
	    return dailyWithdrawalLimit;
	}

	public void setDailyWithdrawalLimit(BigDecimal dailyWithdrawalLimit) {
	    this.dailyWithdrawalLimit = dailyWithdrawalLimit;
	}

	public BigDecimal getDailyWithdrawalUsed() {
	    return dailyWithdrawalUsed;
	}

	public void setDailyWithdrawalUsed(BigDecimal dailyWithdrawalUsed) {
	    this.dailyWithdrawalUsed = dailyWithdrawalUsed;
	}

	public LocalDate getLastWithdrawalDate() {
	    return lastWithdrawalDate;
	}

	public void setLastWithdrawalDate(LocalDate lastWithdrawalDate) {
	    this.lastWithdrawalDate = lastWithdrawalDate;
	}

	
	

}
