package com.tgc.Sarafan;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sentry.Sentry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;

@SpringBootApplication
public class SarafanApplication {

	public static void main(String[] args) {
		SpringApplication.run(SarafanApplication.class, args);
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

}
