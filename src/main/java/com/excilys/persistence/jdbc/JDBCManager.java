package com.excilys.persistence.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.exceptions.DatabaseException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Singleton to have only one access to the database
 * @author excilys
 *
 */
public class JDBCManager {
	
	static {TimeZone.setDefault(TimeZone.getTimeZone("UTC"));}
	private static final Logger logger = 
			LogManager.getLogger(JDBCManager.class);
	private static JDBCManager instance = null;
	
	private static HikariConfig config;
    private static HikariDataSource dataSource;
	
	
	private JDBCManager() {
		config = new HikariConfig("/datasource.properties");
		config.setMaximumPoolSize(5);
		dataSource = new HikariDataSource(config);
	}
	
	public static JDBCManager getInstance() {
		return (instance!=null) ? instance : (instance =new JDBCManager());
	}


	public Connection getConnection() throws DatabaseException {
		try {
			Connection connection = dataSource.getConnection();
			return connection;
			
		} catch (SQLException e){
			logger.error("Problem with the connexion");
			throw new DatabaseException("Problem with the connexion : "+e.getMessage());
		}			
	}


}
