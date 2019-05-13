package com.excilys.validator;

import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

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

@Component
public class Validator {

	
	private static final Logger LOGGER = 
			LogManager.getLogger(Validator.class);
	
	private ComputerMapper computerMapper;
	
	private static final Pattern DATE_PATTERN = Pattern.compile("^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$"
		      + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
		      + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
		      + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$");
	
	public Validator(ComputerMapper computerMapper) {
		this.computerMapper = computerMapper;
	}
	
	
	public boolean validateComputerToCreate(ComputerDTO computerDTO) throws ValidationException, DateParseException {
		boolean valid=false;
		valid=( isValidName(computerDTO.getName()) 
				&& areValidDates(computerDTO.getIntroduced(), computerDTO.getDiscontinued()));
		if(!valid) {
			LOGGER.warn("Computer data is not valid!");
			throw new ComputerValidationException("Computer data is not valid : "+computerDTO);
		}
		return valid;
	}
	
	public boolean validateComputerToUpdate(ComputerDTO computerDTO) throws ValidationException, DateParseException {
		boolean valid =(isValidId(computerDTO.getId()) 
				&& isValidName(computerDTO.getName())
				&& areValidDates(computerDTO.getIntroduced(), computerDTO.getDiscontinued())
				&& isValidId(computerDTO.getCompanyId()));
		if(!valid) {
			LOGGER.warn("Computer data is not valid!");
			throw new ComputerValidationException("Computer data is not valid : "+computerDTO);
		}
		return valid;
	}
	
	public boolean validateCompany(CompanyDTO companyDTO) throws ValidationException {
		boolean valid = isValidName(companyDTO.getName());
		if(!valid) {
			LOGGER.warn("Company data is not valid!");
			throw new CompanyValidationException("Company data is not valid : "+companyDTO);
		}
		return valid;
	}

	private boolean isValidId(String id) throws IdValidationException {
		boolean valid=false; 
		if(id!=null && !id.trim().isEmpty() && Pattern.matches("^[1-9][0-9]*$",id)) valid=true;
		if(id==null || id.trim().isEmpty()) valid=true;
		if(!valid) {
			LOGGER.warn("Id is not valid");
			throw new IdValidationException("Id is not valid : "+id);
		}
		return valid;
	}
	
	
	private boolean isValidName(String name) throws NameValidationException {
		boolean valid =( name!=null && !name.trim().isEmpty() && name.length()>2 
				&& Pattern.matches("^[\\w-,.0-9][^_!¡?÷?¿/\\+=@#$%ˆ&*(){}|~:]{2,}$",name));
		if(!valid) {
			LOGGER.warn("Name is not valid");
			throw new NameValidationException("Name is not valid : "+name);
		}
		return valid;
	}
	
	private boolean areValidDates(String introducedDate, String discontinuedDate) throws ValidationException, DateParseException {
		boolean valid= false;
		if (isValidDate(introducedDate) && isValidDate(discontinuedDate)){
			if(!introducedDate.isEmpty() && introducedDate!=null && !discontinuedDate.isEmpty() && discontinuedDate!=null) {
				valid =(discontinuedDate.compareTo(introducedDate)>=0)? true : false;
			} else {
				valid =true;
			}
		}
		if(!valid) {
			LOGGER.warn("Dates are not valid");
			throw new DateValidationException("Dates are not valid "+introducedDate+" "+discontinuedDate);
		}
		return valid;
	}
	
	private boolean isValidDate(String date) throws ValidationException, DateParseException {
		boolean valid= false;
		if (date!=null && !date.trim().isEmpty() 
				&& DATE_PATTERN.matcher(date).matches() 
				&& computerMapper.castLocalDate(date).getYear()>1970) valid=true; 
		if(date== null || date.trim().isEmpty()) valid = true;
		if(!valid) {
			LOGGER.warn("Date is not valid");
			throw new DateValidationException("Date is not valid : "+date);
		}
		return valid;
	}
	
}
