package com.udi.gaaf.autentificacion.auth;

import com.udi.gaaf.autentificacion.usuario.Roles;

public record DatosDetalleRegistro(
		String usuario,
		String correo,
		String nombre,
		String telefono,
		Roles rol
) {
	
		

}
