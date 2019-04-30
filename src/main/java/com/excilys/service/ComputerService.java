package com.excilys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.excilys.binding.dto.ComputerDTO;
import com.excilys.binding.mapper.ComputerMapper;
import com.excilys.exception.DatabaseQueryException;
import com.excilys.model.Computer;
import com.excilys.persistence.dao.CompanyDAO;
import com.excilys.persistence.dao.ComputerDAO;
import com.excilys.validator.ComputerValidator;

public class ComputerService {

	private static ComputerService instance = null;
	
	private static final Logger logger = 
			LogManager.getLogger(ComputerService.class);
	
	private ComputerDAO computerDAO = ComputerDAO.getInstance();
	private ComputerMapper computerMapper = ComputerMapper.getInstance(CompanyDAO.getInstance());
	ComputerValidator computerValidator = new ComputerValidator();
	
	private ComputerService() {}
	
	public static ComputerService getInstance() {
		return (instance!=null) ? instance : (instance = new ComputerService());
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
			logger.error("Query error : " + e.getMessage());
		}
		return created;
	}
	
	public List<ComputerDTO> getComputers() {
		List<Computer> computers = new ArrayList<Computer>();
		try {
			computers = computerDAO.findAll();
		} catch (DatabaseQueryException e) {
			logger.error("Query error : "+ e.getMessage());
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
			logger.error("Query error : " + e.getMessage());
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
			logger.error("Query error :" + e.getMessage());
		}
		ComputerDTO computerDTO = computerMapper.modelToDto(computer);
		return computerDTO;
	}
	
	public boolean update(ComputerDTO computerDTO)  {
		computerValidator.validate(computerDTO);
		Computer computer = computerMapper.dtoToModel(computerDTO);
		boolean updated =false;
		try {
			updated = computerDAO.update(computer);
		} catch (DatabaseQueryException e) {
			logger.error("Query error : " + e.getMessage());
		}
		return updated;
	}
	
	public boolean delete(Long id)  {
		boolean deleted =false;
		try {
			deleted = computerDAO.delete(id);
		} catch (DatabaseQueryException e) {
			logger.error("Query error : " + e.getMessage());
		}
		return deleted;
	}
	
	public int count() {
		int count =0;
		try {
			count = computerDAO.count();
		} catch (DatabaseQueryException e){
			logger.error("Query error : " + e.getMessage());
		}
		return count;
	}
}
