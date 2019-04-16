package app;


import controller.CDBController;
import view.CDBView;

public class ComputerDatabaseApp {

	public static void main(String[] args) {
		CDBView view = new CDBView();
		CDBController controller = new CDBController(view);
	}

}
