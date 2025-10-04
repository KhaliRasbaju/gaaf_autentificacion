package com.udi.gaaf.autentificacion.auth;

import jakarta.validation.constraints.NotBlank;

public record DatosIniciarSesion(

		@NotBlank
		String usuario,
		@NotBlank(message = "Minimo 8 caracteres entre mayusculas, minusculas, y caracter especial")
		String contraseña
) {

}
