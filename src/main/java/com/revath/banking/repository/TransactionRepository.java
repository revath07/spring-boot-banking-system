package com.revath.banking.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.revath.banking.entity.Account;
import com.revath.banking.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
	
	//List<Transaction> findByAccountOrderByTransactionTimeDesc(Account account);
	
	Page<Transaction> findByAccountOrderByTransactionTimeDesc(Account account, Pageable pageable);
	

}
