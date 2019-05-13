package com.excilys.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.binding.dto.ComputerDTO;
import com.excilys.controller.ComputerController;
import com.excilys.exceptions.DatabaseException;
import com.excilys.model.Page;
import com.excilys.model.Sorting;


public class DashboardServlet extends HttpServlet {

	private static final int LIMIT=10;
	private static final int CURRENT_PAGE=1;

	private ComputerController computerController;
	private static final Logger LOGGER = LogManager.getLogger(DashboardServlet.class.getName());
	
	private static final long serialVersionUID = 1L;


	@Override
	public void init() throws ServletException {
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		this.computerController = wac.getBean(ComputerController.class);
	}
	
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)

			throws ServletException, IOException {

		int limit =LIMIT;
		int offset=CURRENT_PAGE;
		int numberOfComputers=0;
		
		try {
			numberOfComputers = computerController.count();
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
		
		List<ComputerDTO> computers = new ArrayList<>();
		try {
			computers = computerController.getComputers(page,filter,sorting);
		} catch (DatabaseException e) {
			LOGGER.warn(e.getMessage(), e);
		}
		
		request.setAttribute( "computers", computers );
		request.setAttribute("pages", pages);
		
		request.setAttribute("limit", limit);
		request.setAttribute("page", offset);
		request.setAttribute( "number", numberOfComputers );
		request.setAttribute("filter",filter);
		request.setAttribute("field", field);
		request.setAttribute("order", order);
		LOGGER.info("nb comp:"+ numberOfComputers);
		
		request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp")
		.forward(request, response);

	}

	@Override
	public  void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
		String computersSelection = request.getParameter("selection");
		String[] computers = computersSelection.split(",");
		
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
				computerController.delete(id);
			} catch (DatabaseException e) {
				LOGGER.warn("Invalid id");
			}
		});

		request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp")
		.forward(request, response);
		
	}
}
