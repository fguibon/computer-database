package com.excilys.test.persistence.dao;

import static org.junit.Assert.assertEquals;

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
import com.excilys.persistence.dao.CompanyDAO;
import com.excilys.test.ScriptExecuter;
import com.excilys.test.config.TestConfig;
import com.zaxxer.hikari.HikariDataSource;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CompanyDAOTest {

	private CompanyDAO companyDAO;
	private ScriptExecuter executer;

	private Company companyTest;
	private List<Company> companies;

	private HikariDataSource dataSource;
	private SessionFactory sessionFactory;

	@Before
	public void setUp() throws Exception {
		executer = new ScriptExecuter(dataSource);
		executer.reload();

		companyDAO = new CompanyDAO(sessionFactory);

		companies = new ArrayList<Company>();

		companyTest = new Company.CompanyBuilder().id(1L).name("Apple Inc.").build();
		companies.add(companyTest);
		companies.add(new Company.CompanyBuilder().id(2L).name("Thinking Machines").build());
		companies.add(new Company.CompanyBuilder().id(3L).name("RCA").build());
		companies.add(new Company.CompanyBuilder().id(4L).name("Netronics").build());
		companies.add(new Company.CompanyBuilder().id(5L).name("Tandy Corporation").build());
	}



	@Test
	public void findAllTest() throws DatabaseException {	
		assertEquals("Expected same company lists",companies, companyDAO.findAll());
	}

	@Test
	public void findByIdTest() throws DatabaseException {
		assertEquals("Expected same companies",companyTest, companyDAO.read(1L));
	}


	@Test
	public void deleteTest() throws DatabaseException {
		assertEquals(1,companyDAO.delete(5L));
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
