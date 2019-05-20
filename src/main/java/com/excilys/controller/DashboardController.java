package com.excilys.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.binding.dto.ComputerDTO;
import com.excilys.binding.mapper.ComputerMapper;
import com.excilys.exceptions.DatabaseException;
import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.model.Sorting;
import com.excilys.service.ComputerService;

@Controller
public class DashboardController {

	private static final int LIMIT=10;
	private static final int CURRENT_PAGE=1;

	private ComputerService computerService;
	private ComputerMapper computerMapper;
	private static final Logger LOGGER = LogManager.getLogger(DashboardController.class.getName());
	

	@GetMapping("/dashboard")
	public String displayComputerList() {

		int limit =LIMIT;
		int offset=CURRENT_PAGE;
		int numberOfComputers=0;
		
		try {
			numberOfComputers = computerService.count();
		} catch (DatabaseException e) {
			LOGGER.warn(e.getMessage(), e);
		}
		
		String pageParam =request.getParameter("page");
		String noOfRecordsParam = request.getParameter("number");
		String stringToSearch = request.getParameter("filter");
		String fieldParam = request.getParameter("field");
		String orderParam = request.getParameter("order");
		 
		if(noOfRecordsParam !=null && !noOfRecordsParam.isEmpty()) limit = Integer.parseInt(noOfRecordsParam);
		int maximumPage = (int) Math.ceil(numberOfComputers * 1.0 / limit);
		
		if(pageParam != null && !pageParam.isEmpty()) offset = Integer.parseInt(pageParam);
		if(offset<1) offset = CURRENT_PAGE;
		if(offset>=maximumPage) offset = maximumPage;
					
		String filter = (stringToSearch==null)? "":stringToSearch;
		String field = (fieldParam==null)? "":fieldParam;
		String order = (orderParam==null)? "":orderParam;
		Page page = new Page(offset,limit);
		Sorting sorting = new Sorting(field,order);
		List<Integer> pages = page.getPageList(offset);
		
		List<ComputerDTO> computersList = new ArrayList<>();
		
		try {
			List<Computer> computers = computerService.findAll(page,filter, sorting);
			computersList = computers
			.stream().map(s -> computerMapper.modelToDto(s))
			.collect(Collectors.toList());
		} catch (DatabaseException e) {
			LOGGER.warn(e.getMessage(), e);
		}
		
		request.setAttribute( "computers", computersList);
		request.setAttribute("pages", pages);
		
		request.setAttribute("limit", limit);
		request.setAttribute("page", offset);
		request.setAttribute("number", numberOfComputers);
		request.setAttribute("filter", filter);
		request.setAttribute("field", field);
		request.setAttribute("order", order);
		
		return("/WEB-INF/jsp/dashboard.jsp");

	}

	@PostMapping(value="/dashboard")
	public String deleteComputers(@RequestParam String selection){

		String[] computers = selection.split(",");
		
		List<Long> computersToDelete = new ArrayList<>();
		try {
			 if (Objects.isNull(computers)) {
	                computersToDelete= Collections.emptyList();
	            } else {
	                computersToDelete = Arrays.stream(computers).map(Long::valueOf).collect(Collectors.toList());
	            }
		} catch(NumberFormatException e) {
			LOGGER.error("Could not format the ids");
		}
		
		computersToDelete.stream().forEach(id -> {
			try {
				computerService.delete(id);
			} catch (DatabaseException e) {
				LOGGER.warn("Invalid id");
			}
		});
		return "redirect:/";
	}
	
}
