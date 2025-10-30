package com.udi.gaaf.autentificacion.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Representa los datos necesarios para editar la información de un usuario existente.
 *
 * <p>Este {@code record} se utiliza para recibir y validar los datos enviados
 * desde el frontend al actualizar la información de un usuario.</p>
 *
 * @param nombre nombre completo del usuario
 * @param usuario nombre de usuario utilizado para autenticación
 * @param correo dirección de correo electrónico del usuario
 * @param telefono número de teléfono del usuario
 * @param rol rol asignado al usuario dentro del sistema
 */
public record DatosEditarUsuario(
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

    @NotNull(message = "El rol es obligatorio. [ADMIN | JEFE_BODEGA | COORDINADOR_COMPRAS | GERENTE]")
    Roles rol
) {}
