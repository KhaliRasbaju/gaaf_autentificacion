package com.udi.gaaf.autentificacion.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

	@Bean
	
	public OpenAPI autenticacionOpenAPI() {
		return new OpenAPI() 
				.addServersItem(new Server()
                .url("http://localhost:8080/api/autentificacion")
                .description("Gateway Autenticación"))
        .info(new Info()
                .title("API de Autenticación")
                .version("1.0.0")
                .description("Microservicio responsable del manejo de usuarios y autenticación JWT.")
                .contact(new Contact()
                        .name("Semillero UDI")))
        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
        .components(new Components()
                .addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                                .name("bearerAuth")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
	}
}
