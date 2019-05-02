package com.excilys.validator;

import java.util.regex.Pattern;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.binding.dto.ComputerDTO;

public class Validator {

	private static Validator instance =null ;
	
	private Validator() {	
	}
	
	public static Validator getInstance() {
		return (instance!=null) ? instance : (instance = new Validator());
	}
	
	public void validateComputerToCreate(ComputerDTO computerDTO) {
		boolean valid=false;
		valid=( isValidName(computerDTO.getName()) 
				&& areValidDates(computerDTO.getIntroduced(), computerDTO.getDiscontinued()));
		if(!valid) throw new RuntimeException("Computer data is not valid!");
	}
	
	public void validateComputerToUpdate(ComputerDTO computerDTO) {
		
	}
	
	public void validateCompany(CompanyDTO companyDTO) {
		boolean valid=false;
		valid = isValidName(companyDTO.getName());
		if(!valid) throw new RuntimeException("Company data is not valid!");
	}

	public boolean isValidId(String id) {
		boolean valid=false; 
		if(id!=null && !id.trim().isEmpty() && Pattern.matches("^[1-9][0-9]*$",id)) valid=true;
		if(id==null || id.trim().isEmpty()) valid=true;
		return valid;
	}
	
	
	public boolean isValidName(String name) {
		boolean valid=false;
		valid=( name!=null && !name.trim().isEmpty() && name.length()>2 
				&& Pattern.matches("^[\\w-,.0-9][^_!¡?÷?¿/\\+=@#$%ˆ&*(){}|~:]{2,}$",name));
		return valid;
	}
	
	public boolean areValidDates(String introducedDate, String discontinuedDate) {
		boolean valid=false;
		if(introducedDate!=null && !introducedDate.trim().isEmpty() 
				&&  discontinuedDate!=null && !discontinuedDate.trim().isEmpty()) {
			valid = discontinuedDate.compareTo(introducedDate)>0;
		} else {  
			valid = true;
		}
		return valid;
	}
	
}
