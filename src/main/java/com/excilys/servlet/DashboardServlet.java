package com.excilys.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.binding.dto.ComputerDTO;
import com.excilys.service.ComputerService;


public class DashboardServlet extends HttpServlet {

	private final int LIMIT=10;
	private final int CURRENT_PAGE=1;

	private final ComputerService computerService = ComputerService.getInstance();
	
	private static final long serialVersionUID = -4872201161261134158L;

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)

			throws ServletException, IOException {
		int offset =CURRENT_PAGE;
		int limit = LIMIT;
		int numberOfComputers = computerService.count();
		int maximumPage = (int) Math.ceil(numberOfComputers * 1.0 / LIMIT);
		
		String currentPage =request.getParameter("page");
		String recordsPerPage = request.getParameter("noOfRecords");
		
		if(currentPage != null && !currentPage.isEmpty()) offset = Integer.parseInt(currentPage); 
		if(recordsPerPage !=null && !recordsPerPage.isEmpty()) limit = Integer.parseInt(recordsPerPage);
		if(offset<1) offset = CURRENT_PAGE;
		if(offset>maximumPage) offset = maximumPage;
		List<ComputerDTO> computers = new ArrayList<ComputerDTO>();
		computers = computerService.getComputers(limit, offset);
		
		request.setAttribute("currentPage", offset);
		request.setAttribute("noOfRecords", limit);
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
