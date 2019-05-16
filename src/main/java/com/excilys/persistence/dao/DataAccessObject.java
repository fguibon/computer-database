package com.excilys.persistence.dao;

import com.excilys.exceptions.DatabaseException;

public interface DataAccessObject<T> {

	
	public abstract boolean create(T dto) throws DatabaseException;
	public abstract T findById(Long id) throws DatabaseException;
	public abstract boolean update( T dto) throws DatabaseException;
	public abstract void delete(Long id) throws DatabaseException;
}
