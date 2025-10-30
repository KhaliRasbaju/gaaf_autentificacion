package com.udi.gaaf.autentificacion.usuario;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de acceso a datos para la entidad {@link Usuario}.
 * 
 * <p>Proporciona métodos personalizados para la búsqueda de usuarios
 * en la base de datos de MongoDB según diferentes criterios.</p>
 */
@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    
    /**
     * Obtiene los detalles de un usuario por su identificador único.
     *
     * @param id identificador del usuario.
     * @return los detalles del usuario como {@link UserDetails}.
     */
    UserDetails findDetailById(String id);
    
    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param usuario nombre de usuario.
     * @return un {@link Optional} que puede contener los detalles del usuario si existe.
     */
    Optional<UserDetails> findByUsuario(String usuario);
    
    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param correo dirección de correo del usuario.
     * @return un {@link Optional} que puede contener los detalles del usuario si existe.
     */
    Optional<UserDetails> findByCorreo(String correo);
}
