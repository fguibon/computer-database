package com.excilys.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.binding.dto.ComputerDTO;
import com.excilys.binding.mapper.CompanyMapper;
import com.excilys.persistence.dao.CompanyDAO;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

public class AddComputerServlet extends HttpServlet {


	private static final long serialVersionUID = -8850897282598090626L;
	
	
	private final ComputerService computerService = ComputerService.getInstance();
	private final CompanyService companyService = CompanyService.getInstance(CompanyDAO.getInstance(), CompanyMapper.getInstance());

	public void doGet(HttpServletRequest request, HttpServletResponse response)

			throws ServletException, IOException {
		
		List<CompanyDTO> companies = new ArrayList<CompanyDTO>();
		companies = companyService.getCompanies();
		
		request.setAttribute("companies", companies);
		
		request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/addComputer.jsp")
		.forward(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
		
		String nameParam =request.getParameter("computerName");
		String introducedParam = request.getParameter("introduced");
		String discontinuedParam = request.getParameter("discontinued");
		String companyId = request.getParameter("companyId");
		
		ComputerDTO computer = new ComputerDTO("1", nameParam, introducedParam, discontinuedParam, new CompanyDTO(companyId, ""));
		
		computerService.createComputer(computer);
		
		request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/addComputer.jsp")
		.forward(request, response);
	}
	
}
