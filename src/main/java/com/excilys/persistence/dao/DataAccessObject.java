package com.excilys.persistence.dao;

import java.util.List;

import com.excilys.exceptions.DatabaseQueryException;

public abstract class DataAccessObject<T> {
	
	public DataAccessObject() {
		super();
	}
	
	public abstract boolean create(T dto) throws DatabaseQueryException;
	public abstract List<T> findAll() throws DatabaseQueryException;
	public abstract T findById(Long id) throws DatabaseQueryException;
	public abstract boolean update( T dto) throws DatabaseQueryException;
	public abstract boolean delete(Long id) throws DatabaseQueryException;
}
