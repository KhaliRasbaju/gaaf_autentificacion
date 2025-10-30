package com.udi.gaaf.autentificacion.errors;

/**
 * Excepción personalizada para representar errores de solicitud incorrecta (HTTP 400).
 * <p>
 * Se lanza cuando los datos enviados por el cliente no cumplen con los requisitos
 * esperados o la solicitud no puede ser procesada correctamente.
 * </p>
 *
 * @see RuntimeException
 */
@SuppressWarnings("serial")
public class BadRequestException extends RuntimeException {

    /**
     * Crea una nueva excepción de solicitud incorrecta con un mensaje descriptivo.
     * 
     * @param message mensaje que describe la causa del error.
     */
    public BadRequestException(String message) {
        super(message);
    }
}
