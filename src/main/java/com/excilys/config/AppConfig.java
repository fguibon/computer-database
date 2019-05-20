package com.excilys.config;

import java.util.TimeZone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan("com.excilys")
public class AppConfig {

	static {TimeZone.setDefault(TimeZone.getTimeZone("UTC"));}
	
	@Bean
	public HikariDataSource mySqlDataSource() {
		HikariConfig config = new HikariConfig("/datasource.properties");
		config.setMaximumPoolSize(5);
		return new HikariDataSource(config);
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate(HikariDataSource datasource) {
		return new JdbcTemplate(datasource);
	}
	
}
