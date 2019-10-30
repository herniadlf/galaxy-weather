# Galaxy Weather #
Por Hernán de la Fuente

**Contenido**
- Aclaraciones Previas
- Solución (Modelo)
- Solución (Carpetas)
- Tecnologías y Librerías
- Configuración
- Test automáticos
- Endpoints
- Ejecución local

Este repositorio contiene una solución al ejercicio propuesto. 
En él se presenta un sistema solar con tres planetas, todos orbitando circularmente alrededor de un sol.
Se necesita estimar el clima de los próximos 10 años con el conocimiento de ciertos fenómenos
que ocurren según la disposición de los planetas en la galaxia.

## Aclaraciones Previas ##

- Para los días en los que los planetas forman un triángulo (es decir, no están alineados entre si ni con el sol)
y a su vez el sol no está contenido en él, se definió el tipo de clima *NORMAL*.
- Cuando se habla de *períodos de lluvia*, se asume que son días específicos ya que puede no ser exacto a nivel
meteorológico pero da una idea de las condiciones. Es decir, si 1 año hay un sólo período de lluvia es
porque sólo uno de los días hubo lluvia.
- Para saber el pico de días lluviosos, se crearon los atributos de detalle que puede tener el clima de un día. 
En este caso, hay un solo "tipo de detalle" que sería la intensidad (*INTENSITY*) solo calculada para días lluviosos. 


## Solución (Modelo) ##

Los componentes más importantes para la solución son:

- Clase ``Galaxy`` 
- Interfaz ``OrbitalMovable``
- Interfaz ``WeatherGuru``
- Clase ``GalaxyWeather``
- Interfaz ``GalaxyContainer``

La clase ``Galaxy`` contiene un ``OrbitalCenter`` y una lista de ``OrbitalComponent``, entre otras cosas.
Ambos heredan de la clase ``GalaxyComponent``, la cual define su posición en la galaxia dentro de un plano cartesiano 
como una ``GalaxyPosition``.

Los ``OrbitalComponent`` implementan la interfaz ``OrbitalMovable`` que declara los métodos ``getSpeed`` y ``move``.
Como su nombre lo indica, cada componente deberá definir de qué manera se 'mueve' dentro de una galaxia.
Para la solución requerida, el desplazamiento es circular por lo que se crearon objetos ``Planet`` que definen este comportamiento.

Al contener la ``Galaxy`` dichos objetos, es la encargada de marcar el paso del tiempo tomado como 'día'. 
En cada nuevo día, le indica a sus componentes que se muevan y calcula las condiciones climáticas a partir de su posición.

Para este cálculo, utiliza una implementación de la interfaz ``WeatherGuru``. Ésta declara los siguientes métodos : 
``centerAndComponentsAlligned``, ``onlyComponentsAlligned``, ``centerIsSurrounded``, ``buildGalaxyContainer`` y ``calculateWeather``.
La finalidad de esta interfaz es forzar a crear un objeto ``GalaxyContainer`` que defina una figura a partir de la cual
puede comprenderse la ubicación de los componentes de la galaxia. 
Para esta solución, esta figura no fue nada menos que un ``Triangle`` el cuál define el método ``contains``.

Con esto explicado, la lógica más compleja se encuentra justamente en el gurú. 
Es el que define la condición climática de la galaxia a partir de la posición de los componentes de la misma. 

El objeto ``GalaxyWeather``tiene un tipo (se pueden consultar con el endpoint /tipos_de_clima) y una String en formato 
JSON que busca agrupar los *detalles* que puedan querer especificarse para ese clima en particular. Es decir, podría ser
``{ "INTENSITY": 200.000, "PRESSURE": 100.00, "TEMPERATURE": 30.0 }``. Para esta solución, solo se define INTENSITY para
los días lluviosos. 

Se trató de elaborar de tal forma de no estar totalmente supeditados al problema actual sino tener cierta flexibilidad 
para el consumo de galaxias con distintas leyes o composiciones, teniendo que cambiar lo menos posible en la aplicación.

## Solución (Carpetas) ##

    - application:
    | - controller: endpoints
    | - persistance: definición de tablas, repository para acceder a los datos y service para manipularlos
    - model: core de la solución y el cálculo del movimiento de los planetas y el clima que eso genera 
 

## Tecnologías y Librerías ##

- Java 8
- Spring Boot
- Hibernate
- JPA
- Gradle
- PostgreSQL
- Cloud Hosting en heroku: https://galaxyweather-hdlf.herokuapp.com/

## Configuración ##

En el archivo ``galaxy.properties`` se puede definir las configuraciones iniciales de la aplicación:

General
- years: cantidad de años que busca estimarse el clima. Entero

Por Planeta
- name: nombre del planeta. String
- speed: velocidad de desplazamiento angular por día. Entero
- clockwise: ``true`` en caso de que el desplazamiento sea en sentido horario, ``false`` en otro caso. Booleano
- initPositionX: disposición en X en el plano cartesiano que forma la galaxia. Double
- initPositionY: disposición en Y en el plano cartesiano que forma la galaxia. Double

## Test automáticos ##

Ya que se desarrolló siguiendo la metodología de TDD, se pueden correr la batería de tests utilizando ``gradle build && gradle test``

## Endpoints ##

- GET -> ```https://galaxyweather-hdlf.herokuapp.com/galaxy/tipos_de_clima``` : Devuelve la nomenclatura utilizada para cara tipo de clima disponible. Estos son: 
DROUGHT(sequía), RAINY (lluvioso), NORMAL (cuando se forma un triángulo entre los planetas y el sol no está contenido en él)
y OPTIMUM (clima óptimo de presión y temperatura).
- GET -> ``https://galaxyweather-hdlf.herokuapp.com/galaxy/clima?dia=555`` : Devuelve el tipo de clima para el día especificado por parámetro-
- GET -> ``https://galaxyweather-hdlf.herokuapp.com/galaxy/cantidad_de_periodos?tipo=RAINY&detalle=INTENSITY`` : 
Devuelve la cantidad de días en los que el clima fue del **tipo** pasado por parámetro. 
A su vez, es opcional el parámetro **detalle**.
En este caso, se consulta por días donde el clima fue lluvioso y se devuelve como detalle cuál fue el día de mayor intensidad.
- POST ->  ``https://galaxyweather-hdlf.herokuapp.com/galaxy/`` : Inicializa la base de datos con los cálculos de clima.
- DELETE ->  ``https://galaxyweather-hdlf.herokuapp.com/galaxy/`` : Elimina la información guardada en la base de datos.

## Ejecución local ##

Deberá configurarse una DB local en las properties `application.properties`

``gradle build && java -jar build/libs/*.jar``









