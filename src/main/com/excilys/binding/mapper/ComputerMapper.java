package main.com.excilys.binding.mapper;

import java.time.LocalDate;

import main.com.excilys.binding.dto.CompanyDTO;
import main.com.excilys.binding.dto.ComputerDTO;
import main.com.excilys.model.Company;
import main.com.excilys.model.Computer;
import main.com.excilys.persistence.dao.CompanyDAO;

public class ComputerMapper {
	
	private static ComputerMapper instance = new ComputerMapper(CompanyDAO.getInstance());
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
		}
		
		else {
			computer.setName(computerDTO.getName());
			computer.setIntroduced(LocalDate.parse(computerDTO.getIntroduced()));
			computer.setDiscontinued(LocalDate.parse(computerDTO.getDiscontinued()));
			Company company = companyDAO.findById(Long.parseLong(computerDTO.getCompanyDTO().getId()));

			computer.setCompany(company);
			
		}
		return computer;
	}


	public ComputerDTO modelToDto(Computer computer) {
		ComputerDTO theComputerDTO = new ComputerDTO();
		theComputerDTO.setId(Long.toString(computer.getId()));
		theComputerDTO.setName(computer.getName());
		if (computer.getIntroduced() != null) {
			theComputerDTO.setIntroduced(computer.getIntroduced().toString());
		}
		if (computer.getDiscontinued() != null) {
			theComputerDTO.setDiscontinued(computer.getDiscontinued().toString());
		}
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setId(Long.toString(computer.getCompany().getId()));
		theComputerDTO.setCompanyDTO(companyDTO);
		return theComputerDTO;
}
}
