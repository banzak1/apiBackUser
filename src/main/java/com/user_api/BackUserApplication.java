package com.user_api;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@CrossOrigin(origins = "http://localhost:8081")
@SpringBootApplication
@RestController
public class BackUserApplication {

	@Bean
	public WebClient webClient(WebClient.Builder builder) {
		return builder
				.baseUrl("http://localhost:8085")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}



	public static void main(String[] args) {
		SpringApplication.run(BackUserApplication.class, args);
	}

	@Configuration
	@EnableWebSecurity
	class SecurityConfiguration extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.cors().and()
					.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
					.oauth2ResourceServer().jwt();
		}
	}

}