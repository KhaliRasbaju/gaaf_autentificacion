package com.udi.gaaf.autentificacion.errors;

/**
 * Excepción lanzada cuando un token JWT no es válido o ha expirado.
 * <p>
 * Extiende {@link AuthenticationException} para integrarse con el flujo de
 * autenticación de Spring Security.
 * </p>
 *
 * @see com.udi.gaaf.autentificacion.errors.AuthenticationException
 */
@SuppressWarnings("serial")
public class NotTokenValidException extends AuthenticationException {

    /**
     * Crea una nueva excepción indicando que el token proporcionado no es válido.
     * 
     * @param message mensaje que describe el motivo por el cual el token es inválido.
     */
    public NotTokenValidException(String message) {
        super(message);
    }
}
