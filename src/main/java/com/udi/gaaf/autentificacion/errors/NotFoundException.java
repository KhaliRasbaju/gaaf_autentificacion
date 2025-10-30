package com.udi.gaaf.autentificacion.errors;

/**
 * Excepción personalizada para indicar que un recurso no fue encontrado (HTTP 404).
 * <p>
 * Se lanza cuando se intenta acceder a un recurso inexistente en la base de datos
 * o cuando la entidad solicitada no puede ser localizada.
 * </p>
 *
 * @see RuntimeException
 */
@SuppressWarnings("serial")
public class NotFoundException extends RuntimeException {

    /**
     * Crea una nueva excepción de recurso no encontrado con un mensaje descriptivo.
     * 
     * @param message mensaje que describe el motivo del error.
     */
    public NotFoundException(String message) {
        super(message);
    }
}
