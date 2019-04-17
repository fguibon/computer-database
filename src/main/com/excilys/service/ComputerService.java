package main.com.excilys.service;

import java.util.List;

import main.com.excilys.model.Computer;
import main.com.excilys.persistence.ComputerDAO;

public class ComputerService {

	private ComputerDAO computerDAO;
	
	public ComputerService(ComputerDAO computerDAO) {
		this.computerDAO = computerDAO;
	}
	
	public boolean createComputer(Computer computer) {
		return computerDAO.create(computer);
	}
	
	public List<Computer> getComputers(){
		return computerDAO.findAll();
	}
	
	public Computer findById(Long id) {
		return computerDAO.findById(id);
	}
	
	public void update(Computer computer) {
		computerDAO.update(computer);
	}
	
	public void delete(Long id) {
		computerDAO.delete(id);
	}
}
