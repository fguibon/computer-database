package com.excilys.test.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.excilys.binding.exceptions.DatabaseException;
import com.excilys.core.Company;
import com.excilys.core.Computer;
import com.excilys.core.Sorting;
import com.excilys.persistence.dao.ComputerDAO;
import com.excilys.test.ScriptExecuter;
import com.excilys.test.config.TestConfig;
import com.zaxxer.hikari.HikariDataSource;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ComputerDAOTest {

	private ComputerDAO computerDAO;
	private ScriptExecuter executer;
	
	private List<Computer> computers;
	private Company companyTest;
	private Company companyTest2;
	private Computer computerTest;
	private Sorting sorting;

	private HikariDataSource dataSource;
	private SessionFactory sessionFactory;
	
	@Before
	public void setUp() throws Exception {
		executer = new ScriptExecuter(dataSource);
		executer.reload();
		
		computerDAO = new ComputerDAO(sessionFactory);
		
		computers = new ArrayList<Computer>();
		sorting = new Sorting(1, 10,"","","");
		
		companyTest = new Company.CompanyBuilder().id(1L).name("Apple Inc.").build();
		companyTest2 = new Company.CompanyBuilder().id(2L).name("Thinking Machines").build();
		computerTest = new Computer.ComputerBuilder().id(1L).name("MacBook Pro 15.4 inch")
				.company(companyTest).build();
		
		computers.add(computerTest);
		computers.add(new Computer.ComputerBuilder().id(2L).name("CM-2a").company(companyTest2).build());
		computers.add(new Computer.ComputerBuilder().id(3L).name("CM-200").company(companyTest2).build());
		computers.add(new Computer.ComputerBuilder().id(4L).name("CM-5e").company(companyTest2).build());
		computers.add(new Computer.ComputerBuilder().id(5L).name("CM-5")
				.introduced(LocalDate.of(1991,1,1)).discontinued(LocalDate.of(1991,1,2))
				.company(companyTest2).build());
		computers.add(new Computer.ComputerBuilder().id(6L).name("MacBook Pro")
				.introduced(LocalDate.of(2006,1,10)).company(companyTest).build());
		computers.add(new Computer.ComputerBuilder().id(7L).name("Apple IIe").build());
		computers.add(new Computer.ComputerBuilder().id(8L).name("Apple IIc").build());
		computers.add(new Computer.ComputerBuilder().id(9L).name("Apple IIGS").build());
		computers.add(new Computer.ComputerBuilder().id(10L).name("Apple IIc Plus").build());

	}
	
	
	@Test
	public void createComputerTest() throws DatabaseException {
		computerDAO.create(computerTest);
		computerTest.setId(11L);
		
		assertEquals("Expected same companies",computerTest, computerDAO.read(11L));
		
	}
	
	@Test
	public void findAllTest() throws DatabaseException {
		List<Computer> findAll = computerDAO.findAll();
		for(int i=0;i<computers.size();i++) {
			assertEquals(computers.get(i), findAll.get(i));
		}
		assertEquals("List different from  expected",computers, findAll);
	}
	
	@Test
	public void findAllPagedTest() throws DatabaseException {
		assertEquals("List different from expected",computers, computerDAO.findAllPaged(sorting));
	}
	
	@Test
	public void findByIdTest() throws DatabaseException {	
		assertEquals("Can't find computer",computerTest, computerDAO.read(1L));
	}
	
	@Test
	public void updateTest() throws DatabaseException {
		computerDAO.update(computerTest);
		assertEquals("Objects should be equals",computerTest, computerDAO.read(1L));
	}
	
	@Test
	public void countTest() throws DatabaseException {
		assertSame(10,computerDAO.count());
	}

	@Autowired
	public void setDataSource(HikariDataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}	
	
}
