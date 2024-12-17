package com.hrapp.global.config;

import com.hrapp.global.logging.LoggingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;


@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

	/**
	 * تفعيل LoggingAspect في بيئة التطوير فقط
	 * 
	 * @param env البيئة
	 * @return LoggingAspect
	 */
	@Bean
	@Profile("dev") // تفعيل في بيئة dev فقط
	public LoggingAspect loggingAspect(Environment env) {
		return new LoggingAspect(env);
	}
}
