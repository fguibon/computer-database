package com.excilys.exceptions;

public class CompanyValidationException extends ValidationException {

	private static final long serialVersionUID = 1L;

	public CompanyValidationException() {
		super();
	}

	@Override
	public String getMessage() {
		return "The company data is not valid.";
	}
}
