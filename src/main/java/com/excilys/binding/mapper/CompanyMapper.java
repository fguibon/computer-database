package com.excilys.binding.mapper;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.model.Company;

public class CompanyMapper {

	private static CompanyMapper instance = new CompanyMapper();
	
	private CompanyMapper() {}
	
	public static CompanyMapper getInstance() {
		return instance;
	}
	
	
	public Company dtoToModel(CompanyDTO companyDTO) {
		Company company = null;
		if(companyDTO!=null) {
			company = new Company(Long.parseLong(companyDTO.getId()),
					companyDTO.getName());
			return company;
		}
		return company;
	}

	
	public CompanyDTO modelToDto(Company company) {
		CompanyDTO companyDTO = null;
		if(company !=null) {
			companyDTO = new CompanyDTO(Long.toString(company.getId()),
					company.getName());
		}
		return companyDTO;
}
}
