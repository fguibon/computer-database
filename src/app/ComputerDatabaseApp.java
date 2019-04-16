package app;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import controller.CDBController;
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
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM company;");
			while(rs.next()) {
				System.out.println(rs.getInt(1));
			}
		} catch (SQLException e ) {
			e.printStackTrace();
		}
		//CDBView view = new CDBView();
		//CDBController controller = new CDBController(view);
	}

}
