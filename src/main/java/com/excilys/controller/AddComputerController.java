package com.excilys.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.binding.dto.ComputerDTO;
import com.excilys.binding.mapper.CompanyMapper;
import com.excilys.binding.mapper.ComputerMapper;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validator.Validator;

@Controller
public class AddComputerController {


	private ComputerService computerService;
	private CompanyService companyService;
	private CompanyMapper companyMapper;
	private ComputerMapper computerMapper;
	private Validator validator;
	private static final Logger LOGGER = LogManager.getLogger(DashboardController.class);


	@GetMapping("/add-computer")
	public void displayForm()

			throws ServletException, IOException {

		List<CompanyDTO> companyList = companyService.getCompanies()
				.stream().map(s -> companyMapper.modelToDto(s))
				.collect(Collectors.toList());
		request.setAttribute("companies", companyList);


	}

	@PostMapping("/add-computer")
	public void addComputer(){

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
			computerService.createComputer(computerMapper.dtoToModel(computer));
		} catch (Exception e) {
			LOGGER.warn(e.getMessage(), e);
		} 

	}

}
