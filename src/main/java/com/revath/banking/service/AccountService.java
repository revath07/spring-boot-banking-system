package com.revath.banking.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.revath.banking.dto.AccountDetailsResponse;
import com.revath.banking.dto.AccountResponse;
import com.revath.banking.dto.DepositRequest;
import com.revath.banking.dto.TransactionResponse;
import com.revath.banking.dto.TransferRequest;
import com.revath.banking.dto.TransferResponse;
import com.revath.banking.dto.UserAccountResponse;
import com.revath.banking.dto.WithdrawRequest;
import com.revath.banking.entity.Account;
import com.revath.banking.entity.Transaction;
import com.revath.banking.entity.TransactionType;
import com.revath.banking.entity.User;
import com.revath.banking.repository.AccountRepository;
import com.revath.banking.repository.TransactionRepository;
import com.revath.banking.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AccountService {
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private EmailService emailService;
	
	public AccountResponse createAccount(Long userid)
	{
		User user=userRepository.findById(userid)
				.orElseThrow(()-> new RuntimeException("User not found"));
		Account account=new Account();
		account.setAccountNumber("ACC"+System.currentTimeMillis());
		account.setBalance(BigDecimal.ZERO);
		account.setUser(user);
		Account savedAccount=accountRepository.save(account);
		
		AccountResponse response=new AccountResponse();
		response.setId(savedAccount.getId());
		response.setAccountNumber(savedAccount.getAccountNumber());
		response.setBalance(savedAccount.getBalance());
		return response;
	}
	
	public AccountResponse deposit(String accountNumber, DepositRequest request)
	{
		Account account=accountRepository.findByAccountNumber(accountNumber)
				.orElseThrow(()-> new RuntimeException("Account not found"));
		if(request.getAmount()==null || request.getAmount().compareTo(BigDecimal.ZERO)<=0)
			throw new RuntimeException("Deposit amount need to be greater than zero");
		account.setBalance(request.getAmount().add(account.getBalance()));
		Account updatedAccount=accountRepository.save(account);
		AccountResponse response=new AccountResponse();
		response.setId(updatedAccount.getId());
		response.setAccountNumber(updatedAccount.getAccountNumber());
		response.setBalance(updatedAccount.getBalance());
		Transaction transaction =new Transaction();
		transaction.setAccount(account);
		transaction.setTransactionType(TransactionType.DEPOSIT);
		transaction.setAmount(request.getAmount());
		transaction.setTransactionTime(LocalDateTime.now());
		transaction.setDescription("Deposit successful");
		transactionRepository.save(transaction);
		emailService.sendEmail(
		        account.getUser().getEmail(),
		        "Deposit Successful",
		        "₹" + request.getAmount() +
		        " has been deposited into your account.\nCurrent Balance: ₹"
		        + account.getBalance()
		);
		return response;
	}
	
	public AccountResponse withdraw(String accountNumber,WithdrawRequest request)
	{
		Account account=accountRepository.findByAccountNumber(accountNumber)
				.orElseThrow(()-> new RuntimeException("Account Not Found"));
		if(request.getAmount()==null || request.getAmount().compareTo(BigDecimal.ZERO)<=0)
		{
			throw new RuntimeException("WIthdrawl amount must be greater than zero");
		}
		
		if(account.getBalance().compareTo(request.getAmount())<0)
		{
			throw new RuntimeException("Insufficient Balance");
		}
		account.setBalance(account.getBalance().subtract(request.getAmount()));
		Account updatedAccount=accountRepository.save(account);
		AccountResponse response=new AccountResponse();
		response.setAccountNumber(updatedAccount.getAccountNumber());
		response.setBalance(updatedAccount.getBalance());
		response.setId(updatedAccount.getId());
		
		Transaction transaction = new Transaction();
		transaction.setAccount(account);
		transaction.setTransactionType(TransactionType.WITHDRAW);
		transaction.setAmount(request.getAmount());
		transaction.setTransactionTime(LocalDateTime.now());
		transaction.setDescription("Withdrawal successful");
		transactionRepository.save(transaction);
		emailService.sendEmail(
		        account.getUser().getEmail(),
		        "Withdrawal Alert",
		        "₹" + request.getAmount() +
		        " has been withdrawn from your account.\nRemaining Balance: ₹"
		        + account.getBalance()
		);
		return response;
		
		
	}
	
	public TransferResponse transfer(TransferRequest request)
	{
		Account sender=accountRepository.findByAccountNumber(request.getFromAccountNumber())
				.orElseThrow(()-> new RuntimeException("Sender account not found"));
		Account receiver=accountRepository.findByAccountNumber(request.getToAccountNumber())
				.orElseThrow(()-> new RuntimeException("Reciver account not found"));
		if(sender.getBalance().compareTo(request.getAmount())<0)
			throw new RuntimeException("Insufficient Balance");
		sender.setBalance(sender.getBalance().subtract(request.getAmount()));
		receiver.setBalance(receiver.getBalance().add(request.getAmount()));
		accountRepository.save(sender);
		accountRepository.save(receiver);
		 TransferResponse response = new TransferResponse();
		    response.setFromAccountNumber(sender.getAccountNumber());
		    response.setToAccountNumber(receiver.getAccountNumber());
		    response.setTransferredAmount(request.getAmount());
		    response.setSenderBalance(sender.getBalance());
		    response.setTransactionTime(java.time.LocalDateTime.now());
		
		    //sender trnsaction saving to transaction table
		    Transaction senderTransaction = new Transaction();
		    senderTransaction.setAccount(sender);
		    senderTransaction.setTransactionType(TransactionType.TRANSFER);
		    senderTransaction.setAmount(request.getAmount());
		    senderTransaction.setReferenceAccountNumber(receiver.getAccountNumber());
		    senderTransaction.setTransactionTime(LocalDateTime.now());
		    senderTransaction.setDescription("Transfer to " + receiver.getAccountNumber());
		    transactionRepository.save(senderTransaction);
		    
		    //Receiver transaction saving to transaction table
		    Transaction receiverTransaction = new Transaction();
		    receiverTransaction.setAccount(receiver);
		    receiverTransaction.setTransactionType(TransactionType.TRANSFER);
		    receiverTransaction.setAmount(request.getAmount());
		    receiverTransaction.setReferenceAccountNumber(sender.getAccountNumber());
		    receiverTransaction.setTransactionTime(LocalDateTime.now());
		    receiverTransaction.setDescription("Received from " + sender.getAccountNumber());

		    transactionRepository.save(receiverTransaction);
		    emailService.sendEmail(
		            sender.getUser().getEmail(),
		            "Money Transfer Successful",
		            "₹" + request.getAmount() +
		            " transferred to account " +
		            receiver.getAccountNumber() +
		            ".\nRemaining Balance: ₹" + sender.getBalance()
		    );
		    emailService.sendEmail(
		            receiver.getUser().getEmail(),
		            "Money Received",
		            "₹" + request.getAmount() +
		            " received from account " +
		            sender.getAccountNumber() +
		            ".\nCurrent Balance: ₹" + receiver.getBalance()
		    );
		    
		return response;
	}
	
	public Page<TransactionResponse> getTransactions(String accountNumber, int page, int size) {

	    Account account = accountRepository.findByAccountNumber(accountNumber)
	            .orElseThrow(() -> new RuntimeException("Account not found"));

	    Pageable pageable = PageRequest.of(page, size);

	    Page<Transaction> transactionPage =
	            transactionRepository.findByAccountOrderByTransactionTimeDesc(account, pageable);

	    return transactionPage.map(transaction -> {
	        TransactionResponse response = new TransactionResponse();
	        response.setTransactionType(transaction.getTransactionType());
	        response.setAmount(transaction.getAmount());
	        response.setReferenceAccountNumber(transaction.getReferenceAccountNumber());
	        response.setDescription(transaction.getDescription());
	        response.setTransactionTime(transaction.getTransactionTime());
	        return response;
	    });
	}
	
	public AccountDetailsResponse getAccountDetails(String accountNumber)
	{
		Account account=accountRepository.findByAccountNumber(accountNumber)
				.orElseThrow(()->new RuntimeException("Account Not Found"));
		AccountDetailsResponse response=new AccountDetailsResponse();
		response.setAccountNumber(account.getAccountNumber());
		response.setBalance(account.getBalance());
		response.setUserEmail(account.getUser().getEmail());
		response.setUserName(account.getUser().getName());
		return response;
	}
	
	public List<UserAccountResponse> getAccountByUser(Long userId)
	{
		List<Account> accounts=accountRepository.findByUserId(userId);
		List<UserAccountResponse> responseList=new ArrayList<>();
		for(Account acc:accounts)
		{
			UserAccountResponse res=new UserAccountResponse();
			res.setAccountNumber(acc.getAccountNumber());
			res.setBalance(acc.getBalance());
			responseList.add(res);
		}
		return responseList;
	}
	
	public List<TransactionResponse> getTransactionsByType(String accountNumber,TransactionType type)
	{
		List<Transaction> transactions =transactionRepository.findByAccount_AccountNumberAndTransactionType(accountNumber,type);
		List<TransactionResponse> responseList=new ArrayList<>();
		for(Transaction transaction:transactions)
		{
			TransactionResponse response=new TransactionResponse();
			response.setAmount(transaction.getAmount());
			response.setTransactionType(transaction.getTransactionType());
			response.setReferenceAccountNumber(transaction.getReferenceAccountNumber());
			response.setDescription(transaction.getDescription());
			response.setTransactionTime(transaction.getTransactionTime());
			responseList.add(response);
		}
		return responseList;
		
		
	}
	public List<TransactionResponse> getStatement(String accountNumber,LocalDate fromDate,LocalDate toDate)
	{
		LocalDateTime start = fromDate.atStartOfDay();
	    LocalDateTime end = toDate.atTime(23,59,59);
	    List<Transaction> transactions=transactionRepository.findByAccount_AccountNumberAndTransactionTimeBetween(accountNumber,start,end);
	    List<TransactionResponse> responseList=new ArrayList<>();
	    for(Transaction transaction:transactions)
	    {
	    	TransactionResponse response= new TransactionResponse();
	    	response.setTransactionType(transaction.getTransactionType());
	    	response.setAmount(transaction.getAmount());
	    	response.setReferenceAccountNumber(transaction.getReferenceAccountNumber());
	    	response.setDescription(transaction.getDescription());
	    	response.setTransactionTime(transaction.getTransactionTime());
	    	responseList.add(response);
	    }
	    return responseList;
	}
	
	
}
	


