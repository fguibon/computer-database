package com.excilys.test.binding.mapper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.binding.dto.ComputerDTO;
import com.excilys.binding.mapper.ComputerMapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.persistence.dao.CompanyDAO;

public class ComputerMapperTest {
	
	ComputerMapper mapper;
	
	Computer computer;
	Company company;
	ComputerDTO computerDTO;
	CompanyDTO companyDTO;

	@Mock
	CompanyDAO daoMock;

	

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		company = new Company(4L, "NASA");
		companyDTO = new CompanyDTO("4", "NASA");
		computer = new Computer(9L, "Coucou", LocalDate.of(2014, 4, 5), LocalDate.of(2015, 3, 2), company);
		computerDTO = new ComputerDTO("9", "Coucou", "2014-04-05", "2015-03-02", companyDTO);
		
		when(daoMock.findById(4L)).thenReturn(company);
		mapper = ComputerMapper.getInstance(daoMock);
	}


	@Test
	public void modelToDTOtest() {
		assertEquals(computerDTO, mapper.modelToDto(computer));
	}

	@Test
	public void DTOtoModelTest() {
		assertEquals(computer, mapper.dtoToModel(computerDTO));
	}


	@Test
	public void parsingDateTest() {
		String sDate = "2014-04-05";
		LocalDate lDate = LocalDate.of(2014, 4, 5);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String sDate2 = format.format(Date.from(lDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
		LocalDate lDate2 = LocalDate.parse(sDate);
		assertEquals(sDate, sDate2);
		assertEquals(lDate, lDate2);
	}
}
