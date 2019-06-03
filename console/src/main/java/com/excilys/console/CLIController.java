package com.excilys.console;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.binding.dto.ComputerDTO;
import com.excilys.binding.mapper.CompanyMapper;
import com.excilys.binding.mapper.ComputerMapper;
import com.excilys.binding.exceptions.DatabaseException;
import com.excilys.binding.exceptions.DateParseException;
import com.excilys.binding.exceptions.MappingException;
import com.excilys.core.Company;
import com.excilys.core.Computer;
import com.excilys.core.Sorting;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

@Component
public class CLIController {

	private static final int LIMIT=10;
	private static final int CURRENT_PAGE=1;
	
	private Sorting sorting;
	private CLIView view;
	private CompanyService companyService;
	private ComputerService computerService;
	private ComputerMapper computerMapper;
	private CompanyMapper companyMapper;
	
	
	public CLIController(CompanyService companyService, 
			ComputerService computerService,
			ComputerMapper computerMapper,
			CompanyMapper companyMapper){
		sorting = new Sorting(LIMIT,CURRENT_PAGE,"id","asc","");
		view = new CLIView(System.in);
		this.companyService = companyService;
		this.computerService = computerService;
		this.computerMapper = computerMapper;
		this.companyMapper = companyMapper;
	}
	
	
	/**
	 * Function calling the display of the starting menu
	 * @throws DatabaseException 
	 * @throws DateParseException 
	 * @throws MappingException 
	 */
	public void start() throws DatabaseException, DateParseException {
		boolean ok = true;
		while(ok) {
			int choice =view.menu();
			switch(choice) {
				case 0:
					view.bye();
					ok=false;
					break;
				case 1:
					listComputers();
					break;
				case 2:
					listCompanies();
					break;
				case 3:
					showComputerDetail();
					break;
				case 4:
					createComputer();
					break;
				case 5:
					updateComputer();
					break;
				case 6:
					deleteComputer();
					break;
				case 7:
					deleteCompany();
					break;
				default:
					view.notifyInvalidNumber();
					break;
			}
		}
	}
	

	/**
	 * @throws DatabaseException  
	 * option 1
	 * @throws  
	 */
	public void listComputers() throws DatabaseException {
		int currentPage =1;
		boolean ok=true;
		sorting.setPage(currentPage);
		sorting.setLimit(LIMIT);
		while(ok) {
			List<ComputerDTO> computersDTO = new ArrayList<>();
			List<Computer> computers = this.computerService.findAll(sorting);
			for(Computer c:computers) {
				computersDTO.add(computerMapper.modelToDto(c));
			}
			if(computers.isEmpty())	ok=false;
			this.view.displayComputers(computersDTO,sorting);
			currentPage+=1;
			sorting.setPage(currentPage);
		}
	}
	
	/**
	 * option 2
	 */
	public void listCompanies() {
		List<CompanyDTO> companiesDTO = new ArrayList<>();
		List<Company> companies = this.companyService.getCompanies();
		for(Company c :companies) {
			companiesDTO.add(companyMapper.modelToDto(c));
		}
		this.view.displayCompanies(companiesDTO);
	}
	
	/** 
	 * option 3
	 * @throws DatabaseException 
	 */
	public void showComputerDetail() throws DatabaseException {
		ComputerDTO computer = null;
		Long id = this.view.queryId();
		if(id!=null) computer = computerMapper.modelToDto(this.computerService.findById(id));
		if(computer!=null)	this.view.displayComputer(computer);
	}
	
	/**
	 * option 4
	 * @throws DateParseException 
	 * @throws MappingException 
	 * @throws DatabaseException 
	 *
	 */
	public void createComputer() throws DatabaseException, DateParseException {
		ComputerDTO computer =null;
		computer = this.queryComputerToCreate();
		if(computer!=null) {
			this.computerService.createComputer(computerMapper.dtoToModel(computer));
		}
	}
	

	/**
	 * option 5
	 * @throws DateParseException 
	 * @throws MappingException 
	 * @throws DatabaseException 
	 */
	public void updateComputer() throws DatabaseException, DateParseException {
		ComputerDTO computer = this.queryComputerToUpdate();
		if(computerService.update(computerMapper.dtoToModel(computer))==1) view.notifySuccess();

	}
	
	/**
	 * option 6
	 * @throws DatabaseException 
	 */
	public void deleteComputer() throws DatabaseException {
		Long id = null;
		id = this.queryEntryToDelete();
		if(id!=null) {
			this.computerService.delete(id);	
		}
	}
	
	public void deleteCompany() {
		Long id = null;
		id = this.queryEntryToDelete();
		if(id!=null) {
			if(this.companyService.delete(id)==1) view.notifySuccess();
		}
	}
	
	
	/**
	 * Asks for the computer information
	 * @return a Computer object
	 */
	public ComputerDTO queryComputerToCreate() {
		ComputerDTO computer = null;
		String name =view.queryName();
		if(name!=null && !name.isEmpty()) {
			computer = new ComputerDTO();
			computer.setName(name);
			computer.setIntroduced(view.queryDate());
			computer.setDiscontinued(view.queryDate());
			computer.setCompanyId(Long.toString(view.queryId()));
		} 
		return computer;
	}
	
	/**
	 * Asks for the computer information
	 * @return a Computer object
	 */
	public ComputerDTO queryComputerToUpdate() {
		ComputerDTO computer = new ComputerDTO();
		
		computer.setId(Long.toString(view.queryId()));
		computer.setName(view.queryName());
		computer.setIntroduced(view.queryDate());
		computer.setDiscontinued(view.queryDate());
		computer.setCompanyId(Long.toString(view.queryId()));

		return computer;
	}
	

	/**
	 * Asks for an id
	 * @return the id
	 */
	public Long queryEntryToDelete() {
		return view.queryId();
	}


}
