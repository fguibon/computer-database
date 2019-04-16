package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.Computer;

public class ComputerDAO {

	public Connection connect = DatabaseConnectionManager.getInstance();

	public List<Computer> getAllComputers(){
		List<Computer> computers = new ArrayList<Computer>();
		
		try {
			ResultSet result = this.connect.createStatement()
					.executeQuery("SELECT id,name from computer-database-db.company;");
			while (result.next()) {
				Long id = result.getLong("id");
				String name = result.getString("name");
				Timestamp introduced = result.getTimestamp("introduced");
				Timestamp discontinued = result.getTimestamp("discontinued");
				Long id_company = result.getLong("id_company");
				Computer computer = new Computer(id, name,introduced, discontinued, id_company);
				computers.add(computer);
			}
			result.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return computers;
	}

	public Computer getComputer(Long id) {
		
		Computer computer = new Computer(id, null, null, null, null);
		PreparedStatement ps = null;
		String query = "SELECT * from computer-database-db.company WHERE id=?;";
		try {
			ResultSet result = null;
			ps = connect.prepareStatement(query);
			ps.setLong(1, id);
			result= ps.executeQuery();
			while (result.next()) {
				String name = result.getString("name");
				Timestamp introduced = result.getTimestamp("introduced");
				Timestamp discontinued = result.getTimestamp("discontinued");
				Long id_company = result.getLong("id_company");
				computer.setName(name);
				computer.setIntroduced(introduced);
				computer.setDiscontinued(discontinued);
				computer.setId_company(id_company);
			}
			result.close();
			ps.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return computer;	
	}

	public void createComputer(Computer computer) {
		PreparedStatement ps = null;
		String query = "INSERT INTO computer-database.computer"
				+ "(id,name,introduced,discontinued,id_company) VALUES(?,?,?,?,?)";
		try {
			ResultSet rs = null;
			ps = connect.prepareStatement(query);
			
			ps.setLong(1, computer.getId());
			ps.setString(2, computer.getName());
			ps.setTimestamp(3, computer.getIntroduced());
			ps.setTimestamp(4, computer.getDiscontinued());
			ps.setLong(5, computer.getId_company());
			
			rs = ps.executeQuery();
			
			rs.close();
			ps.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void updateComputer(Computer computer) {
		PreparedStatement ps = null;
		String query = "UPDATE computer-database.computer"
				+ "(id,name,introduced,discontinued,id_company) SET VALUES(?,?,?,?,?)";
		try {
			ResultSet rs = null;
			ps = connect.prepareStatement(query);
			
			ps.setLong(1, computer.getId());
			ps.setString(2, computer.getName());
			ps.setTimestamp(3, computer.getIntroduced());
			ps.setTimestamp(4, computer.getDiscontinued());
			ps.setLong(5, computer.getId_company());
			
			rs = ps.executeQuery();
			
			rs.close();
			ps.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteComputer(Long id) {

	}
}
