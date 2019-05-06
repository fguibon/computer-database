package com.excilys.exceptions;

public class IdValidationException extends ValidationException {

	private static final long serialVersionUID = 1L;

	public IdValidationException() {
		super();
	}

	@Override
	public String getMessage() {
		return "The id provided is not valid.";
	}
}
