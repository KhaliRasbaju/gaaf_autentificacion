package com.udi.gaaf.autentificacion.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.udi.gaaf.autentificacion.usuario.UsuarioRepository;

/**
 * Servicio encargado de la autenticación de usuarios en el sistema.
 * <p>
 * Implementa la interfaz {@link UserDetailsService} de Spring Security, 
 * la cual permite cargar los detalles de un usuario (credenciales, roles, permisos)
 * a partir de su nombre de usuario.
 * </p>
 * 
 * <p><b>Propósito principal:</b></p>
 * <ul>
 *   <li>Consultar el usuario desde la base de datos por su nombre de usuario.</li>
 *   <li>Validar si el usuario existe antes de permitir el inicio de sesión.</li>
 *   <li>Retornar los detalles necesarios para el proceso de autenticación (roles, contraseñas, estado, etc.).</li>
 * </ul>
 * 
 * <p>
 * En caso de que el usuario no exista, se lanza una excepción {@link UsernameNotFoundException}.
 * Si ocurre un error inesperado, se lanza una excepción de tipo {@link RuntimeException}.
 * </p>
 * 
 * @see org.springframework.security.core.userdetails.UserDetailsService
 * @see com.udi.gaaf.autentificacion.usuario.UsuarioRepository
 */
@Service
public class AuthenticationService implements UserDetailsService {

    /** Repositorio del usuario. */
    @Autowired
    private UsuarioRepository repository;

    /**
     * Carga un usuario por su nombre de usuario.
     * <p>
     * Este método es invocado automáticamente por Spring Security durante 
     * el proceso de autenticación. Busca al usuario en el repositorio y, 
     * si se encuentra, retorna su información para que el framework la use 
     * en la validación del token o contraseña.
     * </p>
     * 
     * @param username nombre de usuario utilizado para iniciar sesión.
     * @return un objeto {@link UserDetails} con la información del usuario autenticado.
     * @throws UsernameNotFoundException si el usuario no existe en la base de datos.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            System.out.println("Buscando usuario: " + username);
            var usuario = repository.findByUsuario(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
            System.out.println("Usuario encontrado: " + usuario);
            return usuario;
        } catch (Exception ex) {
            throw new RuntimeException("Error al cargar el usuario: " + ex.getMessage(), ex);
        }
    }
}
