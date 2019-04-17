package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Computer;
import util.DataAccessObject;

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
					+ "WHERE id= ? ;";
	
	private static final String DELETE=
			"DELETE FROM computer WHERE id= ? ;";

	public ComputerDAO(Connection connection) {
		super(connection);
	}


	@Override
	public boolean create(Computer dto) {
		try {
			PreparedStatement ps = this.connection.prepareStatement(INSERT);
			ps.setString(1, dto.getName());
			ps.setTimestamp(2, dto.getIntroduced());
			ps.setTimestamp(3,dto.getDiscontinued());
			ps.setLong(4, dto.getCompanyId());
			ps.execute();
			// TODO Verify if updated
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

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
				computer.setIntroduced(rs.getTimestamp("introduced"));
				computer.setDiscontinued(rs.getTimestamp("discontinued"));
				computer.setCompanyId(rs.getLong("company_id"));
				computers.add(computer);
			}
			rs.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return computers;
	}


	@Override
	public Computer findById(Long id) {
		Computer computer = new Computer();
		try (PreparedStatement ps = this.connection.prepareStatement(SELECT_ONE);){
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				computer.setId(rs.getLong("id"));
				computer.setName(rs.getString("name"));
				computer.setIntroduced(rs.getTimestamp("introduced"));
				computer.setDiscontinued(rs.getTimestamp("discontinued"));
				computer.setCompanyId(rs.getLong("company_id"));
			}	
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return computer;
	}

	@Override
	public Computer update(Computer dto) {
		Computer computer = null;
		try(PreparedStatement ps = this.connection.prepareStatement(UPDATE);) {
			ps.setString(1, dto.getName());
			ps.setTimestamp(2, dto.getIntroduced());
			ps.setTimestamp(3,dto.getDiscontinued());
			ps.setLong(4, dto.getCompanyId());
			ps.setLong(5, dto.getId());
			ps.execute();
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
