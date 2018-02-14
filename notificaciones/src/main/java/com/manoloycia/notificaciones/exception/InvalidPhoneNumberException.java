package com.manoloycia.notificaciones.exception;

/**
 * 
 * @author emruiz
 *
 */
public class InvalidPhoneNumberException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String errorMessage;
	 
	public String getErrorMessage() {
		return errorMessage;
	}
	public InvalidPhoneNumberException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}
	public InvalidPhoneNumberException() {
		super();
	}


	
	
	
	
 	

 
}
