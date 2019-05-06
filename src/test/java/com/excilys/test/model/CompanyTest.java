package com.excilys.test.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.excilys.model.Company;

public class CompanyTest {

	
	Company companyTest = new Company.Builder().setId(6L).setName("Intel").build();
	
	@Test
	public void comparisonEqualValues() {
		Company company = companyTest;
		Company company2 = new Company.Builder().setId(6L).setName("Intel").build();
		assertEquals("Expected same companies",company2, company);
		
	}
	
	@Test
	public void comparisonDifferentIds() {
		Company company = companyTest;
		Company company2 = new Company.Builder().setId(3L).setName("Intel").build();
		assertNotEquals("Expected different companies",company2, company);
	}
	
	@Test
	public void comparisonDifferentNames() {
		Company company = companyTest;
		Company company2 = new Company.Builder().setId(3L).setName("Intell").build();
		assertNotEquals("Expected different companies",company2, company);
	}
	
	@Test
	public void comparisonReferenceNulle() {
		Company company = null;
		Company company2 = companyTest;
		assertNotEquals("Expected different companies",company2, company);
	}
	
	@Test
	public void comparisonWithoutValue() {
		Company company = new Company();
		Company company2 = new Company();
		assertEquals("Expected same companies",company2, company);
	}
	
	
	

}
