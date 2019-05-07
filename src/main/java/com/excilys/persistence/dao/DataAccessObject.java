package com.excilys.persistence.dao;

import com.excilys.exceptions.DatabaseException;

public abstract class DataAccessObject<T> {
	
	public DataAccessObject() {
		super();
	}
	
	public abstract boolean create(T dto) throws DatabaseException;
	public abstract T findById(Long id) throws DatabaseException;
	public abstract boolean update( T dto) throws DatabaseException;
	public abstract boolean delete(Long id) throws DatabaseException;
}
