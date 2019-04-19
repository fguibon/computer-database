package main.com.excilys.persistence.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlDataSource;

/**
 * Singleton to have only one access to the database
 * @author excilys
 *
 */
public class JDBCManager {

	
	public static DataSource getDataSource() {
		Properties props = new Properties();
		FileInputStream fis = null;
		MysqlDataSource source = null;
		try {
			fis = new FileInputStream("db.properties");
			props.load(fis);
			source = new MysqlDataSource();
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
