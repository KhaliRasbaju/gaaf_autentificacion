package com.udi.gaaf.autentificacion.notificacion;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Document(collation = "notificacion")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Notificacion {
	
	@Id
	private String id;
	
	private String mensaje; 
	
	private LocalDateTime fecha;
	
	private String remitente;
	
	private EstadoNotificacion estado;
	

}
