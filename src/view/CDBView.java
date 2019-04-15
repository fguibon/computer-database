package view;

import java.util.Scanner;

public class CDBView {
	

	public CDBView () {
		Scanner sc = new Scanner(System.in);
		System.out.println(
				"Enter a number ("
				+ "1: list all computers, "
				+ "2: List companies," 
				+ "3: Show computer detail,"
				+ "4: Create a computer,"
				+ "5: Update a computer,"
				+ "6: Delete a computer)");
		String number = sc.nextLine();
	}
	
	
	
}
