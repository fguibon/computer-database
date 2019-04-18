package main.com.excilys.app;


import main.com.excilys.controller.CDBController;
import main.com.excilys.service.CompanyService;
import main.com.excilys.service.ComputerService;
import main.com.excilys.view.CDBView;

public class ComputerDatabaseApp {

	public static void main(String[] args) {
	
		CDBView view = new CDBView(System.in);
		CompanyService cpnService = new CompanyService();
		ComputerService cptService = new ComputerService();
		
		CDBController controller = new CDBController(view, cpnService, cptService);
		
		controller.start();
	}

}
