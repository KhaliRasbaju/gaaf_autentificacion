package com.udi.gaaf.autentificacion.errors;

@SuppressWarnings("serial")
public class NotRequestBodyException extends RuntimeException {
	public NotRequestBodyException(String message) {
		super(message);
	}
}
