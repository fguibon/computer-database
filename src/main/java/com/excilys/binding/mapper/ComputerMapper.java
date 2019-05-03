package com.excilys.binding.mapper;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.binding.dto.ComputerDTO;
import com.excilys.exceptions.DatabaseQueryException;
import com.excilys.exceptions.InvalidDateException;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.persistence.dao.CompanyDAO;

public class ComputerMapper {

	private static ComputerMapper instance=null;
	
	private static final Logger logger = 
			LogManager.getLogger(ComputerMapper.class);

	private CompanyDAO companyDAO = CompanyDAO.getInstance();
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


	private ComputerMapper() {}

	public static ComputerMapper getInstance() {
		return (instance!=null) ? instance : (instance = new ComputerMapper());
	}


	public Computer dtoToModel(ComputerDTO computerDTO){
		Computer computer = new Computer();

		if (computerDTO != null) {
			String id =computerDTO.getId();
			computer.setId(convertStringToId(id));
			computer.setName(computerDTO.getName());
			computer.setIntroduced(castLocalDate(computerDTO.getIntroduced()));
			computer.setDiscontinued(castLocalDate(computerDTO.getDiscontinued()));

			Company company = new Company();
			
			try {
				company = companyDAO.findById(convertStringToId(computerDTO.getCompanyId()));
			} catch (NumberFormatException | DatabaseQueryException e) {
				logger.error("Invalid conversion");
			}

			computer.setCompany(company);
		}

		return computer;
	}


	public ComputerDTO modelToDto(Computer computer) {
		ComputerDTO computerDTO = new ComputerDTO();
		if(computer!=null) {
			computerDTO = new ComputerDTO();
			computerDTO.setId(Long.toString(computer.getId()));
			computerDTO.setName(computer.getName());
			if (computer.getIntroduced() != null) {
				computerDTO.setIntroduced(castString(computer.getIntroduced()));
			}
			if (computer.getDiscontinued() != null) {
				computerDTO.setDiscontinued(castString(computer.getDiscontinued()));
			}
			if(computer.getCompany()!=null) {
				computerDTO.setCompanyId(convertIdToString(computer.getCompany().getId()));
			}	
		}
		return computerDTO;
	}

	public LocalDate castLocalDate(String date) {
		try {
			return (date==null || date.isEmpty() )? null : LocalDate.parse(date);
		} catch (Exception e){
			logger.error("Failed cast to LocalDate :" +e.getMessage());
			throw new InvalidDateException(date);
		}
	}
	
	public String castString(LocalDate ldate) {
		try {
			return (ldate==null)? null :format.format(Date.from(ldate.atTime(22,30).atZone(ZoneId.systemDefault()).toInstant())) ;
		} catch (Exception e){
			logger.error("Failed format to String :" + e.getMessage());
			throw new InvalidDateException("Local date invalid");
		}
	}
	
	
	public String convertIdToString(Long id) {
		return (id == null || id == 0) ? null : String.valueOf(id);
	}
	
	
	public Long convertStringToId(String id) {
		return (id == null || "0".equals(id)) ? null : Long.valueOf(id);
}
}
