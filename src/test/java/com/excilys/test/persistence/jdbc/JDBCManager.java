package com.excilys.test.persistence.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.h2.jdbcx.JdbcDataSource;





/**
 * Singleton to have only one access to the database
 * @author excilys
 *
 */
public class JDBCManager {

	
	public static JdbcDataSource getDataSource() {
		Properties props = new Properties();
		FileInputStream fis = null;
		JdbcDataSource source = null;
		try {
			fis = new FileInputStream("src/test/resources/db.properties");
			props.load(fis);
			source = new JdbcDataSource();
			source.setURL(props.getProperty("MYSQL_DB_URL"));
			source.setUser(props.getProperty("MYSQL_DB_USERNAME"));
			source.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return source;
	}
	
	
	public static Connection getInstance() {
		Connection cn = null;
		try {
			cn = getDataSource().getConnection();
		} catch (SQLException e){
			System.out.println("Problem with the connexion : "+e.getMessage());
		}
		return cn;			
	}
	
	
}
