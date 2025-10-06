package com.udi.gaaf.autentificacion.errors;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Field;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ValidationException;

@RestControllerAdvice
public class ManejoExcepciones {

	
	@SuppressWarnings("unused")
	private record DatoErrorValidation(String campo, String error) {
		public DatoErrorValidation(FieldError error) {
			this(error.getField(), error.getDefaultMessage());
		}
	}
	
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(NotRequestBodyException.class)
	public ResponseEntity TratarNotRequestBody(NotRequestBodyException e) {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("status", HttpStatus.BAD_REQUEST.value());
		json.put("message", e.getMessage());
		return ResponseEntity.badRequest().body(json);
	}
	
	
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity TratarBadRequestBody(BadRequestException e) {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("status", HttpStatus.BAD_REQUEST.value());
		json.put("message", e.getMessage());
		return ResponseEntity.badRequest().body(json);
	}
	
	
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity TratarNotFound(NotFoundException e) {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("status", HttpStatus.NOT_FOUND.value());
		json.put("message", e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(json);
	}
	@SuppressWarnings("rawtypes")
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity TratarAuthenticationException(AuthenticationException e) {
        Map<String, Object> json = new HashMap<>();
        json.put("status", HttpStatus.UNAUTHORIZED.value());
        json.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(json);
    }
	
	
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity TratarError404(MethodArgumentNotValidException e) {
		var errores = e.getFieldErrors().stream().map(DatoErrorValidation::new).toList();
		return ResponseEntity.badRequest().body(errores);
		
	}
	
	
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity TratarUsuarioNoEncontrado(UsernameNotFoundException e) {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("status", HttpStatus.NOT_FOUND.value());
		json.put("message", e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(json);
	}
	
	
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity TratarErrorValidacion(ValidationException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	
	
	
	

}
