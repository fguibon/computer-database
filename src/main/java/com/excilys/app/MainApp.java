
package com.excilys.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.controller.CLIController;

public class MainApp {

	private static Logger logger = 
			LogManager.getLogger(MainApp.class);
	
	public static void main(String[] args) {
		try {
			CLIController.getInstance().start();
			logger.info("App started");
		} catch (Exception e) {
			logger.error("Failed to start app : "+ e.getMessage());
		}
		
	}

}