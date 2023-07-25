package com.group3.apirestquiz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API-REST-QUIZ")
                        .description("API-REST-QUIZ est une API crée par les apprenant de ODK3(Orange Digital Center) qui sont : Drissa Sidiki Traore, Ibrahim Djingareye Maiga et Kaly Diallo.\nL'objectif était de leur permettre de s'initialiser avec le framework Spring Boot.")
                        .version("1.0"));
    }
}