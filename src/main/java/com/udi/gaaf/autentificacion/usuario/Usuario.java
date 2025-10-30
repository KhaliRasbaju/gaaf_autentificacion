package com.udi.gaaf.autentificacion.usuario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.udi.gaaf.autentificacion.notificacion.Notificacion;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



/**
 * Entidad que representa al usuario 
*/


@SuppressWarnings("serial")
@Document(collection  = "usuario")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of ="id")
public class Usuario implements UserDetails{
	
	/** Identificador del usuario. */
	@Id
	private String id;
	/** Nombre de usuario. */
	@Field("usuario")
	private String usuario;
	/** Nombre del usuario. */
	private String nombre;
	/** Correo del usuario. */
	private String correo;
	/** Telefono del usuario. */
	private String telefono;
	/** Contraseña del usuario. */
	@Field("contrasena")
	private String contrasena;
	/** Estado del usuario. */
	private Boolean activo;
	/** Rol del usuario. */
	private Roles rol;
	/** Lista que representa las notificaciones. */
	private List<Notificacion> notificacion;
	/** Contructor para asignar valores. 
	 * 
	 * @param datos tipo Record del manejo de datos de entarada de registro de usuario
	 * @param contraseñaEncriptada contraseña encriptada
	 * */
	public Usuario(DatosRegistrarUsuario datos, String contraseñaEncriptada) {
		this.usuario = datos.usuario();
		this.nombre = datos.nombre();
		this.correo = datos.correo();
		this.telefono = datos.telefono();
		this.contrasena = contraseñaEncriptada;
		this.activo = true;
		this.rol = datos.rol();
		this.notificacion= new ArrayList<>();
	}
	/** Obtener autorización. */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String roles = "ROLE_" + rol.toString(); 	
		return List.of(new SimpleGrantedAuthority(roles));
	}
	/** Obtener contraseña. */
	@Override
	public String getPassword() {
		return contrasena;
	}
	/** Obtener nombre de usuario */
	@Override
	public String getUsername() {
		return usuario;
	}
	/** Obtener si la cuenta no esta expirada*/
    @Override
    public boolean isAccountNonExpired() { return true; }
	/** Obtener si la cuenta no es bloqueada*/
    @Override
    public boolean isAccountNonLocked() { return true; }
	/** Obtener si las credenciales no esta expirada*/
    @Override
    public boolean isCredentialsNonExpired() { return true; }
	/** Obtener si esta habilitado */
    @Override
    public boolean isEnabled() { return activo; }
}	
