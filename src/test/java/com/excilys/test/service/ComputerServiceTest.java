package com.excilys.test.service;

import static org.junit.Assert.*;

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

import com.excilys.exceptions.DatabaseException;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.model.Sorting;
import com.excilys.persistence.dao.ComputerDAO;
import com.excilys.service.ComputerService;
import com.excilys.test.config.TestConfig;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ComputerServiceTest {

	List<Computer> computers;
	Computer computerTest;
	Company companyTest;
	Company companyTestModified;
	Page page;
	Sorting sorting;
	
	@Autowired
	ComputerService computerService;
	
	@Autowired
	ComputerDAO daoMock;
	
	@Before
	public void setUp() throws Exception {
		page = new Page(1, 1);
		sorting = new Sorting("","");
		computers = new ArrayList<Computer>();
		companyTest = new Company.Builder().setId(1L).setName("Apple Inc.").build();
		companyTestModified = new Company.Builder().setId(1L).setName("Apple").build();
		computerTest = new Computer.Builder().setId(1L)
				.setName("MacBook Pro 15.4 inch").setCompany(companyTest).build();
		computers.add(computerTest);
		
		Mockito.when(daoMock.create(computerTest)).thenReturn(true);
		Mockito.when(daoMock.findById(1L)).thenReturn(computerTest);
		Mockito.when(daoMock.findAllPaged(page,"",sorting)).thenReturn(computers);
		Mockito.when(daoMock.update(computerTest)).thenReturn(true);
		Mockito.when(daoMock.count()).thenReturn(10);
	}
	
	@Test
	public void createComputerTest() throws DatabaseException {
		assertTrue(computerService.createComputer(computerTest));
	}
	
	@Test
	public void finAllTest() throws DatabaseException {
		assertEquals("Expected same computers",computers,computerService.findAll(page, "", sorting));
	}
	
	@Test
	public void findbyIdTest() throws DatabaseException {
		assertEquals("Expected same computers",computerTest,computerService.findById(1L));
	}
	
	@Test
	public void updateTest() throws DatabaseException {
		computerTest.setCompany(companyTestModified);
		assertTrue(computerService.update(computerTest));
	}

}
