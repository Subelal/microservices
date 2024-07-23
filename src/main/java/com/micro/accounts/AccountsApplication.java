package com.micro.accounts;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info=@Info(
				title = "Account Mircoservices REST API Documentation",
				description = "Axis Bank Accounts Microservices Rest API Documentations ",
				version="v1",
				contact = @Contact(
						name="Apache2.0",
						email ="subelal.pal@gmail.com",
						url="https://subelal.com"
				)

		)
)
public class AccountsApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
