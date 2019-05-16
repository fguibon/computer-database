package com.excilys.persistence.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.excilys.exceptions.DatabaseException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Singleton to have only one access to the database
 * @author excilys
 *
 */

@Component
public class JDBCManager {
	
	static {TimeZone.setDefault(TimeZone.getTimeZone("UTC"));}
	private static final Logger LOGGER = 
			LogManager.getLogger(JDBCManager.class);
	
    private static HikariDataSource dataSource;
	
	
	private JDBCManager() {
		HikariConfig config = new HikariConfig("/datasource.properties");
		config.setMaximumPoolSize(5);
		dataSource = new HikariDataSource(config);
	}


	public Connection getConnection() throws DatabaseException {
		try {
			return dataSource.getConnection();
			
		} catch (SQLException e){
			LOGGER.error("Problem with the connexion");
			throw new DatabaseException("Problem with the connexion : "+e.getMessage());
		}			
	}


}
