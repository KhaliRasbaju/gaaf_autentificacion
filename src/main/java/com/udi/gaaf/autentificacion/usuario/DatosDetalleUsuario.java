package com.udi.gaaf.autentificacion.usuario;

public record DatosDetalleUsuario(
		
	
	String id,
		
	String usuario,
	
	String nombre,
	
	String correo,
	
	String telfono,
	
	Boolean activo,
	
	Roles rol
) {

}
