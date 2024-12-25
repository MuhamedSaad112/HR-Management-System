package com.hrapp.global.config;

import ch.qos.logback.classic.LoggerContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class LoggingConfiguration {

	public LoggingConfiguration(@Value("${spring.application.name}") String appName,
			@Value("${server.port}") String serverPort, ObjectMapper mapper) throws Exception {
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

		Map<String, String> map = new HashMap<>();
		map.put("app_name", appName);
		map.put("app_port", serverPort);
		String customFields = mapper.writeValueAsString(map);

	}
}
