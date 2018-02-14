package com.manoloycia.notificaciones.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manoloycia.notificaciones.dto.NotificacionDTO;
import com.manoloycia.notificaciones.model.Notificacion;
import com.manoloycia.notificaciones.repository.NotificacionDAO;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servicio de Gestión de Notificaciones
 * @author emruiz
 *
 */
@Service
public class NotificacionesService {
	
	private static final Logger LOG = Logger.getLogger(NotificacionesService.class.getName());
	
	private INotificaciones gestorNotificaciones;
	
	@Autowired
	private NotificacionDAO repositorio;

	public Notificacion getNotificacionById(Long id) {
		return repositorio.findOne(id);
	}
	
	/**
	 * Crea un notificación nueva
	 * @param notificacion
	 * @return Notificacion creada
	 */
	public Notificacion crearNotificacion(Notificacion notificacion){
		
		return repositorio.save(notificacion);
	}
	
	/**
	 * Modificar los datos de una notificación existente
	 * @param notificacion
	 * @return Notificacion modificada
	 */
	public Notificacion modificarNotificacion(Notificacion notificacion){
		LOG.log(Level.INFO,"Modificando la Notificación: {0}" , notificacion.getId());
		return repositorio.save(notificacion);
	}
	
	
	/**
	 * Borra una Notificacion a través de su id
	 * @param uid
	 */
	 public void deleteNotificacion(Long uid){
		  repositorio.delete(uid);
	 }
	 
	 

	public NotificacionDTO enviarNotificacionPorSms(NotificacionDTO notificacionDTO) throws Exception {
		//Llamar al servicio
		gestorNotificaciones= new NotificacionesPorSmsImpl();
		return gestorNotificaciones.enviarNotificacion(notificacionDTO);

	}

	public NotificacionDTO enviarNotificacionPorFax(NotificacionDTO notificacionDTO) throws Exception {
		//Llamar al servicio
		gestorNotificaciones= new NotificacionesPorFaxImpl();
		return gestorNotificaciones.enviarNotificacion(notificacionDTO);
	
	}

	
}
