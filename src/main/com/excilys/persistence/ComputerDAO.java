package main.com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import main.com.excilys.model.Computer;
import main.com.excilys.util.DataAccessObject;

/**
 * ComputerDAO class : makes requests to the computer table
 * @author excilys
 *
 */
public class ComputerDAO extends DataAccessObject<Computer>{

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

	public ComputerDAO(Connection connection) {
		super(connection);
	}


	/**
	 * Creates a computer
	 * @return a boolean value to know if it is created
	 */
	@Override
	public boolean create(Computer dto) {
		try {
			PreparedStatement ps = this.connection.prepareStatement(INSERT);
			ps.setString(1, dto.getName());
			ps.setTimestamp(2,Timestamp.valueOf(dto.getIntroduced().atStartOfDay()));
			ps.setTimestamp(3,Timestamp.valueOf(dto.getDiscontinued().atStartOfDay()));
			ps.setLong(4, dto.getCompanyId());
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

		try {
			ResultSet rs = this.connection.createStatement()
					.executeQuery(SELECT_ALL);
			while (rs.next()) {
				Computer computer = new Computer();
				computer.setId(rs.getLong("id"));
				computer.setName(rs.getString("name"));
				
				Timestamp tstamp = rs.getTimestamp("introduced");
				LocalDate ldate =null;
				if(tstamp!=null) {
					ldate  = tstamp.toLocalDateTime().toLocalDate();
				}
				computer.setIntroduced(ldate);
				
				tstamp = rs.getTimestamp("discontinued");
				if(tstamp!=null) {
					ldate  = tstamp.toLocalDateTime().toLocalDate();
				}
				computer.setDiscontinued(ldate);
				computer.setCompanyId(rs.getLong("company_id"));
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
		Computer computer = new Computer();
		try (PreparedStatement ps = this.connection.prepareStatement(SELECT_ONE);){
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				computer.setId(rs.getLong("id"));
				computer.setName(rs.getString("name"));
				Timestamp date = rs.getTimestamp("introduced");
				LocalDate ldate =null;
				if(date!=null) {
					ldate  = date.toLocalDateTime().toLocalDate();
				}
				computer.setIntroduced(ldate);
				
				date = rs.getTimestamp("discontinued");
				if(date!=null) {
					ldate  = date.toLocalDateTime().toLocalDate();
				}
				computer.setDiscontinued(ldate);
				
				computer.setCompanyId(rs.getLong("company_id"));
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
	public Computer update(Computer dto) {
		Computer computer = null;
		try(PreparedStatement ps = this.connection.prepareStatement(UPDATE);) {
			ps.setString(1, dto.getName());
			ps.setTimestamp(2, Timestamp.valueOf(dto.getIntroduced().atStartOfDay()));
			ps.setTimestamp(3,Timestamp.valueOf(dto.getDiscontinued().atStartOfDay()));
			ps.setLong(4, dto.getCompanyId());
			ps.setLong(5, dto.getId());
			ps.executeUpdate();
			return computer;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Delete a computer from the database
	 */
	@Override
	public void delete(Long id) {
		try (PreparedStatement ps = this.connection.prepareStatement(DELETE);){
			ps.setLong(1, id);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
