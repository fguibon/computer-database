package com.excilys.persistence.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.excilys.exceptions.DatabaseException;
import com.excilys.model.Company;
import com.excilys.model.Page;
import com.zaxxer.hikari.HikariDataSource;

/**
 * CompanyDAO class : makes requests to the company table
 * @author excilys
 *
 */

@Component
public class CompanyDAO implements DataAccessObject<Company>{

	private static final Logger LOGGER = 
			LogManager.getLogger(CompanyDAO.class);
	
	private static final String INSERT =
			"INSERT INTO company (name) VALUES(?);";

	private static final String SELECT_ONE = 
			"SELECT id,name FROM company WHERE id=?;";

	private static final String SELECT_ALL = 
			"SELECT * FROM company;";

	private static final String UPDATE= 
			"UPDATE company SET name= ? WHERE id= ? ;";
	
	private static final String DELETE_COMPANY=
			"DELETE FROM company WHERE id= ? ;";
	
	private static final String DELETE_COMPUTER_WHERE=
			"DELETE FROM computer where company_id = ? ;";
	
	private static final String SELECT_ALL_PAGED =
			"SELECT id,name FROM company "
			+ " LIMIT ? OFFSET ? ;";

	@Autowired
	private HikariDataSource datasource;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public CompanyDAO(HikariDataSource datasource, JdbcTemplate jdbcTemplate) {
		this.datasource = datasource;
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * Creates a company
	 * @return a boolean value to know if it is created
	 * @throws DatabaseException 
	 */
	@Override
	public int create(Company company) throws DatabaseException {
		int number = 0;
		try {
			 number = jdbcTemplate.update(INSERT,company.getName());
				
		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage(),e);
			throw new DatabaseException("Cannot insert company : "+ company.toString());
		}
		return number;
	}

	/**
	 * Finds and returns all companies in the table
	 * @return a List of companies
	 * @throws DatabaseException 
	 */

	public List<Company> findAll() throws DatabaseException {
		
		List<Company> companies = new ArrayList<>();
		try (
			Connection conn = datasource.getConnection();
			ResultSet rs = conn.createStatement().executeQuery(SELECT_ALL);
			)
		{
			while (rs.next()) {
				companies.add( new Company.Builder().setId(rs.getLong("id"))
						.setName(rs.getString("name")).build());
			}
		} catch(SQLException e) {
			LOGGER.error(e.getMessage(),e);
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
	public List<Company> findAllPaged(Page page) 
			throws DatabaseException {
		
		List<Company> companies = new ArrayList<>();
		try (
			Connection connection = datasource.getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_ALL_PAGED);	
			)
		{
			int offset = ((page.getCurrentPage()-1) * page.getEntriesPerPage());
			ps.setInt(1,page.getEntriesPerPage());
			ps.setInt(2, offset);
			
			try(ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
				companies.add( new Company.Builder().setId(rs.getLong("id"))
						.setName(rs.getString("name")).build());
				}
			} 
		} catch(SQLException e) {
			LOGGER.error(e.getMessage(),e);
			throw new DatabaseException("Cannot find companies with these parameters : " 
			+page.toString());
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
		try (
			Connection conn = datasource.getConnection();
			PreparedStatement ps = conn.prepareStatement(SELECT_ONE);	
			)
		{
			ps.setLong(1, id);
			try(ResultSet rs = ps.executeQuery()) {
				while(rs.next()) {
					company.setId(rs.getLong("id"));
					company.setName(rs.getString("name"));
				}
			}	
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
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
	public int update(Company company) throws DatabaseException {
		int number = 0;
		try{
			number = jdbcTemplate.update(UPDATE, company.getName(),company.getId());
		} catch (DataAccessException e) {
			LOGGER.error("Query error : "+ e.getMessage());
			throw new DatabaseException(UPDATE);
		} 
		return number;
	}

	/**
	 * Deletes a company record and all computers associated
	 * @throws DatabaseException 
	 * 
	 */
	@Override
	public int delete(Long id) throws DatabaseException {
		int number = 0;
		try {
			try {
				datasource.setAutoCommit(false);
				jdbcTemplate.update(DELETE_COMPUTER_WHERE, id);
				number = jdbcTemplate.update(DELETE_COMPANY,id);
				
				datasource.getConnection().commit();
			} catch(SQLException e) {
				LOGGER.error("Could not remove the company of id : "+id);
				datasource.getConnection().rollback();
			}		
		} catch (SQLException e) {
			LOGGER.error("Query error : "+ e.getMessage());
			throw new DatabaseException("Could not remove the company of id : "+id);
		}
		return number; 
	}
	

}
