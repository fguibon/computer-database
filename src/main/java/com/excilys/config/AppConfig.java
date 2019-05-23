package com.excilys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@ComponentScan("com.excilys.controller")
@Import(RootConfig.class)
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
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
