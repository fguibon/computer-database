package com.excilys.config;

import com.excilys.persistence.dao.CompanyDAO;
import com.excilys.persistence.dao.ComputerDAO;
import com.excilys.persistence.dao.UserDAO;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

import org.mockito.Mockito;

@Configuration
@ComponentScan({"com.excilys.persistence.dao"})
public class ServiceTestConfig {
	
	@Bean
	public CompanyDAO companyDAO() {
		return Mockito.mock(CompanyDAO.class);
	}
	
	@Bean
	public ComputerDAO computerDAO() {
		return Mockito.mock(ComputerDAO.class);
	}
	
	@Bean
	public UserDAO userDAO() {
		return Mockito.mock(UserDAO.class);
	}
}
