package com.compuLynx.banker;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Banking REST API", version = "1.0", description = "Baking Demo API, with a Customer who has got " +
		"an account, which has transactions."), security =  @SecurityRequirement(name = "Bearer Authentication"))
@SecurityScheme(name = "Bearer Authentication",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer")
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
public class BankerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankerApplication.class, args);
	}

}
