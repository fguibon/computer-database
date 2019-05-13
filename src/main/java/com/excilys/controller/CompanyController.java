package com.excilys.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.binding.mapper.CompanyMapper;
import com.excilys.model.Company;
import com.excilys.model.Page;
import com.excilys.service.CompanyService;


public class CompanyController {

	
	private static CompanyController instance =null ;
	private CompanyService companyService = CompanyService.getInstance(); 
	private CompanyMapper companyMapper = CompanyMapper.getInstance();
	
	private CompanyController() {
	}
	
	
	public static CompanyController getInstance() {
		return (instance!=null) ? instance : (instance = new CompanyController());
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
