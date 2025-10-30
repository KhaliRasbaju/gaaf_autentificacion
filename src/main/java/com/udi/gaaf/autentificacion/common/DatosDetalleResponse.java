package com.udi.gaaf.autentificacion.common;

/**
 * Representa una respuesta estándar del sistema.
 *
 * <p>Este {@code record} se utiliza como DTO genérico para retornar respuestas 
 * simples desde el backend hacia el frontend, incluyendo un código de estado 
 * HTTP y un mensaje descriptivo.</p>
 *
 * @param status código de estado HTTP asociado a la respuesta
 * @param message mensaje descriptivo que indica el resultado de la operación
 */
public record DatosDetalleResponse(
    Integer status,
    String message
) {}
