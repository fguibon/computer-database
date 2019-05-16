package com.excilys.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.binding.dto.ComputerDTO;
import com.excilys.controller.CompanyController;
import com.excilys.controller.ComputerController;
import com.excilys.validator.Validator;

public class AddComputerServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;
	private ComputerController computerController;
	private CompanyController companyController;
	private Validator validator;
	private static final Logger LOGGER = LogManager.getLogger(DashboardServlet.class);
	
	@Override
	public void init() throws ServletException {
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		this.companyController = wac.getBean(CompanyController.class);
		this.computerController = wac.getBean(ComputerController.class);
		this.validator = wac.getBean(Validator.class);
	}
	
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)

			throws ServletException, IOException {
		
		List<CompanyDTO> companies =  companyController.getCompanies();
		
		request.setAttribute("companies", companies);
		
		request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/addComputer.jsp")
		.forward(request, response);

	}

	@Override
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
			LOGGER.warn(e.getMessage(), e);
		}
		
		try {
			computerController.createComputer(computer);
		} catch (Exception e) {
			LOGGER.warn(e.getMessage(), e);
		} 
		request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/addComputer.jsp")
		.forward(request, response);
	}
	
}
