package com.revath.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;

import com.revath.banking.dto.AccountDetailsResponse;
import com.revath.banking.dto.AccountResponse;
import com.revath.banking.dto.ApiResponse;
import com.revath.banking.dto.DepositRequest;
import com.revath.banking.dto.TransactionResponse;
import com.revath.banking.dto.TransferRequest;
import com.revath.banking.dto.TransferResponse;
import com.revath.banking.dto.UserAccountResponse;
import com.revath.banking.dto.WithdrawRequest;
import com.revath.banking.entity.Account;
import com.revath.banking.entity.TransactionType;
import com.revath.banking.service.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping("/create/{userId}")
	public ApiResponse<AccountResponse> createAccount(@PathVariable Long userId)
	{
		AccountResponse response = accountService.createAccount(userId);
		return new ApiResponse<>("success","Account created successfully",response);
	}
	
	@PostMapping("/deposit/{accountNumber}")
	public ApiResponse<AccountResponse> deposit(@PathVariable String accountNumber, @RequestBody DepositRequest request)
	{
		AccountResponse response=accountService.deposit(accountNumber, request);
		return new ApiResponse<>("success","Money Deposited Successfully",response);
	}
	
	@PostMapping("/withdraw/{accountNumber}")
	public ApiResponse<AccountResponse> withdraw(@PathVariable String accountNumber,@RequestBody WithdrawRequest request)
	{
		AccountResponse response=accountService.withdraw(accountNumber,request);
		return new ApiResponse<>("success","Money withdrawed successfully",response);
	}
	
	@PostMapping("/transfer")
	public ApiResponse<TransferResponse> transfer(@Valid @RequestBody TransferRequest request)
	{
		TransferResponse res=accountService.transfer(request);
		return new ApiResponse<>("success","Money Transferred Successfully",res);
	}
	
	@GetMapping("/{accountNumber}/transactions")
	public ApiResponse<Page<TransactionResponse>> getTransactions(@PathVariable String accountNumber ,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="5") int size)
	{
		Page<TransactionResponse> transactions =
	            accountService.getTransactions(accountNumber, page, size);
		return new ApiResponse<>(
	            "success",
	            "Transactions fetched successfully",
	            transactions
	    );
	}
	
	@GetMapping("/{accountNumber}")
	public ApiResponse<AccountDetailsResponse> getAccountDetails(@PathVariable String accountNumber)
	{
		AccountDetailsResponse response =
	            accountService.getAccountDetails(accountNumber);
		return new ApiResponse<>(
	            "success",
	            "Account details fetched successfully",
	            response
	    );
	}
	
	@GetMapping("/{accountNumber}/transactions/filter")
	public ApiResponse<List<TransactionResponse>> getTransactionByType(@PathVariable String accountNumber,@RequestParam TransactionType type)
	{
		List<TransactionResponse> transactions =
	            accountService.getTransactionsByType(accountNumber, type);

	    return new ApiResponse<>(
	            "success",
	            "Filtered transactions fetched successfully",
	            transactions
	    );
	}
	
	@GetMapping("/{accountNumber}/statement")
	public ApiResponse<List<TransactionResponse>> getStatement(@PathVariable String accountNumber,@RequestParam LocalDate fromDate,@RequestParam LocalDate toDate)
	{
		List<TransactionResponse> statement =
	            accountService.getStatement(accountNumber, fromDate, toDate);

	    return new ApiResponse<>(
	            "success",
	            "Account statement fetched successfully",
	            statement
	    );
	}
	
	

}
