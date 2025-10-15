package com.udi.gaaf.autentificacion.usuario;

import jakarta.validation.constraints.NotBlank;

public record DatosCambiarCredenciales(
	@NotBlank(message = "Minimo 8 caracteres entre mayusculas, minusculas, y caracter especial")
	String contraseña
) {

}
