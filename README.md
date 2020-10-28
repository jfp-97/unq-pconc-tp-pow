# Programación concurrente - UNQ

## Trabajo práctico: Proof of Work

### Autor

* Nombre: Perez, Juan Francisco

* E-mail: juanf.perez.97@gmail.com

* Nro. de legajo: 50775

### Introducción

#### Dominio

El programa presentado busca encontrar una secuencia de cuatro bytes (llamada *nonce*) tal que su concatenación con otra secuencia de bytes ingresada por el usuario produzca un hash que comience con una cantidad de ceros acorde con la especificada como dificultad, también ingresada por el usuario.

Se permite también configurar la cantidad de thread que van a trabajar en paralelo para buscar el nonce: todas las combinaciones posibles de nonce serán repartidas equitativamente entre ellos, de manera tal que cada uno comienza a buscar desde el principio hasta, potencialmente, el final de su rango.

En caso de que terminaran todos los thread de barrer su rango de combinaciones posibles sin que ninguno haya hallado un nonce que satisfaga la dificultad pedida, se le informará al usuario.

#### Diseño

El programa consta de un monitor `ThreadPool` al cual se le pide comenzar la búsqueda, e inmediatamente después se trata de leer de él el resultado obtenido. Esto bloqueará al thread principal del programa hasta que en `ThreadPool` haya sido escrita una respuesta.

Los thread `PowWorker` concen el rango de valores que deben chequear a través de un objeto `WorkUnit`, el cuál consumen de un monitor `Buffer`. El thread productor `WorkUnitProducer` se encarga de suplir al buffer de unidades de trabajo a medida que éstas son consumidas.

Un `PowWorker` trabajará bien hasta encontrar un nonce que satisfaga lo requerido, termine de barrer su rango de valores, o se le notifique que debe parar. Esto último ocurrirá cuando el `ThreadPool` reciba un nonce de algún `PowWorker` que lo haya encontrado.

Si, en cambio, todos los `PowWorker` terminaran sin resultado (habiendo cada uno notificado al `ThreadPool` de esto), el `ThreadPool` dará como respuesta que el nonce pedido no existe.

### Evaluación

### Análisis
