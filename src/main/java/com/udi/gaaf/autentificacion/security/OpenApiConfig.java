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

/**
 * Configuración de la documentación OpenAPI (Swagger) para el microservicio de autenticación.
 * <p>
 * Esta clase define los parámetros de la documentación generada automáticamente
 * por Swagger UI, incluyendo la información del servicio, contacto, servidor base,
 * y el esquema de seguridad JWT para la autenticación de endpoints protegidos.
 * </p>
 * 
 * <p><b>Propósito principal:</b></p>
 * <ul>
 *   <li>Configurar el título, descripción y versión de la API.</li>
 *   <li>Registrar la URL base del microservicio dentro del gateway.</li>
 *   <li>Definir el esquema de autenticación mediante token JWT (Bearer Token).</li>
 *   <li>Permitir el uso del esquema en Swagger UI para probar endpoints autenticados.</li>
 * </ul>
 * 
 * <p><b>Ejemplo de uso:</b></p>
 * <p>Acceder a la documentación en línea en: 
 * <code>http://localhost:8080/api/autentificacion/swagger-ui.html</code></p>
 * 
 * @see io.swagger.v3.oas.models.OpenAPI
 * @see io.swagger.v3.oas.models.security.SecurityScheme
 */
@Configuration
public class OpenApiConfig {

    /**
     * Crea y configura la instancia principal de OpenAPI para el microservicio.
     * <p>
     * Define los metadatos de la API (título, versión, descripción),
     * la URL base del servidor y el esquema de autenticación JWT.
     * </p>
     * 
     * @return una instancia configurada de {@link OpenAPI}
     */
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
