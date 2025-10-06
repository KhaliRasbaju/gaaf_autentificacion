package com.udi.gaaf.autentificacion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udi.gaaf.autentificacion.auth.AuthService;
import com.udi.gaaf.autentificacion.auth.DatosDetalleRegistro;
import com.udi.gaaf.autentificacion.auth.DatosDetalleSesion;
import com.udi.gaaf.autentificacion.auth.DatosIniciarSesion;
import com.udi.gaaf.autentificacion.errors.NotRequestBodyException;
import com.udi.gaaf.autentificacion.usuario.DatosRegistrarUsuario;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/auth")
public class AuthController {
	
	
	@Autowired
	private AuthService service;
	
	
	@PostMapping("/registrar")
	public ResponseEntity<DatosDetalleRegistro> registrar(@RequestBody(required = false) @Valid DatosRegistrarUsuario datos){
		if(datos == null) {
			throw new NotRequestBodyException("Necesito la Request Body");
		}
		var detalle = service.registrar(datos);
		return ResponseEntity.ok(detalle);
	}
	
	
	@PostMapping("/iniciar")
	public ResponseEntity<DatosDetalleSesion> inicar(@RequestBody(required = false) @Valid DatosIniciarSesion datos) {
		if(datos == null) {
			throw new NotRequestBodyException("Necesito la Request Body");
		}
		var detalle = service.inicio(datos);
		return ResponseEntity.ok(detalle);
	}
}
