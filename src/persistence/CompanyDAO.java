package persistence;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import model.Company;
import util.DataAccessObject;

public class CompanyDAO extends DataAccessObject<Company>{

	private static final String INSERT ="INSERT INTO company (name) "
			+ "VALUES(?)";

	private static final String SELECT_ONE = 
			"SELECT id,name FROM company WHERE id=?;";

	private static final String SELECT_ALL = 
			"SELECT * FROM company;";

	private static final String UPDATE= 
			"UPDATE company SET name= ? WHERE id= ? ;";
	
	private static final String DELETE=
			"DELETE FROM company WHERE id=?;";

	public CompanyDAO(Connection connection) {
		super(connection);
	}


	@Override
	public Company create(Company dto) {
		try (PreparedStatement ps = this.connection.prepareStatement(INSERT);){
			ps.setString(1, dto.getName());
			ps.execute();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Company> findAll() {
		List<Company> companies = new ArrayList<Company>();
		try {
			ResultSet rs = this.connection.createStatement()
					.executeQuery(SELECT_ALL);
			while (rs.next()) {
				Long id = rs.getLong("id");
				String name = rs.getString("name");
				Company company = new Company(id, name);
				companies.add(company);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return companies;
	}



	@Override
	public Company findById(Long id) {
		Company company = new Company();
		try (PreparedStatement ps = this.connection.prepareStatement(SELECT_ONE);){
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				company.setId(rs.getLong("id"));
				company.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return company;
	}

	@Override
	public Company update(Company dto) {
		Company company = null;
		try(PreparedStatement ps = this.connection.prepareStatement(UPDATE);) {
			ps.setString(1, dto.getName());
			ps.setLong(2, dto.getId());
			ps.execute();
			company = this.findById(dto.getId());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return company;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}


}
