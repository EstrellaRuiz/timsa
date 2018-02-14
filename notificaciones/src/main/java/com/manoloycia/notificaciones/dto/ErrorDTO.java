package com.manoloycia.notificaciones.dto;
/**
 * Clase para mostrar el detalle de un error de los servicios
 * @author emruiz
 *
 */
public class ErrorDTO {
	
	String codigo;
	String mensaje;
	String descripcion;
	
	
	
	public ErrorDTO(String codigo, String mensaje, String descripcion) {
		super();
		this.codigo = codigo;
		this.mensaje = mensaje;
		this.descripcion = descripcion;
	}
	
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	@Override
	public String toString() {
		return "ErrorDTO [codigo=" + codigo + ", mensaje=" + mensaje + ", descripcion=" + descripcion + "]";
	}
	
	
	

}
