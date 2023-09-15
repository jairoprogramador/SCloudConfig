# Spring Cloud Config
Permite una configuración centralizada para sistemas distribuidos

## Kafka y Monitor
Este modo permite refrescar las propiedades de todos los microservicios sin tener que reiniciarlos. Pero necesita que se llame a un endpoint (<code>/monitor</code>) solicitando actualizar propiedades, esto quiere decir que no es un proceso automático. Para que sea automatico se debe llamar a este endpoint mediande algun hooks de Git por cada cambio en el repositorio de configuraciones.

## Dependencias

    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-bus-kafka</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-cloud-config-monitor</artifactId>
    </dependency>

## Endpoint monitor
Para refrescar las propiedades de todos los nodos hay que llamar al endpoind <code>http://dominio/monitor </code>

    curl --location 'http://localhost:8888/monitor' \
    --header 'Content-Type: application/json' \
    --header 'X-Github-Event: push' \
    --data '{"commits":
        [
            {"modified":
                ["configclient.yml"]
            }
        ]
    }'

## application.yml
Para que que el bus y la conexion con kafka este activa es importante la siguiente configuracion en el archivo application.yml

     spring:
        application:
            name: configserver
     cloud:
        bus:
            enabled: true
            id: configserver
            trace:
                enable: true
            ack:
                enabled: true
        stream:
            kafka:
                binder:
                    defaultBrokerPort: 39092
                    brokers: localhost
                    autoCreateTopics: true
                    autoAddPartitions: true
                    replicationFactor: 3
                    minPartitionCount: 3


## Funcionamiento
Siga los siguientes pasos para probar el funcionamiento del modo **kafka y monitor**.
1. Ejecutar el cliente y el servidor de Spring Cloud Config
2. Hacer una solicitud al cliente <code>GET http://localhost:3001/properties </code>. Tener presente la respuesta de esta solicitud ya que se va ha comparar con uno de los pasos siguientes. Tener el cuenta el puerto del servicio cliente ya que puede ser diferente.
3. Editar el archivo <code>configclient.yml</code> del repositorio de configuraciones con algun valor nuevo, por ejemplo: <code>info:data: nuevoValor</code>
4. Comitear el cambio del paso 3.
5. LLamar al endpoind del servidor <code>POST http://localhost:8888/monitor </code>. Esto realizara la actualizacion de las propiedades con la ayuda de kafka. Tener en cuenta el endpoint monitor descrito anteriormente ya que hay header y un body que se debe enviar en la solicitud para que funcione correctamente.
6. Repetir el paso 2. Notara que ahora la respuesta tiene el nuevo valor editado en el paso 3. No fue necesario reiniciar todos los servicios, tampoco fue necesario avisarles a cada uno, basta con avisarle al servidor de configuraciones central y el se encargara de avisar al resto de servicios.







