package com.revath.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revath.banking.dto.LoginRequest;
import com.revath.banking.dto.UserRequest;
import com.revath.banking.dto.UserResponse;
import com.revath.banking.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	@Autowired
	private UserService userService;
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
	

}
