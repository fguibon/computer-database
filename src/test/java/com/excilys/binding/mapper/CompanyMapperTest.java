package com.excilys.binding.mapper;

import static org.junit.Assert.*;

import org.junit.Test;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.model.Company;

public class CompanyMapperTest {

	@Test
	public void modelToDTOtest() {
		Company company = new Company(3L,"John");
		CompanyDTO companyDTO = new CompanyDTO("3","John");
		assertEquals(companyDTO, CompanyMapper.getInstance().modelToDto(company));
	}
	
	public void DTOToModel() {
		Company company = new Company(20L,"Franck");
		CompanyDTO companyDTO = new CompanyDTO("20","Franck");
		assertEquals(company, CompanyMapper.getInstance().dtoToModel(companyDTO));
	}

}
