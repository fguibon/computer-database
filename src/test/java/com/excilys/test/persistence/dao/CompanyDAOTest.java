package com.excilys.test.persistence.dao;

import org.junit.Before;
import org.junit.Test;

import com.excilys.persistence.dao.ComputerDAO;

public class CompanyDAOTest {

	ComputerDAO computerDAO;
	
	@Before
	public void setUp() throws Exception {
		computerDAO = ComputerDAO.getInstance();
	}
	
	@Test
	public void testCreateCompany_Success() {
		
	}

}
