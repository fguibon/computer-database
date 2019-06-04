package com.excilys.binding.mapper;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.excilys.binding.dto.ComputerDTO;
import com.excilys.binding.exceptions.DateParseException;
import com.excilys.core.Company;
import com.excilys.core.Computer;

@Component
public class ComputerMapper {
	
	private static final Logger LOGGER = 
			LogManager.getLogger(ComputerMapper.class);

	private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	public Computer dtoToModel(ComputerDTO computerDTO) throws DateParseException{
		Computer computer = new Computer();

		computer.setId(convertStringToId(computerDTO.getId()));
		computer.setName(computerDTO.getName());
		try {
			computer.setIntroduced(castLocalDate(computerDTO.getIntroduced()));
		} catch (DateParseException e1) {
			LOGGER.warn(e1.getMessage());
			throw new DateParseException("Failed to parse date");
		}
		try {
			computer.setDiscontinued(castLocalDate(computerDTO.getDiscontinued()));
		} catch (DateParseException e1) {
			LOGGER.warn(e1.getMessage());
			throw new DateParseException("Failed to parse date");
		}

		String id = computerDTO.getCompanyId();
		if(id!=null && !id.isEmpty()) {
			Company company = new Company();
			company.setId(convertStringToId(id));
			company.setName(computerDTO.getCompanyName());
			computer.setCompany(company);
		}	

		return computer;
	}


	public ComputerDTO modelToDto(Computer computer) {
		ComputerDTO computerDTO = new ComputerDTO();
		if(computer!=null) {
			computerDTO.setId(convertIdToString(computer.getId()));
			computerDTO.setName(computer.getName());
			computerDTO.setIntroduced(castString(computer.getIntroduced()));
			computerDTO.setDiscontinued(castString(computer.getDiscontinued()));
			Company company = computer.getCompany();
			if(company!=null) {
				computerDTO.setCompanyId(convertIdToString(company.getId()));
				computerDTO.setCompanyName(company.getName());
			}
		}
		return computerDTO;
	}

	public LocalDate castLocalDate(String date) throws DateParseException {
		try {
			return (date==null || date.isEmpty() )? null : LocalDate.parse(date);
		} catch (DateTimeParseException e){
			LOGGER.error("Failed cast to LocalDate :" +e.getMessage());
			throw new DateParseException("Could not parse date : "+date);
		}
	}
	
	public String castString(LocalDate ldate)  {
		return (ldate==null)? null :format.format(Date.from(ldate.atTime(12,00)
				.atZone(ZoneId.systemDefault()).toInstant())) ;
	}
	
	
	private String convertIdToString(Long id) {
		return (id == null || id == 0L) ? null : String.valueOf(id);
	}
	
	
	private Long convertStringToId(String id) {
		return (id == null || "0".equals(id) || id.isEmpty()) ? null : Long.valueOf(id);
	}
}
