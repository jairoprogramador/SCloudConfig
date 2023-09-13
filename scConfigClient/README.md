# Spring Cloud Config
Permite una configuración centralizada para sistemas distribuidos

## Refresh Config
Este modo permite refrescar las propiedades del sistema sin reiniciar el servidor. Pero necesita que se llame a un endpoint solicitando el refresh, esto quiere decir que no es un proceso automático.

## Dependencias

    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-config</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>

## Anotacion
La anotacion <code>@RefreshScope</code> permite crear un proxy. Se usará para crear un proxy de propiedades que luego se prodra refrescar cuando se llame al endpoind <code>POST http://dominio/actuator/refresh </code>

## application.yml
Para que el endpoint <code>POST http://dominio/actuator/refresh </code> pueda ser expuesto es importante hacer la siguiente configuracion en el archivo application.yml

    management:
        endpoints:
            web:
                exposure:
                    include: refresh

## Funcionamiento
Siga los siguientes pasos para probar el funcionamiento del modo **refresh config**.
1. Ejecutar el cliente y el servidor de Spring Cloud Config
2. Hacer una solicitud a <code>GET http://localhost:3001/properties </code>. Tener presente la respuesta de esta solicitud ya que se va ha comparar con uno de los pasos siguientes.
3. Editar el archivo <code>configclient.yml</code> que esta en el repositorio Git con algun valor nuevo, por ejemplo: <code>info:data: nuevoValor</code>
4. Comitear el cambio del paso 3.
5. LLamar al endpoind <code>POST http://localhost:3001/actuator/refresh </code>. Esto realizara la actualizacion de las propiedades.
6. Repetir el paso 2. Notara que ahora la respuesta tiene el nuevo valor editado en el paso 3. No fue necesario reiniciar el servidor.







