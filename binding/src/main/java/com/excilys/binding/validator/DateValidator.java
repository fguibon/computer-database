package com.excilys.binding.validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.binding.dto.ComputerDTO;

public class DateValidator implements ConstraintValidator<CompareDates, ComputerDTO> {

	private static final Logger LOGGER = LogManager.getLogger(DateValidator.class.getName());
	
	@Override
	public boolean isValid(ComputerDTO value, ConstraintValidatorContext context) {
		final String firstObj = value.getIntroduced();
		final String secondObj = value.getDiscontinued();
		try {
			
			if (firstObj != null && secondObj != null && !firstObj.isEmpty() && !secondObj.isEmpty()) {
				return LocalDate.parse(secondObj).isAfter(LocalDate.parse(firstObj));
			} else {
				return true;
			}

		} catch ( DateTimeParseException e) {
			LOGGER.error("Failed cast to LocalDate : "+firstObj+" "+secondObj+" " +e.getMessage());
		}
		return false;
	}

}
