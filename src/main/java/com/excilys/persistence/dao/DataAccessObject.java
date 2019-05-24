package com.excilys.persistence.dao;

import com.excilys.exceptions.DatabaseException;

public interface DataAccessObject<T> {

	
	public abstract int create(T dto) throws DatabaseException;
	public abstract T findById(Long id) throws DatabaseException;
	public abstract int update( T dto) throws DatabaseException;
	public abstract int delete(Long id) throws DatabaseException;
}
