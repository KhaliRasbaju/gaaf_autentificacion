package com.udi.gaaf.autentificacion.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
) {

}
