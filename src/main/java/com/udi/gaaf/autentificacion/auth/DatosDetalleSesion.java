package com.udi.gaaf.autentificacion.auth;

import com.udi.gaaf.autentificacion.usuario.Roles;

public record DatosDetalleSesion(
		String usuario,
		String token,
		Roles rol
) {

	
}
