package main.com.excilys.controller;

import java.util.List;

import main.com.excilys.model.Company;
import main.com.excilys.model.Computer;
import main.com.excilys.model.Page;
import main.com.excilys.service.CompanyService;
import main.com.excilys.service.ComputerService;
import main.com.excilys.view.CDBView;

public class CDBController {

	CDBView view;
	CompanyService cpnService;
	ComputerService cptService;
	Page page;
	
	private int currentPage;

	
	private final int LIMIT=10;
	private final int CURRENT_PAGE=1;
	
	public CDBController(CDBView view, CompanyService cpnService, ComputerService cptService){
		this.view=view;
		this.cpnService=cpnService;
		this.cptService=cptService;
		page = new Page(LIMIT,CURRENT_PAGE);
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
	 */
	public void listComputers() {
		currentPage =1;
		boolean ok=true;
		List<Computer> computers = null;
		while(ok) {
			computers = this.cptService.getComputers(LIMIT,currentPage);
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
		List<Company> companies = null;
		while(ok) {
			companies = this.cpnService.getCompanies(LIMIT, currentPage);
			if(companies.isEmpty()) ok=false;
			page.setCurrentPage(currentPage++);
			this.view.displayCompanies(companies,page);
		}
	}
	
	/** 
	 * option 3
	 */
	public void showComputerDetail() {
		Computer computer = null;
		Long id = this.view.queryId();
		if(id!=null) computer = this.cptService.findById(id);
		if(computer!=null)	this.view.displayComputer(computer);
	}
	
	/**
	 * option 4
	 */
	public void createComputer() {
		Computer computer =null;
		computer = this.queryComputerToCreate();
		if(computer!=null) this.cptService.createComputer(computer);
	}
	

	/**
	 * option 5
	 */
	public void updateComputer() {
		Computer computer = null;
		computer = this.queryComputerToUpdate();
		if(computer!=null) this.cptService.update(computer);	
	}
	
	/**
	 * option 6
	 */
	public void deleteComputer() {
		Long id = null;
		id = this.queryComputerToDelete();
		if(id!=null) this.cptService.delete(id);	
	}
	
	
	/**
	 * Asks for the computer information
	 * @return a Computer object
	 */
	public Computer queryComputerToCreate() {
		Computer computer = null;
		String name =view.queryName();
		if(name!=null && !name.isEmpty()) {
			computer = new Computer();
			computer.setName(name);
			computer.setIntroduced(view.queryDate());
			computer.setDiscontinued(view.queryDate());
			computer.setCompany(new Company());
			computer.getCompany().setId(view.queryId());
		} else {
			view.notifyInvalidName();
		}
		return computer;
	}
	
	/**
	 * Asks for the computer information
	 * @return a Computer object
	 */
	public Computer queryComputerToUpdate() {
		Computer computer = new Computer();
		
		computer.setId(view.queryId());
		computer.setName(view.queryName());
		computer.setIntroduced(view.queryDate());
		computer.setDiscontinued(view.queryDate());
		computer.getCompany().setId(view.queryId());

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
