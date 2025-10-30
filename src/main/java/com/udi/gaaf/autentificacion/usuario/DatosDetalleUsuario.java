package com.udi.gaaf.autentificacion.usuario;

/**
 * Representa el detalle completo de un usuario dentro del sistema.
 *
 * <p>Este { @code record } se utiliza para transferir información detallada
 * del usuario entre las capas del sistema (por ejemplo, entre el backend
 * y el frontend).</p>
 *
 * @param id identificador único del usuario
 * @param usuario nombre de usuario utilizado para autenticación
 * @param nombre nombre completo del usuario
 * @param correo dirección de correo electrónico del usuario
 * @param telefono número de teléfono del usuario
 * @param activo indica si el usuario se encuentra activo en el sistema
 * @param rol rol asignado al usuario dentro de la aplicación
 */
public record DatosDetalleUsuario(
    String id,
    String usuario,
    String nombre,
    String correo,
    String telefono,
    Boolean activo,
    Roles rol
) {}
