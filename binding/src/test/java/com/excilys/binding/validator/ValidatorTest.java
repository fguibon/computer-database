package com.excilys.binding.validator;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.binding.dto.ComputerDTO;
import com.excilys.binding.mapper.ComputerMapper;
import com.excilys.binding.exceptions.DateParseException;
import com.excilys.binding.exceptions.ValidationException;
import com.excilys.config.BindingConfig;
import com.excilys.binding.validator.Validator;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BindingConfig.class)
public class ValidatorTest {

	private Validator validator;
	private ComputerDTO computerDto;
	private CompanyDTO companyDto;
	private String introduced;
	private String discontinued;
	
	private ComputerMapper computerMapperMock;	
	
	@Before
	public void setUp() throws DateParseException {
		validator = new Validator(computerMapperMock);
		introduced = "2017-05-08";
		discontinued = "2017-05-10";
		computerDto = new ComputerDTO.Builder().setId("1").setName("Mac Book")
				.setIntroduced(introduced).setDiscontinued(discontinued)
				.setCompanyId("1").setCompanyName("MC").build();
		companyDto = new CompanyDTO.Builder().setId("1").setName("Apple Inc.").build();
		
		Mockito.when(computerMapperMock.castLocalDate("")).thenReturn(null);
		Mockito.when(computerMapperMock.castLocalDate(introduced))
		.thenReturn(LocalDate.of(2017, Month.MAY, 8));
		Mockito.when(computerMapperMock.castLocalDate(discontinued))
		.thenReturn(LocalDate.of(2017, Month.MAY, 10));
	}
	
	@Test(expected = ValidationException.class)
	public void validateComputerToCreateTest() throws ValidationException, DateParseException {
		computerDto.setName("+++++++blabla");
		validator.validateComputerToCreate(computerDto);
	}
	
	@Test(expected = ValidationException.class)
	public void validateComputerToCreateDatesReverseTest() throws ValidationException, DateParseException {
		computerDto.setDiscontinued(introduced);
		computerDto.setIntroduced(discontinued);
		validator.validateComputerToCreate(computerDto);
	}
	
	@Test(expected = ValidationException.class)
	public void validateComputerToCreateDateNotValidTest() throws ValidationException, DateParseException {
		computerDto.setDiscontinued("blabla");
		validator.validateComputerToCreate(computerDto);
	}
	
	@Test
	public void validateComputerToCreateValidDataTest() throws ValidationException, DateParseException {
		computerDto.setName("blabla");
		validator.validateComputerToCreate(computerDto);
	}
	
	@Test(expected = ValidationException.class)
	public void validateComputerToUpdateTest() throws ValidationException, DateParseException {
		computerDto.setCompanyId("blabla");
		validator.validateComputerToUpdate(computerDto);
	}
	
	@Test(expected = ValidationException.class)
	public void validateComputerToUpdateDatesReverseTest() throws ValidationException, DateParseException {
		computerDto.setDiscontinued(introduced);
		computerDto.setIntroduced(discontinued);
		validator.validateComputerToCreate(computerDto);
	}
	
	@Test(expected = ValidationException.class)
	public void validateComputerToUpdateDateNotValidTest() throws ValidationException, DateParseException {
		computerDto.setIntroduced("blabla");
		validator.validateComputerToCreate(computerDto);
	}
	
	@Test
	public void validateComputerToUpdateValidDataTest() throws ValidationException, DateParseException {
		computerDto.setCompanyId("1");
		validator.validateComputerToUpdate(computerDto);
	}
	
	@Test(expected = ValidationException.class)
	public void validateCompanyTest() throws ValidationException {
		companyDto.setId("blabla");
		validator.validateCompany(companyDto);
	}
	
	@Test
	public void validateCompanyValidDataTest() throws ValidationException {
		companyDto.setId("1");
		validator.validateCompany(companyDto);
		companyDto.setId("");
		validator.validateCompany(companyDto);
	}

	@Autowired
	public void setComputerMapperMock(ComputerMapper computerMapperMock) {
		this.computerMapperMock = computerMapperMock;
	}
}
