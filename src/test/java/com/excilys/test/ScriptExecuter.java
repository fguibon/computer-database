package com.excilys.test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.excilys.exceptions.DatabaseException;
import com.excilys.persistence.jdbc.JDBCManager;


public class ScriptExecuter {

	private static ScriptExecuter instance = null;
	
	private ScriptExecuter() {	
	}
	
	public static ScriptExecuter getInstance() {
		return (instance!=null) ? instance : (instance =new ScriptExecuter());
	}
	
	
	private static void executeScript(String filename) throws SQLException, IOException, DatabaseException {
		try (final Connection connection = JDBCManager.getInstance().getConnection();
				final Statement statement = connection.createStatement();
				final InputStream resourceAsStream = ScriptExecuter.class.getClassLoader().getResourceAsStream(filename);
				final Scanner scanner = new Scanner(resourceAsStream)) {

			StringBuilder sb = new StringBuilder();
			while (scanner.hasNextLine()) {
				final String nextLine = scanner.nextLine();
				sb.append(nextLine);
			}
			final StringTokenizer stringTokenizer = new StringTokenizer(sb.toString(), ";");

			while (stringTokenizer.hasMoreTokens()) {
				final String nextToken = stringTokenizer.nextToken();
				statement.execute(nextToken);
			}
		}
	}

	public void reload() throws IOException, SQLException, DatabaseException {
		executeScript("schema.sql");
		executeScript("entries.sql");
	}
}
