package com.udi.gaaf.autentificacion.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.udi.gaaf.autentificacion.auth.DatosDetalleSesion;
import com.udi.gaaf.autentificacion.errors.NotTokenValidException;
import com.udi.gaaf.autentificacion.usuario.Roles;
import com.udi.gaaf.autentificacion.usuario.Usuario;

/**
 * Servicio encargado de la generación y validación de tokens JWT.
 * 
 * <p>Proporciona métodos para crear tokens de autenticación con información 
 * del usuario, así como para verificar su validez y extraer datos relevantes.</p>
 */
@Service
public class TokenService {

    /** Clave secreta utilizada para firmar y verificar los tokens JWT. */
    @Value("${api.jwt.secret}")
    private String secret;

    /**
     * Genera la fecha de expiración del token JWT.
     * 
     * <p>Los tokens generados tendrán una duración de 2 horas desde el momento de su creación.</p>
     *
     * @return instante de expiración del token.
     */
    private Instant generarFechaExpiracion() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }

    /**
     * Genera un token JWT a partir de la información de un usuario autenticado.
     *
     * <p>El token contendrá los siguientes datos (claims):</p>
     * <ul>
     *   <li><strong>issuer</strong>: nombre del emisor ("GAAF").</li>
     *   <li><strong>subject</strong>: nombre del usuario.</li>
     *   <li><strong>id</strong>: identificador del usuario.</li>
     *   <li><strong>rol</strong>: rol del usuario dentro del sistema.</li>
     *   <li><strong>expiresAt</strong>: fecha y hora de expiración.</li>
     * </ul>
     *
     * @param usuario entidad {@link Usuario} con los datos del usuario autenticado.
     * @return record {@link DatosDetalleSesion} con el nombre, token y rol del usuario.
     * @throws RuntimeException si ocurre un error durante la generación del token.
     */
    public DatosDetalleSesion generarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            Roles role = usuario.getRol();

            String token = JWT.create()
                    .withIssuer("GAAF")
                    .withSubject(usuario.getNombre())
                    .withClaim("id", usuario.getId())
                    .withClaim("rol", role.toString())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);

            return new DatosDetalleSesion(usuario.getNombre(), token, role, usuario.getId());

        } catch (Exception ex) {
            throw new RuntimeException("Error al generar el token JWT.", ex);
        }
    }

    /**
     * Obtiene el valor del campo {@code subject} contenido en el token JWT.
     *
     * @param token cadena JWT generada previamente.
     * @return nombre del usuario asociado al token.
     * @throws NotTokenValidException si el token no es válido o no puede verificarse.
     * @throws RuntimeException si el token es nulo o el subject es inválido.
     */
    public String getSubject(String token) {
        if (token == null) {
            throw new RuntimeException("El token no puede ser nulo.");
        }

        DecodedJWT verifier;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            verifier = JWT.require(algorithm)
                    .withIssuer("GAAF")
                    .build()
                    .verify(token);
        } catch (JWTVerificationException ex) {
            throw new NotTokenValidException("El token no es válido o ha expirado.");
        }

        if (verifier.getSubject() == null) {
            throw new RuntimeException("El token no contiene un subject válido.");
        }

        return verifier.getSubject();
    }

    /**
     * Obtiene el identificador del usuario a partir del token JWT.
     *
     * @param token cadena JWT generada previamente.
     * @return identificador del usuario contenido en el token.
     * @throws IllegalArgumentException si el token es nulo.
     * @throws NotTokenValidException si el token no es válido o no puede verificarse.
     */
    public String getUsuarioId(String token) {
        if (token == null) {
            throw new IllegalArgumentException("El token no puede ser nulo.");
        }

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer("GAAF")
                    .build()
                    .verify(token);

            return decodedJWT.getClaim("id").asString();

        } catch (JWTVerificationException ex) {
            throw new NotTokenValidException("El token es inválido o no puede verificarse.");
        }
    }
}
