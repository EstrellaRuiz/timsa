package com.manoloycia.notificaciones.util.data.validation.error;

/**
 * Textos de los correspondientes códigos de error.
 *
 * @author emaria.ruiz
 *
 */
public class ResponseErrorCode {
	

    private ResponseErrorCode() {
		super();
	}


	public static final String MYC001 ="No se han informado todos los campos.";
    public static final String MYC002 ="No se han encontrado coincidencias.";  
    
    //Datos Obligatorios
    public static final String MYC003 ="No se ha informado el id";
    
    public static final String MYC006 ="Error desconocido.";
    public static final String MYC007="Se han encontrado entidades repetidas.";
    
  
    //Notificaciones
    public static final String MYC004="No existe una Notificacion con ese identificador";
    public static final String MYC005="El teléfono no es válido";
    public static final String MYC008="El FAX no es válido";
    public static final String MYC009="El mensaje no puede superar el número de caracteres permitidos";
    
    
 
}
