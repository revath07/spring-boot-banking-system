package com.revath.banking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revath.banking.dto.LoginRequest;
import com.revath.banking.dto.ResetPasswordRequest;
import com.revath.banking.dto.SetPinRequest;
import com.revath.banking.dto.UserAccountResponse;
import com.revath.banking.dto.UserRequest;
import com.revath.banking.dto.UserResponse;
import com.revath.banking.service.AccountService;
import com.revath.banking.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountService accountService;
	@PostMapping("/register")
	public ResponseEntity<UserResponse> registerUser( @Valid @RequestBody UserRequest request)
	{
		UserResponse response=userService.registerUser(request);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	@PostMapping("/login")
	public ResponseEntity<UserResponse> login(@RequestBody LoginRequest request)
	{
		return ResponseEntity.ok(userService.login(request));
	}
	@GetMapping("/{userId}/accounts")
	public List<UserAccountResponse> getUserAccounts(@PathVariable Long userId)
	{
		return accountService.getAccountByUser(userId);
	}
	@PostMapping("/reset-password")
	public String resetPassword(@RequestBody ResetPasswordRequest request)
	{
		userService.resetPassword(request);
		return "Password updated successfully";
	}
	
	@PostMapping("/set-pin")
	public ResponseEntity<String> setTransactionPin(@Valid @RequestBody SetPinRequest req)
	{
		userService.setTransactionPin(req);
		return ResponseEntity.ok("Transaction PIN set successfully");
	}
	

}
