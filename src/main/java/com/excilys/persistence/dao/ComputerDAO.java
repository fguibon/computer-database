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

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.persistence.jdbc.JDBCManager;

/**
 * ComputerDAO class : makes requests to the computer table
 * @author excilys
 *
 */
public class ComputerDAO extends DataAccessObject<Computer>{

	private static ComputerDAO instance = new ComputerDAO();
	
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
	}
	
	public static ComputerDAO getInstance() {
		return instance;
	}


	/**
	 * Creates a computer
	 * @return a boolean value to know if it is created
	 */
	@Override
	public boolean create(Computer dto) {
		Connection conn = JDBCManager.getInstance();
		try(PreparedStatement ps = conn.prepareStatement(INSERT);) {
			
			ps.setString(1, dto.getName());
			ps.setTimestamp(2,Timestamp.valueOf(dto.getIntroduced().atStartOfDay()));
			ps.setTimestamp(3,Timestamp.valueOf(dto.getDiscontinued().atStartOfDay()));
			ps.setLong(4, dto.getCompany().getId());
			ps.execute();
			// TODO Verify if updated
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Finds and return all computers in the table
	 * @return a List of computers
	 */
	@Override
	public List<Computer> findAll() {
		List<Computer> computers = new ArrayList<Computer>();
		Connection conn = JDBCManager.getInstance();
		try {
			ResultSet rs = conn.createStatement()
					.executeQuery(SELECT_ALL);
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
					Company cp =CompanyDAO.getInstance().findById(company_id);
					computer.setCompany(cp);
				}
				computers.add(computer);
			}
			rs.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return computers;
	}
	
	/**
	 * Finds all computers and limits the results
	 * @param limit
	 * @param currentPage
	 * @return
	 */
	public List<Computer> findAllPaged(int limit, int currentPage) {
		List<Computer> computers = new ArrayList<Computer>();
		int offset = ((currentPage-1) * limit);
		Connection conn = JDBCManager.getInstance();
		try (PreparedStatement ps = conn.prepareStatement(SELECT_ALL_PAGED);){
			ps.setInt(1,limit);
			ps.setInt(2, offset);
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
				computers.add(computer);
			}
			rs.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return computers;
	}


	/**
	 * Find a Computer by its id
	 * @return a Computer object
	 */
	@Override
	public Computer findById(Long id) {
		Computer computer = null;
		Connection conn = JDBCManager.getInstance();
		try (PreparedStatement ps = conn.prepareStatement(SELECT_ONE);){
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
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return computer;
	}

	
	/**
	 * Updates a Computer information
	 * @return a Computer object
	 */
	
	@Override
	public boolean update(Computer dto) {
		boolean ok=false;
		Connection conn = JDBCManager.getInstance();
		try(PreparedStatement ps = conn.prepareStatement(UPDATE);) {
			ps.setString(1, dto.getName());
			ps.setTimestamp(2, Timestamp.valueOf(dto.getIntroduced().atStartOfDay()));
			ps.setTimestamp(3,Timestamp.valueOf(dto.getDiscontinued().atStartOfDay()));
			ps.setLong(4, dto.getCompany().getId());
			ps.setLong(5, dto.getId());
			ps.executeUpdate();
			ok=true;
			return ok;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Deletes a computer from the database
	 */
	@Override
	public void delete(Long id) {
		Connection conn = JDBCManager.getInstance();
		try (PreparedStatement ps = conn.prepareStatement(DELETE);){
			ps.setLong(1, id);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
