package com.manoloycia.notificaciones.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.manoloycia.notificaciones.model.Notificacion;


@Repository
public interface  NotificacionDAO  extends CrudRepository<Notificacion, Long>{
	

}
