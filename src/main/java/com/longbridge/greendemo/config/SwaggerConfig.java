package com.longbridge.greendemo.config;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	 
    public static final String AUTHORIZATION_HEADER = "Authorization: Bearer ";

	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("public-api")
				.apiInfo(apiInfo()).select().paths(postPaths()).build();
	}

	private Predicate<String> postPaths() {
		return or(regex("/api/v1.*"), regex("/api/v2.*"));
	}

	
	@Bean
	public Docket api() {
	    return new Docket(DocumentationType.SWAGGER_2)
	            .select()
	            .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
	            .paths(PathSelectors.any())
	            .build()
	            .apiInfo(apiInfo())
	            .securitySchemes(Arrays.asList(apiKey()));
	}

	@SuppressWarnings("deprecation")
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Maker-Checker API by idisimagha dublin-green")
				.description("Maker-checker API implementation with spring boot, demo for longbridgetech")
				.termsOfServiceUrl("http://longbridgetech.com")
				.contact("idisimagha dublin-green (greendublin007@gmail.com)").license("Apache 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0").version("1.0").build();
	}

	private ApiKey apiKey() {
	    return new ApiKey("jwtToken", "Authorization", "header");
	}

}