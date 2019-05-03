package com.excilys.test.persistence.dao;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.excilys.exception.DatabaseQueryException;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.persistence.dao.ComputerDAO;
import com.excilys.test.ScriptExecuter;

public class ComputerDAOTest {

	ComputerDAO computerDAO;
	
	Company companyTest = new Company(1L,"Apple Inc.");
	Company companyTest2 = new Company(2L,"Thinking Machines");
	Computer computerTest = new Computer(1L,"MacBook Pro 15.4 inch",
			null,null,companyTest);
	List<Computer> computersTest = new ArrayList<Computer>();
	
	
	@Before
	public void setUp() throws Exception {
		ScriptExecuter.getInstance().reload();
		computerDAO = ComputerDAO.getInstance();
		computersTest.add(computerTest);
		computersTest.add(new Computer(2L,"CM-2a",null,null,companyTest2));
		computersTest.add(new Computer(3L,"CM-200",null,null,companyTest2));
		computersTest.add(new Computer(4L,"CM-5e",null,null,companyTest2));
		computersTest.add(new Computer(5L,"CM-5",LocalDate.of(1991,1,1),null,companyTest2));
		computersTest.add(new Computer(6L,"MacBook Pro",LocalDate.of(2006,1,10),null,companyTest));
		computersTest.add(new Computer(7L,"Apple IIe",null,null,new Company()));
		computersTest.add(new Computer(8L,"Apple IIc",null,null,new Company()));
		computersTest.add(new Computer(9L,"Apple IIGS",null,null,new Company()));
		computersTest.add(new Computer(10L,"Apple IIc Plus",null,null,new Company()));
	}
	
	@Test
	public void testCreateComputer_Success() throws DatabaseQueryException {
		Computer computerExpected = computerTest;
		computerDAO.create(computerExpected);
		computerExpected.setId(11L);
		
		Computer computerActual = computerDAO.findById(11L);
		
		assertEquals("Expected same companies",computerExpected, computerActual);
		
	}
	
	@Test
	public void testFindAll_Success() throws DatabaseQueryException {
		List<Computer> computersExpected = computersTest;
		
		List<Computer> computersActual = new ArrayList<Computer>();
		computersActual = computerDAO.findAll();
		
		assertEquals("List different from  expected",computersExpected, computersActual);
	}
	
	@Test
	public void testFindById_Success() throws DatabaseQueryException {
		Computer computerActual = computerDAO.findById(1L);
		
		assertEquals("Can't find computer",computerTest, computerActual);
	}
	
	@Test
	public void testUpdate_Success() throws DatabaseQueryException {
		Computer computerExpected = new Computer(1L,"MacBook Pro 16 inch",
				null,null,companyTest);
		computerDAO.update(computerExpected);
		Computer computerActual = computerDAO.findById(1L);
		
		assertEquals("Objects not equals",computerExpected, computerActual);
	}
	
	@Test
	public void testDelete_Success() throws DatabaseQueryException {
		Long id = 4L;
		assertNotNull(computerDAO.findById(id));
		computerDAO.delete(id);
		assertNull("Object not null",computerDAO.findById(id));
	}
	
}
