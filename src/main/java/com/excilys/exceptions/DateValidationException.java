package com.excilys.exceptions;

public class DateValidationException extends ValidationException {

	private static final long serialVersionUID = 1L;

	public DateValidationException() {
		super();
	}

	@Override
	public String getMessage() {
		return "The id provided is not valid.";
	}
}
