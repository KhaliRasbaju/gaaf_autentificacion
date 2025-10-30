package com.udi.gaaf.autentificacion.errors;

/**
 * Excepción personalizada para solicitudes sin cuerpo (body) o cuerpo inválido.
 * <p>
 * Se utiliza cuando una solicitud HTTP requiere un cuerpo con datos (por ejemplo,
 * una creación o actualización de entidad), pero este no está presente o tiene
 * un formato incorrecto.
 * </p>
 *
 * @see RuntimeException
 */
@SuppressWarnings("serial")
public class NotRequestBodyException extends RuntimeException {

    /**
     * Crea una nueva excepción indicando que el cuerpo de la solicitud es inválido o ausente.
     * 
     * @param message mensaje que describe la causa del error.
     */
    public NotRequestBodyException(String message) {
        super(message);
    }
}
