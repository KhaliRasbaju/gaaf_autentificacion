package com.udi.gaaf.autentificacion.usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.udi.gaaf.autentificacion.common.DatosDetalleResponse;
import com.udi.gaaf.autentificacion.errors.BadRequestException;
import com.udi.gaaf.autentificacion.errors.NotFoundException;

/**
 * Servicio que gestiona las operaciones relacionadas con los usuarios.
 *
 * <p>Proporciona métodos para registrar, editar, eliminar y consultar usuarios,
 * así como para cambiar contraseñas y estados de activación.</p>
 */
@Service
public class UsuarioService {

    /** Repositorio para la gestión de entidades {@link Usuario}. */
    @Autowired
    private UsuarioRepository repository;

    /** Codificador de contraseñas utilizado para encriptar y verificar contraseñas. */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Verifica si existe un usuario en la base de datos mediante correo y nombre de usuario.
     *
     * @param correo correo electrónico del usuario.
     * @param usuario nombre de usuario.
     * @return {@code true} si existe un usuario con el correo o nombre indicado; {@code false} en caso contrario.
     */
    private Boolean existeUsuario(String correo, String usuario) {
        var usuarioCorreo = repository.findByCorreo(correo);
        var usuarioNombre = repository.findByUsuario(usuario);
        return usuarioCorreo.isPresent() || usuarioNombre.isPresent();
    }

    /**
     * Obtiene un usuario por su identificador único.
     *
     * @param id identificador del usuario.
     * @return instancia de {@link Usuario}.
     * @throws NotFoundException si no se encuentra un usuario con el ID especificado.
     */
    private Usuario obtenerUsuarioPorId(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado por el ID: " + id));
    }

    /**
     * Convierte una entidad {@link Usuario} en un record {@link DatosDetalleUsuario}.
     *
     * @param usuario entidad de tipo {@link Usuario}.
     * @return record con los detalles del usuario.
     */
    private DatosDetalleUsuario detalleUsuario(Usuario usuario) {
        return new DatosDetalleUsuario(
                usuario.getId(),
                usuario.getUsuario(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getTelefono(),
                usuario.getActivo(),
                usuario.getRol()
        );
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * <p>Valida si el usuario ya existe mediante correo o nombre de usuario,
     * y encripta la contraseña antes de guardar los datos.</p>
     *
     * @param datos record con la información necesaria para registrar un usuario.
     * @return entidad {@link Usuario} recién creada.
     * @throws BadRequestException si el usuario ya existe.
     */
    public Usuario register(DatosRegistrarUsuario datos) {
        if (existeUsuario(datos.correo(), datos.usuario())) {
            throw new BadRequestException("El usuario ya existe.");
        }
        String contraseñaEncriptada = passwordEncoder.encode(datos.contraseña());
        var usuario = new Usuario(datos, contraseñaEncriptada);
        return repository.save(usuario);
    }

    /**
     * Edita la información de un usuario existente.
     *
     * <p>Actualiza únicamente los campos modificados en el record recibido.</p>
     *
     * @param datos record con la información de edición.
     * @param id identificador del usuario a editar.
     * @return record {@link DatosDetalleUsuario} con los nuevos datos del usuario.
     * @throws NotFoundException si no se encuentra el usuario.
     */
    public DatosDetalleUsuario editar(DatosEditarUsuario datos, String id) {
        var usuario = obtenerUsuarioPorId(id);

        if (!usuario.getNombre().equals(datos.nombre())) usuario.setNombre(datos.nombre());
        if (!usuario.getUsuario().equals(datos.usuario())) usuario.setUsuario(datos.usuario());
        if (!usuario.getCorreo().equals(datos.correo())) usuario.setCorreo(datos.correo());
        if (!usuario.getTelefono().equals(datos.telefono())) usuario.setTelefono(datos.telefono());
        if (usuario.getRol() != datos.rol()) usuario.setRol(datos.rol());

        repository.save(usuario);
        return detalleUsuario(usuario);
    }

    /**
     * Cambia la contraseña de un usuario según su identificador.
     *
     * <p>Encripta la nueva contraseña y actualiza el registro solo si es diferente de la actual.</p>
     *
     * @param datos record con las nuevas credenciales.
     * @param id identificador del usuario.
     * @return record {@link DatosDetalleResponse} con el mensaje de operación exitosa.
     * @throws BadRequestException si ocurre un error durante la actualización.
     */
    public DatosDetalleResponse cambiarContrasenaPorId(DatosCambiarCredenciales datos, String id) {
        try {
            var usuario = obtenerUsuarioPorId(id);

            if (!passwordEncoder.matches(datos.contraseña(), usuario.getContrasena())) {
                var contraseñaEncriptada = passwordEncoder.encode(datos.contraseña());
                usuario.setContrasena(contraseñaEncriptada);
                repository.save(usuario);
            }
            return new DatosDetalleResponse(200, "Contraseña cambiada correctamente.");
        } catch (Exception e) {
            System.out.println("Error tipo: " + e);
            throw new BadRequestException("Error al cambiar la contraseña.");
        }
    }

    /**
     * Obtiene un usuario por su identificador y devuelve su detalle.
     *
     * @param id identificador del usuario.
     * @return record {@link DatosDetalleUsuario} con la información del usuario.
     */
    public DatosDetalleUsuario obtenerPorId(String id) {
        var usuario = obtenerUsuarioPorId(id);
        return detalleUsuario(usuario);
    }

    /**
     * Obtiene la lista completa de usuarios registrados en el sistema.
     *
     * @return lista de records {@link DatosDetalleUsuario}.
     */
    public List<DatosDetalleUsuario> obtenerTodos() {
        var usuarios = repository.findAll();
        return usuarios.stream().map(this::detalleUsuario).toList();
    }

    /**
     * Elimina un usuario del sistema según su identificador.
     *
     * @param id identificador del usuario a eliminar.
     * @return record {@link DatosDetalleResponse} con el mensaje de eliminación exitosa.
     */
    public DatosDetalleResponse eliminarPorId(String id) {
        var usuario = obtenerUsuarioPorId(id);
        repository.delete(usuario);
        return new DatosDetalleResponse(200, "Usuario eliminado correctamente.");
    }

    /**
     * Cambia el estado de activación de un usuario.
     *
     * <p>Si el usuario está activo, lo desactiva; si está inactivo, lo activa.</p>
     *
     * @param id identificador del usuario.
     * @return record {@link DatosDetalleResponse} con el mensaje de confirmación del cambio.
     */
    public DatosDetalleResponse cambiarEstado(String id) {
        var usuario = obtenerUsuarioPorId(id);
        usuario.setActivo(!usuario.getActivo());
        repository.save(usuario);
        return new DatosDetalleResponse(200, "Estado del usuario cambiado correctamente.");
    }
}
