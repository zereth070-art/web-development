# POO avanzado

## Como pensar clases

Una clase debe responder a:

```text
Que datos guarda?
Que sabe hacer?
Que no deberia hacer?
```

Ejemplo:

```text
Alumno
Datos: nombre, dni, curso, prestamos
Hace: puedePedirLibros(), agregarPrestamo()
No deberia hacer: leer ficheros, mostrar menus
```

## Herencia

Usa herencia cuando varias clases comparten identidad y comportamiento.

```java
public abstract class Persona {
    private String nombre;
    private String dni;

    public Persona(String nombre, String dni) {
        this.nombre = nombre;
        this.dni = dni;
    }

    public abstract boolean puedePedirLibros();
}
```

```java
public class Alumno extends Persona {
    private int curso;

    public Alumno(String nombre, String dni, int curso) {
        super(nombre, dni);
        this.curso = curso;
    }

    @Override
    public boolean puedePedirLibros() {
        return curso >= 2;
    }
}
```

## Clase abstracta o interfaz

Usa clase abstracta si hay datos comunes:

```text
Persona: nombre, dni
```

Usa interfaz si quieres exigir una capacidad:

```java
public interface Prestable {
    boolean puedePedirLibros();
}
```

## Separacion sana

En ejercicios de examen, intenta separar asi:

```text
Modelo:
- Persona
- Alumno
- Profesor
- Producto
- Prestamo

Servicio/Gestor:
- Biblioteca
- Tienda
- GestorAlumnos

Entrada/salida:
- LectorFicheros
- EscritorFicheros
- App
```

No siempre necesitas tantas clases, pero esta separacion te ayuda a no mezclar todo en `main`.

## Ejercicio guiado

Crea un sistema de biblioteca:

```text
Persona abstracta
- nombre
- dni
- puedePedirLibros()

Alumno extends Persona
- curso
- prestamosActivos
- puedePedirLibros(): true si tiene menos de 3 prestamos

Profesor extends Persona
- departamento
- fijo
- puedePedirLibros(): true siempre

Biblioteca
- HashMap<String, Persona> personas
- agregarPersona(Persona persona)
- buscarPersona(String dni)
- prestarLibro(String dni, String titulo)
- mostrarPersonasAutorizadas()
```

Extra: si `prestarLibro` recibe un DNI inexistente, muestra mensaje claro. Si existe pero no puede pedir libros, tambien.

## Fallos tipicos en constructores

Mal:

```java
public Alumno(String nombre, String dni) {
    super(nombre, dni);
    this.curso = curso;
    this.librosPrestados = librosPrestados;
}
```

Aqui `curso` y `librosPrestados` no vienen como parametros ni se inicializan. Resultado: `curso` vale 0 y `librosPrestados` puede quedar `null`.

Bien:

```java
public Alumno(String nombre, String dni, int curso) {
    super(nombre, dni);
    this.curso = curso;
    this.librosPrestados = new HashMap<>();
}
```
