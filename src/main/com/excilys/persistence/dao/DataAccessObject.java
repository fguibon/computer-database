package main.com.excilys.persistence.dao;

import java.util.List;

public abstract class DataAccessObject<T> {
	
	public DataAccessObject() {
		super();
	}
	
	public abstract boolean create(T dto);
	public abstract List<T> findAll();
	public abstract T findById(Long id);
	public abstract boolean update( T dto);
	public abstract void delete(Long id);
}
