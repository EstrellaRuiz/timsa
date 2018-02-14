package com.manoloycia.notificaciones.exception;

/**
 * 
 * @author emruiz
 *
 */
public class InvalidMensajeException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String errorMessage;
	 
	public String getErrorMessage() {
		return errorMessage;
	}
	public InvalidMensajeException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}
	public InvalidMensajeException() {
		super();
	}


	
	
	
	
 	

 
}
