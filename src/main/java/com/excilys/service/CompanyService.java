package com.excilys.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.exceptions.DatabaseException;
import com.excilys.model.Company;
import com.excilys.model.Page;
import com.excilys.persistence.dao.CompanyDAO;

public class CompanyService {
	
	private static CompanyService instance =null ;
	private static final Logger logger = LogManager.getLogger(CompanyService.class);
	
	private CompanyDAO companyDAO = CompanyDAO.getInstance(); 
	private CompanyService() {
	}
	
	
	public static CompanyService getInstance() {
		return (instance!=null) ? instance : (instance = new CompanyService());
	}
	
	
	public List<Company> getCompanies() {
		List<Company> companies = new ArrayList<>();
		try {
			companies = companyDAO.findAll();
		} catch (DatabaseException e) {
			logger.error(e.getMessage());
		}
		return companies;
	}
	

	public List<Company> getCompanies(Page page) {
		List<Company> companies = new ArrayList<>();
		try {
			companies = companyDAO.findAllPaged(page);
		} catch (DatabaseException e) {
			logger.error("Query error : "+ e.getMessage());
		}
		return companies;
	}
	
	public Company findById(Long id)  {
		Company company = new Company();
		try {
			company = companyDAO.findById(id);
		} catch (DatabaseException e) {
			logger.error("Query error : "+ e.getMessage());
		}
		return company;
	}
	 
}
