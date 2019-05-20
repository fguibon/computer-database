
package com.excilys.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.excilys.config.AppConfig;

public class MainApp {

	private static final Logger LOGGER = 
			LogManager.getLogger(MainApp.class);
	
	public static void main(String[] args) {
		
		AbstractApplicationContext context = 
				new AnnotationConfigApplicationContext(AppConfig.class);
		
		try {
			LOGGER.info("App started");
			context.getBean(CLIController.class).start();
		} catch (Exception e) {
			LOGGER.error("Failed to start app : "+ e.getMessage());
		}
		context.close();
		
	}

}