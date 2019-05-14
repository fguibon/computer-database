package com.excilys.test.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.excilys.model.Company;
import com.excilys.persistence.dao.CompanyDAO;
import com.excilys.service.CompanyService;
import com.excilys.test.config.TestConfig;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CompanyServiceTest {

	List<Company> companies;
	Company companyTest;
	
	@Autowired
	CompanyDAO daoMock;
	
	@Autowired
	CompanyService service;
	
	@Before
	public void setUp() throws Exception {
		
		companies = new ArrayList<Company>();
		companies.add(new Company.Builder().setId(4L).setName("NASA").build());
		companies.add(new Company.Builder().setId(10L).setName("ESA").build());	
		companyTest = new Company.Builder().setId(1L).setName("MacBook Pro 15.4 inch").build();
		when(daoMock.findAll()).thenReturn(companies);
		when(daoMock.findById(1L)).thenReturn(companyTest);
	}
	
	@Test
	public void getCompaniesTest() {
		assertEquals("Expected same companies",companies,service.getCompanies());
	}
	
	@Test
	public void findbyIdTest() {
		assertEquals("Expected same companies",companyTest,service.findById(1L));
	}	


}
