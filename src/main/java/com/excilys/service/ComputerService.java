package com.excilys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.excilys.binding.dto.ComputerDTO;
import com.excilys.binding.mapper.ComputerMapper;
import com.excilys.exception.DatabaseQueryException;
import com.excilys.model.Computer;
import com.excilys.persistence.dao.ComputerDAO;
import com.excilys.validator.ComputerValidator;

public class ComputerService {

	private static ComputerService instance = new ComputerService();
	
	private static final Logger logger = 
			LogManager.getLogger(ComputerService.class);
	
	private ComputerDAO computerDAO = ComputerDAO.getInstance();
	private ComputerMapper computerMapper = ComputerMapper.getInstance();
	ComputerValidator computerValidator = new ComputerValidator();
	
	private ComputerService() {}
	
	public static ComputerService getInstance() {
		return instance;
	}
	
	public ComputerService(ComputerValidator validator) {
		validator = new ComputerValidator();
	}
	
	public boolean createComputer(ComputerDTO computerDTO)  {
		computerValidator.validate(computerDTO);
		Computer computer = computerMapper.dtoToModel(computerDTO);
		boolean created =false;
		try {
			 created = computerDAO.create(computer);
		} catch (DatabaseQueryException e) {
			logger.error("Query error");
		}
		return created;
	}
	
	public List<ComputerDTO> getComputers() {
		List<Computer> computers = new ArrayList<Computer>();
		try {
			computers = computerDAO.findAll();
		} catch (DatabaseQueryException e) {
			logger.error("Query error");
		}
		List<ComputerDTO> computersDTO = (List<ComputerDTO>)computers
		.stream().map(s -> computerMapper.modelToDto(s))
		.collect(Collectors.toList());
		return computersDTO;
	}
	
	public List<ComputerDTO> getComputers(int limit, int currentPage) {
		List<Computer> computers = new ArrayList<Computer>();
		try {
			computers = computerDAO.findAllPaged(limit, currentPage);
		} catch (DatabaseQueryException e) {
			logger.error("Query error");
		}
		List<ComputerDTO> computersDTO = (List<ComputerDTO>)computers
		.stream().map(s -> computerMapper.modelToDto(s))
		.collect(Collectors.toList());
		return computersDTO;
	}
	
	public ComputerDTO findById(Long id)  {
		Computer computer = new Computer();
		try {
			computer = computerDAO.findById(id);
		} catch (DatabaseQueryException e) {
			logger.error("Query error");
		}
		ComputerDTO computerDTO = computerMapper.modelToDto(computer);
		return computerDTO;
	}
	
	public void update(ComputerDTO computerDTO)  {
		computerValidator.validate(computerDTO);
		Computer computer = computerMapper.dtoToModel(computerDTO);
		try {
			computerDAO.update(computer);
		} catch (DatabaseQueryException e) {
			logger.error("Query error");
		}
	}
	
	public void delete(String id)  {
		Long ids = Long.parseLong(id);
		try {
			computerDAO.delete(ids);
		} catch (DatabaseQueryException e) {
			logger.error("Query error");
		}
	}
}
