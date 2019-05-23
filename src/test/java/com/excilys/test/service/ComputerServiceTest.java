package com.excilys.test.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.excilys.exceptions.DatabaseException;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.Sorting;
import com.excilys.persistence.dao.ComputerDAO;
import com.excilys.service.ComputerService;
import com.excilys.test.config.TestConfig;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ComputerServiceTest {

	private ComputerService computerService;
	private List<Computer> computers;
	private Computer computerTest;
	private Company companyTest;
	private Sorting sorting;
	
	private ComputerDAO daoMock;

	@Before
	public void setUp() throws Exception {
		
		computerService = new ComputerService(daoMock);
		sorting = new Sorting(1, 1,"","","");
		computers = new ArrayList<Computer>();
		companyTest = new Company.Builder().setId(1L).setName("Apple Inc.").build();
		computerTest = new Computer.Builder().setId(1L)
				.setName("MacBook Pro 15.4 inch").setCompany(companyTest).build();
		computers.add(computerTest);
		
		Mockito.when(daoMock.create(computerTest)).thenReturn(1);
		Mockito.when(daoMock.findById(1L)).thenReturn(computerTest);
		Mockito.when(daoMock.findAllPaged(sorting)).thenReturn(computers);
		Mockito.when(daoMock.update(computerTest)).thenReturn(1);
		Mockito.when(daoMock.delete(1L)).thenReturn(1);
		Mockito.when(daoMock.count()).thenReturn(10);
		
		Mockito.doThrow(DatabaseException.class).when(daoMock).delete(0L);
		Mockito.when(daoMock.count()).thenReturn(10);
	}
	
	@Test
	public void createComputerTest() throws DatabaseException {
		assertEquals(1,computerService.createComputer(computerTest));
	}
	
	@Test
	public void finAllTest() throws DatabaseException {
		assertEquals("Expected same computers",computers,computerService.findAll(sorting));
	}
	
	@Test
	public void findbyIdTest() throws DatabaseException {
		assertEquals("Expected same computers",computerTest,computerService.findById(1L));
	}
	
	@Test
	public void updateTest() throws DatabaseException {
		computerTest.setName("Mc Book");
		assertEquals(1,computerService.update(computerTest));
	}
	
	@Test(expected = DatabaseException.class)
	public void deleteExpectExceptionTest() throws Exception {
		computerService.delete(0L);
	}
	
	@Test
	public void deleteTest() throws Exception {
		assertEquals(1,computerService.delete(1L));
	}
	
	@Test
	public void countTest() throws DatabaseException {
		assertEquals(10,computerService.count());
	}
	
	@Autowired
	public void setDaoMock(ComputerDAO daoMock) {
		this.daoMock = daoMock;
	}

}
