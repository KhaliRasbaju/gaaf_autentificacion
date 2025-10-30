package com.udi.gaaf.autentificacion.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.udi.gaaf.autentificacion.errors.AuthenticationException;
import com.udi.gaaf.autentificacion.security.TokenService;
import com.udi.gaaf.autentificacion.usuario.DatosRegistrarUsuario;
import com.udi.gaaf.autentificacion.usuario.Usuario;
import com.udi.gaaf.autentificacion.usuario.UsuarioService;

/**
 * Servicio encargado de la lógica de autenticación y registro de usuarios.
 *
 * <p>Proporciona operaciones para registrar nuevos usuarios y autenticar credenciales 
 * existentes mediante Spring Security. Genera y devuelve tokens JWT para sesiones válidas.</p>
 */
@Service
public class AuthService {

    /** Servicio de gestión de usuarios. */
    @Autowired
    private UsuarioService usuarioService;

    /** Administrador de autenticación de Spring Security. */
    @Autowired
    private AuthenticationManager authenticationManager;

    /** Servicio encargado de la generación y validación de tokens JWT. */
    @Autowired
    private TokenService tokenService;

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * <p>Valida la existencia previa del usuario y encripta la contraseña antes de persistirlo 
     * en la base de datos. Retorna un DTO con la información básica del registro exitoso.</p>
     *
     * @param datos Record que contiene los datos de registro (nombre, correo, usuario, contraseña, etc.)
     * @return un objeto {@link DatosDetalleRegistro} con los datos del usuario registrado
     */
    public DatosDetalleRegistro registrar(DatosRegistrarUsuario datos) {
        Usuario usuario = usuarioService.register(datos);
        return new DatosDetalleRegistro(
            usuario.getUsuario(),
            usuario.getCorreo(),
            usuario.getNombre(),
            usuario.getTelefono(),
            usuario.getRol()
        );
    }

    /**
     * Inicia sesión con las credenciales del usuario.
     *
     * <p>Autentica las credenciales mediante el {@link AuthenticationManager} y, 
     * si son válidas, genera un token JWT asociado al usuario autenticado.</p>
     *
     * @param datos Record que contiene las credenciales de acceso (usuario y contraseña)
     * @return un objeto {@link DatosDetalleSesion} que contiene el token JWT y los datos del usuario autenticado
     * @throws AuthenticationException si las credenciales son incorrectas
     * @throws RuntimeException si ocurre un error inesperado durante el proceso de autenticación
     */
    public DatosDetalleSesion inicio(DatosIniciarSesion datos) {
        try {
            Authentication authenticationRequest =
                new UsernamePasswordAuthenticationToken(datos.usuario(), datos.contraseña());
            
            Authentication usuarioAutenticado = authenticationManager.authenticate(authenticationRequest);
            
            // Generación del token JWT a partir del usuario autenticado
            var jwtToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
            return jwtToken;

        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Usuario o contraseña incorrectos");
        } catch (Exception e) {
            throw new RuntimeException("Error en el proceso de autenticación", e);
        }
    }
}
