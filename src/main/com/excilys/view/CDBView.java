package main.com.excilys.view;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import main.com.excilys.model.Company;
import main.com.excilys.model.Computer;

public class CDBView {
	

	InputStream in;
	
	public CDBView (InputStream in) {
		this.in = in;
	}
	
	/**
	 * Displays the menu and ask a number
	 * @return an int
	 * */
	public int menu() {
		Scanner sc = new Scanner(in);
		System.out.println(
				"Enter a number ("
				+ "1: list computers, "
				+ "2: List companies," 
				+ "3: Show computer detail,"
				+ "4: Create a computer,"
				+ "5: Update a computer,"
				+ "6: Delete a computer)");
		String ans = sc.nextLine();
		sc.close();
		int number =0;
		try {
			number = Integer.parseInt(ans);
		} catch(NumberFormatException fn) {
			System.out.println(fn.toString());
		}
		return number;
	}
	
	/**
	 * List all companies
	 * @param companies
	 */
	public void displayCompanies(List<Company> companies) {
		for(Company c:companies) {
			System.out.println(c);
		}
	}
	
	/**
	 * List all computers
	 * @param computers
	 */
	public void displayComputers(List<Computer> computers) {
		for(Computer c:computers) {
			System.out.println(c);
		}
	}
	
	public void displayComputer(Computer computer) {
		System.out.println(computer);
	}
	
	
	/**
	 * Asks for the computer information
	 * @return a Computer object
	 */
	public Computer queryComputerToCreate() {
		Computer computer = new Computer();
		
		computer.setName(this.queryName());
		computer.setIntroduced(this.queryDate());
		computer.setDiscontinued(this.queryDate());
		computer.setCompanyId(this.queryId());
		
		return computer;
	}
	
	/**
	 * Asks for an id
	 * @return the id
	 */
	public Long queryComputerToShow() {
		return this.queryId();
	}
	
	/**
	 * Asks for the computer information
	 * @return a Computer object
	 */
	public Computer queryComputerToUpdate() {
		Computer computer = new Computer();
		
		computer.setId(this.queryId());
		computer.setName(this.queryName());
		computer.setIntroduced(this.queryDate());
		computer.setDiscontinued(this.queryDate());
		computer.setCompanyId(this.queryId());

		return computer;
	}
	
	/**
	 * Asks for an id
	 * @return the id
	 */
	public Long queryComputerToDelete() {
		return this.queryId();
	}
	
	

	/** 
	 * Asks for the id
	 * @return a Long
	 */
	public Long queryId() {
		Scanner sc = new Scanner(in);
		System.out.println("Enter the id: ");
		String ans = sc.nextLine();
		Long id = null;
		if(ans!=null && !ans.isEmpty()) {
			try {
				id = Long.parseLong(ans);
			} catch(NumberFormatException fn) {
				this.notifyInvalidId();
				System.out.println(fn.toString());
			}
		} else {
			this.notifyInvalidId();
		}	
		
		sc.close();
		return id;
	}
	
	/**
	 * Asks for the name
	 * @return a String
	 */
	public String queryName() {
		Scanner sc = new Scanner(in);
		
		System.out.println("Enter the name: ");
		String ans = sc.nextLine();
		String name="";
		if(ans!=null && !ans.isEmpty()) {
			name = ans;
		} else {
			this.notifyInvalidName();
		}
		
		sc.close();
		return name;
	}
	
	/**
	 * Asks for the date
	 * @return a String
	 */
	public LocalDate queryDate() {
		LocalDate date=null;
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		Scanner sc = new Scanner(in);
		System.out.println("Enter the year: ");
		String year = sc.nextLine();
		System.out.println("Enter the month: ");
		String month = sc.nextLine();
		System.out.println("Enter the day: ");
		String day = sc.nextLine();
		
		date = LocalDate.parse(year+"-"+month+"-"+day,format);
		
		sc.close();
		return date;
	}
	
	
	public void notifyInvalidNumber() {
		System.out.println("Invalid number please enter one between 1 and 6.");	
	}
	
	public void notifyInvalidId() {
		System.out.println("Invalid id try another!");
	}
	
	public void notifyInvalidName() {
		System.out.println("Invalid name try again!");
	}
	
	public void notifyInvalidDate() {
		System.out.println("Invalid date try another!");
	}
	
}
