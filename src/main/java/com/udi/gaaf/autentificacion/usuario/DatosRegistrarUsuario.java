package com.udi.gaaf.autentificacion.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistrarUsuario(
		@NotBlank
		String nombre,
		@NotBlank
		String usuario,
		@Email
		String correo,
		@NotBlank
		String telefono,
		@NotBlank(message = "Minimo 8 caracteres entre mayusculas, minusculas, y caracter especial")
		String contraseña,
		@NotNull(message = "El rol es obligatorio. [ADMIN | JEFE_BODEGA | COORDINADOR_COMPRAS | GERENTE]")
		Roles rol
) {

}
