package com.excilys.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DashboardServlet extends HttpServlet {


	public void doGet(HttpServletRequest req, HttpServletResponse res)

			throws ServletException, IOException {

		res.setContentType("text/html");

		ServletOutputStream out = res.getOutputStream();

		out.println("<HTML>\n");
		out.println("<HEAD>\n");
		out.println("<TITLE>Bonjour</TITLE>\n");
		out.println("</HEAD>\n");
		out.println("<BODY>\n");
		out.println("<H1>Bonjour</H1>\n");
		out.println("</BODY>\n");
		out.println("</HTML>");

	}

	public  void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {

		doGet(request, response) ;
	}
}
