package main.com.excilys.app;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import main.com.excilys.controller.CDBController;
import main.com.excilys.model.Computer;
import main.com.excilys.persistence.CompanyDAO;
import main.com.excilys.persistence.ComputerDAO;
import main.com.excilys.persistence.DatabaseConnectionManager;
import main.com.excilys.service.CompanyService;
import main.com.excilys.service.ComputerService;
import main.com.excilys.view.CDBView;

public class ComputerDatabaseApp {

	public static void main(String[] args) {
		DatabaseConnectionManager dbManager = new DatabaseConnectionManager(
				"127.0.0.1:3306", 
				"computer-database-db", 
				"admincdb", 
				"qwerty1234");
		Connection conn = null;
		try {
			conn = dbManager.getConnexion();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		CompanyDAO companyDAO = new CompanyDAO(conn);
		ComputerDAO computerDAO = new ComputerDAO(conn);
		CDBView view = new CDBView(System.in);
		CompanyService cpnService = new CompanyService(companyDAO);
		ComputerService cptService = new ComputerService(computerDAO);
		CDBController controller = new CDBController(view, cpnService, cptService);
		
	}

}
