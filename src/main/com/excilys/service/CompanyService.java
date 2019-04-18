package main.com.excilys.service;

import java.util.List;

import main.com.excilys.model.Company;
import main.com.excilys.persistence.CompanyDAO;

public class CompanyService {

	private CompanyDAO companyDAO = CompanyDAO.getInstance();
	
	public CompanyService() {
	}
	
	public List<Company> getCompanies() {
		return companyDAO.findAll();
	}
	

	public List<Company> getCompanies(int limit, int currentPage) {
		return companyDAO.findAllPaged(limit,currentPage);
	}
	 
}
