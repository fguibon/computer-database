package com.excilys.config;

import java.util.TimeZone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({"com.excilys.binding.mapper","com.excilys.persistence.dao",
	"com.excilys.service","com.excilys.validator","com.excilys.cli"})
public class RootConfig {

	static {TimeZone.setDefault(TimeZone.getTimeZone("UTC"));}

	@Bean
	public HikariDataSource mySqlDataSource() {
		HikariConfig config = new HikariConfig("/hibernate.properties");
		config.setMaximumPoolSize(10);
		return new HikariDataSource(config);
	}
	
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean factory = new LocalSessionFactoryBean();
		factory.setDataSource(mySqlDataSource());
		factory.setPackagesToScan("com.excilys.model");

		return factory;
	}

	@Bean
	public PlatformTransactionManager hibernateTransactionManager() {
		HibernateTransactionManager transactionManager
		= new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}

}
