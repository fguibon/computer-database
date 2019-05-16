package com.excilys.persistence.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import com.excilys.exceptions.DatabaseException;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.model.Sorting;
import com.excilys.persistence.rowmapper.ComputerRowMapper;
import com.zaxxer.hikari.HikariDataSource;

/**
 * ComputerDAO class : makes requests to the computer table
 * @author excilys
 *
 */

@Component
public class ComputerDAO implements DataAccessObject<Computer>{

	public enum Field { ID,NAME,INTRODUCED,DISCONTINUED,COMPANY_ID}

	private enum Order {ASC, DESC}

	private static final Logger LOGGER = 
			LogManager.getLogger(ComputerDAO.class);


	private static final String CREATE =
			"INSERT INTO computer (name,introduced,discontinued,company_id)"
					+ " VALUES (?, ?, ?, ?) ;";

	private static final String SELECT_ONE = 
			"SELECT id,name,introduced,discontinued,company_id FROM computer WHERE id=?;";

	private static final String SELECT_ALL = 
			"SELECT * FROM computer;";

	private static final String SELECT_ORDER_BY =
			"SELECT id,name,introduced,discontinued,company_id FROM computer "
					+ "WHERE UPPER(name) LIKE UPPER(?) ORDER BY ";

	private static final String PAGED=" LIMIT ? OFFSET ? ; ";

	private static final String UPDATE= 
			"UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=?"
					+ " WHERE id=? ;";

	private static final String DELETE=
			"DELETE FROM computer WHERE id=? ;";


	private static final String COUNT = 
			"SELECT COUNT(id) FROM computer; ";

	@Autowired
	private HikariDataSource datasource;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private CompanyDAO companyDAO;
	
	
	public ComputerDAO(HikariDataSource datasource, JdbcTemplate jdbcTemplate, CompanyDAO companyDAO) {	
		this.datasource = datasource;
		this.jdbcTemplate = jdbcTemplate;
		this.companyDAO = companyDAO;
	}


	/**
	 * Creates a computer
	 * @return a boolean value to know if it is created
	 * @throws Exception 
	 */
	@Override
	public int create(Computer computer) throws DatabaseException {
		int number = 0;
		SqlParameterSource params = new BeanPropertySqlParameterSource(computer);
		try {
			number = jdbcTemplate.update(CREATE,params);
		} catch (DataAccessException e) {
			LOGGER.warn(e.getMessage());
			throw new DatabaseException("Cannot insert computer : "+computer.toString());
		}
		return number;
				
	}

	/**
	 * Finds and return all computers in the table
	 * @return a List of computers
	 * @throws Exception 
	 */
	public List<Computer> findAll() throws DatabaseException {
		
		List<Computer> computers = new ArrayList<>();
		try{
			ComputerRowMapper rowMapper = new ComputerRowMapper();
			computers = jdbcTemplate.query(SELECT_ALL,rowMapper);
		} catch(DataAccessException e) {
			LOGGER.error(e.getMessage());
			throw new DatabaseException(SELECT_ALL);
		}
		return computers;
	}

	/**
	 * Finds all computers and limits the results
	 * @param limit
	 * @param currentPage
	 * @return
	 * @throws Exception 
	 */
	public List<Computer> findAllPaged(Page page, String filter, Sorting sorting) throws DatabaseException {
		List<Computer> computers = new ArrayList<>();	
		boolean isAscending = ( getOrder(sorting.getOrder()).toString().compareToIgnoreCase("ASC")==0);
		int offset = ((page.getCurrentPage()-1) * page.getEntriesPerPage());
		String sql = getMyTableQuerySQL(sorting.getField(), isAscending);
		try {
			ComputerRowMapper rowMapper = new ComputerRowMapper();
			
			computers = jdbcTemplate.query(sql,
					new Object[] {"%"+filter +"%",page.getEntriesPerPage(),offset},rowMapper);
		} catch(DataAccessException e) {
			LOGGER.error(e.getMessage());
			throw new DatabaseException(SELECT_ORDER_BY);
		}
		return computers;
	}


	/**
	 * Find a Computer by its id
	 * @return a Computer object
	 * @throws Exception 
	 */
	@Override
	public Computer findById(Long id) throws DatabaseException {
		Computer computer = null;

		try (
				Connection conn = datasource.getConnection();
				PreparedStatement ps = conn.prepareStatement(SELECT_ONE);
				)
		{
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					Long computerId = rs.getLong("id");
					computer = new Computer();
					computer.setId(computerId);
					computer.setName(rs.getString("name"));
					Date introDate =rs.getDate("introduced");
					LocalDate ldate = (introDate==null)? null:introDate.toLocalDate();
					computer.setIntroduced(ldate);

					Date discoDate = rs.getDate("discontinued");
					LocalDate ldate2 = (discoDate==null)?null:discoDate.toLocalDate();
					computer.setDiscontinued(ldate2);

					Long companyId =rs.getLong("company_id");
					if(companyId!=null) {
						Company cp =companyDAO.findById(companyId);
						computer.setCompany(cp);
					}			
				}
			}

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
			throw new DatabaseException(SELECT_ONE);
		}
		return computer;
	}


	/**
	 * Updates a Computer information
	 * @return the number of rows affected 
	 */
	@Override
	public int update(Computer computer) throws DatabaseException {
		int number = 0;
		SqlParameterSource params = new BeanPropertySqlParameterSource(computer);
		try {
			number = jdbcTemplate.update(UPDATE,params);
		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage());
			throw new DatabaseException(UPDATE);
		}
		return number;	
	}

	/**
	 * Deletes a computer from the database
	 * @return the number of rows affected
	 */
	@Override
	public int delete(Long id) throws DatabaseException {
		int number = 0;
		try {
			number = jdbcTemplate.update(DELETE,id);
		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage());
			throw new DatabaseException(DELETE);
		}
		return number;	
	}

	/**
	 * Get the computers total count
	 * @return the total number 
	 * @throws DatabaseException 
	 */
	public int count() throws DatabaseException  {
		int number = 0;
		try {
			number = jdbcTemplate.queryForObject(COUNT, Integer.class);
		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage());
			throw new DatabaseException(COUNT);
		}
		 return number;	
	}


	public Field getField(String choice) {
		switch(choice) {
		case "name":
			return Field.NAME;
		case "intro":
			return Field.INTRODUCED;
		case "disco":
			return Field.DISCONTINUED;
		case "company":
			return Field.COMPANY_ID;
		default:
			return Field.ID;
		}
	}

	public Order getOrder(String choice) {
		switch(choice) {
		case "asc":
			return Order.ASC;
		case "desc":
			return Order.DESC;
		default:
			return Order.ASC;
		}
	}

	public String getMyTableQuerySQL( String fieldParam, boolean isAscending ){
		return SELECT_ORDER_BY + getField(fieldParam).toString()+ 
				( isAscending ? " ASC " : " DESC " ) + PAGED;
	}

}
