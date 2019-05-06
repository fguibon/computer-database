package com.excilys.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.binding.dto.ComputerDTO;
import com.excilys.exceptions.DatabaseException;
import com.excilys.service.ComputerService;


public class DashboardServlet extends HttpServlet {

	private final int LIMIT=10;
	private final int CURRENT_PAGE=1;

	private final ComputerService computerService = ComputerService.getInstance();
	private static final Logger logger = LogManager.getLogger(DashboardServlet.class);
	
	private static final long serialVersionUID = 1L;

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)

			throws ServletException, IOException {
		int offset =CURRENT_PAGE;
		int limit = LIMIT;
		int numberOfComputers=0;
		try {
			numberOfComputers = computerService.count();
		} catch (DatabaseException e) {
			logger.warn(e.getMessage(), e);
		}
		
		String pageParam =request.getParameter("page");
		String noOfRecordsParam = request.getParameter("noOfRecords");
		 
		if(noOfRecordsParam !=null && !noOfRecordsParam.isEmpty()) limit = Integer.parseInt(noOfRecordsParam);
		int maximumPage = (int) Math.ceil(numberOfComputers * 1.0 / limit);
		
		if(pageParam != null && !pageParam.isEmpty()) offset = Integer.parseInt(pageParam);
		if(offset<1) offset = CURRENT_PAGE;
		if(offset>maximumPage) offset = maximumPage;
		
		List<ComputerDTO> computers = new ArrayList<ComputerDTO>();
		try {
			computers = computerService.getComputers(limit, offset);
		} catch (DatabaseException e) {
			logger.warn(e.getMessage(), e);
		}
		
		request.setAttribute("limit", limit);
		request.setAttribute("currentPage", offset);
		request.setAttribute( "numberOfComputers", numberOfComputers );
		request.setAttribute( "computers", computers );
		
		request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp")
		.forward(request, response);

	}

	public  void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
		doGet(request, response) ;
	}
}
