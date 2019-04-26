package com.excilys.test.persistence.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.excilys.exception.DatabaseQueryException;
import com.excilys.model.Company;
import com.excilys.persistence.dao.CompanyDAO;
import com.excilys.test.ScriptExecuter;

public class CompanyDAOTest {

	CompanyDAO companyDAO;
	
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

}
