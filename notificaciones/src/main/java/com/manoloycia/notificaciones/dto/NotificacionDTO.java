package com.manoloycia.notificaciones.dto;

/**
 * DTO Notificacion para usar en la capa de presentación
 * @author emruiz
 *
 */
public class NotificacionDTO {
	
	Long idNotificacion;
	
	String mensaje;
	
	String de;
	
	String para;
	
	String estado;
	
	String tipo;
	
	String fechaEnvio;


	public NotificacionDTO() {
		super();
	}

	public NotificacionDTO(String mensaje, String de, String para) {
		super();
		this.mensaje = mensaje;
		this.de = de;
		this.para = para;
	}
		

	public NotificacionDTO(Long idNotificacion, String mensaje, String de, String para, String estado, String tipo,
			String fechaEnvio) {
		super();
		this.idNotificacion = idNotificacion;
		this.mensaje = mensaje;
		this.de = de;
		this.para = para;
		this.estado = estado;
		this.tipo = tipo;
		this.fechaEnvio = fechaEnvio;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getDe() {
		return de;
	}

	public void setDe(String de) {
		this.de = de;
	}

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	public Long getIdNotificacion() {
		return idNotificacion;
	}

	public void setIdNotificacion(Long idNotificacion) {
		this.idNotificacion = idNotificacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(String fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	@Override
	public String toString() {
		return "NotificacionDTO [idNotificacion=" + idNotificacion + ", mensaje=" + mensaje + ", de=" + de + ", para="
				+ para + ", estado=" + estado + ", tipo=" + tipo + ", fechaEnvio=" + fechaEnvio + "]";
	}

	
	
	

}
