package com.excilys.test.persistence.dao;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.excilys.config.AppConfig;
import com.excilys.exceptions.DatabaseException;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.persistence.dao.ComputerDAO;
import com.excilys.test.ScriptExecuter;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ComputerDAOTest {

	@Autowired
	private ComputerDAO computerDAO;
	
	@Autowired
	private ScriptExecuter executer;
	
	Company companyTest;
	Company companyTest2;
	Computer computerTest;
	List<Computer> computersTest = new ArrayList<Computer>();
	
	
	@Before
	public void setUp() throws Exception {
		executer.reload();
		
		companyTest = new Company.Builder().setId(1L).setName("Apple Inc.").build();
		companyTest2 = new Company.Builder().setId(2L).setName("Thinking Machines").build();
		computerTest = new Computer.Builder().setId(1L).setName("MacBook Pro 15.4 inch")
				.setCompany(companyTest).build();
		
		computersTest.add(computerTest);
		computersTest.add(new Computer.Builder().setId(2L).setName("CM-2a").setCompany(companyTest2).build());
		computersTest.add(new Computer.Builder().setId(3L).setName("CM-200").setCompany(companyTest2).build());
		computersTest.add(new Computer.Builder().setId(4L).setName("CM-5e").setCompany(companyTest2).build());
		computersTest.add(new Computer.Builder().setId(5L).setName("CM-5")
				.setIntroduced(LocalDate.of(1991,1,1)).setCompany(companyTest2).build());
		computersTest.add(new Computer.Builder().setId(6L).setName("MacBook Pro")
				.setIntroduced(LocalDate.of(2006,1,10)).setCompany(companyTest).build());
		computersTest.add(new Computer.Builder().setId(7L).setName("Apple IIe").setCompany(new Company()).build());
		computersTest.add(new Computer.Builder().setId(8L).setName("Apple IIc").setCompany(new Company()).build());
		computersTest.add(new Computer.Builder().setId(9L).setName("Apple IIGS").setCompany(new Company()).build());
		computersTest.add(new Computer.Builder().setId(10L).setName("Apple IIc Plus").setCompany(new Company()).build());
	}
	
	@Test
	public void createComputerTest() throws DatabaseException {
		Computer computerExpected = computerTest;
		computerDAO.create(computerExpected);
		computerExpected.setId(11L);
		
		Computer computerActual = computerDAO.findById(11L);
		
		assertEquals("Expected same companies",computerExpected, computerActual);
		
	}
	
	@Test
	public void findAllTest() throws DatabaseException {
		List<Computer> computersExpected = computersTest;
		
		List<Computer> computersActual = new ArrayList<Computer>();
		computersActual = computerDAO.findAll();
		
		assertEquals("List different from  expected",computersExpected, computersActual);
	}
	
	@Test
	public void findByIdTest() throws DatabaseException {
		Computer computerActual = computerDAO.findById(1L);
		
		assertEquals("Can't find computer",computerTest, computerActual);
	}
	
	@Test
	public void updateTest() throws DatabaseException {
		computerDAO.update(computerTest);
		Computer computerActual = computerDAO.findById(1L);
		
		assertEquals("Objects not equals",computerTest, computerActual);
	}
	
	@Test
	public void deleteTest() throws DatabaseException {
		Long id = 4L;
		assertNotNull(computerDAO.findById(id));
		computerDAO.delete(id);
		assertNull("Object not null",computerDAO.findById(id));
	}
	
}
