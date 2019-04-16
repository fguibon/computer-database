package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton to have only one access to the database
 * @author excilys
 *
 */
public class DBConnection {

	/**
	 * Connection url
	 */
	private static String url = "jdbc:mysql//127.0.0.1:3306";
	
	/**
	 * User
	 */
	private static String user = "admincdb";
	
	/**
	 * Password
	 */
	private static String pswd = "qwerty1234";
	
	private static Connection connect;
	
	public static Connection getInstance() {
		if(connect==null) {
			try {
				connect = DriverManager.getConnection(url, user, pswd);
			} catch (SQLException e){
				e.printStackTrace();
			}
		}
		return connect;			
	}
	
	
}
