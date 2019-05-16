package com.excilys.persistence.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.excilys.exceptions.DatabaseException;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.model.Sorting;
import com.excilys.persistence.jdbc.JDBCManager;

/**
 * ComputerDAO class : makes requests to the computer table
 * @author excilys
 *
 */

@Component
public class ComputerDAO implements DataAccessObject<Computer>{

	private enum Field { ID,NAME,INTRODUCED,DISCONTINUED,COMPANY_ID}

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
			"SELECT id FROM computer; ";

	private JDBCManager datasource;
	private CompanyDAO companyDAO;
	
	public ComputerDAO(JDBCManager datasource,CompanyDAO companyDAO) {	
		this.datasource = datasource;
		this.companyDAO = companyDAO;
	}




	/**
	 * Creates a computer
	 * @return a boolean value to know if it is created
	 * @throws Exception 
	 */
	@Override
	public boolean create(Computer computer) throws DatabaseException {
		try(
				Connection connection = datasource.getConnection();
				PreparedStatement ps = connection.prepareStatement(CREATE);
				) 
		{
			ps.setString(1, computer.getName());
			LocalDate introducedDate = computer.getIntroduced();
			if(introducedDate!=null) {
				ps.setTimestamp(2,Timestamp.valueOf(introducedDate.atStartOfDay()));
			} else {
				ps.setTimestamp(2, null);
			}
			LocalDate discontinuedDate =computer.getDiscontinued();
			if(discontinuedDate!=null) {
				ps.setTimestamp(3,Timestamp.valueOf(discontinuedDate.atStartOfDay()));
			} else {
				ps.setTimestamp(3,null);
			}
			Company company = computer.getCompany();
			if(company!=null) {
				Long id =company.getId();
				if(id!=null) {
					ps.setLong(4, id);
				} else {
					ps.setNull(4, Types.BIGINT);
				}
			} else {
				ps.setNull(4, Types.BIGINT);
			}
			return ps.executeUpdate()>0;
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
			throw new DatabaseException(CREATE);
		}
	}

	/**
	 * Finds and return all computers in the table
	 * @return a List of computers
	 * @throws Exception 
	 */
	public List<Computer> findAll() throws DatabaseException {
		List<Computer> computers = new ArrayList<>();

		try(
				Connection conn = datasource.getConnection();
				ResultSet rs = conn.createStatement().executeQuery(SELECT_ALL);
				) 
		{
			while (rs.next()) {
				Computer computer = new Computer();
				computer.setId(rs.getLong(Field.ID.toString()));
				computer.setName(rs.getString("name"));

				Date date =rs.getDate(Field.INTRODUCED.toString());
				LocalDate ldate = null;
				if(date!=null) {
					ldate  = date.toLocalDate();
				}
				computer.setIntroduced(ldate);

				Date date2 = rs.getDate(Field.DISCONTINUED.toString());
				LocalDate ldate2 = null;
				if(date2!=null) {
					ldate2  = date2.toLocalDate();
				}
				computer.setDiscontinued(ldate2);
				Long companyId =rs.getLong(Field.COMPANY_ID.toString());
				if(companyId!=null) {
					Company company = companyDAO.findById(companyId);
					computer.setCompany(company);
				}
				computers.add(computer);
			}
		} catch(SQLException e) {
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

		try ( 
				Connection connection = datasource.getConnection();
				PreparedStatement ps = connection.prepareStatement(getMyTableQuerySQL(sorting.getField(), isAscending));
				)
		{
			int offset = ((page.getCurrentPage()-1) * page.getEntriesPerPage());
			ps.setString(1, "%" +filter +"%");
			ps.setInt(2,page.getEntriesPerPage());
			ps.setInt(3, offset);
			try (ResultSet rs = ps.executeQuery()){
				while (rs.next()) {
					Computer computer = new Computer();
					computer.setId(rs.getLong(Field.ID.toString()));
					computer.setName(rs.getString(Field.NAME.toString()));

					Date date =rs.getDate(Field.INTRODUCED.toString());
					LocalDate ldate = null;
					if(date!=null) {
						ldate  = date.toLocalDate();
					}
					computer.setIntroduced(ldate);

					Date date2 = rs.getDate(Field.DISCONTINUED.toString());
					LocalDate ldate2 = null;
					if(date2!=null) {
						ldate2  = date2.toLocalDate();
					}
					computer.setDiscontinued(ldate2);
					Long companyId =rs.getLong(Field.COMPANY_ID.toString());
					if(companyId!=null) {
						Company company =companyDAO.findById(companyId);
						computer.setCompany(company);
					}
					computers.add(computer);
				}	
			}
		} catch(SQLException e) {
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
	 * @return a Computer object
	 * @throws Exception 
	 */

	@Override
	public boolean update(Computer computer) throws DatabaseException {
		try(
				Connection conn = datasource.getConnection();
				PreparedStatement ps = conn.prepareStatement(UPDATE);
				) 
		{
			ps.setString(1, computer.getName());
			LocalDate introducedDate = computer.getIntroduced();
			if(introducedDate!=null) {
				ps.setTimestamp(2,Timestamp.valueOf(introducedDate.atStartOfDay()));
			} else {
				ps.setTimestamp(2, null);
			}
			LocalDate discontinuedDate =computer.getDiscontinued();
			if(discontinuedDate!=null) {
				ps.setTimestamp(3,Timestamp.valueOf(discontinuedDate.atStartOfDay()));
			} else {
				ps.setTimestamp(3,null);
			}

			Long id =(computer.getCompany()==null) ? null :computer.getCompany().getId();
			if(id!=null) {
				ps.setLong(4, id);
			} else {
				ps.setNull(4, Types.BIGINT);
			}
			ps.setLong(5, computer.getId());
			return ps.executeUpdate()>0;
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
			throw new DatabaseException(UPDATE);
		}
	}

	/**
	 * Deletes a computer from the database
	 * @return  
	 * @throws Exception 
	 */
	@Override
	public void delete(Long id) throws DatabaseException {

		try (
				Connection conn = datasource.getConnection();
				PreparedStatement ps = conn.prepareStatement(DELETE);
				)
		{
			ps.setLong(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error( e.getMessage());
			throw new DatabaseException(DELETE);
		}
	}

	/**
	 * Get the computers total count
	 * @return
	 * @throws DatabaseException
	 */
	public int count() throws DatabaseException {
		int number = 0;
		try (
				Connection conn = datasource.getConnection();
				ResultSet rs = conn.createStatement().executeQuery(COUNT);
				)
		{
			while (rs.next()) {
				number++;
			}
		} catch (SQLException e) {
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
