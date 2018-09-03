package com.psys.hobb.common.time.util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomLocalDateSerializer extends JsonSerializer<LocalDate>{

	@Override
	public void serialize(LocalDate date, JsonGenerator generator, SerializerProvider sp)
			throws IOException, JsonProcessingException {
		String formattedDateTime = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); 
        generator.writeString(formattedDateTime);
	}

}
