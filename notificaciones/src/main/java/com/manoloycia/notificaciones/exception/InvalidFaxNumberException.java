package com.manoloycia.notificaciones.exception;

/**
 * 
 * @author emruiz
 *
 */
public class InvalidFaxNumberException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String errorMessage;
	 
	public String getErrorMessage() {
		return errorMessage;
	}
	public InvalidFaxNumberException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}
	public InvalidFaxNumberException() {
		super();
	}


	
	
	
	
 	

 
}
