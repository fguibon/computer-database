package com.excilys.persistence.rowmapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.model.Company;
import com.excilys.model.Computer;

public class ComputerRowMapper implements RowMapper<Computer>{

	@Override
	public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Computer computer = new Computer();
		computer.setId(rs.getLong("computer_id"));
		computer.setName(rs.getString("computer_name"));
		Date introduced =rs.getDate("introduced");
		computer.setIntroduced((introduced==null)? null : introduced.toLocalDate());
		Date discontinued = rs.getDate("discontinued");
		computer.setDiscontinued((discontinued==null)? null : discontinued.toLocalDate());
		long companyId = rs.getLong("company_id");
		if(companyId!=0) {
			Company company = new Company();
			company.setId(companyId);
			company.setName(rs.getString("company_name"));
			computer.setCompany(company);
		}
		return computer;
	}

}
