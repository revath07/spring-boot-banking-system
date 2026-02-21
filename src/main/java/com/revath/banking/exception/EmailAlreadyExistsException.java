package com.revath.banking.exception;

public class EmailAlreadyExistsException extends RuntimeException{
	public EmailAlreadyExistsException(String message)
	{
		super(message);
	}
}
