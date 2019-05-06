package com.excilys.exceptions;

public class ComputerValidationException extends ValidationException{

	private static final long serialVersionUID = 1L;

	public ComputerValidationException() {
		super();
	}

	@Override
	public String getMessage() {
		return "The computer data is not valid.";
	}
	
}
