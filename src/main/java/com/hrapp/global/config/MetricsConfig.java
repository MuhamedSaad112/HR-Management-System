package com.hrapp.global.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class MetricsConfig {
	@Bean
	public MeterRegistry meterRegistry() {
		return new SimpleMeterRegistry();
	}
}
