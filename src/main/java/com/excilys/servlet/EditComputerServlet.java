package com.excilys.servlet;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.binding.dto.ComputerDTO;
import com.excilys.binding.mapper.CompanyMapper;
import com.excilys.binding.mapper.ComputerMapper;
import com.excilys.exceptions.DatabaseException;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validator.Validator;

@WebServlet(
		name = "EditComputerServlet",
		description = "Edit Computer Servlet to edit a computer in database",
		urlPatterns = {"/edit-computer"}
		)
public class EditComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ComputerService computerService;
	private CompanyService companyService;
	private ComputerMapper computerMapper;
	private CompanyMapper companyMapper;
	private Validator validator;

	private static final Logger LOGGER = LogManager.getLogger(DashboardServlet.class);


	@Override
	public void init() throws ServletException {
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		this.companyService = wac.getBean( CompanyService.class);
		this.computerService = wac.getBean( ComputerService.class);
		this.computerMapper = wac.getBean(ComputerMapper.class);
		this.companyMapper = wac.getBean(CompanyMapper.class);
		this.validator = wac.getBean( Validator.class);
	}


	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idParam = request.getParameter("id");

		ComputerDTO computer =new ComputerDTO();
		Long id =(idParam == null || "0".equals(idParam) || idParam.isEmpty()) ? null : Long.valueOf(idParam);
		try {
			computer = computerMapper.modelToDto(computerService.findById(id));
		} catch (DatabaseException e) {
			LOGGER.warn(e.getMessage(), e);
		}	

		List<CompanyDTO> companyList = companyService.getCompanies()
				.stream().map(s -> companyMapper.modelToDto(s)).collect(Collectors.toList());

		request.setAttribute("computer", computer);
		request.setAttribute("companies", companyList);

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
			LOGGER.warn(e.getMessage(), e);
		}

		try {
			computerService.update(computerMapper.dtoToModel(computer));
		} catch (Exception e) {
			LOGGER.warn(e.getMessage(), e);
		} 

		response.sendRedirect("dashboard");

	}

}
