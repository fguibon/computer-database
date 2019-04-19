package main.com.excilys.controller;

import java.util.List;

import main.com.excilys.binding.dto.CompanyDTO;
import main.com.excilys.binding.dto.ComputerDTO;
import main.com.excilys.model.Company;
import main.com.excilys.model.Computer;
import main.com.excilys.model.Page;
import main.com.excilys.service.CompanyService;
import main.com.excilys.service.ComputerService;
import main.com.excilys.view.CDBView;

public class CDBController {

	private final int LIMIT=10;
	private final int CURRENT_PAGE=1;
	
	private int currentPage;
	
	private static CDBController instance = new CDBController(
			CompanyService.getInstance(),ComputerService.getInstance());
	private Page page;
	private CDBView view;
	private CompanyService companyService;
	private ComputerService computerService;
	
	
	private CDBController(CompanyService companyService, ComputerService computerService){
		page = new Page(LIMIT,CURRENT_PAGE);
		view = new CDBView(System.in);
	}
	
	public static CDBController getInstance() {
		return instance;
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
		List<ComputerDTO> computers = null;
		while(ok) {
			computers = this.computerService.getComputers(LIMIT,currentPage);
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
		List<CompanyDTO> companies = null;
		while(ok) {
			companies = this.companyService.getCompanies(LIMIT, currentPage);
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
		Long id = this.view.queryId();
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
		Computer computer = null;
		computer = this.queryComputerToUpdate();
		if(computer!=null) this.computerService.update(computer);	
	}
	
	/**
	 * option 6
	 */
	public void deleteComputer() {
		Long id = null;
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
	public ComputerDTO queryComputerToUpdate() {
		ComputerDTO computer = new ComputerDTO();
		
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
