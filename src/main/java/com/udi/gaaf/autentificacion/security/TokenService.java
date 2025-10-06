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

@Service
public class TokenService {

	@Value("${api.jwt.secret}")
	private String secret;
	
	private Instant generarFechaExpiracion() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
	}
	
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
			
			return new DatosDetalleSesion(usuario.getNombre(), token, role);
			
		}catch(Exception ex) {
			 throw new RuntimeException(ex);
		}
	}
	
	

	
	public String getSubject(String token) {
		if(token == null) {
			throw new RuntimeException();
		}
		DecodedJWT verifier = null;
		
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			verifier = JWT.require(algorithm)
					.withIssuer("GAAF")
					.build()
					.verify(token);
			
			verifier.getSubject();
		}catch (JWTVerificationException ex) {
			throw new NotTokenValidException("No hay token");
		}
		
		if(verifier.getSubject() == null) {
			throw new RuntimeException("Verifire Invalido");
		}
		return verifier.getSubject();
	}
	
	public String getUsuarioId(String token) {
	    if (token == null) {
	    	 throw new IllegalArgumentException("El token no puede ser nulo");
        }
	    
	    try {
	    	 Algorithm algorithm = Algorithm.HMAC256(secret);
	    	 DecodedJWT decodedJWT = JWT.require(algorithm)
	    			 .withIssuer("GAAF")
	    			 .build()
	    			 .verify(token);
	    	 
	    	 return decodedJWT.getClaim("id").asString();
	    	
	    } catch(JWTVerificationException ex) {
	    	 throw new NotTokenValidException("Token invalido o verificable");
	    }
	}
}
