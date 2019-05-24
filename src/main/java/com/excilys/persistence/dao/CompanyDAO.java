package com.excilys.persistence.dao;


import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.exceptions.DatabaseException;
import com.excilys.model.Company;
import com.excilys.model.Sorting;
import com.excilys.persistence.rowmapper.CompanyRowMapper;

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
			"DELETE FROM company WHERE id=? ;";
	
	private static final String SELECT_ALL_PAGED =
			"SELECT id,name FROM company "
			+ " LIMIT ? OFFSET ? ;";
	
	private JdbcTemplate jdbcTemplate;
	
	
	public CompanyDAO(JdbcTemplate jdbcTemplate) {
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
		try {
			CompanyRowMapper rowMapper = new CompanyRowMapper();
			companies = jdbcTemplate.query(SELECT_ALL,rowMapper);
		} catch(DataAccessException e) {
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
	public List<Company> findAllPaged(Sorting sorting) 
			throws DatabaseException {
		
		List<Company> companies = new ArrayList<>();
		int offset = ((sorting.getPage()-1) * sorting.getLimit());
		try {
			CompanyRowMapper rowMapper = new CompanyRowMapper();
			companies = jdbcTemplate.query(SELECT_ALL_PAGED,
					new Object[] {sorting.getLimit(),offset},rowMapper);		
		} catch(DataAccessException e) {
			LOGGER.error(e.getMessage(),e);
			throw new DatabaseException("Cannot find companies with : "+sorting.toString());
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
		try {
			CompanyRowMapper rowMapper = new CompanyRowMapper();
			List<Company> companies = jdbcTemplate.query(SELECT_ONE,new Object[] {id}, rowMapper);
			if(!companies.isEmpty()) company = companies.get(0);	
		} catch (DataAccessException e) {
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
			LOGGER.error(e.getMessage());
			throw new DatabaseException("Could not update the company of id : "+company.toString());
		} 
		return number;
	}

	/**
	 * Deletes a company record
	 * @throws DatabaseException 
	 * 
	 */
	@Override
	@Transactional
	public int delete(Long id) throws DatabaseException {
		int number = 0;
		try {			
			number = jdbcTemplate.update(DELETE_COMPANY, id);
		} catch(DataAccessException e) {
			LOGGER.error(e.getMessage());
			throw new DatabaseException("Could not remove the company of id : ");
		}		
		return number; 
	}	

}
