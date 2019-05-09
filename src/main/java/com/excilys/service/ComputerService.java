package com.excilys.service;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.exceptions.DatabaseException;
import com.excilys.exceptions.DateParseException;
import com.excilys.exceptions.MappingException;
import com.excilys.exceptions.ValidationException;
import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.persistence.dao.ComputerDAO;

public class ComputerService {

	private static ComputerService instance = null;
	
	private ComputerDAO computerDAO = ComputerDAO.getInstance();
	private static final Logger logger = LogManager.getLogger(ComputerService.class);
	
	private ComputerService() {}
	
	public static ComputerService getInstance() {
		return (instance!=null) ? instance : (instance = new ComputerService());
	}
	
	
	public boolean createComputer(Computer computer) throws ValidationException, DateParseException, MappingException, DatabaseException  {
		boolean created = computerDAO.create(computer);
		return created;
	}
	
	public List<Computer> findAll(Page page,String filter, String field, String order) throws DatabaseException {
		List<Computer> computers = computerDAO.findAllPaged(page,filter, field, order);
		return computers;
	}
	
	public Computer findById(Long id) throws DatabaseException  {
		Computer computer = computerDAO.findById(id);
		return computer;
	}
	
	public boolean update(Computer computer) throws DatabaseException, DateParseException, MappingException  {
		boolean updated = computerDAO.update(computer);
		return updated;
	}
	
	public void delete(Long id) throws DatabaseException  {
		computerDAO.delete(id);
	}
	
	public int count() throws DatabaseException {
		int count =  computerDAO.count();
		return count;
	}
}
