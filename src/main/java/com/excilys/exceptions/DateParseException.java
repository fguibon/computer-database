package com.excilys.exceptions;

public class DateParseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DateParseException(String s) {
		super("Invalid date format");
	}

}
