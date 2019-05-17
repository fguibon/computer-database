package com.excilys.persistence.dao;

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
import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.model.Sorting;
import com.excilys.persistence.rowmapper.ComputerRowMapper;

/**
 * ComputerDAO class : makes requests to the computer table
 * @author excilys
 *
 */

@Component
public class ComputerDAO implements DataAccessObject<Computer>{

	public enum Field { ID,NAME,INTRODUCED,DISCONTINUED}

	private enum Order {ASC, DESC}

	private static final Logger LOGGER = 
			LogManager.getLogger(ComputerDAO.class);


	private static final String CREATE =
			"INSERT INTO computer (name,introduced,discontinued,company_id)"
					+ " VALUES (?, ?, ?, ?) ;";

	private static final String SELECT = 
			"SELECT cpt.id,cpt.name,introduced,discontinued,company_id,cpn.name "
			+ "FROM computer AS cpt ";
	
	private static final String SELECT_ONE = 
			SELECT + "LEFT JOIN company AS cpn ON cpt.company_id=cpn.id "
			+ "WHERE cpt.id=?;";

	private static final String SELECT_ALL = 
			SELECT + "LEFT JOIN company AS cpn ON cpt.company_id=cpn.id;";

	private static final String SELECT_ORDER_BY =
			SELECT + "LEFT JOIN company AS cpn ON cpt.company_id=cpn.id "
			+ "WHERE UPPER(cpt.name) LIKE UPPER(?) ORDER BY ";

	private static final String PAGED=" LIMIT ? OFFSET ? ; ";

	private static final String UPDATE= 
			"UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=?"
					+ " WHERE id=? ;";

	private static final String DELETE=
			"DELETE FROM computer WHERE id=? ;";


	private static final String COUNT = 
			"SELECT COUNT(id) FROM computer; ";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public ComputerDAO(JdbcTemplate jdbcTemplate) {	
		this.jdbcTemplate = jdbcTemplate;
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
			throw new DatabaseException("Could not retrieve the list of computers");
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
		int offset = ((page.getCurrentPage()-1) * page.getEntriesPerPage());
		String sql = getSortingQuerySQL(sorting.getField(), sorting.getOrder());
		try {
			ComputerRowMapper rowMapper = new ComputerRowMapper();
			computers = jdbcTemplate.query(sql,
					new Object[] {"%"+filter +"%",page.getEntriesPerPage(),offset},rowMapper);
		} catch(DataAccessException e) {
			LOGGER.error(e.getMessage());
			throw new DatabaseException("Could not retrieve the computers");
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
		Computer computer = new Computer();
		try {
			ComputerRowMapper rowMapper = new ComputerRowMapper();
			computer = jdbcTemplate.query(SELECT_ONE,new Object[] {id}, rowMapper).get(0);
		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage());
			throw new DatabaseException("Could not retrieve the computer of id: "+id);
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
		try {
			number = jdbcTemplate.update(UPDATE,computer.getName(),computer.getIntroduced(),
					computer.getDiscontinued(),computer.getCompany().getId(), computer.getId());
		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage());
			throw new DatabaseException("Could not update the computer: "+computer.toString());
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
			throw new DatabaseException("Could not delete the computer of id: "+id);
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
			throw new DatabaseException("Could not retrieve the total number of computers");
		}
		 return number;	
	}


	public String getField(String choice) {
		switch(choice) {
		case "name":
			return "cpt."+Field.NAME.toString();
		case "intro":
			return ""+Field.INTRODUCED.toString();
		case "disco":
			return ""+Field.DISCONTINUED.toString();
		case "company":
			return "cpn."+Field.NAME.toString();
		default:
			return "cpt."+Field.ID.toString();
		}
	}

	public String getOrder(String choice) {
		switch(choice) {
		case "asc":
			return Order.ASC.toString();
		case "desc":
			return Order.DESC.toString();
		default:
			return Order.ASC.toString();
		}
	}

	public String getSortingQuerySQL( String fieldParam, String orderParam ){
		String sql = SELECT_ORDER_BY + getField(fieldParam)+ 
				" " +getOrder(orderParam) + PAGED;
		LOGGER.debug(sql);
		return sql;
	}

}
