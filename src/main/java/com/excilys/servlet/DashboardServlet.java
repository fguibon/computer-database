package com.excilys.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DashboardServlet extends HttpServlet {


	private static final long serialVersionUID = -4872201161261134158L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)

			throws ServletException, IOException {
		//req.getContextPath();

	}

	public  void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {

		doGet(request, response) ;
	}
}
