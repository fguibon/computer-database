package com.excilys.test.binding.mapper;

import static org.junit.Assert.*;

import org.junit.Test;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.binding.mapper.CompanyMapper;
import com.excilys.model.Company;

public class CompanyMapperTest {

	@Test
	public void modelToDTO_ValidData() {
		Company company = new Company(3L,"John");
		CompanyDTO companyDTO = new CompanyDTO("3","John");
		assertEquals("Expected same company",companyDTO, CompanyMapper.getInstance().modelToDto(company));
	}

	
	
	
	public void DTOToModel_SameData() {
		Company company = new Company(20L,"Franck");
		CompanyDTO companyDTO = new CompanyDTO("20","Franck");
		assertEquals("Expected same company",company, CompanyMapper.getInstance().dtoToModel(companyDTO));
	}

}
