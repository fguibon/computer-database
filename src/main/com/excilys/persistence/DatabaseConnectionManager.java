package main.com.excilys.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Singleton to have only one access to the database
 * @author excilys
 *
 */
public class DatabaseConnectionManager {

	private final String url;
	private final Properties properties;
	private final String host ="127.0.0.1:3306";
	private final String databaseName ="computer-database-db";
	private final String username = "admincdb";
	private final String password = "qwerty1234";
	
	public DatabaseConnectionManager () {
		this.url ="jdbc:mysql://"+host+"/"+databaseName+"?serverTimezone=UTC";
		this.properties = new Properties();
		this.properties.setProperty("user", username);
		this.properties.setProperty("password", password);
	}
	
	
	public Connection getConnexion() throws SQLException {
		return DriverManager.getConnection(this.url, this.properties);
				
	}
	
	
}
