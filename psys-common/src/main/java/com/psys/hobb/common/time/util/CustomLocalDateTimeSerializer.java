package com.psys.hobb.common.time.util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomLocalDateTimeSerializer extends JsonSerializer<LocalDateTime>{

    @Override
    public void serialize(LocalDateTime dateTime, JsonGenerator generator, SerializerProvider sp)
            throws IOException, JsonProcessingException {
        String formattedDateTime = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); 
        generator.writeString( formattedDateTime);
    }

}
