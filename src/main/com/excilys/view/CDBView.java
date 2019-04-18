package main.com.excilys.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
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
	public int menu()  {
		System.out.println(
				"Enter a number ("
				+ "0: Quit, "
				+ "1: List computers, "
				+ "2: List companies," 
				+ "3: Show computer detail,\r\n"
				+ "4: Create a computer,"
				+ "5: Update a computer,"
				+ "6: Delete a computer)");
		;
		int number =0;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String ans = br.readLine();
			number = Integer.parseInt(ans);
		} catch(NumberFormatException fn) {
			this.notifyInvalidId();
		} catch (IOException e) {
			System.out.println("IO error"+e.getMessage());
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
	
	/**
	 * Show the information of one computer
	 * @param computers
	 */
	public void displayComputer(Computer computer) {
		System.out.println(computer);
	}
	
	
	/**
	 * Asks for the computer information
	 * @return a Computer object
	 */
	public Computer queryComputerToCreate() {
		Computer computer = null;
		String name =this.queryName();
		if(name!=null && !name.isEmpty()) {
			computer = new Computer();
			computer.setName(name);
			computer.setIntroduced(this.queryDate());
			computer.setDiscontinued(this.queryDate());
			computer.getCompany().setId(this.queryId());
		} else {
			this.notifyInvalidName();
		}
		return computer;
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
		computer.getCompany().setId(this.queryId());

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
			
		Long id = null;
		System.out.println("Enter the id: ");
		String ans ="";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			ans= br.readLine();
			id = Long.parseLong(ans);
		} catch(NumberFormatException fn) {
			this.notifyInvalidId();
		} catch (IOException e) {
			System.out.println("IO error"+e.getMessage());
		}	
		return id;
	}
	
	/**
	 * Asks for the name
	 * @return a String
	 */
	public String queryName() {
		
		String name="";
		System.out.println("Enter the name: ");
		try {	
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			name = br.readLine();
		} catch (IOException e) {
			System.out.println("IO error"+e.getMessage());
		}
		return name;
	}
	
	/**
	 * Asks for the date
	 * @return a String
	 */
	public LocalDate queryDate() {
		LocalDate date=null;
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		String year ="",month="",day="";
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter the year: ");
			year = br.readLine();
			System.out.println("Enter the month : ");
			month = br.readLine();
			System.out.println("Enter the day: ");
			day = br.readLine();
		} catch (IOException e) {
			System.out.println("IO error"+e.getMessage());
		}
		try {
			date = LocalDate.parse(year+"-"+month+"-"+day,format);
		} catch(DateTimeParseException e) {
			System.out.println("Invalid date : "+e.getMessage());
		}
		
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
	
	public void bye() {
		System.out.println("Bye!");
	}
	
}
