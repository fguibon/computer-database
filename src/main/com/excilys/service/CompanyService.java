package main.com.excilys.service;

import java.util.List;

import main.com.excilys.model.Company;
import main.com.excilys.persistence.CompanyDAO;

public class CompanyService {

	private CompanyDAO companyDAO;
	
	public CompanyService(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}
	
	public List<Company> getCompanies() {
		return companyDAO.findAll();
	}
	 
}
