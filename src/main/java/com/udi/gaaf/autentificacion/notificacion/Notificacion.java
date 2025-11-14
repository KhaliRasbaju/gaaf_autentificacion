package com.udi.gaaf.autentificacion.notificacion;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.udi.gaaf.autentificacion.usuario.Usuario;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


/**
 * Entidad que representa la notificación. 
 */

@Document(collection = "notificacion")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Notificacion {
	
	/** Identificador de la notificación. */
	@Id
	private String id;
	/** Mensaje. */
	private String mensaje; 
	/** Fecha que se realizo el mensaje. */
	private LocalDateTime fecha;
	/** Persona que realizo el mensaje. */
	private String remitente;
	/** Estado de la notificación. */
	private EstadoNotificacion estado;
	/** Relacion con usuarios **/
	private  Usuario usuario;
	

}
