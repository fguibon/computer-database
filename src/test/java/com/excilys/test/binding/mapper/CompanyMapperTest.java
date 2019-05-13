package com.excilys.test.binding.mapper;

import static org.junit.Assert.*;

import org.junit.Test;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.binding.mapper.CompanyMapper;
import com.excilys.exceptions.MappingException;
import com.excilys.model.Company;

public class CompanyMapperTest {

	
	@Test
	public void modelToDTO_ValidData() {
		Company company = new Company.Builder().setId(3L).setName("John").build();
		CompanyDTO companyDTO = new CompanyDTO.Builder().setId("3").setName("John").build();
		assertEquals("Expected same company",companyDTO, CompanyMapper.getInstance().modelToDto(company));
	}

	
	public void DTOToModel_SameData() throws MappingException {
		Company company = new Company.Builder().setId(20L).setName("Franck").build();
		CompanyDTO companyDTO = new CompanyDTO.Builder().setId("20").setName("Franck").build();
		assertEquals("Expected same company",company, CompanyMapper.getInstance().dtoToModel(companyDTO));
	}

}
