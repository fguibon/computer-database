package com.excilys.exceptions;

public class NameValidationException extends ValidationException {

	private static final long serialVersionUID = 1L;

	public NameValidationException() {
		super();
	}

	@Override
	public String getMessage() {
		return "The name provided is not valid.";
	}
}
