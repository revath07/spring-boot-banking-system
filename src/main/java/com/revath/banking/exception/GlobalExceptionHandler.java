package com.revath.banking.exception;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> handleValidateErrors(MethodArgumentNotValidException ex)
	{
		Map<String,String> errors=new HashMap();
		List<FieldError> fieldErrors=ex.getBindingResult().getFieldErrors();
		for(FieldError error:fieldErrors)
		{
			String fieldName=error.getField();
			String errorMessage=error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		}
		return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<Map<String,String>> handleEmailExists(EmailAlreadyExistsException e)
	{
		Map<String,String> error=new HashMap<>();
		error.put("message",e.getMessage());
		return new ResponseEntity<>(error,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Map<String, String>> handleRuntime(RuntimeException e) {
	    Map<String, String> error = new HashMap<>();
	    error.put("message", e.getMessage());
	    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}


}
