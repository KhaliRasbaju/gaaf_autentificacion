package com.udi.gaaf.autentificacion.errors;

/**
 * Excepción personalizada para errores de autenticación.
 * <p>
 * Esta clase se utiliza para representar errores relacionados con el proceso
 * de autenticación de usuarios, como credenciales incorrectas o token inválido.
 * </p>
 *
 * @see RuntimeException
 */
@SuppressWarnings("serial")
public class AuthenticationException extends RuntimeException {

    /**
     * Crea una nueva excepción de autenticación con un mensaje descriptivo.
     * 
     * @param message mensaje que describe el error de autenticación.
     */
    public AuthenticationException(String message) {
        super(message);
    }
}
