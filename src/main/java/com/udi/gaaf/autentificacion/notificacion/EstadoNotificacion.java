package com.udi.gaaf.autentificacion.notificacion;

/**
 * Representa los posibles estados de una notificación dentro del sistema.
 *
 * <p>Esta enumeración permite identificar si una notificación ha sido vista, 
 * no vista o resuelta por el usuario.</p>
 */
public enum EstadoNotificacion {

    /** La notificación ha sido visualizada por el usuario. */
    VISTA,

    /** La notificación aún no ha sido visualizada por el usuario. */
    NO_VISTA,

    /** La notificación ha sido atendida o solucionada. */
    RESUELTA
}
