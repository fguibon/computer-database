package com.excilys.test.binding.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.excilys.binding.dto.ComputerDTO;
import com.excilys.binding.mapper.ComputerMapper;
import com.excilys.binding.exceptions.DateParseException;
import com.excilys.core.Company;
import com.excilys.core.Computer;
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


	@Before
	public void setUp() throws Exception {

		computerMapper = new ComputerMapper();

		company = new Company.CompanyBuilder().id(4L).name("NASA").build();
		computer = new Computer.ComputerBuilder().id(9L).name("Coucou")
				.introduced(lDate).discontinued(LocalDate.of(2015, 3, 2))
				.company(company).build();
		computerDTO = new ComputerDTO.Builder().setId("9").setName("Coucou")
				.setIntroduced(sDate).setDiscontinued("2015-03-02")
				.setCompanyId(companyId).setCompanyName("NASA").build();

	}


	@Test
	public void modelToDtoTest() {
		assertEquals("Expected same computer",computerDTO, computerMapper.modelToDto(computer));
	}
	
	@Test
	public void modelToDtoIdZeroTest() {
		computerDTO.setId(null);
		computer.setId(0L);
		assertEquals("Expected same computer",computerDTO, computerMapper.modelToDto(computer));
	}
	
	@Test
	public void modelToDtoIdNullTest() {
		computerDTO.setId(null);
		computer.setId(null);
		assertEquals("Expected same computer",computerDTO, computerMapper.modelToDto(computer));
	}
	

	@Test
	public void dtoToModelTest() throws DateParseException {
		assertEquals("Expected same computer",computer, computerMapper.dtoToModel(computerDTO));
		computerDTO.setId("0");
		computer.setId(null);
		assertEquals("Expected same computer",computer, computerMapper.dtoToModel(computerDTO));
		computerDTO.setId("");
		assertEquals("Expected same computer",computer, computerMapper.dtoToModel(computerDTO));
		computerDTO.setId(null);
		assertEquals("Expected same computer",computer, computerMapper.dtoToModel(computerDTO));
	}
	
	@Test(expected = DateParseException.class)
	public void dtoToModelInvalidIntroducedDateTest() throws DateParseException {
		computerDTO.setIntroduced("blabla");
		computerMapper.dtoToModel(computerDTO);
	}
	
	@Test(expected = DateParseException.class)
	public void dtoToModelInvalidDiscontinuedDateTest() throws DateParseException {
		computerDTO.setDiscontinued("blabla");
		computerMapper.dtoToModel(computerDTO);
	}

	@Test
	public void castLocalDateTest() throws DateParseException {
		assertEquals("Expected same dates",lDate, computerMapper.castLocalDate(sDate));
		assertNull( computerMapper.castLocalDate(null));
		assertEquals( null,computerMapper.castLocalDate(""));
	}


	@Test
	public void castStringTest() {
		assertEquals("Expected same dates",sDate, computerMapper.castString(lDate));
		assertEquals( null,computerMapper.castString(null));
	}


}
