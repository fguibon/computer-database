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
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.binding.dto.ComputerDTO;
import com.excilys.binding.mapper.CompanyMapper;
import com.excilys.binding.mapper.ComputerMapper;
import com.excilys.exceptions.DatabaseException;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validator.Validator;

@Controller
public class EditComputerController  {


	private final ComputerService computerService;
	private final CompanyService companyService;
	private final ComputerMapper computerMapper;
	private final CompanyMapper companyMapper;
	private final Validator validator;

	private static final Logger LOGGER = LogManager.getLogger(EditComputerController.class);


	public EditComputerController(ComputerService computerService,CompanyService companyService,
			CompanyMapper companyMapper, ComputerMapper computerMapper, Validator validator) {
		this.computerService = computerService;
		this.companyService = companyService;
		this.computerMapper = computerMapper;
		this.companyMapper = companyMapper;
		this.validator = validator;
	}


	@GetMapping("/edit-computer")
	public String displayForm(Model model, @RequestParam String id){

		ComputerDTO computer =new ComputerDTO();
		Long idParam =(id == null || "0".equals(id) || id.isEmpty()) ? null : Long.valueOf(id);
		try {
			computer = computerMapper.modelToDto(computerService.findById(idParam));
		} catch (DatabaseException e) {
			LOGGER.warn(e.getMessage(), e);
		}	

		List<CompanyDTO> companyList = companyService.getCompanies()
				.stream().map(s -> companyMapper.modelToDto(s)).collect(Collectors.toList());

		model.addAttribute("computer", computer);
		model.addAttribute("companies", companyList);

		return "editComputer";
	}

	
	@PostMapping("/edit-computer")
	public String editComputer(Model model, @ModelAttribute("computer") ComputerDTO computer) {

		try {
			validator.validateComputerToUpdate(computer);
		} catch (Exception e) {
			LOGGER.warn(e.getMessage(), e);
		}

		try {
			computerService.update(computerMapper.dtoToModel(computer));
		} catch (Exception e) {
			LOGGER.warn(e.getMessage(), e);
		} 
		return "redirect:/dashboard";
	}
	
	@ModelAttribute("computer")
	public ComputerDTO getComputer() {
		return new ComputerDTO();
	}

}
