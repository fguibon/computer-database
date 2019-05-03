package com.excilys.test.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.excilys.exceptions.DatabaseQueryException;
import com.excilys.model.Company;
import com.excilys.persistence.dao.CompanyDAO;
import com.excilys.test.ScriptExecuter;

public class CompanyDAOTest {

	CompanyDAO companyDAO;
	Company companyTest = new Company(1L,"Apple Inc.");
	
	@Before
	public void setUp() throws Exception {
		ScriptExecuter.getInstance().reload();
		companyDAO = CompanyDAO.getInstance();
	}
	
	@Test
	public void testCreateCompany_Success() throws DatabaseQueryException {
		Company companyExpected = new Company();
		companyExpected.setName("IBM");
		companyDAO.create(companyExpected);
		
		companyExpected.setId(6L);
		Company companyActual = companyDAO.findById(6L);
		
		assertEquals("Expected same companies",companyExpected, companyActual);
		
	}
	

	@Test
	public void testFindAll_Success() throws DatabaseQueryException {
		List<Company> companiesExpected = new ArrayList<Company>();
		companiesExpected.add(companyTest);
		companiesExpected.add(new Company(2L, "Thinking Machines"));
		companiesExpected.add(new Company(3L,"RCA"));
		companiesExpected.add(new Company(4L,"Netronics"));
		companiesExpected.add(new Company(5L,"Tandy Corporation"));
		
		List<Company> companiesActual = new ArrayList<Company>();
		companiesActual = companyDAO.findAll();
		
		assertEquals("Expected same company lists",companiesExpected, companiesActual);
	}
	
	@Test
	public void testFindById_Success() throws DatabaseQueryException {
		Company companyActual = companyDAO.findById(1L);
		
		assertEquals("Expected same companies",companyTest, companyActual);
	}
	
	@Test
	public void testUpdate_Success() throws DatabaseQueryException {
		Company companyExpected = new Company(1L,"IBM");
		companyDAO.update(new Company(1L,"IBM"));
		Company companyActual = companyDAO.findById(1L);
		
		assertEquals("Expected same companies",companyExpected, companyActual);
	}
	
	@Test
	public void testDelete_Success() throws DatabaseQueryException {
		Long id = 4L;
		assertNotNull("Expected company not null",companyDAO.findById(id));
		companyDAO.delete(id);
		assertNull("Expected company null",companyDAO.findById(id).getId());
		assertNull("Expected company null",companyDAO.findById(id).getName());
	}

}
