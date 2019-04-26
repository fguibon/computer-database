package com.excilys.persistence.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.exception.DatabaseQueryException;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.persistence.jdbc.JDBCManager;

/**
 * ComputerDAO class : makes requests to the computer table
 * @author excilys
 *
 */
public class ComputerDAO extends DataAccessObject<Computer>{

	private static final Logger logger = 
			LogManager.getLogger(ComputerDAO.class);

	private static ComputerDAO instance = null;

	private static final String INSERT =
			"INSERT INTO computer (name,introduced,discontinued,company_id)"
					+ " VALUES(?, ?, ?, ?)";

	private static final String SELECT_ONE = 
			"SELECT id,name,introduced,discontinued,company_id FROM computer WHERE id=?;";

	private static final String SELECT_ALL = 
			"SELECT * FROM computer;";

	private static final String UPDATE= 
			"UPDATE computer SET name= ?, introduced = ?, discontinued = ?, company_id = ?"
					+ " WHERE id= ? ;";

	private static final String DELETE=
			"DELETE FROM computer WHERE id= ? ;";

	private static final String SELECT_ALL_PAGED =
			"SELECT id,name,introduced,discontinued,company_id FROM computer "
					+ "LIMIT ? OFFSET ? ;";


	private ComputerDAO() {	
		super();
	}

	public static ComputerDAO getInstance() {
		return (instance!=null) ? instance : (instance = new ComputerDAO());
	}


	/**
	 * Creates a computer
	 * @return a boolean value to know if it is created
	 * @throws DatabaseQueryException 
	 */
	@Override
	public boolean create(Computer computer) throws DatabaseQueryException {
		try(
				Connection conn = JDBCManager.getInstance().getConnection();
				PreparedStatement ps = conn.prepareStatement(INSERT);
				) {
			ps.setString(1, computer.getName());
			ps.setTimestamp(2,Timestamp.valueOf(computer.getIntroduced().atStartOfDay()));
			ps.setTimestamp(3,Timestamp.valueOf(computer.getDiscontinued().atStartOfDay()));
			ps.setLong(4, computer.getCompany().getId());
			return ps.executeUpdate()>0;
		} catch (SQLException e) {
			logger.error("Query error : "+ e.getMessage());
			throw new DatabaseQueryException(INSERT);
		}
	}

	/**
	 * Finds and return all computers in the table
	 * @return a List of computers
	 * @throws DatabaseQueryException 
	 */
	@Override
	public List<Computer> findAll() throws DatabaseQueryException {
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

				date = rs.getDate("discontinued");
				if(date!=null) {
					ldate  = date.toLocalDate();
				}
				computer.setDiscontinued(ldate);
				Long company_id =rs.getLong("company_id");
				if(company_id!=null) {
					Company company = CompanyDAO.getInstance().findById(company_id);
					computer.setCompany(company);
				}
				computers.add(computer);
			}
		} catch(SQLException e) {
			logger.error("Query error : "+ e.getMessage());
			throw new DatabaseQueryException(SELECT_ALL);
		}
		return computers;
	}

	/**
	 * Finds all computers and limits the results
	 * @param limit
	 * @param currentPage
	 * @return
	 * @throws DatabaseQueryException 
	 */
	public List<Computer> findAllPaged(int limit, int currentPage) throws DatabaseQueryException {
		List<Computer> computers = new ArrayList<Computer>();
		int offset = ((currentPage-1) * limit);

		try ( Connection connection = JDBCManager.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PAGED);)
		{
			statement.setInt(1,limit);
			statement.setInt(2, offset);
			ResultSet rs = statement.executeQuery();
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

				date = rs.getDate("discontinued");
				if(date!=null) {
					ldate  = date.toLocalDate();
				}
				computer.setDiscontinued(ldate);
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
			throw new DatabaseQueryException(SELECT_ALL_PAGED);
		}
		return computers;
	}


	/**
	 * Find a Computer by its id
	 * @return a Computer object
	 * @throws DatabaseQueryException 
	 */
	@Override
	public Computer findById(Long id) throws DatabaseQueryException {
		Computer computer = null;

		try (Connection conn = JDBCManager.getInstance().getConnection();
				PreparedStatement ps = conn.prepareStatement(SELECT_ONE);){
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Long computer_id = rs.getLong("id");
				if(id!=null) {
					computer = new Computer();
					computer.setId(computer_id);
					computer.setName(rs.getString("name"));
					Date date =rs.getDate("introduced");
					LocalDate ldate = null;
					if(date!=null) {
						ldate  = date.toLocalDate();
					}
					computer.setIntroduced(ldate);
					date = rs.getDate("discontinued");
					if(date!=null) {
						ldate  = date.toLocalDate();
					}
					computer.setDiscontinued(ldate);

					Long company_id =rs.getLong("company_id");
					if(company_id!=null) {
						Company cp =CompanyDAO.getInstance().findById(company_id);
						computer.setCompany(cp);
					}
				}		
			}	
		} catch (SQLException e) {
			logger.error("Query error : "+ e.getMessage());
			throw new DatabaseQueryException(SELECT_ONE);
		}
		return computer;
	}


	/**
	 * Updates a Computer information
	 * @return a Computer object
	 * @throws DatabaseQueryException 
	 */

	@Override
	public boolean update(Computer computer) throws DatabaseQueryException {
		try(Connection conn = JDBCManager.getInstance().getConnection();
				PreparedStatement ps = conn.prepareStatement(UPDATE);) {
			ps.setString(1, computer.getName());
			ps.setTimestamp(2, Timestamp.valueOf(computer.getIntroduced().atStartOfDay()));
			ps.setTimestamp(3,Timestamp.valueOf(computer.getDiscontinued().atStartOfDay()));
			ps.setLong(4, computer.getCompany().getId());
			ps.setLong(5, computer.getId());
			return ps.executeUpdate()>0;
		} catch (SQLException e) {
			logger.error("Query error : "+ e.getMessage());
			throw new DatabaseQueryException(UPDATE);
		}
	}

	/**
	 * Deletes a computer from the database
	 * @return 
	 * @throws DatabaseQueryException 
	 */
	@Override
	public boolean delete(Long id) throws DatabaseQueryException {

		try (Connection conn = JDBCManager.getInstance().getConnection();
				PreparedStatement ps = conn.prepareStatement(DELETE);){
			ps.setLong(1, id);
			return ps.executeUpdate()>0;
		} catch (SQLException e) {
			logger.error("Query error : "+ e.getMessage());
			throw new DatabaseQueryException(DELETE);
		}
	}

}
