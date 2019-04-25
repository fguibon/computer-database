package com.excilys.binding.mapper;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.binding.dto.ComputerDTO;
import com.excilys.exception.DatabaseQueryException;
import com.excilys.exception.InvalidDateException;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.persistence.dao.CompanyDAO;

public class ComputerMapper {

	private static ComputerMapper instance=null;
	
	private static final Logger logger = 
			LogManager.getLogger(ComputerMapper.class);

	private CompanyDAO companyDAO;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


	private ComputerMapper(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	public static ComputerMapper getInstance(CompanyDAO companyDAO) {
		return (instance!=null) ? instance : (instance = new ComputerMapper(companyDAO));
	}


	public Computer dtoToModel(ComputerDTO computerDTO){
		Computer computer = null;

		if (computerDTO != null) {
			computer = new Computer();
			computer.setId(Long.parseLong(computerDTO.getId()));
			computer.setName(computerDTO.getName());
			computer.setIntroduced(castLocalDate(computerDTO.getIntroduced()));
			computer.setDiscontinued(castLocalDate(computerDTO.getDiscontinued()));

			Company company = null;
			if(computerDTO.getCompanyDTO()!=null) {
				try {
					company = companyDAO.findById(Long.parseLong(computerDTO.getCompanyDTO().getId()));
				} catch (NumberFormatException | DatabaseQueryException e) {
					logger.error("Invalid conversion");
				}
			}

			computer.setCompany(company);
		}

		return computer;
	}


	public ComputerDTO modelToDto(Computer computer) {
		ComputerDTO computerDTO = null;
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
			CompanyDTO companyDTO = null;
			if(computer.getCompany()!=null) {
				companyDTO = new CompanyDTO();
				companyDTO.setId(computer.getCompany().getId().toString());
				companyDTO.setName(computer.getCompany().getName());
			}
			computerDTO.setCompanyDTO(companyDTO);
		}
		return computerDTO;
	}

	private LocalDate castLocalDate(String date) {
		try {
			return (date==null)? null : LocalDate.parse(date);
		} catch (Exception e){
			throw new InvalidDateException(date);
		}
	}
	
	private String castString(LocalDate ldate) {
		try {
			return (ldate==null)? null :format.format(Date.from(ldate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())) ;
		} catch (Exception e){
			throw new RuntimeException();
		}
	}
}
