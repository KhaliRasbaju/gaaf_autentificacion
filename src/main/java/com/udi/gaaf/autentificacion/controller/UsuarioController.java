package com.udi.gaaf.autentificacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udi.gaaf.autentificacion.common.DatosDetalleResponse;
import com.udi.gaaf.autentificacion.errors.NotRequestBodyException;
import com.udi.gaaf.autentificacion.usuario.DatosCambiarCredenciales;
import com.udi.gaaf.autentificacion.usuario.DatosDetalleUsuario;
import com.udi.gaaf.autentificacion.usuario.DatosEditarUsuario;
import com.udi.gaaf.autentificacion.usuario.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService service;
	
	
	@GetMapping("/{id}")
	public ResponseEntity<DatosDetalleUsuario> obtenerPorId(@PathVariable String id) {
		var detalle = service.obtenerPorId(id);
		return ResponseEntity.ok(detalle);
	}
	
	@GetMapping
	public ResponseEntity<List<DatosDetalleUsuario>> obtenerTodos(){
		var detalle = service.obtenerTodos();
		return ResponseEntity.ok(detalle);
	}
	
	@PutMapping("/editar/{id}")
	public  ResponseEntity<DatosDetalleUsuario> editar(@RequestBody(required = false) @Valid DatosEditarUsuario datos, @PathVariable String id) {
		if(datos == null) {
			throw new NotRequestBodyException("Se requiere el body");
		}
		var detalle = service.editar(datos, id);
		return ResponseEntity.ok(detalle);
	}
	
	@PutMapping("/credenciales/{id}")
	public ResponseEntity<DatosDetalleResponse> cambiarContrasenaPorId(@RequestBody(required = false) @Valid DatosCambiarCredenciales datos, @PathVariable String id) {
		if(datos == null) {
			throw new NotRequestBodyException("Se requiere el body");
		}
		var detalle = service.cambiarContrasenaPorId(datos, id);
		return ResponseEntity.ok(detalle);
	}
	
	@PutMapping("/estado/{id}")
	public ResponseEntity<DatosDetalleResponse> cambiarEstado(@PathVariable String id) {
		var detalle = service.cambiarEstado(id);
		return ResponseEntity.ok(detalle);
	}

	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<DatosDetalleResponse> eliminarPorId(@PathVariable String id) {
		var detalle = service.eliminarPorId(id);
		return ResponseEntity.ok(detalle);
	}	
}
