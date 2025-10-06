package com.udi.gaaf.autentificacion.errors;

@SuppressWarnings("serial")
public class NotTokenValidException extends AuthenticationException{
	public NotTokenValidException(String message) {
		super(message);
	}
	
}
