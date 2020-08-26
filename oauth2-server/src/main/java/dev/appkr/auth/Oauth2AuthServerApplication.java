package dev.appkr.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;

@SpringBootApplication(exclude = LiquibaseAutoConfiguration.class)
public class
Oauth2AuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Oauth2AuthServerApplication.class, args);
	}

}
