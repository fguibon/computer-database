package com.excilys.test.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.excilys.exceptions.DatabaseException;
import com.excilys.model.Company;
import com.excilys.model.Sorting;
import com.excilys.persistence.dao.CompanyDAO;
import com.excilys.test.ScriptExecuter;
import com.excilys.test.config.TestConfig;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CompanyDAOTest {
	
	private CompanyDAO companyDAO;
	
	@Autowired
	private ScriptExecuter executer;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private Company companyTest;
	private List<Company> companies;
	private Sorting sorting;
	
	@Before
	public void setUp() throws Exception {
		executer.reload();
		
		companyDAO = new CompanyDAO(jdbcTemplate);
		
		companies = new ArrayList<Company>();
		sorting = new Sorting(1, 5,"","","");
		
		companyTest = new Company.Builder().setId(1L).setName("Apple Inc.").build();
		companies.add(companyTest);
		companies.add(new Company.Builder().setId(2L).setName("Thinking Machines").build());
		companies.add(new Company.Builder().setId(3L).setName("RCA").build());
		companies.add(new Company.Builder().setId(4L).setName("Netronics").build());
		companies.add(new Company.Builder().setId(5L).setName("Tandy Corporation").build());
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
		companyDAO.update(new Company.Builder().setId(1L).setName("Apple Inc.").build());
		assertEquals("Expected same companies",companyTest, companyDAO.findById(1L));
	}
	
	@Test
	public void deleteTest() throws DatabaseException {
		Long id = 2L;
		assertNotNull("Expected company not null",companyDAO.findById(id));
		companyDAO.delete(id);
		assertNull("Expected company null",companyDAO.findById(id).getId());
		assertNull("Expected company null",companyDAO.findById(id).getName());
	}

}
