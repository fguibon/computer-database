package com.excilys.persistence.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.exceptions.DatabaseException;
import com.excilys.model.Company;
import com.excilys.model.Page;
import com.excilys.persistence.jdbc.JDBCManager;

/**
 * CompanyDAO class : makes requests to the company table
 * @author excilys
 *
 */
public class CompanyDAO extends DataAccessObject<Company>{

	private static final Logger logger = 
			LogManager.getLogger(CompanyDAO.class);
	
	private static CompanyDAO instance = null;
	
	private static final String INSERT =
			"INSERT INTO company (name) VALUES(?);";

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
		return (instance!=null) ? instance : (instance =new CompanyDAO());
	}


	/**
	 * Creates a company
	 * @return a boolean value to know if it is created
	 * @throws DatabaseException 
	 */
	@Override
	public boolean create(Company company) throws DatabaseException {
		try (Connection conn = JDBCManager.getInstance().getConnection();
				PreparedStatement ps = conn.prepareStatement(INSERT);){
			ps.setString(1, company.getName());
			return ps.executeUpdate()>0;	
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			throw new DatabaseException("Cannot insert company : "+ company.toString());
		}
	}

	/**
	 * Finds and returns all companies in the table
	 * @return a List of companies
	 * @throws DatabaseException 
	 */
	@Override
	public List<Company> findAll() throws DatabaseException {
		
		List<Company> companies = new ArrayList<Company>();
		try (Connection conn = JDBCManager.getInstance().getConnection();
				ResultSet rs = conn.createStatement().executeQuery(SELECT_ALL);){
			while (rs.next()) {
				companies.add( new Company.Builder().setId(rs.getLong("id"))
						.setName(rs.getString("name")).build());
			}
		} catch(SQLException e) {
			logger.error(e.getMessage(),e);
			throw new DatabaseException("Cannot find companies") ;
		}
		return companies;
	}
	
	/**
	 * Finds companies and limits the results
	 * @param limit
	 * @param currentPage
	 * @return
	 * @throws DatabaseException 
	 */
	public List<Company> findAllPaged(int limit, int currentPage) 
			throws DatabaseException {
		
		List<Company> companies = new ArrayList<Company>();
		try (Connection conn = JDBCManager.getInstance().getConnection();
				PreparedStatement ps = conn.prepareStatement(SELECT_ALL_PAGED);){
			int offset = ((currentPage-1) * limit);
			ps.setInt(1,limit);
			ps.setInt(2, offset);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				companies.add( new Company.Builder().setId(rs.getLong("id"))
						.setName(rs.getString("name")).build());
			}
		} catch(SQLException e) {
			logger.error(e.getMessage(),e);
			throw new DatabaseException("Cannot find companies with these parameters : " 
			+new Page(limit,currentPage).toString());
		}
		return companies;
	}


	/**
	 * Find a Company by its id
	 * @return a Company object
	 * @throws DatabaseException 
	 */
	@Override
	public Company findById(Long id) throws DatabaseException {
		Company company = new Company();
		try (Connection conn = JDBCManager.getInstance().getConnection();
				PreparedStatement ps = conn.prepareStatement(SELECT_ONE);){
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				company.setId(rs.getLong("id"));
				company.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new DatabaseException("Cannot find the id provided : "+ id);
		} 
		return company;
	}

	/**
	 * Updates a Company information
	 * @return a Company object
	 * @throws DatabaseException 
	 */
	@Override
	public boolean update(Company company) throws DatabaseException {
		try(Connection conn = JDBCManager.getInstance().getConnection();
				PreparedStatement ps = conn.prepareStatement(UPDATE);) {
			ps.setString(1, company.getName());
			ps.setLong(2, company.getId());
			return ps.executeUpdate()>0;
		} catch (SQLException e) {
			logger.error("Query error : "+ e.getMessage());
			throw new DatabaseException(UPDATE);
		} 
	}

	/**
	 * Deletes a company record
	 * @throws DatabaseException 
	 * 
	 */
	@Override
	public boolean delete(Long id) throws DatabaseException {
		try (Connection conn = JDBCManager.getInstance().getConnection();
				PreparedStatement ps = conn.prepareStatement(DELETE);){
			ps.setLong(1, id);
			return ps.executeUpdate()>0;
		} catch (SQLException e) {
			logger.error("Query error : "+ e.getMessage());
			throw new DatabaseException(DELETE);
		} 
	}
	

}
