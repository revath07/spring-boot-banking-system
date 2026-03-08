package com.revath.banking.repository;

import com.revath.banking.entity.EmailOtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailOtpRepository extends JpaRepository<EmailOtp, Long> {

    //Optional<EmailOtp> findByEmail(String email);
	
	Optional<EmailOtp> findTopByEmailOrderByIdDesc(String email);
}