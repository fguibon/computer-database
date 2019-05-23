package com.excilys.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.excilys.exceptions.DatabaseException;
import com.excilys.model.Company;
import com.excilys.model.Sorting;
import com.excilys.persistence.dao.CompanyDAO;
import com.excilys.persistence.dao.ComputerDAO;

@Service
public class CompanyService {
	
	private static final Logger LOGGER = LogManager.getLogger(CompanyService.class);
	
	private CompanyDAO companyDAO;
	private ComputerDAO computerDAO;
	
	public CompanyService(CompanyDAO companyDAO, ComputerDAO computerDAO) {
		this.companyDAO = companyDAO;
		this.computerDAO = computerDAO;
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
	

	public List<Company> getCompanies(Sorting sorting) {
		List<Company> companies = new ArrayList<>();
		try {
			companies = companyDAO.findAllPaged(sorting);
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
	
	public int delete(Long id) {
		int number=0;
		try {
			computerDAO.deleteComputerWhere(id);
			number = companyDAO.delete(id);
		} catch (DatabaseException e) {
			LOGGER.error(e.getMessage());
		}
		return number;
	}
	 
}
