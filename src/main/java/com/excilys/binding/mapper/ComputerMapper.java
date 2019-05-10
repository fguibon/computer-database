package com.excilys.binding.mapper;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.binding.dto.ComputerDTO;
import com.excilys.exceptions.DateParseException;
import com.excilys.model.Company;
import com.excilys.model.Computer;

public class ComputerMapper {

	private static ComputerMapper instance=null;
	
	private static final Logger logger = 
			LogManager.getLogger(ComputerMapper.class);

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


	private ComputerMapper() {}

	public static ComputerMapper getInstance() {
		return (instance!=null) ? instance : (instance = new ComputerMapper());
	}


	public Computer dtoToModel(ComputerDTO computerDTO) throws DateParseException{
		Computer computer = new Computer();

		computer.setId(convertStringToId(computerDTO.getId()));
		computer.setName(computerDTO.getName());
		try {
			computer.setIntroduced(castLocalDate(computerDTO.getIntroduced()));
		} catch (DateParseException e1) {
			logger.error(e1.getMessage(), e1);
			throw new DateParseException("Failed to parse date");
		}
		try {
			computer.setDiscontinued(castLocalDate(computerDTO.getDiscontinued()));
		} catch (DateParseException e1) {
			logger.error(e1.getMessage(), e1);
			throw new DateParseException("Failed to parse date");
		}

		Company company = new Company();
		company.setId(convertStringToId(computerDTO.getCompanyId()));
		company.setName(computerDTO.getCompanyName());
		computer.setCompany(company);

		return computer;
	}


	public ComputerDTO modelToDto(Computer computer) {
		ComputerDTO computerDTO = new ComputerDTO();
		if(computer!=null) {
			computerDTO.setId(Long.toString(computer.getId()));
			computerDTO.setName(computer.getName());
			computerDTO.setIntroduced(castString(computer.getIntroduced()));
			computerDTO.setDiscontinued(castString(computer.getDiscontinued()));
			computerDTO.setCompanyId(convertIdToString(computer.getCompany().getId()));
			computerDTO.setCompanyName(computer.getCompany().getName());
	
		}
		return computerDTO;
	}

	public LocalDate castLocalDate(String date) throws DateParseException {
		try {
			return (date==null || date.isEmpty() )? null : LocalDate.parse(date);
		} catch (Exception e){
			logger.error("Failed cast to LocalDate :" +e.getMessage());
			throw new DateParseException("Could not parse date : "+date);
		}
	}
	
	public String castString(LocalDate ldate)  {
		return (ldate==null)? null :format.format(Date.from(ldate.atTime(12,00).atZone(ZoneId.systemDefault()).toInstant())) ;

	}
	
	
	private String convertIdToString(Long id) {
		return (id == null || id == 0) ? null : String.valueOf(id);
	}
	
	
	private Long convertStringToId(String id) {
		return (id == null || "0".equals(id) || id.isEmpty()) ? null : Long.valueOf(id);
	}
}
