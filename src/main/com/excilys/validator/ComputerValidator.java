package main.com.excilys.validator;

import main.com.excilys.binding.dto.ComputerDTO;

public class ComputerValidator {

	public ComputerValidator() {	
	}
	
	public void validate(ComputerDTO computerDTO) {
		boolean valid=false;
		valid=( computerDTO.getName()!=null 
				&& computerDTO.getDiscontinued().compareTo(computerDTO.getIntroduced())>0 );
		if(!valid) throw new RuntimeException();
	}
	
}
