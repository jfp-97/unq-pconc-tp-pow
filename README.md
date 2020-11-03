# Programación concurrente - UNQ

## Trabajo práctico: Proof of Work

### 1. Autor

* Nombre: Perez, Juan Francisco

* E-mail: juanf.perez.97@gmail.com

* Nro. de legajo: 50775

### 2. Introducción

#### 2.1. Dominio

El programa presentado busca encontrar una secuencia de cuatro bytes (llamada *nonce*) tal que su concatenación con otra secuencia de bytes ingresada por el usuario produzca un hash que comience con una cantidad de ceros acorde con la especificada como dificultad, también ingresada por el usuario.

Se permite también configurar la cantidad de thread que van a trabajar en paralelo para buscar el nonce: todas las combinaciones posibles de nonce serán repartidas equitativamente entre ellos, de manera tal que cada uno comienza a buscar desde el principio hasta, potencialmente, el final de su rango.

En caso de que terminaran todos los thread de barrer su rango de combinaciones posibles sin que ninguno haya hallado un nonce que satisfaga la dificultad pedida, se le informará al usuario.

#### 2.2. Diseño

El programa consta de un `ThreadPool`, que se encarga de instanciar los thread (`PowWorker`) que llevan a cabo la búsqueda. Para esto, se pasan como parámetros la configuración pasada por el usuario, un buffer del cual cada worker tomará su unidad de trabajo, y otro buffer al cual los worker le comunicarán su resultado.

Inmediatamente luego de indicar al `ThreadPool` que inicie la búsqueda, se trata de leer del `ResultBuffer` la respuesta obtenida. Esto bloqueará al thread principal del programa hasta que en `ResultBuffer` haya sido escrita una respuesta.

Los thread `PowWorker` conocen el rango de combinaciones que deben chequear a través de un objeto `WorkUnit`, el cuál consumen del monitor `Buffer`. El thread productor `WorkUnitProducer` se encarga de suplir al buffer de unidades de trabajo a medida que éstas son consumidas, y es lanzado antes de comenzar la búsqueda a través del `ThreadPool`.

Un `PowWorker` trabajará bien hasta encontrar un nonce que satisfaga lo requerido, termine de barrer su rango de valores, o se le notifique que debe parar. Esto último ocurrirá cuando el `ThreadPool` reciba un mensaje indicando que debe detener la búsqueda, el cuál es enviado luego de ser leído el resultado de la búsqueda.

Si, en cambio, todos los `PowWorker` terminaran sin resultado (habiendo cada uno notificado al `ResultBuffer` de esto), el `ResultBuffer` dará como respuesta que el nonce pedido no existe.

### 3. Evaluación

#### 3.1. Especificación el equipo de prueba

* Procesador: AMD A9, 3.0 GHz, 2 cores

* RAM: 8 GB

* Sistema operativo: Linux Mint 20

#### 3.2. Datos

Los datos presentados muestran el tiempo de ejecución tanscurrido para hallar un nonce con la dificultades 2 y 3, usando 1, 2, 4, 6, 8 y 10 threads. El prefijo siempre se deja vacío.

Las pruebas fueron corridas en el equipo especificado en el punto anterior. Cada combinación de dificultad/cantidad de threads fue corrida cinco veces, y se presenta el promedio de tiempo en todos los casos.

##### 3.2.a. Con dificultad 2

| Cantidad de threads | Tiempo transcurrido |
| --- | --- |
| 1 | 407 ms |
| 2 | 538 ms |
| 4 | 550 ms |
| 6 | 796 ms |
| 8 | 538 ms |
| 10 | 617 ms |

##### 3.2.b. Con dificultad 3

| Cantidad de threads | Tiempo transcurrido |
| --- | --- |
| 1 | 20540 ms |
| 2 | 11015 ms |
| 4 | 1304 ms |
| 6 | 8181 ms |
| 8 | 2464 ms |
| 10 | 15963 ms |

##### 3.2.c. Gráfico

![](https://i.imgur.com/Pw0oq4n.png)

### 4. Análisis

#### 4.1. Dificultad < 4

Dada la abundante cantidad de nonce para las dificultades 2 y 3, y la naturaleza determínistica de la posición de los mismos, es notable que la mejor performance se corresponde con una cantidad de threads en la cuál haya un nonce cerca de alguno de los puntos de entrada para la búsqueda.

Por ejemplo, usando 4 threads en dificultad 3, el nonce encontrado es siempre el mismo: `{ 64, -124, -111, 9 }`. Si dividieramos las combinaciones en 4 sectores, se encuentra muy cerca del comienzo del cuarto sector.

La ventaja de tener un nonce cerca de algún punto de entrada usando *n* threads es acarreada a los múltiplos de *n*: con 8 y 12 threads, el nonce hayado sigue siendo siempre el mismo que con 4, y el tiempo de búsqueda sigue siendo similar, si bien un poco mayor debido a la mayor cantidad de interleavings.

#### 4.2. Golden nonce

Intentando encontrar el *golden nonce* (dificultad 4, prefijo vacío), se realizaron dos pruebas: una con cuatro threads, y otra con uno solo. Ambas recorrieron todas las combinaciones y concluyeron que no existe tal nonce.

Sin embargo, es de interés la diferencia de performance entre los escenarios: la búsqueda con cuatro threads finalizó luego de 1728705 ms (~29 min), mientras que con un solo thread, terminó recién luego de 2564721 ms (~43 min).

Este diferencia considerable en el tiempo de búsqueda se debe a que la mayor cantidad de threads hace mejor uso del tiempo ocioso del procesador.
