package com.excilys.persistence.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.persistence.dao.CompanyDAO;

/**
 * Singleton to have only one access to the database
 * @author excilys
 *
 */
public class JDBCManager {

	private static final Logger logger = 
			LogManager.getLogger(CompanyDAO.class);
	private static JDBCManager instance = null;
	
	private static String url;
	private static String user;
	private static String password;
	
	private JDBCManager() {
		getConnectionProprieties();
	}
	
	public static JDBCManager getInstance() {
		return (instance!=null) ? instance : (instance =new JDBCManager());
	}
	
	private static ResourceBundle bundle() {
		return ResourceBundle.getBundle("database");
	}

	
	public static void getConnectionProprieties() {

		ResourceBundle bundle = bundle();
		url=bundle.getString("MYSQL_DB_URL");
		user=bundle.getString("MYSQL_DB_USERNAME");
		password=bundle.getString("MYSQL_DB_PASSWORD");
	}


	public Connection getConnection() {
		Connection cn = null;
		try {
			cn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e){
			logger.error("Problem with the connexion : "+e.getMessage());
		}
		return cn;			
	}


}
