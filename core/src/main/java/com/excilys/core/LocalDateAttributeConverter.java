package com.excilys.core;

import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Timestamp>{

	@Override
	public Timestamp convertToDatabaseColumn(LocalDate date) {
		return (date==null)? null : Timestamp.valueOf(date.atStartOfDay());
	}

	@Override
	public LocalDate convertToEntityAttribute(Timestamp timestamp) {
		return (timestamp==null)? null : timestamp.toLocalDateTime().toLocalDate();
	}

}
