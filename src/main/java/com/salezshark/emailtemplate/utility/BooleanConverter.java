package com.salezshark.emailtemplate.utility;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanConverter implements AttributeConverter<Boolean, String> {

	@Override
	public String convertToDatabaseColumn(Boolean value) {

		return (value != null && value) ? "Y" : "N";
	}

	@Override
	public Boolean convertToEntityAttribute(String value) {

		return "Y".equalsIgnoreCase(value);
	}
}
