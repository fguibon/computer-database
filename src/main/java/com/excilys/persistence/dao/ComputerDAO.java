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

import com.excilys.exceptions.DatabaseException;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.persistence.jdbc.JDBCManager;

/**
 * ComputerDAO class : makes requests to the computer table
 * @author excilys
 *
 */
public class ComputerDAO extends DataAccessObject<Computer>{

	private static enum Field { ID,NAME,INTRODUCED,DISCONTINUED,COMPANY_ID}
	
	private static enum Order {ASC, DESC}
	
	private static final Logger logger = 
			LogManager.getLogger(ComputerDAO.class);

	private static ComputerDAO instance = null;

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
	
	private static final String PAGED=	" LIMIT ? OFFSET ? ; ";

	private static final String UPDATE= 
			"UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=?"
					+ " WHERE id=? ;";

	private static final String DELETE=
			"DELETE FROM computer WHERE id=? ;";


	private static final String COUNT = 
			"SELECT id FROM computer; ";


	private ComputerDAO() {	
		super();
	}

	public static ComputerDAO getInstance() {
		return (instance!=null) ? instance : (instance = new ComputerDAO());
	}


	/**
	 * Creates a computer
	 * @return a boolean value to know if it is created
	 * @throws Exception 
	 */
	@Override
	public boolean create(Computer computer) throws DatabaseException {
		try(
				Connection conn = JDBCManager.getInstance().getConnection();
				PreparedStatement ps = conn.prepareStatement(CREATE);
				) {
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
			logger.error("Query error : "+ e.getMessage());
			throw new DatabaseException(CREATE);
		}
	}

	/**
	 * Finds and return all computers in the table
	 * @return a List of computers
	 * @throws Exception 
	 */
	public List<Computer> findAll() throws DatabaseException {
		List<Computer> computers = new ArrayList<Computer>();

		try(
				Connection conn = JDBCManager.getInstance().getConnection();
				ResultSet rs = conn.createStatement().executeQuery(SELECT_ALL);
				) {
			while (rs.next()) {
				Computer computer = new Computer();
				computer.setId(rs.getLong("id"));
				computer.setName(rs.getString("name"));

				Date date =rs.getDate("introduced");
				LocalDate ldate = null;
				if(date!=null) {
					ldate  = date.toLocalDate();
				}
				computer.setIntroduced(ldate);

				Date date2 = rs.getDate("discontinued");
				LocalDate ldate2 = null;
				if(date2!=null) {
					ldate2  = date2.toLocalDate();
				}
				computer.setDiscontinued(ldate2);
				Long company_id =rs.getLong("company_id");
				if(company_id!=null) {
					Company company = CompanyDAO.getInstance().findById(company_id);
					computer.setCompany(company);
				}
				computers.add(computer);
			}
		} catch(SQLException e) {
			logger.error("Query error : "+ e.getMessage());
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
	public List<Computer> findAllPaged(String filter, String field, String order, 
			int limit, int currentPage ) throws DatabaseException {
		List<Computer> computers = new ArrayList<Computer>();
		int offset = ((currentPage-1) * limit);
		boolean isAscending = ( getOrder(order).toString().compareToIgnoreCase("ASC")==0)? true : false;
		try ( 
			Connection connection = JDBCManager.getInstance().getConnection();
			PreparedStatement ps = connection.prepareStatement(getMyTableQuerySQL(field, isAscending));)
		{
			ps.setString(1, "%" +filter +"%");
			ps.setInt(2,limit);
			ps.setInt(3, offset);
			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Computer computer = new Computer();
				computer.setId(rs.getLong("id"));
				computer.setName(rs.getString("name"));

				Date date =rs.getDate("introduced");
				LocalDate ldate = null;
				if(date!=null) {
					ldate  = date.toLocalDate();
				}
				computer.setIntroduced(ldate);

				Date date2 = rs.getDate("discontinued");
				LocalDate ldate2 = null;
				if(date2!=null) {
					ldate2  = date2.toLocalDate();
				}
				computer.setDiscontinued(ldate2);
				Long company_id =rs.getLong("company_id");
				if(company_id!=null) {
					Company company =CompanyDAO.getInstance().findById(company_id);
					computer.setCompany(company);
				}
				computers.add(computer);
			}
			rs.close();
		} catch(SQLException e) {
			logger.error("Query error : "+ e.getMessage());
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

		try (Connection conn = JDBCManager.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(SELECT_ONE);)
		{
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Long computer_id = rs.getLong("id");
				if(id!=null) {
					computer = new Computer();
					computer.setId(computer_id);
					computer.setName(rs.getString("name"));
					Date introDate =rs.getDate("introduced");
					LocalDate ldate = (introDate==null)? null:introDate.toLocalDate();
					computer.setIntroduced(ldate);

					Date discoDate = rs.getDate("discontinued");
					LocalDate ldate2 = (discoDate==null)?null:discoDate.toLocalDate();
					computer.setDiscontinued(ldate2);

					Long company_id =rs.getLong("company_id");
					if(company_id!=null) {
						Company cp =CompanyDAO.getInstance().findById(company_id);
						computer.setCompany(cp);
					}
				}		
			}	
		} catch (SQLException e) {
			logger.error("Query error : "+ e.getMessage());
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
				Connection conn = JDBCManager.getInstance().getConnection();
				PreparedStatement ps = conn.prepareStatement(UPDATE);) 
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
			logger.error("Query error : "+ e.getMessage());
			throw new DatabaseException(UPDATE);
		}
	}

	/**
	 * Deletes a computer from the database
	 * @return  
	 * @throws Exception 
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

	/**
	 * Get the computers total count
	 * @return
	 * @throws DatabaseException
	 */
	public int count() throws DatabaseException {
		int number = 0;
		try (Connection conn = JDBCManager.getInstance().getConnection();
				ResultSet rs = conn.createStatement().executeQuery(COUNT);){
			while (rs.next()) {
				number++;
			}
		} catch (SQLException e) {
			logger.error("Query error : "+ e.getMessage());
			throw new DatabaseException(COUNT);
		}
		return number;
	}
	
	public Field getField(String choice) {
		switch(choice) {
		case "id":
			return Field.ID;
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
	
	 public String getMyTableQuerySQL( String fieldParam, boolean isAscending )
	 {
	     return SELECT_ORDER_BY + getField(fieldParam).toString()+ ( isAscending ? " ASC " : " DESC " ) + PAGED;
	 }

}
