package com.udi.gaaf.autentificacion.usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.udi.gaaf.autentificacion.common.DatosDetalleResponse;
import com.udi.gaaf.autentificacion.errors.BadRequestException;
import com.udi.gaaf.autentificacion.errors.NotFoundException;

@Service
public class UsuarioService {
		
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private Boolean existeUsuario(String correo, String usuario) {
		var usuarioCorreo = repository.findByCorreo(correo);
		var usuarioNombre = repository.findByUsuario(usuario);
		if(!usuarioCorreo.isPresent() || !usuarioNombre.isPresent()) {
			return false;
		}
		return true;
	}
	
	private Usuario obtenerUsuarioPorId(String id) {
		return repository.findById(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado por el id: " + id));
	}

	private DatosDetalleUsuario detalleUsuario( Usuario usuario) {
		return new DatosDetalleUsuario(usuario.getId(),usuario.getUsuario(), usuario.getNombre(), usuario.getCorreo(), usuario.getTelefono(), usuario.getActivo(), usuario.getRol());
	}
	
	
	
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
	
	
	public DatosDetalleUsuario editar(DatosEditarUsuario datos, String id) {
		var usuario = obtenerUsuarioPorId(id);
		if(usuario.getNombre() != datos.nombre()) usuario.setNombre(datos.nombre());
		if(usuario.getUsuario() != datos.usuario()) usuario.setUsuario(datos.usuario());
		if(usuario.getCorreo() !=  datos.correo()) usuario.setCorreo(datos.correo());
		if(usuario.getTelefono() != datos.telefono()) usuario.setTelefono(datos.telefono());
		if(usuario.getRol() != datos.rol()) usuario.setRol(datos.rol());
		repository.save(usuario);
		return detalleUsuario(usuario);
	}
	
	public DatosDetalleResponse cambiarContrasenaPorId(DatosCambiarCredenciales datos, String id) {
		try {
			
			var usuario = obtenerUsuarioPorId(id);
			if(!passwordEncoder.matches(datos.contraseña(), usuario.getContrasena())) {
				var contraseñaEncriptada = passwordEncoder.encode(datos.contraseña());
				usuario.setContrasena(contraseñaEncriptada);			
				repository.save(usuario);
			}
			return new DatosDetalleResponse(200, "Credenciales cambiadas correctamente");
		}catch (Exception e) {
			System.out.println("Error tipo: " +e);
			throw new BadRequestException("Error");
		}
	}

	public DatosDetalleUsuario obtenerPorId(String id) {
		var usuario = obtenerUsuarioPorId(id);
		return detalleUsuario(usuario);
	}
	
	public List<DatosDetalleUsuario> obtenerTodos(){
		var usuarios = repository.findAll();
		return usuarios.stream().map(u -> detalleUsuario(u)).toList();
	}
	
	
	public DatosDetalleResponse eliminarPorId(String id) {
		var usuario = obtenerUsuarioPorId(id);
		repository.delete(usuario);
		return new DatosDetalleResponse(200, "Usuario eliminado correctamente");
	}
	
	public DatosDetalleResponse cambiarEstado(String id) {
		var usuario = obtenerUsuarioPorId(id);
		if(usuario.getActivo()) {
			usuario.setActivo(false);
		} else {
			usuario.setActivo(true);
		}
		repository.save(usuario);
		return new DatosDetalleResponse(200, "Usuario estado cambiado correctamente");
	}
	
	
	
}
