package com.excilys.exceptions;

public class MappingException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public MappingException() {
		super();
	}
	
	@Override
	public String getMessage() {
		return "Mapping failed";
	}
}
