package main.com.excilys.util;

import java.sql.Connection;
import java.util.List;

public abstract class DataAccessObject<T extends DataTransferObject> {
	protected final Connection connection;
	
	public DataAccessObject(Connection connection) {
		super();
		this.connection = connection;
	}
	
	public abstract boolean create(T dto);
	public abstract List<T> findAll();
	public abstract T findById(Long id);
	public abstract T update( T dto);
	public abstract void delete(Long id);
}
