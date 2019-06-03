package com.excilys.config;

import com.excilys.binding.mapper.ComputerMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

import org.mockito.Mockito;

@Configuration
@ComponentScan({"com.excilys.binding.validator","com.excilys.binding.mapper"})
public class BindingConfig {
	
	
	@Bean
	public ComputerMapper computerMapper() {
		return Mockito.mock(ComputerMapper.class);
	}	
}
