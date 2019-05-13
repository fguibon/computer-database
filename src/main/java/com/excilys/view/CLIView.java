package com.excilys.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.binding.dto.ComputerDTO;
import com.excilys.model.Page;

public class CLIView {
	
	InputStream in;
	private static final Logger LOGGER = LogManager.getLogger(CLIView.class);
	
	public CLIView (InputStream in) {
		this.in = in;
	}
	
	/**
	 * Displays the menu and ask a number
	 * @return an int
	 * */
	public int menu()  {
		LOGGER.info(
				"-----MENU-----\r\n"
				+ "Enter a number \r\n"
				+ "0: Quit \r\n"
				+ "1: List computers \r\n"
				+ "2: List companies \r\n" 
				+ "3: Show computer detail \r\n"
				+ "4: Create a computer \r\n"
				+ "5: Update a computer \r\n"
				+ "6: Delete a computer ");
		int number =0;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String ans = br.readLine();
			number = Integer.parseInt(ans);
		} catch(NumberFormatException | IOException e) {
			this.notifyProblem();
		} 
		return number;
	}
	
	/**
	 * List all companies
	 * @param companies
	 */
	public void displayCompanies(List<CompanyDTO> companies) {
		for(CompanyDTO c:companies) {
			if(c!=null) LOGGER.info(c);
		}
	}
	
	/**
	 * List all computers
	 * @param computers
	 * @param page 
	 */
	public void displayComputers(List<ComputerDTO> computers, Page page) {
		LOGGER.info("Page number : "+page.getCurrentPage());
		for(ComputerDTO c:computers) {
			if(c!=null) LOGGER.info(c);
		}
	}
	
	/**
	 * Show the information of one computer
	 * @param computers
	 */
	public void displayComputer(ComputerDTO computer) {
		if(computer!=null) LOGGER.info(computer);
	}
	
	

	/** 
	 * Asks for the id
	 * @return a Long
	 */
	public Long queryId() {
			
		LOGGER.info("Enter the id: ");
		String ans ="";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			ans= br.readLine();	
		} catch (IOException e) {
			this.notifyProblem();
		}
		Long id = null;
		try{ id = Long.parseLong(ans); } 
		catch (NumberFormatException nfe) { this.notifyInvalidId();}
		return id;
	}
	
	/**
	 * Asks for the name
	 * @return a String
	 */
	public String queryName() {
		
		String name="";
		LOGGER.info("Enter the name: ");
		try (BufferedReader br = new BufferedReader(new InputStreamReader(in));){	
			name = br.readLine();
		} catch (IOException e) {
			this.notifyProblem();
		}
		if(name==null || name.isEmpty()) this.notifyInvalidName();
		return name;
	}
	
	/**
	 * Asks for the date
	 * @return a String
	 */
	public String queryDate() {
			
		String date="";
		String year ="";
		String month="";
		String day="";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			LOGGER.info("Enter the year: ");
			year = br.readLine();
			LOGGER.info("Enter the month : ");
			month = br.readLine();
			LOGGER.info("Enter the day: ");
			day = br.readLine();
		} catch (IOException e) {
			this.notifyProblem();
		}
		date = year+"-"+month+"-"+day;
		
		return date;
	}
	
	public void notifyProblem() {
		LOGGER.warn("Invalid entry try again!");
	}
	
	public void notifySuccess() {
		LOGGER.info("Success!");
	}
	
	public void notifyInvalidNumber() {
		LOGGER.warn("Invalid number please enter one between 1 and 6.");	
	}
	
	public void notifyInvalidId() {
		LOGGER.warn("Invalid id try another!");
	}
	
	public void notifyInvalidName() {
		LOGGER.warn("Invalid name try again!");
	}
	
	public void notifyInvalidDate() {
		LOGGER.warn("Invalid date try another!");
	}
	
	public void bye() {
		LOGGER.info("Bye!");
	}
	
}
