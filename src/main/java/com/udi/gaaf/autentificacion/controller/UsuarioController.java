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

/**
 * Controlador para la gestión de usuarios.
 * 
 * <p>Proporciona endpoints para realizar operaciones de administración sobre los usuarios,
 * incluyendo obtención, modificación, cambio de credenciales, cambio de estado y eliminación.</p>
 * 
 */
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    /** Servicio que contiene la lógica de negocio relacionada con los usuarios. */
    @Autowired
    private UsuarioService service;

    /**
     * Obtiene un usuario por su identificador único.
     *
     * @param id identificador único del usuario
     * @return detalles del usuario solicitado
     */
    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleUsuario> obtenerPorId(@PathVariable String id) {
        var detalle = service.obtenerPorId(id);
        return ResponseEntity.ok(detalle);
    }

    /**
     * Obtiene todos los usuarios registrados en el sistema.
     *
     * @return lista con los detalles de todos los usuarios
     */
    @GetMapping
    public ResponseEntity<List<DatosDetalleUsuario>> obtenerTodos() {
        var detalle = service.obtenerTodos();
        return ResponseEntity.ok(detalle);
    }

    /**
     * Modifica la información de un usuario existente.
     *
     * <p>Este método permite actualizar los datos personales de un usuario,
     * validando que el cuerpo de la solicitud no sea nulo.</p>
     *
     * @param datos datos del usuario a modificar
     * @param id identificador único del usuario
     * @return detalles del usuario actualizado
     * @throws NotRequestBodyException si el cuerpo de la solicitud está vacío
     */
    @PutMapping("/editar/{id}")
    public ResponseEntity<DatosDetalleResponse> editar(
            @RequestBody(required = false) @Valid DatosEditarUsuario datos,
            @PathVariable String id) {
        if (datos == null) {
            throw new NotRequestBodyException("Se requiere el body");
        }
        var detalle = service.editar(datos, id);
        return ResponseEntity.ok(detalle);
    }

    /**
     * Cambia la contraseña de un usuario por su identificador.
     *
     * <p>Valida que el cuerpo de la solicitud no sea nulo antes de realizar el cambio.</p>
     *
     * @param datos datos necesarios para el cambio de contraseña
     * @param id identificador único del usuario
     * @return respuesta con el estado del proceso
     * @throws NotRequestBodyException si el cuerpo de la solicitud está vacío
     */
    @PutMapping("/credenciales/{id}")
    public ResponseEntity<DatosDetalleResponse> cambiarContrasenaPorId(
            @RequestBody(required = false) @Valid DatosCambiarCredenciales datos,
            @PathVariable String id) {
        if (datos == null) {
            throw new NotRequestBodyException("Se requiere el body");
        }
        var detalle = service.cambiarContrasenaPorId(datos, id);
        return ResponseEntity.ok(detalle);
    }

    /**
     * Cambia el estado de un usuario (activo/inactivo) por su identificador.
     *
     * @param id identificador único del usuario
     * @return respuesta con el estado del proceso
     */
    @PutMapping("/estado/{id}")
    public ResponseEntity<DatosDetalleResponse> cambiarEstado(@PathVariable String id) {
        var detalle = service.cambiarEstado(id);
        return ResponseEntity.ok(detalle);
    }

    /**
     * Elimina un usuario del sistema por su identificador.
     *
     * @param id identificador único del usuario
     * @return respuesta con el estado de la operación
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<DatosDetalleResponse> eliminarPorId(@PathVariable String id) {
        var detalle = service.eliminarPorId(id);
        return ResponseEntity.ok(detalle);
    }
}
