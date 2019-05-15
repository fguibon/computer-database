package com.excilys.test.validator;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.binding.dto.ComputerDTO;
import com.excilys.config.AppConfig;
import com.excilys.exceptions.DateParseException;
import com.excilys.exceptions.ValidationException;
import com.excilys.validator.Validator;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ValidatorTest {

	@Autowired
	private Validator validator;
	
	private ComputerDTO computerDto;
	private CompanyDTO companyDto;	
	
	@Before
	public void setUp() {
		computerDto = new ComputerDTO.Builder().setId("1").setName("Mac Book").build();
		companyDto = new CompanyDTO.Builder().setId("1").setName("Apple Inc.").build();
	}
	
	@Test
	public void validateComputerToCreateTest() throws ValidationException, DateParseException {
		assertTrue(validator.validateComputerToCreate(computerDto));
	}
	
	@Test
	public void validateComputerToUpdateTest() throws ValidationException, DateParseException {
		assertTrue(validator.validateComputerToUpdate(computerDto));
	}
	
	@Test
	public void validateCompany() throws ValidationException {
		assertTrue(validator.validateCompany(companyDto));
	}
}
