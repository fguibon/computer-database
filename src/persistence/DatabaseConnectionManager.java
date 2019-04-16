package persistence;

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
	
	
	public DatabaseConnectionManager (String host, String databaseName, 
			String username, String pswd) {
		this.url ="jdbc:mysql://"+host+"/"+databaseName+"?serverTimezone=UTC";
		this.properties = new Properties();
		this.properties.setProperty("user", username);
		this.properties.setProperty("password", pswd);
	}
	
	
	public Connection getConnexion() throws SQLException {
		return DriverManager.getConnection(this.url, this.properties);
				
	}
	
	
}
