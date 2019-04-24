package com.excilys.exception;

public class InvalidDateException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidDateException(String s) {
		super("Invalid date format");
	}

}
