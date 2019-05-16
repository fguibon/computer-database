package com.excilys.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.binding.dto.ComputerDTO;
import com.excilys.binding.mapper.ComputerMapper;
import com.excilys.controller.ComputerController;
import com.excilys.exceptions.DatabaseException;
import com.excilys.exceptions.DateParseException;
import com.excilys.exceptions.MappingException;
import com.excilys.exceptions.ValidationException;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.model.Sorting;
import com.excilys.service.ComputerService;
import com.excilys.test.config.TestConfig;
import com.excilys.validator.Validator;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ComputerControllerTest {

	private ComputerController computerController;
	private Page page;
	private Sorting sorting;
	
	private List<Computer> computers;
	private Computer computer;
	private Company company;
	
	private List<ComputerDTO> computersDTO;
	private ComputerDTO computerDto;
	private CompanyDTO companyDto;	
	
	@Autowired
	private ComputerService computerServiceMock;
	
	@Autowired
	private ComputerMapper computerMapperMock;
	
	@Autowired
	private Validator computerValidatorMock;
	
	@Before
	public void setUp() throws Exception {
		
		computerController = new ComputerController(computerServiceMock, computerMapperMock, computerValidatorMock);
		page = new Page(1, 1);
		sorting = new Sorting("","");
		
		computers = new ArrayList<Computer>();
		company = new Company.Builder().setId(1L).setName("Apple Inc.").build();
		computer = new Computer.Builder().setId(1L)
				.setName("MacBook Pro 15.4 inch").setCompany(company).build();
		computers.add(computer);
		
		computersDTO = new ArrayList<ComputerDTO>();
		computerDto = new ComputerDTO.Builder().setId("1")
				.setName("MacBook Pro 15.4 inch").setCompanyId("1").setCompanyName("Apple Inc.").build();
		companyDto = new CompanyDTO.Builder().setId("1").setName("Apple Inc.").build();
		computersDTO.add(computerDto);
		
		Mockito.when(computerServiceMock.createComputer(computer)).thenReturn(1);
		Mockito.when(computerServiceMock.findById(1L)).thenReturn(computer);
		Mockito.when(computerServiceMock.findAll(page,"",sorting)).thenReturn(computers);
		Mockito.when(computerServiceMock.update(computer)).thenReturn(1);
		Mockito.doThrow(DatabaseException.class).when(computerServiceMock).delete(2L);
		Mockito.when(computerServiceMock.count()).thenReturn(10);
		Mockito.when(computerServiceMock.findById(3L)).thenReturn(null);
		
		Mockito.when(computerMapperMock.dtoToModel(computerDto)).thenReturn(computer);
		Mockito.when(computerMapperMock.modelToDto(computer)).thenReturn(computerDto);
		
	}
	
	@Test
	public void createComputerTest() throws ValidationException, DateParseException, MappingException, DatabaseException {
		assertEquals(1,computerController.createComputer(computerDto));
	}
	
	@Test
	public void finAllTest() throws DatabaseException {
		assertEquals("Expected same computers",computersDTO,computerController.findAll(page, "", sorting));
	}
	
	@Test
	public void findbyIdTest() throws DatabaseException {
		assertEquals("Expected same computers",computerDto,computerController.findById(1L));
	}
	
	@Test
	public void updateTest() throws DatabaseException, ValidationException, DateParseException, MappingException {
		computer.setName("Mc Book");
		assertEquals(1,computerController.update(computerMapperMock.modelToDto(computer)));
	}
	
	@Test(expected = DatabaseException.class)
	public void deleteExpectExceptionTest() throws Exception {
		computerController.delete(2L);
	}
	
	public void deleteSuccessTest() throws DatabaseException {
		computerController.delete(3L);
		assertNull("Expected null", computerController.findById(3L));
	}
	
	@Test
	public void countTest() throws DatabaseException {
		assertSame(10,computerController.count());
	}

	
}
