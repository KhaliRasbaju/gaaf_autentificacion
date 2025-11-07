package com.udi.gaaf.autentificacion.auth;

import com.udi.gaaf.autentificacion.usuario.Roles;

/**
 * Representa los datos devueltos tras un inicio de sesión exitoso.
 *
 * <p>Este { @code record } se utiliza como DTO para transferir al frontend
 * la información esencial del usuario autenticado, incluyendo el token JWT.</p>
 *
 * @param usuario nombre de usuario autenticado
 * @param token token JWT generado para la sesión del usuario
 * @param rol rol asignado al usuario dentro del sistema
 * @param id identificación del usario
 */
public record DatosDetalleSesion(
    String usuario,
    String token,
    Roles rol,
    String id
) {}
