package com.excilys.test.binding.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.binding.mapper.CompanyMapper;
import com.excilys.config.AppConfig;
import com.excilys.exceptions.MappingException;
import com.excilys.model.Company;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class CompanyMapperTest {

	@Autowired
	private CompanyMapper companyMapper;	
	
	private Company company = new Company.Builder().setId(20L).setName("Franck").build();
	private CompanyDTO companyDTO = new CompanyDTO.Builder().setId("20").setName("Franck").build();
	
	@Test
	public void modelToDtoTest() {
		assertEquals("Expected same company",companyDTO, companyMapper.modelToDto(company));
	}

	@Test
	public void dtoToModelTest() throws MappingException {
		assertEquals("Expected same company",company, companyMapper.dtoToModel(companyDTO));
	}

}