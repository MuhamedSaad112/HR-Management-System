package com.hrapp.global.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.LoggerContext;

@Configuration
public class LoggingConfiguration {

	public LoggingConfiguration(@Value("${spring.application.name}") String appName,
			@Value("${server.port}") String serverPort, ObjectMapper mapper) throws Exception {
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

		Map<String, String> map = new HashMap<>();
		map.put("app_name", appName);
		map.put("app_port", serverPort);
		String customFields = mapper.writeValueAsString(map);

		// هنا يمكن إضافة أي من appenders المعتادة مثل ConsoleAppender أو
		// LogstashAppender
		// تعتمد على استخدامك للـ JSON Format و Logstash
		// إذا كنت تحتاج إلى تطبيق Logstash أو تنسيق JSON بدون JHipster، يمكن إضافة
		// appender وفقًا لذلك
		// مثل إضافة LogstashAppender أو ConsoleAppender هنا.
	}
}
