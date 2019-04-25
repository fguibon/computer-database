package com.excilys.controller;

import java.util.ArrayList;
import java.util.List;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.binding.dto.ComputerDTO;
import com.excilys.binding.mapper.CompanyMapper;
import com.excilys.model.Page;
import com.excilys.persistence.dao.CompanyDAO;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.view.CDBView;

public class CDBController {

	private final int LIMIT=10;
	private final int CURRENT_PAGE=1;
	
	private int currentPage;
	
	private static CDBController instance = null;
	private Page page;
	private CDBView view;
	private CompanyService companyService;
	private ComputerService computerService;
	
	
	private CDBController(CompanyService companyService, ComputerService computerService){
		page = new Page(LIMIT,CURRENT_PAGE);
		view = new CDBView(System.in);
		this.companyService = companyService;
		this.computerService = computerService;
	}
	
	public static CDBController getInstance() {
		return (instance!=null) ? instance : (instance = new CDBController(
				CompanyService.getInstance(CompanyDAO.getInstance(), CompanyMapper.getInstance()),ComputerService.getInstance()));
	}
	
	
	/**
	 * Function calling the display of the starting menu
	 */
	public void start() {
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
			}
		}
	}
	

	/**
	 * option 1
	 * @throws  
	 */
	public void listComputers() {
		currentPage =1;
		boolean ok=true;
		List<ComputerDTO> computers = new ArrayList<ComputerDTO>();
		while(ok) {
			computers = this.computerService.getComputers(this.LIMIT,currentPage);
			if(computers.isEmpty())	ok=false;
			page.setCurrentPage(currentPage++);
			this.view.displayComputers(computers,page);
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
			companies = this.companyService.getCompanies(page, currentPage);
			if(companies.isEmpty()) ok=false;
			page.setCurrentPage(currentPage++);
			this.view.displayCompanies(companies,page);
		}
	}
	
	/** 
	 * option 3
	 */
	public void showComputerDetail() {
		ComputerDTO computer = null;
		Long id = Long.parseLong(this.view.queryId());
		if(id!=null) computer = this.computerService.findById(id);
		if(computer!=null)	this.view.displayComputer(computer);
	}
	
	/**
	 * option 4
	 */
	public void createComputer() {
		ComputerDTO computer =null;
		computer = this.queryComputerToCreate();
		if(computer!=null) this.computerService.createComputer(computer);
	}
	

	/**
	 * option 5
	 */
	public void updateComputer() {
		ComputerDTO computer = this.queryComputerToUpdate();
		if(computer!=null) this.computerService.update(computer);	
	}
	
	/**
	 * option 6
	 */
	public void deleteComputer() {
		String id = null;
		id = this.queryComputerToDelete();
		if(id!=null) this.computerService.delete(id);	
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
			computer.setCompanyDTO(new CompanyDTO());
			computer.getCompanyDTO().setId(view.queryId());
		} else {
			view.notifyInvalidName();
		}
		return computer;
	}
	
	/**
	 * Asks for the computer information
	 * @return a Computer object
	 */
	public ComputerDTO queryComputerToUpdate() {
		ComputerDTO computer = new ComputerDTO();
		computer.setCompanyDTO(new CompanyDTO());
		
		computer.setId(view.queryId());
		computer.setName(view.queryName());
		computer.setIntroduced(view.queryDate());
		computer.setDiscontinued(view.queryDate());
		computer.getCompanyDTO().setId(view.queryId());

		return computer;
	}
	

	/**
	 * Asks for an id
	 * @return the id
	 */
	public String queryComputerToDelete() {
		return view.queryId();
	}


}
