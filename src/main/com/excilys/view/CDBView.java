package main.com.excilys.view;

import java.util.Scanner;

public class CDBView {
	

	public CDBView () {

	}
	
	public int menu() {
		Scanner sc = new Scanner(System.in);
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

	public void notifyInvalid() {
		System.out.println("Invalid number please enter one between 1 and 6");
		
	}
	
	
}
