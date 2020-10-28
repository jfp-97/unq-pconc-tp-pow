# Programación concurrente - UNQ

## Trabajo práctico: Proof of Work

### Autor

* Nombre: Perez, Juan Francisco

* E-mail: juanf.perez.97@gmail.com

* Nro. de legajo: 50775

### Introducción

El programa presentado busca encontrar una secuencia de cuatro bytes (llamada *nonce*) tal que su concatenación con otra secuencia de bytes ingresada por el usuario produzca un hash que comience con una cantidad de ceros acorde con la especificada como dificultad, también ingresada por el usuario.

Se permite también configurar la cantidad de thread que van a trabajar en paralelo para buscar el nonce: todas las combinaciones posibles de nonce serán repartidas equitativamente entre ellos, de manera tal que cada uno comienza a buscar desde el principio hasta, potencialmente, el final de su rango.

En caso de que terminaran todos los thread de barrer su rango de combinaciones posibles sin que ninguno haya hallado un nonce que satisfaga la dificultad pedida, se le informará al usuario.

### Evaluación

### Análisis
