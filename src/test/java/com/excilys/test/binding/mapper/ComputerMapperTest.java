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

import com.excilys.binding.dto.ComputerDTO;
import com.excilys.binding.mapper.ComputerMapper;
import com.excilys.exceptions.DateParseException;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.persistence.dao.CompanyDAO;

public class ComputerMapperTest {
	
	ComputerMapper mapper;
	
	Computer computer;
	Company company;
	ComputerDTO computerDTO;
	String companyId = "4";

	@Mock
	CompanyDAO daoMock;	

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		company = new Company.Builder().setId(4L).setName("NASA").build();
		computer = new Computer.Builder().setId(9L).setName("Coucou")
				.setIntroduced(LocalDate.of(2014, 4, 5)).setDiscontinued(LocalDate.of(2015, 3, 2))
				.setCompany(company).build();
		computerDTO = new ComputerDTO.Builder().setId("9").setName("Coucou")
				.setIntroduced("2014-04-05").setDiscontinued("2015-03-02")
				.setCompanyId(companyId).build();
		
		when(daoMock.findById(4L)).thenReturn(company);
		mapper = ComputerMapper.getInstance();
	}


	@Test
	public void modelToDTOtest() throws DateParseException {
		assertEquals("Expected same computer",computerDTO, mapper.modelToDto(computer));
	}

	@Test
	public void DTOtoModelTest() throws Exception {
		assertEquals("Expected same computer",computer, mapper.dtoToModel(computerDTO));
	}


	@Test
	public void parsingDateTest() {
		String sDate = "2014-04-05";
		LocalDate lDate = LocalDate.of(2014, 4, 5);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String sDate2 = format.format(Date.from(lDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
		LocalDate lDate2 = LocalDate.parse(sDate);
		assertEquals("Expected same dates",sDate, sDate2);
		assertEquals("Expected same dates",lDate, lDate2);
	}
}
