package com.udi.gaaf.autentificacion.errors;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Punto de entrada personalizado para manejar errores de autenticación.
 * <p>
 * Esta clase intercepta los intentos de acceso no autorizados y devuelve
 * una respuesta en formato JSON con el estado HTTP 401 (Unauthorized).
 * </p>
 *
 * <p><b>Ejemplo de respuesta:</b></p>
 * <pre>
 * {
 *   "status": 401,
 *   "message": "Token invalido"
 * }
 * </pre>
 *
 * @see AuthenticationEntryPoint
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Maneja los intentos de acceso no autorizados devolviendo una respuesta JSON
     * con el código HTTP 401 y un mensaje descriptivo.
     *
     * @param request la solicitud HTTP entrante.
     * @param response la respuesta HTTP que se enviará al cliente.
     * @param authException la excepción de autenticación generada.
     * @throws IOException si ocurre un error al escribir la respuesta.
     * @throws ServletException si ocurre un error durante el manejo de la solicitud.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> json = new HashMap<>();
        json.put("status", HttpStatus.UNAUTHORIZED.value());
        json.put("message", "Token invalido");

        String responseBody = new ObjectMapper().writeValueAsString(json);
        response.getWriter().write(responseBody);
    }
}
