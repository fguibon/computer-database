package com.excilys.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.binding.mapper.CompanyMapper;
import com.excilys.exceptions.DatabaseException;
import com.excilys.model.Company;
import com.excilys.model.Page;
import com.excilys.service.CompanyService;


public class CompanyController {

	
	private static CompanyController instance =null ;
	private static final Logger logger = LogManager.getLogger(CompanyController.class);
	
	private CompanyService companyService = CompanyService.getInstance(); 
	private CompanyMapper companyMapper = CompanyMapper.getInstance();
	
	private CompanyController() {
	}
	
	
	public static CompanyController getInstance() {
		return (instance!=null) ? instance : (instance = new CompanyController());
	}
	
	
	public List<CompanyDTO> getCompanies() {
		List<Company> companies = new ArrayList<Company>();
		companies = companyService.getCompanies();
		List<CompanyDTO> companiesDTO = (List<CompanyDTO>)companies
		.stream().map(s -> companyMapper.modelToDto(s))
		.collect(Collectors.toList());
		return companiesDTO;
	}
	

	public List<CompanyDTO> getCompanies(Page page) {
		List<Company> companies = new ArrayList<Company>();
		companies = companyService.getCompanies(page);
		List<CompanyDTO> companiesDTO = (List<CompanyDTO>)companies
		.stream().map(s -> companyMapper.modelToDto(s))
		.collect(Collectors.toList());
		return companiesDTO;
	}
	
	public CompanyDTO findById(Long id) throws DatabaseException  {
		Company company = companyService.findById(id);

		CompanyDTO companyDTO = companyMapper.modelToDto(company);
		return companyDTO;
	}
}
