package main.com.excilys.app;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import main.com.excilys.controller.CDBController;
import main.com.excilys.model.Company;
import main.com.excilys.model.Computer;
import main.com.excilys.persistence.CompanyDAO;
import main.com.excilys.persistence.ComputerDAO;
import main.com.excilys.persistence.DatabaseConnectionManager;
import main.com.excilys.view.CDBView;

public class ComputerDatabaseApp {

	public static void main(String[] args) {
		DatabaseConnectionManager dbManager = new DatabaseConnectionManager(
				"127.0.0.1:3306", 
				"computer-database-db", 
				"admincdb", 
				"qwerty1234");
		try {
			Connection conn = dbManager.getConnexion();
			ComputerDAO computerDAO = new ComputerDAO(conn);
			
			Computer computer = new Computer();
			computer.setName("Flo");
			computer.setIntroduced(new Timestamp(1429257940000L));
			computer.setDiscontinued(new Timestamp(1499257940000L));
			computer.setCompanyId(new Long(43));
			computerDAO.create(computer);
			
			
			
		} catch (SQLException e ) {
			e.printStackTrace();
		}
		//CDBView view = new CDBView();
		//CDBController controller = new CDBController(view);
	}

}
