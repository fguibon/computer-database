package com.excilys.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.excilys.exceptions.DatabaseException;
import com.excilys.model.Computer;
import com.excilys.model.Sorting;
import com.excilys.persistence.dao.ComputerDAO;

@Service
public class ComputerService {

	private ComputerDAO computerDAO;	
	
	public ComputerService(ComputerDAO computerDAO) {
		this.computerDAO = computerDAO;
	}
		
	public int createComputer(Computer computer) throws DatabaseException  {
		return computerDAO.create(computer);
	}
	
	public List<Computer> findAll(Sorting sorting) throws DatabaseException {
		return computerDAO.findAllPaged(sorting);
	}
	
	public Computer findById(Long id) throws DatabaseException  {
		return computerDAO.findById(id);
	}
	
	public int update(Computer computer) throws DatabaseException {
		return computerDAO.update(computer);
	}
	
	public int delete(Long id) throws DatabaseException  {
		return computerDAO.delete(id);
	}
	
	public int count() throws DatabaseException {
		return computerDAO.count();
	}
}
