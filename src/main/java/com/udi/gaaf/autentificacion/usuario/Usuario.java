package com.udi.gaaf.autentificacion.usuario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.udi.gaaf.autentificacion.notificacion.Notificacion;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collation = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of ="id")
public class Usuario implements UserDetails{
	@Id
	private String id;
	private String usuario;
	private String nombre;
	private String correo;
	private String telfono;
	private String contraseña;
	private Boolean activo;
	
	
	private Roles rol;
	
	
	
	private List<Notificacion> notificacion;


	
	public Usuario(DatosRegistrarUsuario datos, String contraseñaEncriptada) {
		
		this.usuario = datos.usuario();
		this.nombre = datos.nombre();
		this.correo = datos.correo();
		this.telfono = datos.telefono();
		this.contraseña = contraseñaEncriptada;
		this.activo = true;
		this.rol = datos.rol();
		this.notificacion= new ArrayList<>();
		
	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		String roles = "ROLE_" + rol.toString(); 
		
		return List.of(new SimpleGrantedAuthority(roles));
	}



	@Override
	public String getPassword() {
		return contraseña;
	}



	@Override
	public String getUsername() {
		return usuario;
	}
}
