package com.psys.hobb.common.time.util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomLocalDateTimeDeSerializer extends JsonDeserializer<LocalDateTime>{
	
	private static final Logger logger = LoggerFactory.getLogger(CustomLocalDateTimeDeSerializer.class);

	@Override
	public LocalDateTime deserialize(JsonParser jp, DeserializationContext context)
			throws IOException, JsonProcessingException {
		String timeStr = jp.getText();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		try {
			return LocalDateTime.parse(timeStr, formatter);
		} catch (Exception e) {
			logger.warn(timeStr + "转LocalDateTime时间格式失败");
			return null;
		}
	}

}
