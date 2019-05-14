package com.excilys.test.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.excilys.config.AppConfig;
import com.excilys.persistence.dao.CompanyDAO;
import com.excilys.persistence.dao.ComputerDAO;

@Profile("test")
@Configuration
@Import(AppConfig.class)
public class TestConfig {

	@Bean
    @Primary
	public CompanyDAO companyDAO() {
		return Mockito.mock(CompanyDAO.class);
	}
	
	@Bean
    @Primary
	public ComputerDAO computerDAO() {
		return Mockito.mock(ComputerDAO.class);
	}
}
