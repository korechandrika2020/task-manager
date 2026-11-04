package com.chandrika.taskmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI taskManagerOpenAPI() {
		return new OpenAPI().info(new Info()
				.title("Task Manager API")
				.description("REST API for managing tasks")
				.version("1.0"));
				
	}
	
}
