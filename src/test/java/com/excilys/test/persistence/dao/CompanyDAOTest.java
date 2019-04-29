package com.excilys.test.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.excilys.exception.DatabaseQueryException;
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
		
		assertEquals(companyExpected, companyActual);
		
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
		
		assertEquals(companiesExpected, companiesActual);
	}
	
	@Test
	public void testFindById_Success() throws DatabaseQueryException {
		Company companyActual = companyDAO.findById(1L);
		
		assertEquals(companyTest, companyActual);
	}
	
	@Test
	public void testUpdate_Success() throws DatabaseQueryException {
		Company companyExpected = new Company(1L,"IBM");
		companyDAO.update(new Company(1L,"IBM"));
		Company companyActual = companyDAO.findById(1L);
		
		assertEquals(companyExpected, companyActual);
	}
	
	@Test
	public void testDelete_Success() throws DatabaseQueryException {
		Long id = 4L;
		assertNotNull(companyDAO.findById(id));
		companyDAO.delete(id);
		assertNull(companyDAO.findById(id).getId());
		assertNull(companyDAO.findById(id).getName());
	}

}
