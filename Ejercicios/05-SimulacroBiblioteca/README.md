# 05 - Simulacro Biblioteca

Tiempo objetivo: 70 minutos.

## Enunciado

Lee `personas.txt` con formato:

```text
tipo,nombre,dni,extra1,extra2
```

Ejemplos:

```text
ALUMNO,Ana,111A,2,
PROFESOR,Luis,222B,Informatica,true
```

## Requisitos

1. Crea `Persona` abstracta.
2. Crea `Alumno` y `Profesor`.
3. Guarda en `HashMap<String, Persona>` usando DNI.
4. Ignora lineas mal formadas.
5. Muestra personas que pueden pedir libros.
6. Escribe esas personas en `autorizados.txt`.

## Consejo

Primero hazlo todo en `App.java`. Cuando funcione, separa clases.
