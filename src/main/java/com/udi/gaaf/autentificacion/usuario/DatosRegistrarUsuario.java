package com.udi.gaaf.autentificacion.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Representa los datos necesarios para registrar un nuevo usuario en el sistema.
 *
 * <p>Este { @code record } se utiliza para recibir y validar la información de un
 * usuario durante el proceso de registro. Incluye validaciones para garantizar 
 * la integridad y formato correcto de los datos.</p>
 *
 * @param nombre nombre completo del usuario
 * @param usuario nombre de usuario utilizado para autenticación
 * @param correo dirección de correo electrónico del usuario
 * @param telefono número de teléfono del usuario
 * @param contraseña contraseña del usuario (mínimo 8 caracteres, incluyendo mayúsculas, minúsculas y un carácter especial)
 * @param rol rol asignado al usuario dentro del sistema
 */
public record DatosRegistrarUsuario(

    @NotBlank
    @NotNull
    String nombre,

    @NotBlank
    @NotNull
    String usuario,

    @Email
    @NotNull
    String correo,

    @NotBlank
    @NotNull
    String telefono,

    @NotBlank(message = "La contraseña debe tener al menos 8 caracteres, incluyendo mayúsculas, minúsculas y un carácter especial.")
    String contraseña,

    @NotNull(message = "El rol es obligatorio. [ADMIN | JEFE_BODEGA | COORDINADOR_COMPRAS | GERENTE]")
    Roles rol
) {}
