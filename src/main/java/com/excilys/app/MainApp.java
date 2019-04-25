
package com.excilys.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.controller.CDBController;

public class MainApp {

	private static Logger logger = 
			LogManager.getLogger(MainApp.class);
	
	public static void main(String[] args) {
		logger.info("App started");
		CDBController.getInstance().start();
		
	}

}