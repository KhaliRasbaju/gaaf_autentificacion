package com.udi.gaaf.autentificacion.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import com.udi.gaaf.autentificacion.security.TokenService;
import com.udi.gaaf.autentificacion.usuario.DatosRegistrarUsuario;
import com.udi.gaaf.autentificacion.usuario.Usuario;
import com.udi.gaaf.autentificacion.usuario.UsuarioService;
import com.udi.gaaf.autentificacion.errors.AuthenticationException;

@Service
public class AuthService {

	@Autowired
	public UsuarioService usuarioService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenService tokenService;
	
	
	public DatosDetalleRegistro registrar(DatosRegistrarUsuario datos) {
		Usuario usuario = usuarioService.register(datos);
		return new DatosDetalleRegistro(usuario.getUsuario(), usuario.getCorreo(),usuario.getNombre(), usuario.getTelfono(), usuario.getRol() );
	}
	
	
	public DatosDetalleSesion inicio(DatosIniciarSesion datos) {
		try {
			System.out.println(datos);
			Authentication authentication = new UsernamePasswordAuthenticationToken(datos.usuario(), datos.contraseña());
			System.out.println(authentication);
			var usuarioAutenticado = authenticationManager.authenticate(authentication);
			System.out.println(usuarioAutenticado);
			var JWTToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
			return JWTToken;
		}catch (BadCredentialsException e) {
            throw new AuthenticationException("Usuario o contraseña incorrectos"); 
        } catch (Exception e) {
            throw new RuntimeException("Error en la autenticación", e);
        }
	}
	
	
}
