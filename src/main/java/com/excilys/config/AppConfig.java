package com.excilys.config;

import java.util.TimeZone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({"com.excilys.binding.mapper","com.excilys.persistence.dao",
	"com.excilys.service","com.excilys.validator","com.excilys.controller"})
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	}

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
	
	@Bean
	public ViewResolver viewResolver() {
	    InternalResourceViewResolver bean = new InternalResourceViewResolver();
	 
	    bean.setViewClass(JstlView.class);
	    bean.setPrefix("/WEB-INF/jsp/");
	    bean.setSuffix(".jsp");
	 
	    return bean;
	}
	
	
}
