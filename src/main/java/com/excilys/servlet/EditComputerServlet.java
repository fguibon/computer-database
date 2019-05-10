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
import com.excilys.exceptions.DatabaseException;
import com.excilys.validator.Validator;

public class EditComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ComputerController computerController = ComputerController.getInstance();
	private CompanyController companyController = CompanyController.getInstance();
	private static final Logger logger = LogManager.getLogger(DashboardServlet.class);

	Validator validator = Validator.getInstance();

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

		String idParam = request.getParameter("id");

		ComputerDTO computer =new ComputerDTO();
		Long id =(idParam == null || "0".equals(idParam) || idParam.isEmpty()) ? null : Long.valueOf(idParam);
		try {
			computer = computerController.findById(id);
		} catch (DatabaseException e) {
			logger.warn(e.getMessage(), e);
		}	

		List<CompanyDTO> companies = new ArrayList<CompanyDTO>();
		companies = companyController.getCompanies();

		request.setAttribute("computer", computer);
		request.setAttribute("companies", companies);

		request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/editComputer.jsp")
		.forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{

		String idParam = request.getParameter("id");
		String nameParam =request.getParameter("computerName");
		String introducedParam = request.getParameter("introduced");
		String discontinuedParam = request.getParameter("discontinued");
		String companyIdParam = request.getParameter("companyId");

		ComputerDTO computer =new ComputerDTO.Builder().setId(idParam).setName(nameParam)
				.setIntroduced(introducedParam).setDiscontinued(discontinuedParam)
				.setCompanyId(companyIdParam).build();
		try {
			validator.validateComputerToUpdate(computer);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}

		try {
			computerController.updateComputer(computer);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		} 
		
		response.sendRedirect("dashboard");

	}

}
