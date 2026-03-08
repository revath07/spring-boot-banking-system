package com.revath.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revath.banking.dto.ResetPasswordRequest;
import com.revath.banking.dto.SendOtpRequest;
import com.revath.banking.dto.VerifyOtpRequest;
import com.revath.banking.service.OtpService;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
	@Autowired
	private OtpService otpService;
	
	@PostMapping("/send-otp")
	public String sendOtp(@RequestBody SendOtpRequest request)
	{
		otpService.sendOtp(request.getEmail());
		return "OTP sent successfully";
	}
	
	@PostMapping("/verify-otp")
	public String verifyOtp(@RequestBody VerifyOtpRequest request)
	{
		otpService.verifyOtp(request.getEmail(),request.getOtp());
		return "Otp verified successfully";
	}
	
	

}
