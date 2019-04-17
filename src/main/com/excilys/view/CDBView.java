package main.com.excilys.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
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
	
	
	/**
	 * Asks for the computer information
	 * @return a Computer object
	 */
	public Computer queryComputerToCreate() {
		Computer computer = new Computer();
		Scanner sc = new Scanner(in);
		
		String name = this.queryName();
		if(name!=null && !name.isEmpty()) {
			computer.setName(name);
		} else {
			System.out.println("Invalid name");
		}
		
		String date = this.queryDate();
		
		sc.close();
		return computer;
	}
	
	/**
	 * Asks for the computer information
	 * @return a Computer object
	 */
	public Computer queryComputerToUpdate() {
		Computer computer = new Computer();
		Scanner sc = new Scanner(in);
		
		Long id = this.queryId();
		computer.setId(id);

		String name=this.queryName();
		if(name!=null && !name.isEmpty()) {
			computer.setName(name);
		}
		
		
		sc.close();
		return computer;
	}
	
	/**
	 * Asks for computer information
	 * @return a Computer object
	 */
	public Computer queryComputerToDelete() {
		Computer computer = new Computer();
		Scanner sc = new Scanner(in);
		
		String name=this.queryName();
		
		
		sc.close();
		return computer;
	}
	
	

	/** 
	 * Asks for the id
	 * @return a Long
	 */
	public Long queryId() {
		System.out.println("Enter the id: ");
		Scanner sc = new Scanner(in);
		String ans = sc.nextLine();
		Long id = null;
		try {
			id = Long.parseLong(ans);
		} catch(NumberFormatException fn) {
			this.notifyInvalidId();
			System.out.println(fn.toString());
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
	public Timestamp queryDate() {
		Scanner sc = new Scanner(in);
		Timestamp date=null;
		System.out.println("Enter the date: ");
		String ans = sc.nextLine();
		try {
			
		}
		
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
