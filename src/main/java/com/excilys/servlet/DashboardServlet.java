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
import com.excilys.binding.dto.ComputerDTO;
import com.excilys.controller.ComputerController;
import com.excilys.exceptions.DatabaseException;
import com.excilys.exceptions.InvalidArgumentsException;
import com.excilys.model.Page;
import com.excilys.model.Sorting;


public class DashboardServlet extends HttpServlet {

	private final int LIMIT=10;
	private final int CURRENT_PAGE=1;

	private ComputerController computerController = ComputerController.getInstance();
	private static final Logger logger = LogManager.getLogger(DashboardServlet.class);
	
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)

			throws ServletException, IOException {
		int offset = CURRENT_PAGE;
		int limit = LIMIT;
		int numberOfComputers=0;
		try {
			numberOfComputers = computerController.count();
		} catch (DatabaseException e) {
			logger.warn(e.getMessage(), e);
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
		
		List<Integer> pages = new ArrayList<Integer>();
		if(offset<=3) {
			for(int i=1;i<6;i++) {
				pages.add(i);
			}
		} else {
			for(int i=offset-2;i<offset+3;i++) {
				pages.add(i);
			}
		}
		
		List<ComputerDTO> computers = new ArrayList<ComputerDTO>();
		String filter = (stringToSearch==null)? "":stringToSearch;
		String field = (fieldParam==null)? "":fieldParam;
		String order = (orderParam==null)? "":orderParam;
		Page page = new Page(offset,limit);
		Sorting sorting = new Sorting(field,order);
		try {
			computers = computerController.getComputers(page,filter,sorting);
		} catch (DatabaseException e) {
			logger.warn(e.getMessage(), e);
		}
		
		request.setAttribute( "computers", computers );
		request.setAttribute("pages", pages);
		
		request.setAttribute("limit", limit);
		request.setAttribute("page", offset);
		request.setAttribute( "number", numberOfComputers );
		request.setAttribute("filter",filter);
		request.setAttribute("field", field);
		request.setAttribute("order", order);
		
		request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp")
		.forward(request, response);

	}

	@Override
	public  void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
		String computersSelection = request.getParameter("selection");
		String[] computers = computersSelection.split(",");
		
		List<Long> computersToDelete = new ArrayList<Long>();
		try {
			 if (Objects.isNull(computers)) {
	                computersToDelete= Collections.emptyList();
	            } else {
	                computersToDelete = Arrays.stream(computers).map(Long::valueOf).collect(Collectors.toList());
	            }
		} catch(NumberFormatException e) {
			logger.error("Could not format the ids");
			throw new InvalidArgumentsException(e.getMessage());
		}
		
		computersToDelete.stream().forEach(id -> {
			try {
				computerController.delete(id);
			} catch (DatabaseException e) {
				logger.warn("Invalid id");
			}
		});

		request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp")
		.forward(request, response);
		
	}
}
