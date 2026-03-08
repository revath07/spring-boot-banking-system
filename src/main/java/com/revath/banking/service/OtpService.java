package com.revath.banking.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revath.banking.entity.EmailOtp;
import com.revath.banking.repository.EmailOtpRepository;

@Service
public class OtpService {
	
	@Autowired
	private EmailOtpRepository otpRepository;
	
	@Autowired
	private EmailService emailService;
	
	public void sendOtp(String email)
	{
		String otp=String.valueOf((int)(Math.random()*900000)+100000);
		EmailOtp emailOtp=new EmailOtp();
		emailOtp.setEmail(email);
		emailOtp.setExpiryTime(LocalDateTime.now().plusMinutes(5));
		emailOtp.setOtp(otp);
		otpRepository.save(emailOtp);
		emailService.sendOtp(email,otp);
	}
	public void verifyOtp(String email,String otp)
	{
		EmailOtp emailotp=otpRepository.findTopByEmailOrderByIdDesc(email)
				.orElseThrow(()-> new RuntimeException("OTP not found"));
		if(!emailotp.getOtp().equals(otp))
			throw new RuntimeException("Invalid Otp");
		if(emailotp.getExpiryTime().isBefore(LocalDateTime.now()))
			throw new RuntimeException("OTP expired");
		emailotp.setVerified(true);
		otpRepository.save(emailotp);
		
	}

}
