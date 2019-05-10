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

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.binding.dto.ComputerDTO;
import com.excilys.controller.CompanyController;
import com.excilys.controller.ComputerController;
import com.excilys.validator.Validator;

public class AddComputerServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;
	private final ComputerController computerController = ComputerController.getInstance();
	private final CompanyController companyController = CompanyController.getInstance();
	private static final Logger logger = LogManager.getLogger(DashboardServlet.class);
	
	Validator validator = Validator.getInstance();
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)

			throws ServletException, IOException {
		
		List<CompanyDTO> companies = new ArrayList<CompanyDTO>();
		companies = companyController.getCompanies();
		
		request.setAttribute("companies", companies);
		
		request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/addComputer.jsp")
		.forward(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
		
		String nameParam =request.getParameter("computerName");
		String introducedParam = request.getParameter("introduced");
		String discontinuedParam = request.getParameter("discontinued");
		String companyIdParam = request.getParameter("companyId");
		
		ComputerDTO computer =new ComputerDTO.Builder().setName(nameParam)
				.setIntroduced(introducedParam).setDiscontinued(discontinuedParam)
				.setCompanyId(companyIdParam).build();
		try {
			validator.validateComputerToCreate(computer);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		
		try {
			computerController.createComputer(computer);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		} 
		request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/addComputer.jsp")
		.forward(request, response);
	}
	
}
