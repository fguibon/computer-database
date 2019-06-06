package com.excilys.webapp.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.binding.dto.ComputerDTO;
import com.excilys.binding.exceptions.DatabaseException;
import com.excilys.binding.mapper.CompanyMapper;
import com.excilys.binding.mapper.ComputerMapper;
import com.excilys.core.Computer;
import com.excilys.core.Sorting;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

@RestController
@RequestMapping("/api/computers")
public class RestComputerController {

	private static final int LIMIT=10;
	private static final int CURRENT_PAGE=1;
	
	private ComputerService computerService;
	private ComputerMapper computerMapper;
	private final CompanyService companyService;
	private final CompanyMapper companyMapper;
	
	private static final Logger LOGGER = LogManager.getLogger(RestComputerController.class.getName());
	
	public RestComputerController(ComputerService computerService,CompanyService companyService,
			ComputerMapper computerMapper, CompanyMapper companyMapper) {
		this.computerService = computerService;
		this.companyService = companyService;
		this.computerMapper = computerMapper;
		this.companyMapper = companyMapper;
	}
	
	@GetMapping({"/computers"})
	@PreAuthorize("permitAll")
	public List<ComputerDTO> getComputers(Model model,
			@ModelAttribute("sorting") Sorting sorting,
			@ModelAttribute("numberOfComputer") int numberOfComputers) {

		int page = (sorting.getPage() > 0)? sorting.getPage() : CURRENT_PAGE;
		int limit = (sorting.getLimit() > 0)? sorting.getLimit() : LIMIT;
		String filter = (sorting.getFilter()==null)? "":sorting.getFilter();
		String field = (sorting.getField()==null)? "":sorting.getField();
		String order = (sorting.getOrder()==null)? "":sorting.getOrder();

		int maximumPage = (int) Math.ceil(numberOfComputers * 1.0 / limit);
		if(page>=maximumPage) page =  maximumPage;

		sorting.setPage(page);
		sorting.setLimit(limit);
		sorting.setFilter(filter);
		sorting.setField(field);
		sorting.setOrder(order);

		List<ComputerDTO> computersList = new ArrayList<>();
		try {
			List<Computer> computers = computerService.findAll(sorting);
			computersList = computers
					.stream().map(computerMapper::modelToDto)
					.collect(Collectors.toList());
		} catch (DatabaseException e) {
			LOGGER.warn(e.getMessage(), e);
		}

		return computersList;
	}

	
	@PostMapping("/computers/delete")
	@PreAuthorize("hasRole('ADMIN')")
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
		return "";
	}
	
	
	@GetMapping("/computers/add")
	@PreAuthorize("hasRole('ADMIN')")
	public String displayForm() {	
		return "addComputer";
	}

	
	@PostMapping("/computers/add")
	@PreAuthorize("hasRole('ADMIN')")
	public String addComputer(Model model,@Valid @ModelAttribute("computer") ComputerDTO computer,
			BindingResult result){

		if(result.hasErrors()) {
	        return "addComputer";
	    }
		
		try {
			computerService.createComputer(computerMapper.dtoToModel(computer));
		} catch (Exception e) {
			LOGGER.warn(e.getMessage(), e);
		} 
		return "";
	}
	
	
	@GetMapping("/computers/edit")
	@PreAuthorize("hasRole('ADMIN')")
	public String displayForm(Model model, @RequestParam @Positive String id){

		ComputerDTO computer =new ComputerDTO();
		Long idParam =(id == null || "0".equals(id) || id.isEmpty()) ? null : Long.valueOf(id);
		try {
			computer = computerMapper.modelToDto(computerService.findById(idParam));
		} catch (DatabaseException e) {
			LOGGER.warn(e.getMessage(), e);
		}	
		model.addAttribute("computer", computer);
		
		return "editComputer";
	}

	
	@PostMapping("/computers/edit")
	@PreAuthorize("hasRole('ADMIN')")
	public String editComputer(Model model, @Valid @ModelAttribute("computer") ComputerDTO computer,
			BindingResult result) {

		if(result.hasErrors()) {
	        return "editComputer";
	    }
		
		try {
			computerService.update(computerMapper.dtoToModel(computer));
		} catch (Exception e) {
			LOGGER.warn(e.getMessage(), e);
		} 
		return "";
	}
	
	
	@ModelAttribute("computer")
	public ComputerDTO getComputer() {
		return new ComputerDTO();
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
	
	
	@ModelAttribute("companyList")
	public List<CompanyDTO> getCompanyList(Model model){
		List<CompanyDTO> companyList = companyService.getCompanies()
				.stream().map(s -> companyMapper.modelToDto(s)).collect(Collectors.toList());
		model.addAttribute("companies", companyList);
		return companyList;
	}
	
}
