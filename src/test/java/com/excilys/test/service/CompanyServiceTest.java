package com.excilys.test.service;

import static org.junit.Assert.assertEquals;
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
import com.excilys.model.Sorting;
import com.excilys.persistence.dao.CompanyDAO;
import com.excilys.persistence.dao.ComputerDAO;
import com.excilys.service.CompanyService;
import com.excilys.test.config.TestConfig;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CompanyServiceTest {

	private List<Company> companies;
	private Company companyTest;
	private Sorting sorting;
	
	private ComputerDAO computerDaoMock;
	private CompanyDAO companyDaoMock;
	
	private CompanyService service;
	
	@Before
	public void setUp() throws Exception {
		
		service = new CompanyService(companyDaoMock, computerDaoMock);
		
		companies = new ArrayList<Company>();
		sorting = new Sorting(1, 1,"","","");
		
		companyTest = new Company.Builder().setId(1L).setName("Apple Inc.").build();
		companies.add(companyTest);	
	
		when(companyDaoMock.findAll()).thenReturn(companies);
		when(companyDaoMock.findAllPaged(sorting)).thenReturn(companies);
		when(companyDaoMock.findById(1L)).thenReturn(companyTest);
		when(companyDaoMock.delete(1L)).thenReturn(1);
		when(computerDaoMock.delete(1L)).thenReturn(1);
	}
	
	@Test
	public void findAllTest() {
		assertEquals("Expected same companies",companies,service.getCompanies());
	}
	
	@Test
	public void findAllPagedTest() {
		assertEquals("Expected same companies",companies,service.getCompanies(sorting));
	}
	
	@Test
	public void findbyIdTest() {
		assertEquals("Expected same companies",companyTest,service.findById(1L));
	}
	
	@Test
	public void deleteTest() {
		assertEquals("Expected same value",1,service.delete(1L));
	}
	
	@Autowired
	public void setComputerDaoMock(ComputerDAO computerDaoMock) {
		this.computerDaoMock = computerDaoMock;
	}
	
	@Autowired
	public void setCompanyDaoMock(CompanyDAO companyDaoMock) {
		this.companyDaoMock = companyDaoMock;
	}


}
