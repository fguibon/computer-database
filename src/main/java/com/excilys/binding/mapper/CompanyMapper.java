package com.excilys.binding.mapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.exceptions.MappingException;
import com.excilys.model.Company;

public class CompanyMapper {

	private static final Logger logger = LogManager.getLogger(ComputerMapper.class);
	
	private static CompanyMapper instance = null;
	
	private CompanyMapper() {}
	
	public static CompanyMapper getInstance() {
		return (instance!=null) ? instance : (instance =new CompanyMapper());
	}
	
	
	public Company dtoToModel(CompanyDTO companyDTO) throws MappingException {
		Company company = new Company();
		try {
			company.setId(this.convertStringToId(companyDTO.getId()));
		} catch (NumberFormatException e) {
			logger.error(e.getMessage(),e);
			throw new MappingException();
		}
		
		company.setName(companyDTO.getName());

		return company;
	}

	
	public CompanyDTO modelToDto(Company company) {
		CompanyDTO companyDTO  = new CompanyDTO();
		companyDTO.setId(this.convertIdToString(company.getId()));
		companyDTO.setName(company.getName());

		return companyDTO;
	}
	
	private String convertIdToString(Long id) {
		return (id == null || id == 0) ? null : String.valueOf(id);
	}
	
	
	private Long convertStringToId(String id) {
		return (id == null || "0".equals(id)) ? null : Long.valueOf(id);
	}
}
