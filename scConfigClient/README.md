# Spring Cloud Config
Permite una configuración centralizada para sistemas distribuidos

## Kafka y monitor
Este modo permite refrescar las propiedades de todos los microservicios sin tener que reiniciarlos. Pero necesita que se llame a un endpoint (<code>/monitor</code>) solicitando actualizar propiedades, esto quiere decir que no es un proceso automático. Para que sea automatico se debe llamar a este endpoint mediande algun hooks de Git por cada cambio en el repositorio de configuraciones.

## Dependencias

    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-bus-kafka</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>

## Anotacion
La anotacion <code>@RefreshScope</code> permite crear un proxy. Se usará para crear un proxy de propiedades que luego se prodra refrescar cuando se llame al endpoind <code>POST http://dominio/actuator/refresh </code>

## application.yml
Para que el endpoint <code>POST http://dominio/actuator/busrefresh </code> pueda ser expuesto es importante hacer la siguiente configuracion en el archivo application.yml del repositorio de configuraciones

    management:
        endpoints:
            web:
                exposure:
                    include: busrefresh

## Funcionamiento
Siga los siguientes pasos para probar el funcionamiento del modo **refresh config**.
1. Ejecutar el cliente y el servidor de Spring Cloud Config
2. Hacer una solicitud al cliente <code>GET http://localhost:3001/properties </code>. Tener presente la respuesta de esta solicitud ya que se va ha comparar con uno de los pasos siguientes.
3. Editar el archivo <code>configclient.yml</code> del respositorio de configuraciones con algun valor nuevo, por ejemplo: <code>info:data: nuevoValor</code>
4. Comitear el cambio del paso 3.
5. LLamar al endpoind de cliente <code>POST http://localhost:3001/actuator/busrefresh </code>. Esto notificará a todos los nodos del topico para que actualicen sus propiedades. Para que esto funciones necesitamos conocer almenos algun consumidor del topico (cliente), esto se mejora centralizando este llamado en el servidor con el API <code>/monitor</code>. 
6. Repetir el paso 2. Notara que ahora la respuesta tiene el nuevo valor editado en el paso 3. No fue necesario reiniciar el servidor. 







