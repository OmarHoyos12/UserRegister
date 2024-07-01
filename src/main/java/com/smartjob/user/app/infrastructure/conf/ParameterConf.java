package com.smartjob.user.app.infrastructure.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParameterConf {

	@Value("${custom.password.regex}")
	private String passwordRegex;

	@Value("${custom.password.validation.message}")
	private String passwordValidationMessage;

	@Bean
	public String passwordRegex() {
		return passwordRegex;
	}
	
	@Bean
	public String passwordValidationMessage() {
		return passwordValidationMessage;
	}
}
