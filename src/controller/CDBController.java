package controller;

import view.CDBView;

public class CDBController {

	CDBView view;
	
	public CDBController(CDBView view){
		this.view=view;
	}
	
	/**
	 * Function calling the display of the starting menu
	 */
	public void start() {
		int choice =view.menu();
		switch(choice) {
			case 1:
				listComputers();
				break;
			case 2:
				listCompanies();
				break;
			case 3:
				showComputerDetail();
				break;
			case 4:
				createComputer();
				break;
			case 5:
				updateComputer();
				break;
			case 6:
				deleteComputer();
				break;
			default:
				invalidNumber();
		}
	}
	


	/**
	 * option 1
	 */
	public void listComputers() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * option 2
	 */
	public void listCompanies() {
		// TODO Auto-generated method stub
	}
	
	/** 
	 * option 3
	 */
	public void showComputerDetail() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * option 4
	 */
	public void createComputer() {
		// TODO Auto-generated method stub
	}
	

	/**
	 * option 5
	 */
	public void updateComputer() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * option 6
	 */
	public void deleteComputer() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Number equal to 0 or higher than 6
	 */
	public void invalidNumber() {
		view.notifyInvalid();
		start();
		
	}

}
