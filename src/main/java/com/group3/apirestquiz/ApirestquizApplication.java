package com.group3.apirestquiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@SpringBootApplication
public class ApirestquizApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApirestquizApplication.class, args);
	}

	@Configuration
	public class MessageSourceConfig {
		@Bean
		public MessageSource messageSource() {
			ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
			messageSource.setBasename("messages");
			messageSource.setDefaultEncoding("UTF-8");
			return messageSource;
		}
	}

}
