package com.udi.gaaf.autentificacion.security;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.udi.gaaf.autentificacion.usuario.Usuario;
import com.udi.gaaf.autentificacion.usuario.UsuarioRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private TokenService tokenService;
	
	private static List<Pattern> RUTAS_EXCLUIDAS= List.of(
			Pattern.compile("^/auth(/.*)?$")
			);

	@SuppressWarnings("null")
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		var token = request.getHeader("Authorization");
		String requestURI = request.getRequestURI();
		System.out.println("uri:" + requestURI);
		for (Pattern pattern: RUTAS_EXCLUIDAS ) {
			System.out.println(pattern);
			System.out.println(pattern.matcher(requestURI).matches());
			if(pattern.matcher(requestURI).matches()) {
				filterChain.doFilter(request, response);
				return;
			}
		}
		
		if(token != null) {
			token = token.replace("Bearer ", "");
			System.out.println(token);
			var usuarioId = tokenService.getUsuarioId(token);
			if(usuarioId != null) {
				var usuario = usuarioRepository.findDetailById(usuarioId);
				var autentificacion = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
				System.out.println(usuario.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(autentificacion);
			}
		} else {
			//
		}
		
		
		filterChain.doFilter(request, response);
		
	}
}
