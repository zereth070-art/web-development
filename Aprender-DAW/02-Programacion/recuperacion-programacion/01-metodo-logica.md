# Metodo para resolver ejercicios

Tu problema no parece ser "no se Java". Parece mas bien: empiezas a programar antes de tener el mapa mental cerrado. Este metodo es para ganar consistencia.

## Plantilla mental de 2 minutos

Antes de tocar codigo:

```text
Que me dan?
Que tengo que devolver/mostrar/guardar?
Que entidades reales aparecen?
Que coleccion encaja?
Que puede fallar?
```

Ejemplo:

```text
Problema: cargar alumnos desde alumnos.txt y mostrar los que pueden pedir libros.

Que me dan?
Un fichero con lineas de alumnos.

Que tengo que devolver/mostrar/guardar?
Guardar alumnos y mostrar algunos.

Que entidades reales aparecen?
Alumno, Libro/Prestamo si el ejercicio lo pide.

Que coleccion encaja?
HashMap<String, Alumno> si busco por DNI.
ArrayList<Alumno> si solo recorro.

Que puede fallar?
Fichero no existe, linea mal formada, numero invalido, DNI repetido.
```

## Algoritmo antes de codigo

Escribe siempre los pasos en castellano:

```text
1. Crear mapa vacio.
2. Abrir fichero con try-with-resources.
3. Leer cada linea.
4. Separar por comas.
5. Validar cantidad de campos.
6. Crear objeto.
7. Meter objeto en mapa usando DNI.
8. Cerrar automaticamente.
```

Luego conviertes cada paso a Java.

## Como elegir coleccion rapido

Usa esta regla:

- Necesito mantener orden y recorrer: `ArrayList`.
- Necesito buscar rapido por clave: `HashMap`.
- Necesito evitar duplicados: `HashSet`.
- Necesito contar veces que aparece algo: `HashMap<T, Integer>`.
- Necesito agrupar objetos por categoria: `HashMap<Categoria, ArrayList<Objeto>>`.

## Como no perder velocidad

Haz primero la version fea pero correcta:

1. Lee datos.
2. Guarda datos.
3. Muestra resultado.
4. Despues separas en metodos.
5. Despues limpias excepciones.

Primero que funcione. Luego lo peinas.

## Ejercicio de entrenamiento

Para cada enunciado, no programes. Solo rellena entrada, salida, entidades, coleccion y fallos.

1. Leer productos desde `productos.txt` y mostrar los que tengan stock menor que 5.
2. Leer ventas y calcular total vendido por DNI de cliente.
3. Guardar alumnos por curso y mostrar cuantos hay en cada curso.
4. Leer libros prestados y mostrar que usuario tiene mas prestamos.
5. Cargar profesores y alumnos en una unica lista de personas autorizadas.

Cuando tardes menos de 2 minutos por enunciado, vas bien.
