package com.excilys.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.binding.dto.ComputerDTO;
import com.excilys.binding.mapper.CompanyMapper;
import com.excilys.binding.mapper.ComputerMapper;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validator.Validator;

@Controller
public class AddComputerController {


	private final ComputerService computerService;
	private final CompanyService companyService;
	private final CompanyMapper companyMapper;
	private final ComputerMapper computerMapper;
	private final Validator validator;
	private static final Logger LOGGER = LogManager.getLogger(AddComputerController.class);


	public AddComputerController(ComputerService computerService,CompanyService companyService,
			CompanyMapper companyMapper, ComputerMapper computerMapper, Validator validator) {
		this.computerService = computerService;
		this.companyService = companyService;
		this.computerMapper = computerMapper;
		this.companyMapper = companyMapper;
		this.validator = validator;
	}
	
	@GetMapping("/add-computer")
	public String displayForm(Model model) {

		List<CompanyDTO> companyList = companyService.getCompanies()
				.stream().map(s -> companyMapper.modelToDto(s))
				.collect(Collectors.toList());
		model.addAttribute("companies", companyList);
		
		return "addComputer";
	}

	@PostMapping("/add-computer")
	public String addComputer(Model model, @ModelAttribute("computer") ComputerDTO computer, 
			RedirectAttributes attributes){

		try {
			validator.validateComputerToCreate(computer);
		} catch (Exception e) {
			LOGGER.warn(e.getMessage(), e);
		}

		try {
			computerService.createComputer(computerMapper.dtoToModel(computer));
		} catch (Exception e) {
			LOGGER.warn(e.getMessage(), e);
		} 
		return "dashboard";

	}
	
	@ModelAttribute("computer")
	public ComputerDTO getComputer() {
		return new ComputerDTO();
	}

}
