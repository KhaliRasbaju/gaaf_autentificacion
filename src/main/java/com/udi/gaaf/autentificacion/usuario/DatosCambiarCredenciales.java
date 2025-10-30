package com.udi.gaaf.autentificacion.usuario;

import jakarta.validation.constraints.NotBlank;

/**
 * Representa los datos necesarios para cambiar las credenciales de un usuario.
 *
 * <p>Este { @code record } se utiliza para recibir y validar la nueva contraseña 
 * de un usuario durante el proceso de actualización de credenciales.</p>
 *
 * @param contraseña nueva contraseña del usuario.
 */
public record DatosCambiarCredenciales(
    @NotBlank(message = "La contraseña debe tener al menos 8 caracteres, incluir mayúsculas, minúsculas y un carácter especial.")
    String contraseña
) {}
