package com.excilys.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.binding.dto.ComputerDTO;
import com.excilys.binding.mapper.ComputerMapper;
import com.excilys.exceptions.DateParseException;
import com.excilys.exceptions.DateValidationException;
import com.excilys.exceptions.IdValidationException;
import com.excilys.exceptions.NameValidationException;
import com.excilys.exceptions.ValidationException;

@Component
public class Validator {

	
	private ComputerMapper computerMapper;
	
	private static final Pattern DATE_PATTERN = Pattern.compile("^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$"
		      + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
		      + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
		      + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$");
	
	public Validator(ComputerMapper computerMapper) {
		this.computerMapper = computerMapper;
	}
	
	
	public void validateComputerToCreate(ComputerDTO computerDTO) throws DateParseException, ValidationException {
		isValidName(computerDTO.getName()); 
		areValidDates(computerDTO.getIntroduced(), computerDTO.getDiscontinued());
	}
	
	public void validateComputerToUpdate(ComputerDTO computerDTO) throws DateParseException, ValidationException {
		isValidId(computerDTO.getId()); 
		isValidName(computerDTO.getName());
		areValidDates(computerDTO.getIntroduced(), computerDTO.getDiscontinued());
		isValidId(computerDTO.getCompanyId());
	}
	
	public void validateCompany(CompanyDTO companyDTO) throws ValidationException {
		isValidId(companyDTO.getId());
		isValidName(companyDTO.getName());
	}

	private void isValidId(String id) throws IdValidationException  {
		boolean valid = false;
		if(id==null || id.trim().isEmpty()) {
			valid = true;
		}
		else if (Pattern.matches("^[1-9][0-9]*$",id)) valid =true;
		
		if(!valid) {
			throw new IdValidationException("Invalid id : "+id);
		}

	}	
	
	private void isValidName(String name) throws NameValidationException {
		boolean valid = false;
		if(name!=null && name.length()>2 
				&& Pattern.matches("^[\\w-,.0-9][^_!¡?÷?¿/\\+=@#$%ˆ&*(){}|~:]{2,}$",name)) valid =true;
		if(!valid) {
			throw new NameValidationException("Invalid name");
		}
	}
	
	private void areValidDates(String introducedDate, String discontinuedDate) throws DateParseException, ValidationException {
		boolean valid= false;
		isValidDate(introducedDate);
		isValidDate(discontinuedDate);
		if( introducedDate!=null && discontinuedDate!=null && !introducedDate.isEmpty() && !discontinuedDate.isEmpty()) {
			valid =(discontinuedDate.compareTo(introducedDate)>=0);
		} else {
			valid =false;
		}
		if(!valid) {
			throw new DateValidationException("Invalid dates");
		}
	}
	
	private void isValidDate(String date) throws DateParseException, DateValidationException {
		boolean valid= false;
		if(date== null || date.trim().isEmpty()) {
			valid = true;
		}
		else if (DATE_PATTERN.matcher(date).matches() 
				&& computerMapper.castLocalDate(date).getYear()>1970) valid = true;
		if(!valid) {
			throw new DateValidationException("Invalid date :"+date);
		}	
	}
}
