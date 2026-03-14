package com.revath.banking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revath.banking.entity.TransactionRequest;

public interface TransactionRequestRepository extends JpaRepository<TransactionRequest,Long> {
	
	Optional <TransactionRequest> findByRequestId(String requestId);
	

}
