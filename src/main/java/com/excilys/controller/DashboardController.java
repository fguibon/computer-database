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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.binding.dto.ComputerDTO;
import com.excilys.binding.mapper.ComputerMapper;
import com.excilys.exceptions.DatabaseException;
import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.model.Sorting;
import com.excilys.service.ComputerService;

@Controller	
@SessionAttributes("sorting")
public class DashboardController {

	private static final int LIMIT=10;
	private static final int CURRENT_PAGE=1;

	private ComputerService computerService;
	private ComputerMapper computerMapper;
	private static final Logger LOGGER = LogManager.getLogger(DashboardController.class.getName());
	
	 ModelAndView dashboardPage = new ModelAndView("dashboard");

	public DashboardController(ComputerService computerService,ComputerMapper computerMapper) {
		this.computerService = computerService;
		this.computerMapper = computerMapper;
	}
	 
	 
	@GetMapping("/dashboard")
	public ModelAndView displayComputerList(Model model,
			  @ModelAttribute("sorting") Sorting sorting) {

		int limit =LIMIT;
		int offset=CURRENT_PAGE;
		int numberOfComputers=0;
		
		try {
			numberOfComputers = computerService.count();
		} catch (DatabaseException e) {
			LOGGER.warn(e.getMessage(), e);
		}
		
		int pageParam = sorting.getPage().getCurrentPage();
		int noOfRecordsParam = sorting.getPage().getEntriesPerPage();
		String stringToSearch = sorting.getFilter();
		String fieldParam = sorting.getField();
		String orderParam = sorting.getOrder();
		 
		if(noOfRecordsParam >0) limit = noOfRecordsParam;
		if(pageParam>0) offset = pageParam;
		int maximumPage = (int) Math.ceil(numberOfComputers * 1.0 / limit);
		if(offset<1) offset = CURRENT_PAGE;
		if(offset>=maximumPage) offset = maximumPage;
					
		String filter = (stringToSearch==null)? "":stringToSearch;
		String field = (fieldParam==null)? "":fieldParam;
		String order = (orderParam==null)? "":orderParam;
		Page page = new Page(offset,limit);
		Sorting sortingRequested = new Sorting(field,order,filter,page);
		List<Integer> pageList = page.getPageList(offset);
		
		List<ComputerDTO> computersList = new ArrayList<>();
		
		try {
			List<Computer> computers = computerService.findAll(sorting);
			computersList = computers
			.stream().map(s -> computerMapper.modelToDto(s))
			.collect(Collectors.toList());
		} catch (DatabaseException e) {
			LOGGER.warn(e.getMessage(), e);
		}
		
		model.addAttribute("number", numberOfComputers);
		model.addAttribute("computers", computersList);
		model.addAttribute("pageList", pageList);
		
		model.addAttribute("sorting", sortingRequested);
		
		return dashboardPage;

	}

	@PostMapping(value="/dashboard")
	public ModelAndView deleteComputers(@RequestParam String selection){

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
		return dashboardPage;
	}
	
	@ModelAttribute("sorting")
	public Sorting getSorting() {
		return new Sorting();
	}
	
}
