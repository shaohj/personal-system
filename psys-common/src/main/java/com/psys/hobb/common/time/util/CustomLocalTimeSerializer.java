package com.psys.hobb.common.time.util;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomLocalTimeSerializer extends JsonSerializer<LocalTime>{

    @Override
    public void serialize(LocalTime time, JsonGenerator generator, SerializerProvider sp)
            throws IOException, JsonProcessingException {
        String formattedDateTime = time.format(DateTimeFormatter.ofPattern("HH:mm:ss")); 
        generator.writeString( formattedDateTime);
    }

}
