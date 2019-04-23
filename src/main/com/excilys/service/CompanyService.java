package main.com.excilys.service;

import java.util.List;
import java.util.stream.Collectors;

import main.com.excilys.binding.dto.CompanyDTO;
import main.com.excilys.binding.mapper.CompanyMapper;
import main.com.excilys.model.Company;
import main.com.excilys.model.Page;
import main.com.excilys.persistence.dao.CompanyDAO;

public class CompanyService {
	
	private static CompanyService instance = new CompanyService();
	
	private CompanyDAO companyDAO = CompanyDAO.getInstance();
	private CompanyMapper companyMapper = CompanyMapper.getInstance();
	
	private CompanyService() {}
	
	public static CompanyService getInstance() {
		return instance;
	}
	
	public List<CompanyDTO> getCompanies() {
		List<Company> companies = companyDAO.findAll();
		List<CompanyDTO> companiesDTO = (List<CompanyDTO>)companies
		.stream().map(s -> companyMapper.modelToDto(s))
		.collect(Collectors.toList());
		return companiesDTO;
	}
	

	public List<CompanyDTO> getCompanies(Page page, int currentPage) {
		List<Company> companies = companyDAO.findAllPaged(page.getEntriesPerPage(), currentPage);
		List<CompanyDTO> companiesDTO = (List<CompanyDTO>)companies
		.stream().map(s -> companyMapper.modelToDto(s))
		.collect(Collectors.toList());
		return companiesDTO;
	}
	 
}
