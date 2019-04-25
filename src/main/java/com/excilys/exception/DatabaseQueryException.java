package com.excilys.exception;

public class DatabaseQueryException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DatabaseQueryException(String query) {
		super("SQL Query error : " +query );
	}

	public DatabaseQueryException() {
	}

}
