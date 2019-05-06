package com.excilys.exceptions;

public class DatabaseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DatabaseException(String query) {
		super(query);
	}


}
