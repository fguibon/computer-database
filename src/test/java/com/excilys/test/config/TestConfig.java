package com.excilys.test.config;

import java.util.Properties;
import java.util.TimeZone;

import javax.persistence.EntityManagerFactory;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.excilys.binding.BindingConfig;
import com.excilys.binding.mapper.CompanyMapper;
import com.excilys.binding.mapper.ComputerMapper;
import com.excilys.persistence.PersistenceConfig;
import com.excilys.persistence.dao.CompanyDAO;
import com.excilys.persistence.dao.ComputerDAO;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.service.ServiceConfig;
import com.excilys.binding.validator.Validator;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


@Configuration
@ComponentScan({"com.excilys.test"})
@Import({BindingConfig.class,PersistenceConfig.class,ServiceConfig.class})
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
	
	static {TimeZone.setDefault(TimeZone.getTimeZone("UTC"));}

	@Bean
	public HikariDataSource mySqlDataSource() {
		HikariConfig config = new HikariConfig("/datasource.properties");
		config.setMaximumPoolSize(10);
		return new HikariDataSource(config);
	}


	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean fact = new LocalSessionFactoryBean();
		fact.setDataSource(mySqlDataSource());
		fact.setPackagesToScan("com.excilys.core");
		fact.setHibernateProperties(hibernateProperties());

		return fact;
	}
	
	static Properties hibernateProperties() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		hibernateProperties.setProperty("hibernate.show_sql", "true");
		hibernateProperties.setProperty("hibernate.format_sql", "true");
		return hibernateProperties;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
	    JpaTransactionManager transactionManager = new JpaTransactionManager();
	    transactionManager.setEntityManagerFactory(emf);
	 
	    return transactionManager;
	}
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
	    return new PersistenceExceptionTranslationPostProcessor();
	}

}
