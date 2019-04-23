package com.excilys.persistence.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.exception.DatabaseQueryException;
import com.excilys.model.Company;
import com.excilys.persistence.jdbc.JDBCManager;

/**
 * CompanyDAO class : makes requests to the company table
 * @author excilys
 *
 */
public class CompanyDAO extends DataAccessObject<Company>{

	private static CompanyDAO instance = new CompanyDAO();
	
	private static final String INSERT =
			"INSERT INTO company (name) VALUES(?)";

	private static final String SELECT_ONE = 
			"SELECT id,name FROM company WHERE id=?;";

	private static final String SELECT_ALL = 
			"SELECT * FROM company;";

	private static final String UPDATE= 
			"UPDATE company SET name= ? WHERE id= ? ;";
	
	private static final String DELETE=
			"DELETE FROM company WHERE id= ? ;";
	
	
	private static final String SELECT_ALL_PAGED =
			"SELECT id,name FROM company "
			+ " LIMIT ? OFFSET ? ;";

	
	private CompanyDAO() {
		super();
	}
	
	public static CompanyDAO getInstance() {
		return instance;
	}


	/**
	 * Creates a company
	 * @return a boolean value to know if it is created
	 * @throws DatabaseQueryException 
	 */
	@Override
	public boolean create(Company dto) throws DatabaseQueryException {
		boolean created = false;
		try (Connection conn = JDBCManager.getInstance();
				PreparedStatement ps = conn.prepareStatement(INSERT);){
			ps.setString(1, dto.getName());
			if(ps.executeUpdate()!=0) created =true;
			return created;	
		} catch (SQLException e) {
			throw new DatabaseQueryException(INSERT);
		}
	}

	/**
	 * Finds and returns all companies in the table
	 * @return a List of companies
	 * @throws DatabaseQueryException 
	 */
	@Override
	public List<Company> findAll() throws DatabaseQueryException {
		
		List<Company> companies = new ArrayList<Company>();
		try (Connection conn = JDBCManager.getInstance();
				ResultSet rs = conn.createStatement().executeQuery(SELECT_ALL);){
			while (rs.next()) {
				companies.add( new Company(rs.getLong("id"),rs.getString("name")));
			}
		} catch(SQLException e) {
			throw new DatabaseQueryException(SELECT_ALL) ;
		}
		return companies;
	}
	
	/**
	 * Finds companies and limits the results
	 * @param limit
	 * @param currentPage
	 * @return
	 * @throws Exception 
	 */
	public List<Company> findAllPaged(int limit, int currentPage) 
			throws DatabaseQueryException {
		
		List<Company> companies = new ArrayList<Company>();
		try (Connection conn = JDBCManager.getInstance();
				PreparedStatement ps = conn.prepareStatement(SELECT_ALL_PAGED);){
			int offset = ((currentPage-1) * limit);
			ps.setInt(1,limit);
			ps.setInt(2, offset);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				companies.add( new Company(rs.getLong("id"),rs.getString("name")));
			}
		} catch(SQLException e) {
			throw new DatabaseQueryException(SELECT_ALL_PAGED);
		}
		
		return companies;
	}


	/**
	 * Find a Company by its id
	 * @return a Company object
	 * @throws Exception 
	 */
	@Override
	public Company findById(Long id) throws DatabaseQueryException {
		Company company = new Company();
		try (Connection conn = JDBCManager.getInstance();
				PreparedStatement ps = conn.prepareStatement(SELECT_ONE);){
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				company.setId(rs.getLong("id"));
				company.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			throw new DatabaseQueryException(SELECT_ONE);
		} 
		return company;
	}

	/**
	 * Updates a Company information
	 * @return a Company object
	 * @throws DatabaseQueryException 
	 */
	@Override
	public boolean update(Company dto) throws DatabaseQueryException {
		boolean updated = false;
		try(Connection conn = JDBCManager.getInstance();
				PreparedStatement ps = conn.prepareStatement(UPDATE);) {
			ps.setString(1, dto.getName());
			ps.setLong(2, dto.getId());
			if(ps.executeUpdate()!=0) updated = true;
		} catch (SQLException e) {
			throw new DatabaseQueryException(UPDATE);
		} 
		return updated;
	}

	/**
	 * Deletes a company record
	 * @throws DatabaseQueryException 
	 * 
	 */
	@Override
	public void delete(Long id) throws DatabaseQueryException {
		try (Connection conn = JDBCManager.getInstance();
				PreparedStatement ps = conn.prepareStatement(DELETE);){
			ps.setLong(1, id);
			ps.execute();
		} catch (SQLException e) {
			throw new DatabaseQueryException(DELETE);
		} 
	}
	

}
