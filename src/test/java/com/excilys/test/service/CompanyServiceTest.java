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
import com.excilys.model.Page;
import com.excilys.persistence.dao.CompanyDAO;
import com.excilys.service.CompanyService;
import com.excilys.test.config.TestConfig;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CompanyServiceTest {

	List<Company> companies;
	Company companyTest;
	Page page;
	
	@Autowired
	CompanyDAO daoMock;
	
	@Autowired
	CompanyService service;
	
	@Before
	public void setUp() throws Exception {
		
		companies = new ArrayList<Company>();
		page = new Page(1, 1);
		companyTest = new Company.Builder().setId(1L).setName("Apple Inc.").build();
		companies.add(companyTest);	
	
		when(daoMock.findAll()).thenReturn(companies);
		when(daoMock.findAllPaged(page)).thenReturn(companies);
		when(daoMock.findById(1L)).thenReturn(companyTest);
	}
	
	@Test
	public void findAllTest() {
		assertEquals("Expected same companies",companies,service.getCompanies());
	}
	
	@Test
	public void findAllPagedTest() {
		assertEquals("Expected same companies",companies,service.getCompanies(page));
	}
	
	@Test
	public void findbyIdTest() {
		assertEquals("Expected same companies",companyTest,service.findById(1L));
	}	


}
