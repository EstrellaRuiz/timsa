package com.manoloycia.notificaciones.services;

import com.manoloycia.notificaciones.dto.NotificacionDTO;

/**
 * Interfaz para las diferentes implementaciones del envio de una notificación
 * @author emruiz
 *
 */
public interface INotificaciones {
	
	NotificacionDTO enviarNotificacion(NotificacionDTO notificacion) throws Exception;
	
}
