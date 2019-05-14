package com.excilys.test.binding.mapper;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.binding.mapper.CompanyMapper;
import com.excilys.exceptions.MappingException;
import com.excilys.model.Company;
import com.excilys.test.config.TestConfig;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CompanyMapperTest {

	@Autowired
	private CompanyMapper companyMapper;
	
	@Test
	public void modelToDtoTest() {
		Company company = new Company.Builder().setId(3L).setName("John").build();
		CompanyDTO companyDTO = new CompanyDTO.Builder().setId("3").setName("John").build();
		assertEquals("Expected same company",companyDTO, companyMapper.modelToDto(company));
	}

	
	public void dtoToModelTest() throws MappingException {
		Company company = new Company.Builder().setId(20L).setName("Franck").build();
		CompanyDTO companyDTO = new CompanyDTO.Builder().setId("20").setName("Franck").build();
		assertEquals("Expected same company",company, companyMapper.dtoToModel(companyDTO));
	}

}
