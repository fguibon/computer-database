package com.excilys.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.excilys.exceptions.DatabaseException;
import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.model.Sorting;
import com.excilys.persistence.dao.ComputerDAO;

@Component
public class ComputerService {

	private ComputerDAO computerDAO;
	
	
	public ComputerService(ComputerDAO computerDAO) {
		this.computerDAO = computerDAO;
	}
	
	
	public boolean createComputer(Computer computer) throws DatabaseException  {
		return computerDAO.create(computer);
	}
	
	public List<Computer> findAll(Page page,String filter, Sorting sorting) throws DatabaseException {
		return computerDAO.findAllPaged(page,filter,sorting);
	}
	
	public Computer findById(Long id) throws DatabaseException  {
		return computerDAO.findById(id);
	}
	
	public boolean update(Computer computer) throws DatabaseException {
		return computerDAO.update(computer);
	}
	
	public void delete(Long id) throws DatabaseException  {
		computerDAO.delete(id);
	}
	
	public int count() throws DatabaseException {
		return computerDAO.count();
	}
}
