package com.excilys.test.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import com.excilys.binding.mapper.CompanyMapper;
import com.excilys.binding.mapper.ComputerMapper;
import com.excilys.config.AppConfig;
import com.excilys.persistence.dao.CompanyDAO;
import com.excilys.persistence.dao.ComputerDAO;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validator.Validator;


@Profile("test")
@Configuration
@Import(AppConfig.class)
public class TestConfig {
	
	@Bean
	public CompanyDAO companyDAO() {
		return Mockito.mock(CompanyDAO.class);
	}
	
	@Bean
	public ComputerDAO computerDAO() {
		return Mockito.mock(ComputerDAO.class);
	}
	
	@Bean
	public CompanyService companyService() {
		return Mockito.mock(CompanyService.class);
	}
	
	@Bean
	public ComputerService computerService() {
		return Mockito.mock(ComputerService.class);
	}
	
	@Bean
	public CompanyMapper companyMapper() {
		return Mockito.mock(CompanyMapper.class);
	}
	
	@Bean
	public ComputerMapper computerMapper() {
		return Mockito.mock(ComputerMapper.class);
	}
	
	@Bean
	public Validator validator() {
		return Mockito.mock(Validator.class);
	}

}
