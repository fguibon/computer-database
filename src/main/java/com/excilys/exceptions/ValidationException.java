package com.excilys.exceptions;

public class ValidationException extends Exception{

	private static final long serialVersionUID = 1L;

	public ValidationException() {
		super();
	}

	@Override
	public String getMessage() {
		return "An error occured during the validation";
	}

}
