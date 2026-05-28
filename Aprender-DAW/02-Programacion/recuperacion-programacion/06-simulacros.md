# Simulacros de examen

Hazlos con tiempo. Primero intenta resolver sin mirar apuntes. Luego corrige con la chuleta.

## Simulacro 1: Biblioteca

Tiempo objetivo: 70 minutos.

Ficheros:

```text
personas.txt
tipo,nombre,dni,extra1,extra2
```

Ejemplos:

```text
ALUMNO,Ana,111A,2,
PROFESOR,Luis,222B,Informatica,true
ALUMNO,Marcos,333C,1,
```

Tareas:

1. Crea `Persona` abstracta.
2. Crea `Alumno` y `Profesor`.
3. Lee `personas.txt`.
4. Guarda todo en `HashMap<String, Persona>`.
5. Muestra las personas que pueden pedir libros.
6. Ignora lineas mal formadas sin parar el programa.

Criterios:

- Si buscas por DNI, debe ser rapido.
- El `main` no debe tener toda la logica.
- Debes usar `try-with-resources`.

## Simulacro 2: Tienda

Tiempo objetivo: 80 minutos.

Fichero:

```text
productos.txt
codigo,nombre,precio,stock
```

Tareas:

1. Crea `Producto`.
2. Crea `Tienda` con `HashMap<String, Producto>`.
3. Carga productos desde fichero.
4. Permite vender unidades de un producto.
5. Muestra productos con stock bajo, menor que 5.
6. Escribe `resumen.txt` con el valor total del inventario.

Casos raros:

- Codigo repetido.
- Precio mal escrito.
- Stock negativo.
- Producto inexistente al vender.

## Simulacro 3: Gestor de notas

Tiempo objetivo: 60 minutos.

Fichero:

```text
notas.txt
dni,asignatura,nota
```

Tareas:

1. Calcula media por alumno.
2. Muestra aprobados y suspensos.
3. Guarda en `ranking.txt` los alumnos ordenados por media descendente.
4. Ignora notas no numericas.

Pista:

```java
HashMap<String, ArrayList<Double>> notasPorDni = new HashMap<>();
```

## Simulacro 4: Recuperacion final

Tiempo objetivo: 90 minutos.

Tema: gimnasio.

Ficheros:

```text
socios.txt
tipo,nombre,dni,extra
```

Ejemplos:

```text
BASICO,Ana,111A,3
PREMIUM,Luis,222B,entrenador
```

Reglas:

- `Socio` es abstracto.
- `SocioBasico` puede reservar si tiene menos de 5 reservas.
- `SocioPremium` puede reservar siempre.
- Se guardan socios por DNI.
- Se lee un fichero `reservas.txt` con `dni,actividad`.
- Se ignoran reservas de socios inexistentes.
- Se escribe `autorizados.txt` con socios que pueden reservar.

Este simulacro mezcla todo: ficheros, colecciones, excepciones y POO.
