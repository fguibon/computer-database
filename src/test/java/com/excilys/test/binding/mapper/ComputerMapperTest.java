package com.excilys.test.binding.mapper;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;
import org.mockito.Mock;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.binding.dto.ComputerDTO;
import com.excilys.binding.mapper.ComputerMapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.persistence.dao.CompanyDAO;

public class ComputerMapperTest {
	
	@Mock CompanyDAO companyDAO; 

	@Test
	public void modelToDTOtest() {
		Computer computer = new Computer(9L,"Coucou",
				LocalDate.of(2014, 4, 5), LocalDate.of(2015, 3, 2),
				new Company(4L,"NASA"));
		System.out.println(computer);
		ComputerDTO computerDTO = new ComputerDTO("9", "Coucou", "2014-04-05",
				"2015-03-02", new CompanyDTO("4", "NASA"));
		assertEquals(computerDTO, ComputerMapper.getInstance(companyDAO).modelToDto(computer));
	}
	
	@Test
	public void DTOtoModelTest() {
		Computer computer = new Computer(9L,"Coucou",
				LocalDate.of(2014, 4, 5), LocalDate.of(2015, 3, 2),
				new Company(4L,"NASA"));
		ComputerDTO computerDTO = new ComputerDTO("9", "Coucou", "2014-04-05",
				"2015-03-02", new CompanyDTO("4", "NASA"));
		assertEquals(computer, ComputerMapper.getInstance(companyDAO).dtoToModel(computerDTO));
	}

}
