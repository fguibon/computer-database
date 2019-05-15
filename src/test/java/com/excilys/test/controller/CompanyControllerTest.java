package com.excilys.test.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.binding.mapper.CompanyMapper;
import com.excilys.controller.CompanyController;
import com.excilys.model.Company;
import com.excilys.model.Page;
import com.excilys.service.CompanyService;
import com.excilys.test.config.TestConfig;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CompanyControllerTest {

	private List<Company> companies;
	private List<CompanyDTO> companiesDTO;
	private Company companyTest;
	private CompanyDTO companyDtoTest;
	private Page page;
	
	@Autowired
	private CompanyController companyController;
	
	@Autowired
	private CompanyService serviceMock;

	@Autowired
	private CompanyMapper mapperMock;
	
	@Before
	public void setUp() throws Exception {
		page = new Page(1, 1);
		
		companies = new ArrayList<Company>();	
		companyTest = new Company.Builder().setId(1L).setName("Apple Inc.").build();
		companies.add(companyTest);
		
		companiesDTO = new ArrayList<CompanyDTO>();
		companyDtoTest = new CompanyDTO.Builder().setId("1").setName("Apple Inc.").build();
		companiesDTO.add(companyDtoTest);
		
		Mockito.when(serviceMock.getCompanies()).thenReturn(companies);
		Mockito.when(serviceMock.getCompanies(page)).thenReturn(companies);
		Mockito.when(serviceMock.findById(1L)).thenReturn(companyTest);
		
		Mockito.when(mapperMock.dtoToModel(companyDtoTest)).thenReturn(companyTest);
		Mockito.when(mapperMock.modelToDto(companyTest)).thenReturn(companyDtoTest);
	}
	
	@Test
	public void getCompaniesTest() {
		assertEquals("Expected same companies",companiesDTO,companyController.getCompanies());
	}
	
	
	@Test
	public void getCompaniesPagedTest() {
		assertEquals("Expected same companies",companiesDTO,companyController.getCompanies(page));
	}
	
	@Test
	public void findbyIdTest() {
		assertEquals("Expected same companies",companyDtoTest,companyController.findById(1L));
	}
	
}
