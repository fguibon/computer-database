package com.excilys.test.model;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import com.excilys.model.Company;
import com.excilys.model.Computer;

public class ComputerTest {

	@Test
	public void comparisonEqualValues() {
		Computer computer = new Computer(42L,"Latitude",
				LocalDate.of(2014, 2, 5),LocalDate.of(2014, 2, 3), 
				new Company(6L,"Intel"));
		Computer computer2 = new Computer(42L,"Latitude",
				LocalDate.of(2014, 2, 5),LocalDate.of(2014, 2, 3), 
				new Company(6L,"Intel"));
		assertEquals(computer2, computer);
		
	}
	
	@Test
	public void comparisonDifferentIds() {
		Computer computer =  new Computer(42L,null, null, null, null);
		Computer computer2 = new Computer(50L,null, null, null, null);
		assertNotEquals(computer2, computer);
	}
	
	@Test
	public void comparisonDifferentNames() {
		Computer computer = new Computer(50L,"Latitude", null, null, null);
		Computer computer2 = new Computer(50L,"Lattitude", null, null, null);
		assertNotEquals(computer2, computer);
	}
	
	
	@Test
	public void comparisonDifferentCompanies() {
		Computer computer = new Computer(50L,"Latitude", null, null, new Company(80L,"Intel"));
		Computer computer2 = new Computer(50L,"Lattitude", null, null, new Company(6L,"Intel"));
		assertNotEquals(computer2, computer);
	}
	
	
	@Test
	public void comparisonNullReference() {
		Computer computer = null;
		Computer computer2 = new Computer(42L,"Latitude",
				LocalDate.of(2014, 2, 5),LocalDate.of(2014, 2, 3), 
				new Company(6L,"Intel"));
		assertNotEquals(computer2, computer);
	}
	
	@Test
	public void comparisonWithoutValues() {
		Computer computer = new Computer();
		Computer computer2 = new Computer();
		assertEquals(computer2, computer);
	}

}
