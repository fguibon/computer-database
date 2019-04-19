package main.com.excilys.model;

public class ComputerValidator implements Validator<Computer> {

	public ComputerValidator() {	
	}
	
	@Override
	public void validate(Computer computer) {
		boolean valid=false;
		valid=( computer.getName()!=null 
				&& computer.getDiscontinued().compareTo(computer.getIntroduced())<0 );
		if(!valid) throw new RuntimeException();
	}
	
}
