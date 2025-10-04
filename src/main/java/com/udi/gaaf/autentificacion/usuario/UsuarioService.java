package com.udi.gaaf.autentificacion.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
	
	
	
	
	
	@Autowired
	private UsuarioRepository repository;
	
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
	public Usuario register(DatosRegistrarUsuario datos) {
		String contraseñaEncriptada = passwordEncoder.encode(datos.contraseña());
		var usuario = new Usuario(datos, contraseñaEncriptada);
		var nuevoUsuario = repository.save(usuario);
		return nuevoUsuario;
	}

}
