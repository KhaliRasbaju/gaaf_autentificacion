package com.udi.gaaf.autentificacion.security;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.udi.gaaf.autentificacion.errors.NotTokenValidException;
import com.udi.gaaf.autentificacion.usuario.UsuarioRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro de seguridad encargado de validar y procesar tokens JWT en las solicitudes HTTP.
 * <p>
 * Este filtro se ejecuta una vez por cada solicitud entrante ({@link OncePerRequestFilter}),
 * y tiene como propósito validar el encabezado {@code Authorization}, verificar la validez del token,
 * extraer la identidad del usuario y establecer el contexto de seguridad en {@link SecurityContextHolder}.
 * </p>
 *
 * <h2>Comportamiento general:</h2>
 * <ul>
 *   <li>Excluye rutas públicas (como autenticación y documentación).</li>
 *   <li>Valida el token JWT recibido en el encabezado.</li>
 *   <li>Extrae el identificador del usuario mediante {@link TokenService}.</li>
 *   <li>Recupera los detalles del usuario desde {@link UsuarioRepository}.</li>
 *   <li>Establece la autenticación en el contexto de seguridad.</li>
 * </ul>
 *
 * <h3>Rutas excluidas</h3>
 * <p>
 * El filtro ignora las rutas que coinciden con los siguientes patrones:
 * </p>
 * <ul>
 *   <li><b>/auth/**</b>: rutas relacionadas con la autenticación.</li>
 *   <li><b>/usuario/**</b>: registro o administración de usuarios públicos.</li>
 *   <li><b>/docs/**</b>, <b>/v3/api-docs/**</b>, <b>/swagger-ui/**</b>: documentación y Swagger.</li>
 * </ul>
 *
 * <h3>Excepciones</h3>
 * <ul>
 *   <li>{@link NotTokenValidException} si el token no está presente o es inválido.</li>
 * </ul>
 *
 * @see TokenService
 * @see UsuarioRepository
 * @see SecurityContextHolder
 * @see UsernamePasswordAuthenticationToken
 * @see OncePerRequestFilter
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {

    /** Repositorio encargado de acceder a los datos de los usuarios. */
    @Autowired
    private UsuarioRepository usuarioRepository;

    /** Servicio encargado de la validación y decodificación de tokens JWT. */
    @Autowired
    private TokenService tokenService;

    /**
     * Lista de rutas que no requieren autenticación.
     * <p>
     * Estas rutas se excluyen del proceso de validación del token.
     * </p>
     */
    private static final List<Pattern> RUTAS_EXCLUIDAS = List.of(
            Pattern.compile("^/auth/.+$"),
            Pattern.compile("^/usuario(?:/.*)?$"),
            Pattern.compile("^/v3/api-docs(/.*)?$"),
            Pattern.compile("^/docs(/.*)?$"),
            Pattern.compile("^/swagger-ui\\.html$"),
            Pattern.compile("^/swagger-ui(/.*)?$")
    );

    /**
     * Procesa cada solicitud HTTP para validar su autenticación basada en JWT.
     * <p>
     * Si la URI solicitada coincide con alguna ruta excluida, la solicitud continúa sin autenticación.
     * En caso contrario, el método:
     * </p>
     * <ol>
     *   <li>Obtiene el token del encabezado {@code Authorization}.</li>
     *   <li>Valida y decodifica el token mediante {@link TokenService}.</li>
     *   <li>Obtiene los detalles del usuario y establece la autenticación en el contexto.</li>
     * </ol>
     *
     * @param request  solicitud HTTP entrante
     * @param response respuesta HTTP
     * @param filterChain cadena de filtros
     * @throws ServletException si ocurre un error en la cadena del filtro
     * @throws IOException si ocurre un error de lectura o escritura en el flujo HTTP
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        var token = request.getHeader("Authorization");
        String requestURI = request.getRequestURI();
        System.out.println("uri: " + requestURI);

        // Verificación de rutas excluidas
        for (Pattern pattern : RUTAS_EXCLUIDAS) {
            SecurityContextHolder.clearContext();
            if (pattern.matcher(requestURI).matches()) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        // Validación del token JWT
        if (token != null) {
            token = token.replace("Bearer ", "");
            var usuarioId = tokenService.getUsuarioId(token);
            if (usuarioId != null) {
                var usuario = usuarioRepository.findDetailById(usuarioId);
                var autenticacion = new UsernamePasswordAuthenticationToken(
                        usuario, null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(autenticacion);
            }
        } else {
            throw new NotTokenValidException("No hay token");
        }

        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
