package com.udi.gaaf.autentificacion.usuario;

/**
 * Representa los distintos roles que puede tener un usuario dentro del sistema.
 *
 * <p>Esta enumeración define los niveles de acceso y responsabilidades
 * de los usuarios según su función dentro de la organización.</p>
 */
public enum Roles {

    /** Rol con privilegios administrativos totales sobre el sistema. */
    ADMIN,

    /** Rol encargado de la gestión y control del inventario en bodega. */
    JEFE_BODEGA,

    /** Rol con funciones directivas y de supervisión general. */
    GERENTE,

    /** Rol encargado de coordinar los procesos de compra y adquisición. */
    COORDINADOR_COMPRAS
}
