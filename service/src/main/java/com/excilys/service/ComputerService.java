package com.excilys.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.excilys.core.Computer;
import com.excilys.core.Sorting;
import com.excilys.persistence.dao.ComputerDAO;

@Service
public class ComputerService {

	private ComputerDAO computerDAO;	
	
	public ComputerService(ComputerDAO computerDAO) {
		this.computerDAO = computerDAO;
	}
		
	public Long createComputer(Computer computer) {
		return computerDAO.create(computer);
	}
	
	public List<Computer> findAll() {
		return computerDAO.findAll();
	}
	
	public List<Computer> findAll(Sorting sorting) {
		return computerDAO.findAllPaged(sorting);
	}
	
	public Computer findById(Long id)  {
		return computerDAO.read(id);
	}
	
	public int update(Computer computer) {
		return computerDAO.update(computer);
	}
	
	public int delete(Long id) {
		return computerDAO.delete(id);
	}
	
	public int count() {
		return computerDAO.count();
	}
}
