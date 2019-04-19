package main.com.excilys.service;

import java.util.List;

import main.com.excilys.model.Computer;
import main.com.excilys.persistence.ComputerDAO;
import main.com.excilys.validator.ComputerValidator;
import main.com.excilys.validator.Validator;

public class ComputerService {

	private ComputerDAO computerDAO = ComputerDAO.getInstance();
	ComputerValidator computerValidator;
	
	public ComputerService(Validator<Computer> validator) {
		validator = new ComputerValidator();
	}
	
	public boolean createComputer(Computer computer) {
		computerValidator.validate(computer);
		return computerDAO.create(computer);
	}
	
	public List<Computer> getComputers(){
		return computerDAO.findAll();
	}
	
	public List<Computer> getComputers(int limit, int currentPage){
		return computerDAO.findAllPaged(limit, currentPage);
	}
	
	public Computer findById(Long id) {
		return computerDAO.findById(id);
	}
	
	public void update(Computer computer) {
		//computerValidator.validate(computer);
		computerDAO.update(computer);
	}
	
	public void delete(Long id) {
		computerDAO.delete(id);
	}
}
