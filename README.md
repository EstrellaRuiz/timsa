# timsa

Documentación y pruebas de los servicios REST:
----------------------------------------------

http://localhost:8080/swagger-ui.html


Para ver la consola de H2:
--------------------------

http://localhost:8080/h2-console



Ejemplos:
=========

Envío de Notificación por SMS:

curl -X GET --header 'Accept: application/json' 'http://localhost:8080/notificaciones/v0/notificaciones/sms?remitente=Manolo y Cia&destinatario=650221144&mensaje=La obra comenzará pronto'


Envio de Notificación por FAX:

curl -X GET --header 'Accept: application/json' 'http://localhost:8080/notificaciones/v0/notificaciones/fax?remitente=Manolo%20y%20Cia&destinatario=910000000&mensaje=La%20obra%20comenzar%C3%A1%20pronto'

