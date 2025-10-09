package com.udi.gaaf.autentificacion.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.udi.gaaf.autentificacion.errors.BadRequestException;

@Service
public class UsuarioService {
		
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Usuario register(DatosRegistrarUsuario datos) {	
		var existe = existeUsuario(datos.correo(),datos.usuario());
		if(existe) {
			throw new BadRequestException("Usuario ya existe");
		}
		String contraseñaEncriptada = passwordEncoder.encode(datos.contraseña());
		var usuario = new Usuario(datos, contraseñaEncriptada);
		var nuevoUsuario = repository.save(usuario);
		return nuevoUsuario;
	}
	
	public Boolean existeUsuario(String correo, String usuario) {
		var usuarioCorreo = repository.findByCorreo(correo);
		var usuarioNombre = repository.findByUsuario(usuario);
		if(!usuarioCorreo.isPresent() || !usuarioNombre.isPresent()) {
			return false;
		}
		return true;
	}
	
}
