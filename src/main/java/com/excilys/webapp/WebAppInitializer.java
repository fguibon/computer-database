package com.excilys.webapp;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


public class WebAppInitializer implements WebApplicationInitializer {

	private static final Logger LOGGER = 
			LogManager.getLogger(WebAppInitializer.class);
	
	@Override
	public void onStartup(ServletContext context) throws ServletException {
		LOGGER.info("Starting...");
		AnnotationConfigWebApplicationContext webContext =
				new AnnotationConfigWebApplicationContext();
		webContext.register(WebConfig.class);
		webContext.setServletContext(context);
		webContext.refresh();
		
		context.addListener(new ContextLoaderListener(webContext));
		
		ServletRegistration.Dynamic servlet = context.addServlet("cdbapp", new DispatcherServlet(webContext));
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
	}
}
