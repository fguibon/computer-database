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

import com.excilys.binding.dto.ComputerDTO;
import com.excilys.binding.mapper.ComputerMapper;
import com.excilys.exceptions.DatabaseException;
import com.excilys.model.Computer;
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


	public DashboardController(ComputerService computerService,ComputerMapper computerMapper) {
		this.computerService = computerService;
		this.computerMapper = computerMapper;
	}


	@GetMapping({"/","/dashboard"})
	public String displayComputerList(Model model,
			@ModelAttribute("sorting") Sorting sorting,
			@ModelAttribute("numberOfComputer") int numberOfComputers) {

		int offset= (sorting.getPage() > 0)? sorting.getPage() : CURRENT_PAGE;
		int limit = (sorting.getLimit() > 0)? sorting.getLimit() : LIMIT;
		String filter = (sorting.getFilter()==null)? "":sorting.getFilter();
		String field = (sorting.getField()==null)? "":sorting.getField();
		String order = (sorting.getOrder()==null)? "":sorting.getOrder();

		int maximumPage = (int) Math.ceil(numberOfComputers * 1.0 / limit);
		if(offset>=maximumPage) offset =  maximumPage;

		sorting.setPage(offset);
		sorting.setLimit(limit);
		sorting.setFilter(filter);
		sorting.setField(field);
		sorting.setOrder(order);

		List<ComputerDTO> computersList = new ArrayList<>();
		try {
			List<Computer> computers = computerService.findAll(sorting);
			computersList = computers
					.stream().map(s -> computerMapper.modelToDto(s))
					.collect(Collectors.toList());
		} catch (DatabaseException e) {
			LOGGER.warn(e.getMessage(), e);
		}

		model.addAttribute("computers", computersList);
		model.addAttribute("pageList", sorting.getPageList(offset));
		model.addAttribute("sorting", sorting);

		return "dashboard";
	}

	@PostMapping("/dashboard")
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
		return "redirect:/dashboard";
	}

	@ModelAttribute("sorting")
	public Sorting getSorting() {
		return new Sorting();
	}

	@ModelAttribute("numberOfComputer")
	public int getComputerCount(Model model) {
		int numberOfComputers=0;

		try {
			numberOfComputers = computerService.count();
		} catch (DatabaseException e) {
			LOGGER.warn(e.getMessage(), e);
		}
		model.addAttribute("number", numberOfComputers);
		return numberOfComputers;	
	}

}
