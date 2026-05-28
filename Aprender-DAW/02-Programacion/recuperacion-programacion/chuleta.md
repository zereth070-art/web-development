# Chuleta rapida

## Lectura

```java
try (BufferedReader br = new BufferedReader(new FileReader("archivo.txt"))) {
    String linea;
    while ((linea = br.readLine()) != null) {
        String[] partes = linea.split(",");
    }
} catch (IOException e) {
    System.out.println("Error leyendo: " + e.getMessage());
}
```

## Escritura

```java
try (PrintWriter pw = new PrintWriter(new FileWriter("salida.txt"))) {
    pw.println("texto");
} catch (IOException e) {
    System.out.println("Error escribiendo: " + e.getMessage());
}
```

## HashMap

```java
HashMap<String, Alumno> alumnos = new HashMap<>();
alumnos.put(alumno.getDni(), alumno);

Alumno alumno = alumnos.get(dni);

if (alumno != null) {
    System.out.println(alumno);
}
```

## Contar

```java
contador.put(clave, contador.getOrDefault(clave, 0) + 1);
```

## Agrupar

```java
mapa.putIfAbsent(clave, new ArrayList<>());
mapa.get(clave).add(valor);
```

## Clase abstracta

```java
public abstract class Persona {
    private String nombre;
    private String dni;

    public Persona(String nombre, String dni) {
        this.nombre = nombre;
        this.dni = dni;
    }

    public String getDni() {
        return dni;
    }

    public abstract boolean puedePedirLibros();
}
```

## Herencia

```java
public class Alumno extends Persona {
    public Alumno(String nombre, String dni) {
        super(nombre, dni);
    }

    @Override
    public boolean puedePedirLibros() {
        return true;
    }
}
```

## Checklist de entrega

- Puedo explicar que coleccion he usado y por que.
- Compruebo campos antes de acceder a posiciones del array.
- Inicializo colecciones en el constructor.
- No tengo objetos importantes en `null` sin control.
- Uso `try-with-resources`.
- El `main` coordina, no hace todo.
- Los nombres de metodos dicen lo que hacen.
- Hay pruebas con caso normal, vacio, duplicado y dato mal formado.
