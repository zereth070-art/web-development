# Lectura y escritura de ficheros

## Patron base para leer un TXT/CSV

```java
try (BufferedReader br = new BufferedReader(new FileReader("datos.txt"))) {
    String linea;

    while ((linea = br.readLine()) != null) {
        String[] partes = linea.split(",");

        if (partes.length != 3) {
            System.out.println("Linea ignorada: " + linea);
            continue;
        }

        String nombre = partes[0].trim();
        String dni = partes[1].trim();
        int edad = Integer.parseInt(partes[2].trim());

        // crear objeto o guardar datos
    }
} catch (IOException e) {
    System.out.println("No se pudo leer el fichero: " + e.getMessage());
} catch (NumberFormatException e) {
    System.out.println("Hay un numero con formato incorrecto.");
}
```

## Patron base para escribir

```java
try (PrintWriter pw = new PrintWriter(new FileWriter("salida.txt"))) {
    pw.println("Ana,12345678A,20");
    pw.println("Luis,87654321B,22");
} catch (IOException e) {
    System.out.println("No se pudo escribir el fichero: " + e.getMessage());
}
```

Sí: con `BufferedWriter`, si usas `newLine()` tú controlas el salto:

```java
BufferedWriter bw = new BufferedWriter(new FileWriter("datos.txt"));

bw.write("Primera linea");
bw.newLine();
bw.write("Segunda linea");

bw.close();
```

`BufferedWriter` no tiene `println()`. Eso lo tiene `PrintWriter`.

## Borrar lineas de un fichero


Para **borrar líneas de un fichero**, no se borra “en medio” directamente. Lo normal es:

1. Lees todas las líneas.
2. Te quedas solo con las que quieres conservar.
3. Reescribes el fichero.

Ejemplo: borrar líneas que contengan `"BORRAR"`:

```java
import java.io.*;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        ArrayList<String> lineasBuenas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("datos.txt"))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                if (!linea.contains("BORRAR")) {
                    lineasBuenas.add(linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo: " + e.getMessage());
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("datos.txt"))) {
            for (String linea : lineasBuenas) {
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error escribiendo: " + e.getMessage());
        }
    }
}
```

Idea clave: para borrar una línea, realmente reconstruyes el archivo sin esa línea.

## Fallos tipicos

- No comprobar `partes.length` antes de acceder a `partes[2]`.
- Hacer `Integer.parseInt()` sin pensar que puede fallar.
- Usar rutas relativas sin saber desde donde ejecutas.
- Capturar `Exception` para todo y ocultar el problema real.
- Cerrar ficheros manualmente cuando `try-with-resources` ya lo hace.

## Ejercicios

### Nivel 1

Lee `alumnos.txt` con formato:

```text
nombre,dni,curso
```

Muestra solo los alumnos de curso mayor o igual que 2.

### Nivel 2

Lee `productos.txt`:

```text
codigo,nombre,precio,stock
```

Crea objetos `Producto` y guardalos en un `HashMap<String, Producto>` usando `codigo` como clave.

Muestra:

- Productos sin stock.
- Producto mas caro.
- Valor total del almacen: `precio * stock`.

### Nivel 3

Lee `prestamos.txt`:

```text
dni,tituloLibro,diasPrestado
```

Si una linea tiene menos de 3 campos, ignorala. Si `diasPrestado` no es numero, ignorala tambien.

Guarda cuantos libros tiene cada DNI:

```java
HashMap<String, Integer> prestamosPorDni = new HashMap<>();
```

### Nivel examen

Crea un programa que:

1. Lea usuarios desde `usuarios.txt`.
2. Lea prestamos desde `prestamos.txt`.
3. Asocie prestamos a usuarios existentes.
4. Guarde en `morosos.txt` los usuarios con algun prestamo de mas de 30 dias.
5. No se caiga si hay lineas mal formadas.

Pista: primero carga usuarios en `HashMap<String, Usuario>`.
