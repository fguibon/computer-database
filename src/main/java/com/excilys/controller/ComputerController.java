package com.excilys.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.excilys.binding.dto.ComputerDTO;
import com.excilys.binding.mapper.ComputerMapper;
import com.excilys.exceptions.DatabaseException;
import com.excilys.exceptions.DateParseException;
import com.excilys.exceptions.MappingException;
import com.excilys.exceptions.ValidationException;
import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.model.Sorting;
import com.excilys.service.ComputerService;
import com.excilys.validator.Validator;

@Component
public class ComputerController {

	private ComputerService computerService;
	private ComputerMapper computerMapper;
	private Validator computerValidator;
	
	public ComputerController(ComputerService computerService,
			ComputerMapper computerMapper, Validator computerValidator) {
		this.computerService = computerService;
		this.computerMapper = computerMapper;
		this.computerValidator = computerValidator;
	}
	
	
	public boolean createComputer(ComputerDTO computerDTO) throws ValidationException, DateParseException, MappingException, DatabaseException  {
		computerValidator.validateComputerToCreate(computerDTO);
		Computer computer = computerMapper.dtoToModel(computerDTO);
		return computerService.createComputer(computer);

	}
	
	public List<ComputerDTO> findAll(Page page, String filter, Sorting sorting) throws DatabaseException {
		List<Computer> computers = computerService.findAll(page,filter, sorting);
		return computers
		.stream().map(s -> computerMapper.modelToDto(s))
		.collect(Collectors.toList());

	}
	
	public ComputerDTO findById(Long id) throws DatabaseException  {
		Computer computer = computerService.findById(id);
		return computerMapper.modelToDto(computer);

	}
	
	public boolean update(ComputerDTO computerDTO) throws DatabaseException, ValidationException, DateParseException, MappingException  {
		computerValidator.validateComputerToUpdate(computerDTO);
		return computerService.update(computerMapper.dtoToModel(computerDTO));

	}
	
	public void delete(Long id) throws DatabaseException  {
		computerService.delete(id);
	}
	
	public int count() throws DatabaseException {
		return computerService.count();
	}
	
}
