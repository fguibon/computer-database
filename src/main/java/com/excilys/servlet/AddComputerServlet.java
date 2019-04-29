package com.excilys.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddComputerServlet extends HttpServlet {


	private static final long serialVersionUID = -8850897282598090626L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)

			throws ServletException, IOException {
		

		request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/addComputer.jsp")
		.forward(request, response);

	}

	public  void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
		doGet(request, response) ;
	}
	
}
