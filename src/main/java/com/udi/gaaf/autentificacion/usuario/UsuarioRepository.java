package com.udi.gaaf.autentificacion.usuario;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
	UserDetails findDetailById(String id);
	
	Optional<UserDetails> findByUsuario(String usuario);
}
