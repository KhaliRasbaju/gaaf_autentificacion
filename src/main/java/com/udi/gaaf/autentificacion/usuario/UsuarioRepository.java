package com.udi.gaaf.autentificacion.usuario;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
	Usuario findDetailById(String id);
	Optional<Usuario> findByUsuario(String usuario);
}
