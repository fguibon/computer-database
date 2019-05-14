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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.excilys.config.AppConfig;
import com.excilys.exceptions.DatabaseException;
import com.excilys.model.Company;
import com.excilys.persistence.dao.CompanyDAO;
import com.excilys.test.ScriptExecuter;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class CompanyDAOTest {
	
	@Autowired
	private CompanyDAO companyDAO;
	
	@Autowired
	private ScriptExecuter executer;
	
	Company companyTest;
	
	@Before
	public void setUp() throws Exception {
		executer.reload();
		companyTest = new Company.Builder().setId(1L).setName("Apple Inc.").build();
	}
	
	@Test
	public void createCompanyTest() throws DatabaseException {
		Company companyExpected = companyTest;
		companyDAO.create(companyExpected);
		
		companyExpected.setId(6L);
		Company companyActual = companyDAO.findById(6L);
		
		assertEquals("Expected same companies",companyExpected, companyActual);
		
	}
	

	@Test
	public void findAllTest() throws DatabaseException {
		List<Company> companiesExpected = new ArrayList<Company>();
		companiesExpected.add(companyTest);
		companiesExpected.add(new Company.Builder().setId(2L).setName("Thinking Machines").build());
		companiesExpected.add(new Company.Builder().setId(3L).setName("RCA").build());
		companiesExpected.add(new Company.Builder().setId(4L).setName("Netronics").build());
		companiesExpected.add(new Company.Builder().setId(5L).setName("Tandy Corporation").build());
		
		List<Company> companiesActual = new ArrayList<Company>();
		companiesActual = companyDAO.findAll();
		
		assertEquals("Expected same company lists",companiesExpected, companiesActual);
	}
	
	@Test
	public void findByIdTest() throws DatabaseException {
		Company companyActual = companyDAO.findById(1L);
		
		assertEquals("Expected same companies",companyTest, companyActual);
	}
	
	@Test
	public void updateTest() throws DatabaseException {
		Company companyExpected = companyTest;
		companyDAO.update(new Company.Builder().setId(1L).setName("Apple Inc.").build());
		Company companyActual = companyDAO.findById(1L);
		
		assertEquals("Expected same companies",companyExpected, companyActual);
	}
	
	@Test
	public void deleteTest() throws DatabaseException {
		Long id = 4L;
		assertNotNull("Expected company not null",companyDAO.findById(id));
		companyDAO.delete(id);
		assertNull("Expected company null",companyDAO.findById(id).getId());
		assertNull("Expected company null",companyDAO.findById(id).getName());
	}

}
