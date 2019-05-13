package com.excilys.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.binding.mapper.CompanyMapper;
import com.excilys.model.Company;
import com.excilys.model.Page;
import com.excilys.service.CompanyService;

@Component
public class CompanyController {

	private CompanyService companyService; 
	private CompanyMapper companyMapper;
	
	public CompanyController(CompanyService companyService,CompanyMapper companyMapper) {
		this.companyService = companyService;
		this.companyMapper = companyMapper;
	}	
	
	
	public List<CompanyDTO> getCompanies() {
		List<Company> companies = companyService.getCompanies();
		return companies
		.stream().map(s -> companyMapper.modelToDto(s))
		.collect(Collectors.toList());

	}
	

	public List<CompanyDTO> getCompanies(Page page) {
		List<Company> companies = companyService.getCompanies(page);
		return companies
		.stream().map(s -> companyMapper.modelToDto(s))
		.collect(Collectors.toList());	
	}
	
	public CompanyDTO findById(Long id)  {
		Company company = companyService.findById(id);
		return companyMapper.modelToDto(company);
	}
}
