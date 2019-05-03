package com.excilys.validator;

import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.binding.dto.CompanyDTO;
import com.excilys.binding.dto.ComputerDTO;
import com.excilys.binding.mapper.ComputerMapper;
import com.excilys.exceptions.CompanyValidationException;
import com.excilys.exceptions.ComputerValidationException;
import com.excilys.exceptions.DateParseException;
import com.excilys.exceptions.DateValidationException;
import com.excilys.exceptions.IdValidationException;
import com.excilys.exceptions.NameValidationException;
import com.excilys.exceptions.ValidationException;

public class Validator {

	private static Validator instance =null ;
	
	private static final Logger logger = 
			LogManager.getLogger(Validator.class);
	
	private Validator() {	
	}
	
	public static Validator getInstance() {
		return (instance!=null) ? instance : (instance = new Validator());
	}
	
	public boolean validateComputerToCreate(ComputerDTO computerDTO) throws ValidationException, DateParseException {
		boolean valid=false;
		valid=( isValidName(computerDTO.getName()) 
				&& areValidDates(computerDTO.getIntroduced(), computerDTO.getDiscontinued()));
		if(!valid) {
			logger.warn("Computer data is not valid!");
			throw new ComputerValidationException();
		}
		return valid;
	}
	
	public boolean validateComputerToUpdate(ComputerDTO computerDTO) throws ValidationException, DateParseException {
		boolean valid =(isValidId(computerDTO.getId()) 
				&& isValidName(computerDTO.getName())
				&& areValidDates(computerDTO.getIntroduced(), computerDTO.getDiscontinued())
				&& isValidId(computerDTO.getCompanyId()));
		if(!valid) {
			logger.warn("Computer data is not valid!");
			throw new ComputerValidationException();
		}
		return valid;
	}
	
	public boolean validateCompany(CompanyDTO companyDTO) throws ValidationException {
		boolean valid = isValidName(companyDTO.getName());
		if(!valid) {
			logger.warn("Company data is not valid!");
			throw new CompanyValidationException();
		}
		return valid;
	}

	private boolean isValidId(String id) throws IdValidationException {
		boolean valid=false; 
		if(id!=null && !id.trim().isEmpty() && Pattern.matches("^[1-9][0-9]*$",id)) valid=true;
		if(id==null || id.trim().isEmpty()) valid=true;
		if(!valid) {
			logger.warn("Id data is not valid!");
			throw new IdValidationException();
		}
		return valid;
	}
	
	
	private boolean isValidName(String name) throws NameValidationException {
		boolean valid =( name!=null && !name.trim().isEmpty() && name.length()>2 
				&& Pattern.matches("^[\\w-,.0-9][^_!¡?÷?¿/\\+=@#$%ˆ&*(){}|~:]{2,}$",name));
		if(!valid) {
			logger.warn("Name is not valid!");
			throw new NameValidationException();
		}
		return valid;
	}
	
	private boolean areValidDates(String introducedDate, String discontinuedDate) throws ValidationException, DateParseException {
		boolean valid= false;
		if (isValidDate(introducedDate) && isValidDate(discontinuedDate)){
			if((introducedDate.isEmpty() || introducedDate!=null) 
					| (discontinuedDate.isEmpty() || discontinuedDate!=null)) {
				valid = true;
			} else {
				valid =(discontinuedDate.compareTo(introducedDate)>0)? true : false;
			}
		} else {
			valid = false;
		}
		if(!valid) {
			logger.warn("Dates are not valid!");
			throw new DateValidationException();
		}
		return valid;
	}
	
	private boolean isValidDate(String date) throws ValidationException, DateParseException {
		boolean valid= false;
		if (date!=null && !date.trim().isEmpty() 
				&& Pattern.matches(date,"(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}") 
				&& ComputerMapper.getInstance().castLocalDate(date).getYear()>1970) valid=true; 
		if(date== null || date.trim().isEmpty()) valid = true;
		if(!valid) {
			logger.warn("Date is not valid!");
			throw new DateValidationException();
		}
		return valid;
	}
	
}
