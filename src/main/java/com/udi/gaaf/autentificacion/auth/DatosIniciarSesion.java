package com.udi.gaaf.autentificacion.auth;

import jakarta.validation.constraints.NotBlank;

/**
 * Representa los datos de entrada para el proceso de inicio de sesión.
 *
 * <p>Este { @code record } se utiliza como DTO para recibir las credenciales 
 * del usuario desde el frontend durante el proceso de autenticación.</p>
 *
 * @param usuario nombre de usuario utilizado para iniciar sesión
 * @param contraseña contraseña del usuario, debe tener al menos 8 caracteres 
 *                   e incluir mayúsculas, minúsculas y un carácter especial
 */
public record DatosIniciarSesion(

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    String usuario,

    @NotBlank(message = "La contraseña no puede estar vacía y debe cumplir los requisitos de complejidad")
    String contraseña
) {}
