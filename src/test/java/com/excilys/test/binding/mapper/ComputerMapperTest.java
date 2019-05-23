package com.excilys.test.binding.mapper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.excilys.binding.dto.ComputerDTO;
import com.excilys.binding.mapper.ComputerMapper;
import com.excilys.exceptions.DateParseException;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.persistence.dao.CompanyDAO;
import com.excilys.test.config.TestConfig;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ComputerMapperTest {

	private ComputerMapper computerMapper;

	private Computer computer;
	private Company company;
	private ComputerDTO computerDTO;
	private String companyId = "4";
	private String sDate = "2014-04-05";
	private LocalDate lDate = LocalDate.of(2014, 4, 5);

	private CompanyDAO daoMock;	

	@Before
	public void setUp() throws Exception {

		computerMapper = new ComputerMapper();

		company = new Company.Builder().setId(4L).setName("NASA").build();
		computer = new Computer.Builder().setId(9L).setName("Coucou")
				.setIntroduced(lDate).setDiscontinued(LocalDate.of(2015, 3, 2))
				.setCompany(company).build();
		computerDTO = new ComputerDTO.Builder().setId("9").setName("Coucou")
				.setIntroduced(sDate).setDiscontinued("2015-03-02")
				.setCompanyId(companyId).setCompanyName("NASA").build();

		when(daoMock.findById(4L)).thenReturn(company);
	}


	@Test
	public void modelToDtoTest() {
		assertEquals("Expected same computer",computerDTO, computerMapper.modelToDto(computer));
	}

	@Test
	public void dtoToModelTest() throws DateParseException {
		assertEquals("Expected same computer",computer, computerMapper.dtoToModel(computerDTO));
	}
	
	@Test(expected = DateParseException.class)
	public void dtoToModelInvalidDateTest() throws DateParseException {
		computerDTO.setIntroduced("blabla");
		computerDTO.setDiscontinued("blabla");
		computerMapper.dtoToModel(computerDTO);
	}

	@Test
	public void castLocalDateTest() throws DateParseException {
		assertEquals("Expected same dates",lDate, computerMapper.castLocalDate(sDate));
	}


	@Test
	public void castStringTest() {
		assertEquals("Expected same dates",sDate, computerMapper.castString(lDate));
	}

	@Autowired
	public void setDaoMock(CompanyDAO daoMock) {
		this.daoMock = daoMock;
	}


}
