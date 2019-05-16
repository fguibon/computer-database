package com.excilys.config;

import javax.servlet.ServletContext;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class WebAppInitializer implements WebApplicationInitializer {


	@Override
	public void onStartup(ServletContext container) {

		AnnotationConfigWebApplicationContext rootContext =
				new AnnotationConfigWebApplicationContext();
		rootContext.register(AppConfig.class);

		container.addListener(new ContextLoaderListener(rootContext));
	}
}
