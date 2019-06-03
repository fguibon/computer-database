package com.excilys.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.excilys.core.Company;
import com.excilys.persistence.dao.CompanyDAO;
import com.excilys.persistence.dao.ComputerDAO;
import com.excilys.service.CompanyService;
import com.excilys.config.ServiceTestConfig;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ServiceTestConfig.class)
public class CompanyServiceTest {

	private List<Company> companies;
	private Company companyTest;
	
	private ComputerDAO computerDaoMock;
	private CompanyDAO companyDaoMock;
	
	private CompanyService service;
	
	@Before
	public void setUp() throws Exception {
		
		service = new CompanyService(companyDaoMock, computerDaoMock);
		
		companies = new ArrayList<Company>();
		
		companyTest = new Company.CompanyBuilder().id(1L).name("Apple Inc.").build();
		companies.add(companyTest);	
	
		when(companyDaoMock.findAll()).thenReturn(companies);
		when(companyDaoMock.read(1L)).thenReturn(companyTest);
		when(companyDaoMock.delete(1L)).thenReturn(1);
		when(computerDaoMock.delete(1L)).thenReturn(1);
	}
	
	@Test
	public void findAllTest() {
		assertEquals("Expected same companies",companies,service.getCompanies());
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
