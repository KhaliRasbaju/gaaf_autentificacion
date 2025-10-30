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

/**
 * Controlador encargado de la gestión de autenticación de usuarios.
 *
 * <p>Proporciona los endpoints REST para el registro de nuevos usuarios 
 * y el inicio de sesión de usuarios existentes. Utiliza {@link AuthService}
 * para manejar la lógica de autenticación y generación de tokens.</p>
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    /** Servicio principal que gestiona las operaciones de autenticación. */
    @Autowired
    private AuthService service;

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * <p>Valida los datos de entrada antes de enviarlos al servicio de autenticación.
     * Si el cuerpo de la solicitud es nulo, se lanza una {@link NotRequestBodyException}.</p>
     *
     * @param datos objeto {@link DatosRegistrarUsuario} con la información del usuario a registrar
     * @return una respuesta {@link ResponseEntity} con los datos del registro exitoso
     * @throws NotRequestBodyException si la solicitud no contiene un cuerpo válido
     */
    @PostMapping("/registrar")
    public ResponseEntity<DatosDetalleRegistro> registrar(
            @RequestBody(required = false) @Valid DatosRegistrarUsuario datos) {

        if (datos == null) {
            throw new NotRequestBodyException("Necesito la Request Body");
        }

        var detalle = service.registrar(datos);
        return ResponseEntity.ok(detalle);
    }

    /**
     * Inicia sesión en el sistema con las credenciales del usuario.
     *
     * <p>Valida las credenciales enviadas en el cuerpo de la solicitud y,
     * si son correctas, retorna los datos del usuario junto con su token JWT.</p>
     *
     * @param datos objeto {@link DatosIniciarSesion} con las credenciales del usuario
     * @return una respuesta {@link ResponseEntity} con los datos de sesión y token generado
     * @throws NotRequestBodyException si la solicitud no contiene un cuerpo válido
     */
    @PostMapping("/iniciar")
    public ResponseEntity<DatosDetalleSesion> iniciar(
            @RequestBody(required = false) @Valid DatosIniciarSesion datos) {

        if (datos == null) {
            throw new NotRequestBodyException("Necesito la Request Body");
        }

        var detalle = service.inicio(datos);
        return ResponseEntity.ok(detalle);
    }
}
