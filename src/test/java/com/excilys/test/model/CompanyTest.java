package com.excilys.test.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.excilys.model.Company;

public class CompanyTest {

	@Test
	public void comparisonEqualValues() {
		Company company = new Company(6L,"Intel");
		Company company2 = new Company(6L,"Intel");
		assertEquals(company2, company);
		
	}
	
	@Test
	public void comparisonDifferentIds() {
		Company company = new Company(5L,"Intel");
		Company company2 = new Company(3L,"Intel");
		assertNotEquals(company2, company);
	}
	
	@Test
	public void comparisonDifferentNames() {
		Company company = new Company(3L,"LG");
		Company company2 = new Company(3L,"Intel");
		assertNotEquals(company2, company);
	}
	
	@Test
	public void comparisonReferenceNulle() {
		Company company = null;
		Company company2 = new Company(3L,"LG");
		assertNotEquals(company2, company);
	}
	
	@Test
	public void comparisonWithoutValue() {
		Company company = new Company();
		Company company2 = new Company();
		assertEquals(company2, company);
	}
	
	
	

}
