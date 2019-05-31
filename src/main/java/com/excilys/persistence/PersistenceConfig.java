package com.excilys.persistence;

import java.util.Properties;
import java.util.TimeZone;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({"com.excilys.persistence.dao","com.excilys.persistence.utils"})
public class PersistenceConfig {

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
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "validate");
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
