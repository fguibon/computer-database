package main.com.excilys.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton to have only one access to the database
 * @author excilys
 *
 */
public class JDBCManager {

	
	private final static String host ="127.0.0.1:3306";
	private final static String databaseName ="computer-database-db";
	private final static String url="jdbc:mysql://"+host+"/"+databaseName+"?serverTimezone=UTC";
	private final static String username = "admincdb";
	private final static String password = "qwerty1234";
	
	
	public static Connection getInstance() {
		Connection cn = null;
		try {
			cn = DriverManager.getConnection(url,username,password );
		} catch (SQLException e){
			System.out.println("Problem with the connexion : "+e.getMessage());
		}
		return cn;
		
				
	}
	
	
}
