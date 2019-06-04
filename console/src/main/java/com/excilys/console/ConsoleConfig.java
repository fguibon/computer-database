package com.excilys.console;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.excilys.binding.BindingConfig;
import com.excilys.persistence.PersistenceConfig;
import com.excilys.service.ServiceConfig;

@Configuration
@ComponentScan("com.excilys.console")
@Import({PersistenceConfig.class,BindingConfig.class,ServiceConfig.class})
public class ConsoleConfig {

}
