package persistence;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Company;

public class CompanyDAO {
	
	public Connection connect = DBConnection.getInstance();

	public List<Company> getAllCompanies(){
		List<Company> companies = new ArrayList<Company>();
		try {
			ResultSet result = this.connect.createStatement()
					.executeQuery("SELECT id,name from computer-database-db.companies;");
			while (result.next()) {
				Long id = result.getLong("id");
				String name = result.getString("name");
				Company company = new Company(id, name);
				companies.add(company);
			}
			result.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return companies;
	}
	
	
}
