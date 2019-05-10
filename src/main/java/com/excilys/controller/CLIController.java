package com.excilys.controller;

import java.util.ArrayList;
import java.util.List;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.binding.dto.ComputerDTO;
import com.excilys.binding.mapper.ComputerMapper;
import com.excilys.exceptions.DatabaseException;
import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.model.Sorting;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.view.CLIView;

public class CLIController {

	private final int LIMIT=10;
	private final int CURRENT_PAGE=1;
	
	private int currentPage;
	
	private static CLIController instance = null;
	private Page page;
	private Sorting sorting;
	private CLIView view;
	private CompanyService companyService;
	private ComputerService computerService;
	
	
	private CLIController(CompanyService companyService, ComputerService computerService){
		page = new Page(LIMIT,CURRENT_PAGE);
		sorting = new Sorting("id","asc");
		view = new CLIView(System.in);
		this.companyService = companyService;
		this.computerService = computerService;
	}
	
	public static CLIController getInstance() {
		return (instance!=null) ? instance : (instance = new CLIController(
				CompanyService.getInstance(),ComputerService.getInstance()));
	}
	
	
	/**
	 * Function calling the display of the starting menu
	 * @throws Exception 
	 */
	public void start() throws Exception {
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
		currentPage =1;
		boolean ok=true;
		page.setCurrentPage(currentPage);
		page.setEntriesPerPage(LIMIT);
		List<ComputerDTO> computersDTO = new ArrayList<ComputerDTO>();
		List<Computer> computers = new ArrayList<Computer>();
		while(ok) {
			computers = this.computerService.findAll(page,"",sorting);
			for(Computer c:computers) {
				computersDTO.add(ComputerMapper.getInstance().modelToDto(c));
			}
			if(computers.isEmpty())	ok=false;
			page.setCurrentPage(currentPage++);
			this.view.displayComputers(computersDTO,page);
		}
		
	}
	
	/**
	 * option 2
	 */
	public void listCompanies() {
		currentPage =1;
		boolean ok=true;
		List<CompanyDTO> companies = new ArrayList<CompanyDTO>();
		while(ok) {
			companies = this.companyService.getCompanies(page.getEntriesPerPage(), currentPage);
			if(companies.isEmpty()) ok=false;
			page.setCurrentPage(currentPage++);
			this.view.displayCompanies(companies,page);
		}
	}
	
	/** 
	 * option 3
	 * @throws DatabaseException 
	 */
	public void showComputerDetail() throws DatabaseException {
		ComputerDTO computer = null;
		Long id = this.view.queryId();
		if(id!=null) computer = ComputerMapper.getInstance().modelToDto(this.computerService.findById(id));
		if(computer!=null)	this.view.displayComputer(computer);
	}
	
	/**
	 * option 4
	 * @throws Exception 
	 *
	 */
	public void createComputer() throws Exception {
		ComputerDTO computer =null;
		computer = this.queryComputerToCreate();
		if(computer!=null) {
			this.computerService.createComputer(ComputerMapper.getInstance().dtoToModel(computer));
		}
	}
	

	/**
	 * option 5
	 * @throws Exception 
	 */
	public void updateComputer() throws Exception {
		ComputerDTO computer = this.queryComputerToUpdate();
		if(computer!=null) {
			if(this.computerService.update(ComputerMapper.getInstance().dtoToModel(computer))) view.notifySuccess();
		}
	}
	
	/**
	 * option 6
	 * @throws DatabaseException 
	 */
	public void deleteComputer() throws DatabaseException {
		Long id = null;
		id = this.queryComputerToDelete();
		if(id!=null) {
			this.computerService.delete(id);	
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
	public Long queryComputerToDelete() {
		return view.queryId();
	}


}
