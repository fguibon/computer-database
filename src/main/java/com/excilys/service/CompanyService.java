package com.excilys.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.excilys.exceptions.DatabaseException;
import com.excilys.model.Company;
import com.excilys.model.Page;
import com.excilys.persistence.dao.CompanyDAO;

@Component
public class CompanyService {
	
	private static final Logger LOGGER = LogManager.getLogger(CompanyService.class);
	
	private CompanyDAO companyDAO;
	
	public CompanyService(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}	
	
	
	public List<Company> getCompanies() {
		List<Company> companies = new ArrayList<>();
		try {
			companies = companyDAO.findAll();
		} catch (DatabaseException e) {
			LOGGER.error(e.getMessage());
		}
		return companies;
	}
	

	public List<Company> getCompanies(Page page) {
		List<Company> companies = new ArrayList<>();
		try {
			companies = companyDAO.findAllPaged(page);
		} catch (DatabaseException e) {
			LOGGER.error("Query error : "+ e.getMessage());
		}
		return companies;
	}
	
	public Company findById(Long id)  {
		Company company = new Company();
		try {
			company = companyDAO.findById(id);
		} catch (DatabaseException e) {
			LOGGER.error("Query error : "+ e.getMessage());
		}
		return company;
	}
	 
}
