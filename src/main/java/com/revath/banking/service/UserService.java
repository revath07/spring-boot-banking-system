package com.revath.banking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.revath.banking.dto.LoginRequest;
import com.revath.banking.dto.UserRequest;
import com.revath.banking.dto.UserResponse;
import com.revath.banking.entity.User;
import com.revath.banking.exception.EmailAlreadyExistsException;
import com.revath.banking.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public UserResponse registerUser(UserRequest request)
	{
		User user =new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		if(userRepository.existsByEmail(request.getEmail()))
			throw new EmailAlreadyExistsException("EMail already registered try with another email");
		
		User saveduser=userRepository.save(user);
		
		UserResponse response=new UserResponse();
		response.setId(saveduser.getId());
		response.setEmail(saveduser.getEmail());
		response.setName(saveduser.getName());
		return response;
	}
	
	public UserResponse login(LoginRequest request)
	{
		User user=userRepository.findByEmail(request.getEmail())
				.orElseThrow(()-> new RuntimeException("Invalid email"));
		if(!passwordEncoder.matches(request.getPassword(),user.getPassword())) {
			throw new RuntimeException("Invalid Password");
		}
		UserResponse res=new UserResponse();
		res.setId(user.getId());
		res.setEmail(user.getEmail());
		res.setName(user.getName());
		return res;
	}
	

}
