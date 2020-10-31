# Operación fuego de quasar

## Prerrequisitos del proyecto
*	Maven 3.6
* Java 8
* PostgreSQL
* IDE (eclipse, Intellij)

## Ejecución del proyecto 

Repositorio del proyecto: https://github.com/dtmaciasca/firequasar
1.	Clonar el proyecto del repositorio: 

~~~
git clone https://github.com/dtmaciasca/firequasar.git
~~~
2.	Para ejecutarlo local se necesita de una base de datos de Postgresql, ejecutar el script en Postgres.
~~~
application/src/main/resources/db.V1.migracion/001_fire_quasar.sql
~~~
3.	Revisar la configuración local de la base de datos con el proyecto en el archivo application.yml

4.	Ejecutar 
~~~
mvn clean install -Plocal
~~~

5.	Ejecutar la clase FireQuasarApplication con el IDE de su preferencia, tambien puede ejecutarse el siguiente comando para iniciar la aplicación de Spring Boot:
~~~
mvn spring-boot:run -f application
~~~

6.	Abrir la dirección URL: http://localhost:8080/swagger-ui.html#/fire-quasar-rest

## Aplicación desplegada en Heroku

URL: https://firequasar.herokuapp.com/swagger-ui.html#/fire-quasar-rest

POST https://firequasar.herokuapp.com/firequasar/topsecret/
<br/>Ejemplo:
~~~
{
  "satellites": [
    {
      "name": "kenobi",
      "distance": 100.0,
      "message": ["este", "", "", "mensaje", ""]
    },
    {
      "name": "skywalker",
      "distance": 115.5,
      "message": ["", "es", "", "", "secreto"]
    },
    {
      "name": "sato",
      "distance": 142.7,
      "message": ["este", "", "un", "", ""]
    }
  ]
}
~~~

POST https://firequasar.herokuapp.com/firequasar/topsecret_split/{satellite_name}
<br/>Ejemplo:
~~~
{
    "distance": 100.0,
    "message": ["este", "", "", "mensaje", ""]
}
~~~

GET https://firequasar.herokuapp.com/firequasar/topsecret_split/
