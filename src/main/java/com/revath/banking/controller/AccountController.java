package com.revath.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

import com.revath.banking.dto.AccountResponse;
import com.revath.banking.dto.DepositRequest;
import com.revath.banking.dto.TransactionResponse;
import com.revath.banking.dto.TransferRequest;
import com.revath.banking.dto.TransferResponse;
import com.revath.banking.dto.WithdrawRequest;
import com.revath.banking.entity.Account;
import com.revath.banking.service.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping("/create/{userId}")
	public AccountResponse createAccount(@PathVariable Long userId)
	{
		return accountService.createAccount(userId);
	}
	
	@PostMapping("/deposit/{accountNumber}")
	public AccountResponse deposit(@PathVariable String accountNumber, @RequestBody DepositRequest request)
	{
		return accountService.deposit(accountNumber,request);
	}
	
	@PostMapping("/withdraw/{accountNumber}")
	public AccountResponse withdraw(@PathVariable String accountNumber,@RequestBody WithdrawRequest request)
	{
		return accountService.withdraw(accountNumber,request);
	}
	
	@PostMapping("/transfer")
	public TransferResponse transfer(@Valid @RequestBody TransferRequest request)
	{
		TransferResponse res=accountService.transfer(request);
		return res;
	}
	
	@GetMapping("/{accountNumber}/transactions")
	public List<TransactionResponse> getTransactions(@PathVariable String accountNumber)
	{
		return accountService.getTransactions(accountNumber);
	}
	

}
