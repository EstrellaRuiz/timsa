package com.manoloycia.notificaciones.repository;

import java.util.Date;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.manoloycia.notificaciones.model.Notificacion;
import com.manoloycia.notificaciones.repository.NotificacionDAO;

import junit.framework.Assert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class NotificacionDAOTest {
	
	
	 private static final Logger LOG = Logger.getLogger(NotificacionDAOTest.class.getName());
		

	@Autowired
    private NotificacionDAO notificacionDAO;
	
    Notificacion entity;
	
	@Before
	public void setUp() {
		String de = "Manolo y Cia";
		String para ="650221122";
		String estado = "";
		String mensaje = "";
		Date fechaEnvio = new Date();
		String tipoNotificacion = "SMS";
		entity = new Notificacion(de, para , estado, mensaje, fechaEnvio, tipoNotificacion);	
		
	}
	
	@Test
	public void crearNotificacion(){
		Notificacion newNotificacion = notificacionDAO.save(entity);
		LOG.info("Se ha creado la siguiente notificación:\n" + newNotificacion);
	}
	
	
	@Test
	public void findNotificacion(){
		Notificacion newNotificacion = notificacionDAO.save(entity);
		Notificacion find = notificacionDAO.findOne(newNotificacion.getId());
		Assert.assertNotNull(find);
		Assert.assertEquals(newNotificacion.getId(), find.getId());
		notificacionDAO.delete(find);
	}
	
	
	@Test
	public void findAllNotificaciones(){
	        String result = "";
	           
	        for(Notificacion noti : notificacionDAO.findAll()){
	            result += noti.toString() + "</br>";
	        }
	        
	        LOG.info("Se han encontrado las siguientes notificaciones:\n");
	        LOG.info(result);
	       
	}
	
	
	
	@Test
	public void deleteNotificacion(){
		Notificacion notificacionDelete = notificacionDAO.save(entity);
		notificacionDAO.delete(notificacionDelete.getId());
	}

}
