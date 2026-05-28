# 07 - Entreno 1 hora

Objetivo: practicar velocidad. No busques hacerlo perfecto desde el principio.

## Tema

Gestion de reservas de aulas.

## Tiempo total

60 minutos.

## Reglas de entrenamiento

Durante los primeros 25 minutos no puedes hacer getters, setters ni clases extra que no sean necesarias.

Primero debes conseguir:

1. Leer ficheros.
2. Guardar datos en colecciones.
3. Resolver la logica principal.
4. Mostrar resultados.

Despues mejoras.

## Ficheros

`datos/aulas.txt`

```text
codigo,nombre,capacidad,tipo
```

`datos/reservas.txt`

```text
dniProfesor,codigoAula,alumnos
```

## Enunciado

Crea un programa que:

1. Lea las aulas desde `datos/aulas.txt`.
2. Guarde las aulas en un `HashMap<String, Aula>` usando el codigo como clave.
3. Lea las reservas desde `datos/reservas.txt`.
4. Una reserva es valida si:
   - el aula existe;
   - el numero de alumnos es menor o igual que la capacidad del aula;
   - el numero de alumnos es mayor que 0.
5. Muestre por consola las reservas validas.
6. Muestre por consola las reservas rechazadas con una causa sencilla.
7. Escriba en `salida/reservas_validas.txt` las reservas validas.

## Extra si sobra tiempo

8. Cuenta cuantas reservas validas tiene cada aula.
9. Muestra el aula con mas reservas validas.
10. Anade `toString()` a `Aula`.

## Plan de 60 minutos

### Minutos 0-5: mapa mental

Escribe en papel o comentario:

```text
Entrada:
Salida:
Coleccion:
Errores:
Pasos:
```

### Minutos 5-20: clase minima y lectura de aulas

Crea solo:

```java
class Aula {
    String codigo;
    String nombre;
    int capacidad;
    String tipo;
}
```

Nada mas.

### Minutos 20-35: lectura de reservas y validacion

Lee `reservas.txt`, busca el aula en el `HashMap` y decide si la reserva vale.

### Minutos 35-45: escritura

Crea la carpeta `salida` y escribe las reservas validas.

### Minutos 45-55: errores y lineas malas

Ignora lineas mal formadas y numeros invalidos sin romper el programa.

### Minutos 55-60: limpieza minima

Mejora nombres, quita duplicacion obvia y prueba otra vez.

## Comandos

Desde esta carpeta:

```powershell
javac -d bin src\*.java
java -cp bin App
```

## Pista importante

No empieces creando una clase `Reserva` si eso te bloquea. Puedes guardar las reservas validas como `String` al principio:

```java
ArrayList<String> reservasValidas = new ArrayList<>();
```

Si sobra tiempo, ya haces la clase.
