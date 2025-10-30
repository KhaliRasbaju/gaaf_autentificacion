package com.udi.gaaf.autentificacion.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udi.gaaf.autentificacion.errors.CustomAuthenticationEntryPoint;

import jakarta.servlet.http.HttpServletResponse;

/**
 * Configuración general de seguridad para la aplicación.
 * <p>
 * Esta clase define la configuración de seguridad basada en Spring Security,
 * gestionando la autenticación, autorización y manejo de sesiones sin estado (JWT).
 * </p>
 *
 * <p>
 * Configura las rutas públicas, la política de sesiones, el manejo de errores,
 * el filtro personalizado de autenticación y los beans necesarios
 * para la encriptación de contraseñas y la gestión de autenticaciones.
 * </p>
 *
 * <h2>Responsabilidades principales:</h2>
 * <ul>
 *   <li>Deshabilitar CSRF y configurar sesiones sin estado.</li>
 *   <li>Definir rutas públicas y protegidas.</li>
 *   <li>Agregar el {@link SecurityFilter} antes del filtro estándar de autenticación.</li>
 *   <li>Configurar manejadores personalizados para errores y accesos denegados.</li>
 *   <li>Registrar beans de {@link AuthenticationManager} y {@link PasswordEncoder}.</li>
 * </ul>
 *
 * @see SecurityFilter
 * @see CustomAuthenticationEntryPoint
 * @see AuthenticationManager
 * @see PasswordEncoder
 * @see org.springframework.security.web.SecurityFilterChain
 * @see org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	/** Filtro de seguridad encargado de validar los tokens JWT en cada petición. */
	@Autowired
	private SecurityFilter securityFilter;

	/** Punto de entrada personalizado para manejar errores de autenticación. */
	@Autowired
	private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	
	
	/**
	 * Configura la cadena principal de filtros de seguridad.
	 * <p>
	 * Define las rutas públicas, las políticas de sesión (sin estado), 
	 * el filtro de seguridad personalizado y el manejo de accesos denegados.
	 * </p>
	 *
	 * @param http instancia de {@link HttpSecurity} proporcionada por Spring Security
	 * @return {@link SecurityFilterChain} cadena configurada de filtros de seguridad
	 * @throws Exception si ocurre un error durante la configuración
	 */
	@Bean
	public SecurityFilterChain secureFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
				.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(req -> {
					req.requestMatchers("/auth/**").permitAll();
					req.requestMatchers("/usuario/**").permitAll();
					req.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/docs/**").permitAll();
					req.anyRequest().authenticated();
				})
				.exceptionHandling(exceptions -> {
					// exceptions.authenticationEntryPoint(customAuthenticationEntryPoint);
					exceptions.accessDeniedHandler(accessDeniedHandler());
				})
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	
	/**
	 * Proporciona el gestor de autenticación del sistema.
	 * <p>
	 * Este bean permite autenticar las credenciales del usuario
	 * a través de la configuración de autenticación definida.
	 * </p>
	 *
	 * @param authenticationConfiguration configuración de autenticación proporcionada por Spring
	 * @return {@link AuthenticationManager} utilizado para procesar autenticaciones
	 * @throws Exception si no puede inicializar el gestor de autenticación
	 */
	@Bean 
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	/**
	 * Define el codificador de contraseñas utilizado en la aplicación.
	 * <p>
	 * Utiliza el algoritmo BCrypt para encriptar contraseñas antes de almacenarlas.
	 * </p>
	 *
	 * @return {@link PasswordEncoder} instancia de {@link BCryptPasswordEncoder}
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * Define el manejador de accesos denegados.
	 * <p>
	 * Retorna un mensaje JSON con el estado HTTP 401 (No autorizado)
	 * cuando un usuario intenta acceder a un recurso sin los permisos adecuados.
	 * </p>
	 *
	 * @return {@link AccessDeniedHandler} manejador personalizado de accesos denegados
	 */
	private AccessDeniedHandler accessDeniedHandler() {
		return (request, response, accessDeniedException) -> {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			Map<String, Object> json = new HashMap<>();
			json.put("status", HttpStatus.UNAUTHORIZED.value());
			json.put("message", "No tienes permisos suficientes");

			ObjectMapper objectMapper = new ObjectMapper();
			response.getWriter().write(objectMapper.writeValueAsString(json));
			response.getWriter().flush();
		};
	}
}
