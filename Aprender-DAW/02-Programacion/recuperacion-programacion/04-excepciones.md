# Excepciones

## Idea clave

Una excepcion no es un adorno. Es una forma de separar el camino normal del camino en el que algo sale mal.

## Cuanto capturar

Captura excepciones donde puedas hacer algo util:

- Mostrar un mensaje claro.
- Ignorar una linea mala y seguir leyendo.
- Pedir de nuevo un dato.
- Registrar el error.

No captures para volver a lanzar lo mismo sin aportar nada.

## Patron recomendado

```java
public static int convertirEdad(String texto) {
    try {
        return Integer.parseInt(texto);
    } catch (NumberFormatException e) {
        return -1;
    }
}
```

O si quieres obligar a quien llama a decidir:

```java
public static int convertirEdad(String texto) throws NumberFormatException {
    return Integer.parseInt(texto);
}
```

## Excepciones tipicas

| Excepcion | Causa frecuente |
|---|---|
| `IOException` | Problemas leyendo/escribiendo ficheros |
| `FileNotFoundException` | Ruta incorrecta o fichero inexistente |
| `NumberFormatException` | Convertir texto a numero invalido |
| `NullPointerException` | Usar una referencia que vale `null` |
| `ArrayIndexOutOfBoundsException` | Acceder a posicion inexistente |

## Antipatrones

Evita esto:

```java
try {
    // codigo
} catch (RuntimeException e) {
    throw new RuntimeException(e);
}
```

Eso normalmente no soluciona nada. Solo cambia el envoltorio del error.

Mejor:

```java
try {
    // codigo
} catch (IOException e) {
    System.out.println("Error leyendo datos: " + e.getMessage());
}
```

## Ejercicios

1. Crea un metodo `leerEntero(String texto)` que devuelva `-1` si no se puede convertir.
2. Lee un fichero con edades y calcula la media ignorando lineas no numericas.
3. Lee alumnos desde CSV e ignora lineas con campos incompletos.
4. Haz que `venderProducto` lance `IllegalArgumentException` si las unidades son menores o iguales que 0.
5. Haz que `buscarProducto` devuelva `null` si no existe y que el programa principal lo gestione con un mensaje.

## Checklist antes de entregar

- El programa no revienta por una linea mal formada.
- Los mensajes de error ayudan a entender que paso.
- No hay `catch (Exception e)` si no hace falta.
- No hay `throw new RuntimeException(e)` sin motivo claro.
- Los metodos no mezclan demasiadas responsabilidades.
