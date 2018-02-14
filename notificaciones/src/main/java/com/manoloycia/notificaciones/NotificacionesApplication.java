package com.manoloycia.notificaciones;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.manoloycia.notificaciones.repository.NotificacionDAO;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;



/**
 * @author emruiz
 */
@SpringBootApplication
@EnableSwagger2
public class NotificacionesApplication  {
	
	
	private static final Logger LOG = Logger.getLogger(NotificacionesApplication.class.getName());
	
	public static String smsEndPoint;
	public static String faxEndPoint;
	
	@Autowired
	NotificacionDAO repository;
	
	private Properties prop = null;
	
	public NotificacionesApplication(){
		InputStream is = null;
		 try {
	            this.prop = new Properties();
	            is = this.getClass().getResourceAsStream("/local.properties");
	            prop.load(is);
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	
	public Set<Object> getAllKeys(){
		Set<Object> keys = prop.keySet();
		return keys;
	}
	
	public String getPropertyValue(String key){
		return this.prop.getProperty(key);
	}

	public static void main(String[] args) {
		
		NotificacionesApplication mpc = new NotificacionesApplication();
        Set<Object> keys = mpc.getAllKeys();
        for(Object k:keys){
            String key = (String)k;
            if (("sms.endpoint").equals(key)){
            	smsEndPoint=mpc.getPropertyValue(key);
            	LOG.log(Level.INFO,"SMS ENDPOINT: {0}" , smsEndPoint);
            }
            if ("fax.endpoint".equals(key)){
            	faxEndPoint=mpc.getPropertyValue(key);
            	LOG.log(Level.INFO,"FAX ENDPOINT: {0}" ,faxEndPoint);
            }
        }
		SpringApplication.run(NotificacionesApplication.class, args);
	}
	
	
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .groupName("notificaciones")
          .select()  
          .apis(RequestHandlerSelectors.basePackage("com.manoloycia.notificaciones.controllers"))
          .paths(PathSelectors.any()) 
          .build()
          .apiInfo(apiInfo());
    }
	
	
	private ApiInfo apiInfo() {
	    ApiInfo apiInfo = new ApiInfo(
	      "Notificaciones REST API",
	      "Servicios de Notificaciones",
	      "API v 1.0",
	      "Terms of service",
	      "estrellaruizse@gmail.com",
	      "License of API",
	      "API license URL");
	    return apiInfo;
	}
	
	
	
}
