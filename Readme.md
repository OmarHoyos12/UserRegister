# Instrucciones

## Instrucciones para construir el proyecto:
- El proyecto funciona con la versión 17 de JDK.
- Compilación: mvn clean install
- Despliegue: mvn spring-boot:run
- Consola H2: http://localhost:8081/h2-console/
     - URL JDBC: jdbc:h2:mem:smartjob
     - Usuario: smartjob
     - Contraseña: smartjob
    
## Rest Api
- URL Swagger: http://localhost:8080/swagger-ui/index.html
- URL Postman: http://localhost:8081/api/users/register
- Parametros de entrada: 
{
  "name": "Juan Rodriguez",
  "email": "juan@rodriguez.org",
  "password": "Hunter2",
  "phones": [
    {
      "number": "1234567",
      "citycode": "1",
      "contrycode": "57"
    }
  ]
}

## Instrucciones de entrega:
- La solución cumple con los requisitos establecidos en el ejercicio enviado: 
     - Parámetros de usuario de entrada y respuesta en el formato indicado. 
     - Mensaje de error en formato json.
     - Código de status HTTP adecuado, según los casos contemplados.
     - Si el correo se encuentra en base de datos responde con el mensaje indicado.
     - Validación para que el correo corresponda al formato recomendado, sino cumple retorna mensaje indicado.
     - Para el registro la clave cumple con el formato establecido en la expresión regular establecido en el archivo application.properties lo cual lo hace configurable, además se agregó en este archivo el parámetro para establecer el mensaje adecuado de contraseña para guiar al usuario y poner una contraseña que cumpla con las condiciones.
     - El token es persistido junto con el usuario, esta generado con JWT y se envía en la respuesta.
     - Se uso Base de datos en memoria H2.
     - Proceso de build con Maven.
     - Persistencia con JPA.
     - Framework SpringBoot.
     - Versión Java 17
     - Pruebas unitarias a la clase UserServiceImpl.java
     - Implementación de swagger
     - Entrega en un repositorio público github y documentacion detallada con Diagrama de la solución en correo. 