package app;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import controller.CDBController;
import model.Company;
import persistence.CompanyDAO;
import persistence.ComputerDAO;
import persistence.DatabaseConnectionManager;
import view.CDBView;

public class ComputerDatabaseApp {

	public static void main(String[] args) {
		DatabaseConnectionManager dbManager = new DatabaseConnectionManager(
				"127.0.0.1:3306", 
				"computer-database-db", 
				"admincdb", 
				"qwerty1234");
		try {
			Connection conn = dbManager.getConnexion();
			CompanyDAO companyDAO = new CompanyDAO(conn);
			Company cpa1 = companyDAO.findById(new Long(33));
			System.out.println(cpa1.getId());
			System.out.println(cpa1.getName());
			
		} catch (SQLException e ) {
			e.printStackTrace();
		}
		//CDBView view = new CDBView();
		//CDBController controller = new CDBController(view);
	}

}
