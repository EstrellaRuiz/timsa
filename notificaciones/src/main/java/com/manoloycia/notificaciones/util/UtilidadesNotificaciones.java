package com.manoloycia.notificaciones.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.manoloycia.notificaciones.dto.NotificacionDTO;
import com.manoloycia.notificaciones.model.Notificacion;

public class UtilidadesNotificaciones {
	
	private static final Logger LOG = Logger.getLogger(UtilidadesNotificaciones.class.getName());
	
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
	
	private UtilidadesNotificaciones() {
	    throw new IllegalAccessError("Utility class");
	}
	
	public static NotificacionDTO setearNotificacionDTO(Notificacion notificacion) {
		
		NotificacionDTO notificacionDTO = new NotificacionDTO();
		notificacionDTO.setDe(notificacion.getDe());
		notificacionDTO.setEstado(notificacion.getEstado());
		notificacionDTO.setFechaEnvio(dateFormat.format(notificacion.getFechaEnvio()));
		notificacionDTO.setIdNotificacion(notificacion.getId());
		notificacionDTO.setMensaje(notificacion.getMensaje());
		notificacionDTO.setPara(notificacion.getPara());
		notificacionDTO.setTipo(notificacion.getTipoNotificacion());
		return notificacionDTO;
	}
	
	
	public static Notificacion setearNotificacion(NotificacionDTO notificacionDTO){
		Notificacion notificacion = new Notificacion();
		notificacion.setDe(notificacionDTO.getDe());
		notificacion.setEstado(notificacionDTO.getEstado());
		try {
			notificacion.setFechaEnvio(dateFormat.parse(notificacionDTO.getFechaEnvio()));
		} catch (ParseException e) {
			LOG.log(Level.SEVERE,"{0}", e);
		}
		notificacion.setId(notificacionDTO.getIdNotificacion());
		notificacion.setMensaje(notificacionDTO.getMensaje());
		notificacion.setPara(notificacionDTO.getPara());
		notificacion.setTipoNotificacion(notificacionDTO.getTipo());
		return notificacion;
	}
	
	
	public static boolean callService(URL url, String method){
		
		boolean exito = false;
		
		try {
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(method);
			conn.setRequestProperty("Accept", "application/json");
	
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			
			exito= true;
	
			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
	
			String output;
			LOG.info("Respuesta: 200  .... \n");
			while ((output = br.readLine()) != null) {
				LOG.info(output);
			}
	
			conn.disconnect();

	  } catch (MalformedURLException e) {
		  LOG.log(Level.SEVERE,"{0}", e);

	  } catch (IOException e) {
		  LOG.log(Level.SEVERE,"{0}", e);
	  }
		
		return exito;
	}
	
	
	public static boolean validarMovil(String movil){
		  Pattern p = Pattern.compile("^6[0-9]{8}$");
	      Matcher m = p.matcher(movil);
	      return m.find();
	}
	
	public static boolean validarFaxMadrid(String fax){
		Pattern pattern = Pattern.compile("^[91][0-9]{8}$");
		Matcher m = pattern.matcher(fax);
	    return m.find();
	}
	
	public static boolean validarEmail(String email){
		
	      // comprueba que no empieze por punto o @
	      Pattern p = Pattern.compile("^.|^@");
	      Matcher m = p.matcher(email);
	      if (m.find())
	    	  LOG.info("Las direcciones email no empiezan por punto o @");
	         
	      // comprueba que no empieze por www.
	      p = Pattern.compile("^www.");
	      m = p.matcher(email);
	      if (m.find()){
	    	LOG.info("Los emails no empiezan por www");
	        return false;
	      }	

	      // comprueba que contenga @
	      p = Pattern.compile("@");
	      m = p.matcher(email);
	      if (!m.find()){
	    	  LOG.info("La cadena no tiene arroba");
	      	return false;
	      }	
	      	
	      // comprueba que no contenga caracteres prohibidos	
	      p = Pattern.compile("[^A-Za-z0-9.@_-~#]+");
	      m = p.matcher(email);
	      StringBuffer sb = new StringBuffer();
	      boolean resultado = m.find();
	      boolean caracteresIlegales = false;

	      while(resultado) {
	         caracteresIlegales = true;
	         m.appendReplacement(sb, "");
	         resultado = m.find();
	      }

	      // Añade el ultimo segmento de la entrada a la cadena
	      m.appendTail(sb);

	      email = sb.toString();

	      if (caracteresIlegales) {
	    	 LOG.info("La cadena contiene caracteres ilegales");
	         return false;
	      }
	      
	      return true;
	}
	
	/**
	 * Función que elimina acentos y caracteres especiales de
	 * una cadena de texto.
	 * @param input
	 * @return cadena de texto limpia de acentos y caracteres especiales.
	 */
	public static String limpiarMensaje(String input) {
		// Cadena de caracteres original a sustituir.
	    String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
	    // Cadena de caracteres ASCII que reemplazarán los originales.
	    String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
	    String output = input;
	    for (int i=0; i<original.length(); i++) {
	        // Reemplazamos los caracteres especiales.
	        output = output.replace(original.charAt(i), ascii.charAt(i));
	    }//for i
	    return output;
	}
		

}
