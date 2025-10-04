package com.udi.gaaf.autentificacion.notificacion;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionRepository extends MongoRepository<Notificacion, String> {

}
