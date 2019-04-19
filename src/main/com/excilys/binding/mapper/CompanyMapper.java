package main.com.excilys.binding.mapper;

import main.com.excilys.binding.dto.CompanyDTO;
import main.com.excilys.model.Company;

public class CompanyMapper {

	private static CompanyMapper instance = new CompanyMapper();
	
	private CompanyMapper() {}
	
	public static CompanyMapper getInstance() {
		return instance;
	}
	
	
	public Company dtoToModel(CompanyDTO companyDTO) {
		Company company = new Company();
		company.setId(Long.parseLong(companyDTO.getId()));
		company.setName(companyDTO.getName());
		return company;
	}

	
	public CompanyDTO modelToDto(Company company) {
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setId(Long.toString(company.getId()));
		companyDTO.setName(company.getName());
		return companyDTO;
}
}
