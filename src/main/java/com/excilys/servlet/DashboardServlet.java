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


	private static final long serialVersionUID = -4872201161261134158L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)

			throws ServletException, IOException {
		List<ComputerDTO> computers = new ArrayList<ComputerDTO>();
		computers = ComputerService.getInstance().getComputers(LIMIT, CURRENT_PAGE);
		request.setAttribute( "numberOfComputers", computers.size() );
		request.setAttribute( "computers", computers );
		request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp")
		.forward(request, response);

	}

	public  void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {

		doGet(request, response) ;
	}
}
