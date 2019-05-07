package com.excilys.service;

import java.util.List;
import java.util.stream.Collectors;

import com.excilys.binding.dto.ComputerDTO;
import com.excilys.binding.mapper.ComputerMapper;
import com.excilys.exceptions.DatabaseException;
import com.excilys.exceptions.DateParseException;
import com.excilys.exceptions.MappingException;
import com.excilys.exceptions.ValidationException;
import com.excilys.model.Computer;
import com.excilys.persistence.dao.ComputerDAO;
import com.excilys.validator.Validator;

public class ComputerService {

	private static ComputerService instance = null;
	
	
	private ComputerDAO computerDAO = ComputerDAO.getInstance();
	private ComputerMapper computerMapper = ComputerMapper.getInstance();
	Validator computerValidator = Validator.getInstance();
	
	private ComputerService() {}
	
	public static ComputerService getInstance() {
		return (instance!=null) ? instance : (instance = new ComputerService());
	}
	
	
	public boolean createComputer(ComputerDTO computerDTO) throws ValidationException, DateParseException, MappingException, DatabaseException  {
		computerValidator.validateComputerToCreate(computerDTO);
		Computer computer = computerMapper.dtoToModel(computerDTO);
		boolean created = computerDAO.create(computer);
		return created;
	}
	
	public List<ComputerDTO> getComputers(int limit, int currentPage, String filter, String field, String order) throws DatabaseException {
		List<Computer> computers = computerDAO.findAllPaged(filter, field, order, limit, currentPage);
		
		List<ComputerDTO> computersDTO = (List<ComputerDTO>)computers
		.stream().map(s -> computerMapper.modelToDto(s))
		.collect(Collectors.toList());
		return computersDTO;
	}
	
	public ComputerDTO findById(Long id) throws DatabaseException  {
		Computer computer = computerDAO.findById(id);

		ComputerDTO computerDTO = computerMapper.modelToDto(computer);
		return computerDTO;
	}
	
	public boolean update(ComputerDTO computerDTO) throws DatabaseException, ValidationException, DateParseException, MappingException  {
		computerValidator.validateComputerToUpdate(computerDTO);
		boolean updated = computerDAO.update(computerMapper.dtoToModel(computerDTO));
		return updated;
	}
	
	public boolean delete(Long id) throws DatabaseException  {
		boolean deleted = computerDAO.delete(id);
		return deleted;
	}
	
	public int count() throws DatabaseException {
		int count =  computerDAO.count();
		return count;
	}
}
