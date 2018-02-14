package com.manoloycia.notificaciones.services;

import com.manoloycia.notificaciones.NotificacionesApplication;
import com.manoloycia.notificaciones.dto.NotificacionDTO;
import com.manoloycia.notificaciones.util.UtilidadesNotificaciones;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementación del envio de notificaciones por SMS
 * @author emruiz
 *
 */
public class NotificacionesPorSmsImpl implements INotificaciones {
	
	private static final Logger LOG = Logger.getLogger(NotificacionesPorSmsImpl.class.getName());

	@Override
	public NotificacionDTO enviarNotificacion(NotificacionDTO notificacionDTO) throws Exception {
		
		String endPoint = NotificacionesApplication.smsEndPoint;
		
		endPoint= endPoint + "?phone="+notificacionDTO.getPara()+"&message="+ notificacionDTO.getMensaje();
		
		URL url = new URL(endPoint);
		
		LOG.log(Level.INFO,"Call to URL Service: {0}" , url.toString());
		
		UtilidadesNotificaciones.callService(url, "GET");
		
		LOG.log(Level.INFO, "La notificación se ha enviado correctamente: {0}" , notificacionDTO);
		
		return notificacionDTO;

	}
	
	

}
