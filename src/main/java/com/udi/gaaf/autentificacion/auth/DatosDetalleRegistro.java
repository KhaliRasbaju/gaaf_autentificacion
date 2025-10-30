package com.udi.gaaf.autentificacion.auth;

import com.udi.gaaf.autentificacion.usuario.Roles;

/**
 * Representa los datos detallados del registro de un usuario.
 *
 * <p>Este { @code record } se utiliza como DTO para transferir información del usuario
 * recién registrado desde el backend hacia el frontend.</p>
 *
 * @param usuario nombre de usuario utilizado para iniciar sesión
 * @param correo dirección de correo electrónico del usuario
 * @param nombre nombre completo del usuario
 * @param telefono número de teléfono de contacto
 * @param rol rol asignado al usuario dentro del sistema
 */
public record DatosDetalleRegistro(
    String usuario,
    String correo,
    String nombre,
    String telefono,
    Roles rol
) {}
