package com.manoloycia.notificaciones.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import com.manoloycia.notificaciones.dto.ErrorDTO;
import com.manoloycia.notificaciones.dto.NotificacionDTO;
import com.manoloycia.notificaciones.model.Notificacion;
import com.manoloycia.notificaciones.services.NotificacionesService;
import com.manoloycia.notificaciones.util.UtilidadesNotificaciones;
import com.manoloycia.notificaciones.util.data.validation.error.ResponseErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.constraints.NotNull;

import com.manoloycia.notificaciones.exception.*;

/**
 * Controlador para la Gestión de Notificaciones
 * @author emruiz
 *
 */

@RestController
@RequestMapping("/notificaciones/v0/notificaciones")
@Api(value ="/notificaciones/v0/notificaciones", description = "Operaciones del Módulo de Comunicaciones/Notificaciones")
public class NotificacionesController {

	
	 private static final Logger LOG = Logger.getLogger(NotificacionesController.class.getName());
	 
	 private static final String ERRORVALIDACION="Error de validación de los datos de entrada";
	    
	 @Autowired
	 private NotificacionesService notificacionesService;
	 
	 static DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
	 
	 
		
	@RequestMapping(path="/{notificacion-id}" ,method = RequestMethod.GET)
	@ApiOperation("Obtener todo el detalle de una Notificación a través de su id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK", response=NotificacionDTO.class),
			@ApiResponse(code = 404, message = ResponseErrorCode.MYC004)
	})
	public ResponseEntity<Notificacion> getNotificacionById(@ApiParam(value = "notificacion-id") @NotNull @PathVariable(value = "notificacion-id") Long id) {
		  Notificacion notificacion = notificacionesService.getNotificacionById(id);
			if (notificacion == null) {
				 LOG.log(Level.SEVERE, "No se ha encontrado una Notificacion con el id: {0}",id);
				 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(notificacion, HttpStatus.OK);
			}
	}
	
	
	
	@ExceptionHandler(InvalidPhoneNumberException.class)
	public ResponseEntity<ErrorDTO> exceptionHandler(Exception ex) {
		ErrorDTO error = new ErrorDTO("MYC005",ERRORVALIDACION,ResponseErrorCode.MYC005);
		return new ResponseEntity<ErrorDTO>(error, HttpStatus.BAD_REQUEST);
	}
	

	@RequestMapping(path="/sms" ,method = RequestMethod.GET, headers="Accept=application/json")
	@ApiOperation("Enviar una notificación por SMS")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK", response=NotificacionDTO.class),
			@ApiResponse(code = 400, message = ERRORVALIDACION)
	})
	public NotificacionDTO enviarNotificacionPorSms(@ApiParam(value = "Remitente de la notificación") @RequestParam(required = true) String remitente,
            @ApiParam(value = "Número de Teléfono") @RequestParam(required = true) String destinatario,
            @ApiParam(value = "Mensaje a enviar") @RequestParam(required = true) String mensaje
     	   ) throws InvalidPhoneNumberException, InvalidMensajeException{
		
		//Validar teléfono
		boolean movilValido = UtilidadesNotificaciones.validarMovil(destinatario);
		if (movilValido) {
			LOG.log(Level.INFO, "El teléfono  {0}  es válido", destinatario);
		} else {
			LOG.log(Level.INFO, "El teléfono  {0} es inválido", destinatario);
			throw new InvalidPhoneNumberException("El téléfono es inválido");
			//return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		//Validar Mensaje
		if (mensaje.length()> 160 || mensaje.isEmpty()){
			LOG.log(Level.INFO, "El mensaje no es inválido", mensaje);
			throw new InvalidMensajeException("El Mensaje no es válido: supera los 160 caracteres");
		}
		
		NotificacionDTO notificacionDTO = crearNotificacion("SMS","Enviada",remitente, UtilidadesNotificaciones.limpiarMensaje(mensaje), destinatario);
		
		try {
			notificacionesService.enviarNotificacionPorSms(notificacionDTO);
			Notificacion notificacionCreada = notificacionesService.crearNotificacion(UtilidadesNotificaciones.setearNotificacion(notificacionDTO));
			LOG.log(Level.INFO, "Se ha registrado en el sistema la notificación SMS: {0}", notificacionCreada);
			return UtilidadesNotificaciones.setearNotificacionDTO(notificacionCreada) ;
			
		} catch (Exception e) {
			LOG.log(Level.SEVERE,"{0}", e);
			notificacionDTO.setEstado("Error");
			Notificacion notificacionError = notificacionesService.crearNotificacion(UtilidadesNotificaciones.setearNotificacion(notificacionDTO));
			return UtilidadesNotificaciones.setearNotificacionDTO(notificacionError);
			
		}
		
	}
	
	
	@ExceptionHandler(InvalidFaxNumberException.class)
	public ResponseEntity<ErrorDTO> exceptionInvalidFaxNumberHandler(Exception ex) {
		ErrorDTO error = new ErrorDTO("MYC008",ERRORVALIDACION,ResponseErrorCode.MYC008);
		return new ResponseEntity<ErrorDTO>(error, HttpStatus.BAD_REQUEST);
	}
	
	
	
	@ExceptionHandler(InvalidMensajeException.class)
	public ResponseEntity<ErrorDTO> exceptionInvalidMensajeHandler(Exception ex) {
		ErrorDTO error = new ErrorDTO("MYC009",ERRORVALIDACION,ResponseErrorCode.MYC009);
		return new ResponseEntity<ErrorDTO>(error, HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(path="/fax" ,method = RequestMethod.GET)
	@ApiOperation("Enviar una notificación por FAX")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK", response=NotificacionDTO.class),
			@ApiResponse(code = 400, message = ERRORVALIDACION)
	})
	public NotificacionDTO enviarNotificacionPorFax(@ApiParam(value = "Remitente de la notificación") @RequestParam(required = true) String remitente,
            @ApiParam(value = "Número de FAX") @RequestParam(required = true) String destinatario,
            @ApiParam(value = "Mensaje a enviar") @RequestParam(required = true) String mensaje) throws InvalidFaxNumberException, InvalidMensajeException{
		
		//Validar FAX
		boolean faxValido = UtilidadesNotificaciones.validarFaxMadrid(destinatario);
		if (faxValido) {
			LOG.log(Level.INFO, "El FAX  {0} es válido", destinatario);
		} else {
			LOG.log(Level.INFO, "El FAX  {0}  es inválido", destinatario);
			throw new InvalidFaxNumberException("El FAX es inválido");
		}
		//Validar Mensaje
		if (mensaje.length()> 500 || mensaje.isEmpty()){
			LOG.log(Level.INFO, "El mensaje no es inválido", mensaje);
			throw new InvalidMensajeException("El mensaje no es inválido: supera los 500 caracteres");
		}
		
	    NotificacionDTO notificacionDTO = crearNotificacion("FAX","Enviada",remitente, mensaje, destinatario);
				
		try {
			notificacionesService.enviarNotificacionPorFax(notificacionDTO);
			Notificacion notificacionCreada = notificacionesService.crearNotificacion(UtilidadesNotificaciones.setearNotificacion(notificacionDTO));
			LOG.log(Level.INFO, "Se ha registrado en el sistema la notificación FAX: {0}", notificacionCreada);
			return UtilidadesNotificaciones.setearNotificacionDTO(notificacionCreada);
		} catch (Exception e) {
			LOG.log(Level.SEVERE,"{0}", e);
			notificacionDTO.setEstado("Error");
			Notificacion notificacionError = notificacionesService.crearNotificacion(UtilidadesNotificaciones.setearNotificacion(notificacionDTO));
			return UtilidadesNotificaciones.setearNotificacionDTO(notificacionError);
		}
		
		
		
	}

	/**
	 * Crea un objeto NotificacionDTO a partir de los datos de entrada
	 * @param tipo
	 * @param estado
	 * @param remitente
	 * @param mensaje
	 * @param destinatario
	 * @return NotificacionDTO
	 */
	private NotificacionDTO crearNotificacion(String tipo, String estado, String remitente, String mensaje, String destinatario) {
		NotificacionDTO notificacionDTO = new NotificacionDTO();
		notificacionDTO.setDe(remitente);
		notificacionDTO.setEstado(estado);
		notificacionDTO.setMensaje(mensaje.replaceAll(" ","_"));
		notificacionDTO.setPara(destinatario);
		notificacionDTO.setTipo(tipo);
		notificacionDTO.setFechaEnvio(dateFormat.format(new Date()));
		return notificacionDTO;	
	}
	 
	 
	 
}
