package com.udi.gaaf.autentificacion.errors;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ValidationException;

/**
 * Controlador global para el manejo centralizado de excepciones.
 * <p>
 * Esta clase utiliza la anotación {@link RestControllerAdvice} para capturar y manejar
 * las excepciones que se produzcan en toda la aplicación, garantizando que las respuestas
 * sean consistentes y con formato JSON.
 * </p>
 *
 * <p><b>Responsabilidades:</b></p>
 * <ul>
 *   <li>Capturar excepciones personalizadas como {@link BadRequestException}, {@link NotFoundException}, etc.</li>
 *   <li>Retornar respuestas HTTP adecuadas según el tipo de error.</li>
 *   <li>Proveer mensajes claros y estructurados al cliente.</li>
 * </ul>
 *
 * <p><b>Formato general de respuesta JSON:</b></p>
 * <pre>
 * {
 *   "status": 400,
 *   "message": "Descripción del error"
 * }
 * </pre>
 *
 * @see org.springframework.web.bind.annotation.RestControllerAdvice
 * @see org.springframework.web.bind.annotation.ExceptionHandler
 */
@RestControllerAdvice
public class ManejoExcepciones {

    /**
     * Record interno para representar errores de validación de campos.
     * <p>
     * Se usa al manejar excepciones del tipo {@link MethodArgumentNotValidException},
     * permitiendo devolver una lista con el campo y el mensaje de error correspondiente.
     * </p>
     *
     * @param campo nombre del campo con error.
     * @param error mensaje descriptivo del error.
     */
    @SuppressWarnings("unused")
    private record DatoErrorValidation(String campo, String error) {
        public DatoErrorValidation(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

    /**
     * Maneja excepciones cuando no se proporciona un cuerpo (body) en la solicitud.
     *
     * @param e excepción {@link NotRequestBodyException}.
     * @return respuesta JSON con estado 400 (Bad Request).
     */
    @SuppressWarnings("rawtypes")
    @ExceptionHandler(NotRequestBodyException.class)
    public ResponseEntity TratarNotRequestBody(NotRequestBodyException e) {
        Map<String, Object> json = new HashMap<>();
        json.put("status", HttpStatus.BAD_REQUEST.value());
        json.put("message", e.getMessage());
        return ResponseEntity.badRequest().body(json);
    }

    /**
     * Maneja excepciones genéricas de tipo {@link BadRequestException}.
     *
     * @param e excepción lanzada.
     * @return respuesta JSON con estado 400 (Bad Request).
     */
    @SuppressWarnings("rawtypes")
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity TratarBadRequestBody(BadRequestException e) {
        Map<String, Object> json = new HashMap<>();
        json.put("status", HttpStatus.BAD_REQUEST.value());
        json.put("message", e.getMessage());
        return ResponseEntity.badRequest().body(json);
    }

    /**
     * Maneja excepciones cuando un recurso no se encuentra en el sistema.
     *
     * @param e excepción {@link NotFoundException}.
     * @return respuesta JSON con estado 404 (Not Found).
     */
    @SuppressWarnings("rawtypes")
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity TratarNotFound(NotFoundException e) {
        Map<String, Object> json = new HashMap<>();
        json.put("status", HttpStatus.NOT_FOUND.value());
        json.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(json);
    }

    /**
     * Maneja errores de autenticación (por ejemplo, token inválido o sesión expirada).
     *
     * @param e excepción {@link AuthenticationException}.
     * @return respuesta JSON con estado 401 (Unauthorized).
     */
    @SuppressWarnings("rawtypes")
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity TratarAuthenticationException(AuthenticationException e) {
        Map<String, Object> json = new HashMap<>();
        json.put("status", HttpStatus.UNAUTHORIZED.value());
        json.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(json);
    }

    /**
     * Maneja errores de validación de datos recibidos en el cuerpo de la solicitud.
     * <p>
     * Devuelve una lista con los campos que fallaron la validación y sus mensajes asociados.
     * </p>
     *
     * @param e excepción {@link MethodArgumentNotValidException}.
     * @return lista de objetos {@link DatoErrorValidation} con los errores detectados.
     */
    @SuppressWarnings("rawtypes")
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity TratarError404(MethodArgumentNotValidException e) {
        var errores = e.getFieldErrors().stream().map(DatoErrorValidation::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }

    /**
     * Maneja casos en los que un usuario no se encuentra en la base de datos.
     *
     * @param e excepción {@link UsernameNotFoundException}.
     * @return respuesta JSON con estado 404 (Not Found).
     */
    @SuppressWarnings("rawtypes")
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity TratarUsuarioNoEncontrado(UsernameNotFoundException e) {
        Map<String, Object> json = new HashMap<>();
        json.put("status", HttpStatus.NOT_FOUND.value());
        json.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(json);
    }

    /**
     * Maneja errores genéricos de validación de datos (por ejemplo, anotaciones {@code @Valid}).
     *
     * @param e excepción {@link ValidationException}.
     * @return mensaje de error con estado 400 (Bad Request).
     */
    @SuppressWarnings("rawtypes")
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity TratarErrorValidacion(ValidationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
