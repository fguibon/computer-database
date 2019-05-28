package com.excilys.test.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.excilys.exceptions.DatabaseException;
import com.excilys.model.Company;
import com.excilys.model.Sorting;
import com.excilys.persistence.dao.CompanyDAO;
import com.excilys.test.ScriptExecuter;
import com.excilys.test.config.TestConfig;
import com.zaxxer.hikari.HikariDataSource;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CompanyDAOTest {
	
	private CompanyDAO companyDAO;
	private ScriptExecuter executer;
	
	private HikariDataSource dataSource;
	private EntityManager entityManager;

	private Company companyTest;
	private List<Company> companies;
	private Sorting sorting; 
	
	
	@Before
	public void setUp() throws Exception {
		executer = new ScriptExecuter(dataSource);
		executer.reload();
		
		companyDAO = new CompanyDAO(entityManager);
		
		companies = new ArrayList<Company>();
		sorting = new Sorting(1, 5,"","","");
		
		companyTest = new Company.CompanyBuilder().id(1L).name("Apple Inc.").build();
		companies.add(companyTest);
		companies.add(new Company.CompanyBuilder().id(2L).name("Thinking Machines").build());
		companies.add(new Company.CompanyBuilder().id(3L).name("RCA").build());
		companies.add(new Company.CompanyBuilder().id(4L).name("Netronics").build());
		companies.add(new Company.CompanyBuilder().id(5L).name("Tandy Corporation").build());
	}
	
	@Test
	public void createCompanyTest() throws DatabaseException {
		assertEquals(1,companyDAO.create(companyTest));
		companyTest.setId(6L);
		assertEquals("Expected same companies",companyTest,companyDAO.findById(6L));	
	}
	


	@Test
	public void findAllTest() throws DatabaseException {	
		assertEquals("Expected same company lists",companies, companyDAO.findAll());
	}
	
	@Test
	public void findAllPagedTest() throws DatabaseException {	
		assertEquals("Expected same company lists",companies, companyDAO.findAllPaged(sorting));
	}
	
	@Test
	public void findByIdTest() throws DatabaseException {
		assertEquals("Expected same companies",companyTest, companyDAO.findById(1L));
	}
	
	@Test
	public void updateTest() throws DatabaseException {
		companyDAO.update(new Company.CompanyBuilder().id(1L).name("Apple Inc.").build());
		assertEquals("Expected same companies",companyTest, companyDAO.findById(1L));
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
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
