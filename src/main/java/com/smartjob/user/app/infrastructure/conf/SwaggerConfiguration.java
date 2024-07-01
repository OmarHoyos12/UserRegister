package com.smartjob.user.app.infrastructure.conf;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	public static final String API_BASE_URI = "/";
	public static final String API_TITLE = "proceso Desarrollador integraciones Java";
	public static final String API_DESCRIPTION = "Springboot Service Create User";
	public static final String API_VERSION = "1.0.0";
	public static final String API_CONTACT_NAME = "Smartjobs";
	public static final String API_CONTACT_URL = "http://www.smartjob.cl";
	public static final String API_CONTACT_EMAIL = "xxxxx@smartjob.cl";

	@Bean
	public Docket docketFactory(TypeResolver typeResolver) {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(metadata())
				.directModelSubstitute(LocalDate.class, String.class)
				.directModelSubstitute(LocalDateTime.class, String.class)
				.directModelSubstitute(LocalTime.class, String.class)
				.directModelSubstitute(OffsetDateTime.class, String.class)
				.directModelSubstitute(OffsetTime.class, String.class).genericModelSubstitutes(ResponseEntity.class)
				.alternateTypeRules(newRule(
						typeResolver.resolve(DeferredResult.class,
								typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
						typeResolver.resolve(WildcardType.class)))
				.useDefaultResponseMessages(false).select()
				.apis(RequestHandlerSelectors.basePackage("com.smartjob.user.app.infrastructure.rest.controller"))
				.paths(PathSelectors.any()).build();
	}

	@Bean
	public ApiInfo metadata() {
		return new ApiInfoBuilder().title(API_TITLE).description(API_DESCRIPTION).version(API_VERSION)
				.contact(new Contact(API_CONTACT_NAME, API_CONTACT_URL, API_CONTACT_EMAIL)).build();
	}

	@Bean
	public UiConfiguration swaggerUiConfig() {
		return UiConfigurationBuilder.builder().build();
	}

}
