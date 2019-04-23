package com.excilys.binding.mapper;

import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.binding.dto.ComputerDTO;
import com.excilys.exception.DatabaseQueryException;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.persistence.dao.CompanyDAO;

public class ComputerMapper {
	
	private static ComputerMapper instance = new ComputerMapper(CompanyDAO.getInstance());
	private static final Logger logger = 
			LogManager.getLogger(ComputerMapper.class);
	
	private CompanyDAO companyDAO;
	
	
	private ComputerMapper(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}
	
	public static ComputerMapper getInstance() {
		return instance;
	}
	

	public Computer dtoToModel(ComputerDTO computerDTO){
		Computer computer = new Computer();
		
		if (computerDTO.getId() != null) {
			computer.setId(Long.parseLong(computerDTO.getId()));
			computer.setName(computerDTO.getName());
			computer.setIntroduced(LocalDate.parse(computerDTO.getIntroduced()));
			computer.setDiscontinued(LocalDate.parse(computerDTO.getDiscontinued()));
			Company company = new Company();
			try {
				company = companyDAO.findById(Long.parseLong(computerDTO.getCompanyDTO().getId()));
			} catch (NumberFormatException | DatabaseQueryException e) {
				logger.error("Invalid conversion");
			}

			computer.setCompany(company);
		}
		
		
		return computer;
	}


	public ComputerDTO modelToDto(Computer computer) {
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setId(Long.toString(computer.getId()));
		computerDTO.setName(computer.getName());
		if (computer.getIntroduced() != null) {
			computerDTO.setIntroduced(computer.getIntroduced().toString());
		}
		if (computer.getDiscontinued() != null) {
			computerDTO.setDiscontinued(computer.getDiscontinued().toString());
		}
		CompanyDTO companyDTO = new CompanyDTO();
		if(computer.getCompany().getId()!=null) {
			companyDTO.setId(computer.getCompany().getId().toString());
			companyDTO.setName(computer.getName());
		}
		computerDTO.setCompanyDTO(companyDTO);
		return computerDTO;
}
}
