package com.excilys.test.model;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import com.excilys.model.Company;
import com.excilys.model.Computer;

public class ComputerTest {

	Company companyTest = new Company.Builder().setId(6L).setName("Intel").build();
	Company companyTest2 = new Company.Builder().setId(10L).setName("Intel").build();
	Computer computerTest = new Computer.Builder().setId(42L).setName("Latitude")
			.setIntroduced(LocalDate.of(2014, 2, 5)).setDiscontinued(LocalDate.of(2014, 2, 3))
		.setCompany(companyTest).build();
	
	@Test
	public void comparisonEqualValues() {
		Computer computer = new Computer.Builder().setId(42L).setName("Latitude")
				.setIntroduced(LocalDate.of(2014, 2, 5)).setDiscontinued(LocalDate.of(2014, 2, 3))
			.setCompany(companyTest).build();
		Computer computer2 = new Computer.Builder().setId(42L).setName("Latitude")
				.setIntroduced(LocalDate.of(2014, 2, 5)).setDiscontinued(LocalDate.of(2014, 2, 3))
			.setCompany(companyTest).build();
		assertEquals("Expected same computers",computer2, computer);
		
	}
	
	@Test
	public void comparisonDifferentIds() {
		Computer computer =  new Computer.Builder().setId(42L).build();
		Computer computer2 = new Computer.Builder().setId(50L).build();
		assertNotEquals("Expected different computers",computer2, computer);
	}
	
	@Test
	public void comparisonDifferentNames() {
		Computer computer = new Computer.Builder().setId(50L).setName("Latitude").build();
		Computer computer2 = new Computer.Builder().setId(50L).setName("Lattitude").build();
		assertNotEquals("Expected different computers",computer2, computer);
	}
	
	
	@Test
	public void comparisonDifferentCompanies() {
		Computer computer = new Computer.Builder().setId(50L).setName("Latitude").setCompany(companyTest).build();
		Computer computer2 = new Computer.Builder().setId(50L).setName("Latitude").setCompany(companyTest2).build();
		assertNotEquals("Expected different computers",computer2, computer);
	}
	
	
	@Test
	public void comparisonNullReference() {
		Computer computer = null;
		Computer computer2 = computerTest;
		assertNotEquals(computer2, computer);
	}
	
	@Test
	public void comparisonWithoutValues() {
		Computer computer = new Computer();
		Computer computer2 = new Computer();
		assertEquals(computer2, computer);
	}

}
