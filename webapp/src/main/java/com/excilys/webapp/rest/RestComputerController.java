package com.excilys.webapp.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.binding.dto.ComputerDTO;
import com.excilys.binding.mapper.ComputerMapper;
import com.excilys.core.Computer;
import com.excilys.service.ComputerService;
import com.google.common.base.Preconditions;

@RestController
@RequestMapping("/api/computers")
@PreAuthorize("permitAll")
public class RestComputerController {

	private ComputerService computerService;
	private ComputerMapper computerMapper;

	
	public RestComputerController(ComputerService computerService,ComputerMapper computerMapper) {
		this.computerService = computerService;
		this.computerMapper = computerMapper;
	}
	
	@GetMapping
	public List<ComputerDTO> findAll() {
		List<Computer> computers = computerService.findAll();
		return computers
					.stream().map(computerMapper::modelToDto)
					.collect(Collectors.toList());
	}

	
	 @GetMapping(value = "/{id}")
	 public ComputerDTO findById(@PathVariable("id") Long id) {
	        return RestPreConditions.checkFound(computerMapper.modelToDto(computerService.findById(id)));
	 }
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Long create(@Valid @RequestBody ComputerDTO resource){
		Preconditions.checkNotNull(resource);
		return computerService.createComputer(computerMapper.dtoToModel(resource));
	}

	
	@PutMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable("id") Long id, @Valid @RequestBody ComputerDTO resource) {
		Preconditions.checkNotNull(resource);
        RestPreConditions.checkFound(computerService.findById(Long.valueOf(resource.getId())));
		computerService.update(computerMapper.dtoToModel(resource));
	}
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable("id") Long id){
		computerService.delete(id);	
	}
	
}
