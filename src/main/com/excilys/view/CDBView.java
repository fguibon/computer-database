package main.com.excilys.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import main.com.excilys.binding.dto.CompanyDTO;
import main.com.excilys.binding.dto.ComputerDTO;
import main.com.excilys.model.Page;

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
				"-----MENU-----\r\n"
				+ "Enter a number \r\n"
				+ "0: Quit \r\n"
				+ "1: List computers \r\n"
				+ "2: List companies \r\n" 
				+ "3: Show computer detail \r\n"
				+ "4: Create a computer \r\n"
				+ "5: Update a computer \r\n"
				+ "6: Delete a computer ");
		;
		int number =0;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String ans = br.readLine();
			number = Integer.parseInt(ans);
		} catch(NumberFormatException fn) {
			this.notifyInvalidId();
		} catch (IOException e) {
			System.out.println("IO error : "+e.getMessage());
		}
		return number;
	}
	
	/**
	 * List all companies
	 * @param companies
	 */
	public void displayCompanies(List<CompanyDTO> companies, Page page) {
		System.out.println("Page number : "+page.getCurrentPage());
		for(CompanyDTO c:companies) {
			if(c!=null) System.out.println(c);
		}
	}
	
	/**
	 * List all computers
	 * @param computers
	 * @param page 
	 */
	public void displayComputers(List<ComputerDTO> computers, Page page) {
		System.out.println("Page number : "+page.getCurrentPage());
		for(ComputerDTO c:computers) {
			if(c!=null) System.out.println(c);
		}
	}
	
	/**
	 * Show the information of one computer
	 * @param computers
	 */
	public void displayComputer(ComputerDTO computer) {
		if(computer!=null) System.out.println(computer);
	}
	
	

	/** 
	 * Asks for the id
	 * @return a Long
	 */
	public String queryId() {
			
		System.out.println("Enter the id: ");
		String ans ="";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			ans= br.readLine();
		} catch (IOException e) {
			System.out.println("IO error"+e.getMessage());
		}	
		return ans;
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
	public String queryDate() {
		
		
		String date="",year ="",month="",day="";
		
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
		date = year+"-"+month+"-"+day;
		
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
