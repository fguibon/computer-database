package main.com.excilys.service;

import java.util.List;
import java.util.stream.Collectors;

import main.com.excilys.binding.dto.ComputerDTO;
import main.com.excilys.binding.mapper.ComputerMapper;
import main.com.excilys.model.Computer;
import main.com.excilys.persistence.dao.ComputerDAO;
import main.com.excilys.validator.ComputerValidator;
import main.com.excilys.validator.Validator;

public class ComputerService {

	private static ComputerService instance = new ComputerService();
	
	private ComputerDAO computerDAO = ComputerDAO.getInstance();
	private ComputerMapper computerMapper = ComputerMapper.getInstance();
	ComputerValidator computerValidator = new ComputerValidator();
	
	private ComputerService() {}
	
	public static ComputerService getInstance() {
		return instance;
	}
	
	public ComputerService(Validator<Computer> validator) {
		validator = new ComputerValidator();
	}
	
	public boolean createComputer(ComputerDTO computerDTO) {
		//computerValidator.validate(computer);
		Computer computer = computerMapper.dtoToModel(computerDTO);
		return computerDAO.create(computer);
	}
	
	public List<ComputerDTO> getComputers(){
		List<Computer> computers = computerDAO.findAll();
		List<ComputerDTO> computersDTO = (List<ComputerDTO>)computers
		.stream().map(s -> computerMapper.modelToDto(s))
		.collect(Collectors.toList());
		return computersDTO;
	}
	
	public List<ComputerDTO> getComputers(int limit, int currentPage){
		List<Computer> computers = computerDAO.findAllPaged(limit, currentPage);
		List<ComputerDTO> computersDTO = (List<ComputerDTO>)computers
		.stream().map(s -> computerMapper.modelToDto(s))
		.collect(Collectors.toList());
		return computersDTO;
	}
	
	public ComputerDTO findById(Long id) {
		Computer computer = computerDAO.findById(id);
		ComputerDTO computerDTO = computerMapper.modelToDto(computer);
		return computerDTO;
	}
	
	public void update(ComputerDTO computerDTO) {
		//computerValidator.validate(computerDTO);
		Computer computer = computerMapper.dtoToModel(computerDTO);
		computerDAO.update(computer);
	}
	
	public void delete(Long id) {
		computerDAO.delete(id);
	}
}
