# Colecciones

## Decidir rapido

| Necesidad | Coleccion |
|---|---|
| Guardar varios elementos y recorrerlos | `ArrayList<T>` |
| Buscar por clave unica | `HashMap<K, V>` |
| Evitar repetidos | `HashSet<T>` |
| Contar apariciones | `HashMap<T, Integer>` |
| Agrupar por categoria | `HashMap<K, ArrayList<V>>` |

## Patrones que debes dominar

### Recorrer ArrayList

```java
for (Alumno alumno : alumnos) {
    System.out.println(alumno);
}
```

### Buscar en HashMap

```java
Alumno alumno = alumnosPorDni.get(dni);

if (alumno == null) {
    System.out.println("No existe");
} else {
    System.out.println(alumno);
}
```

### Contar apariciones

```java
HashMap<String, Integer> contador = new HashMap<>();

for (String dni : dnis) {
    contador.put(dni, contador.getOrDefault(dni, 0) + 1);
}
```

### Agrupar objetos

```java
HashMap<Integer, ArrayList<Alumno>> alumnosPorCurso = new HashMap<>();

for (Alumno alumno : alumnos) {
    int curso = alumno.getCurso();
    alumnosPorCurso.putIfAbsent(curso, new ArrayList<>());
    alumnosPorCurso.get(curso).add(alumno);
}
```

## Ejercicios de velocidad

Hazlos con cronometro. Maximo 12 minutos cada uno.

1. Dado un `ArrayList<Integer>`, muestra el mayor, el menor y la media.
2. Dado un `ArrayList<String>`, muestra las palabras que aparecen mas de una vez.
3. Dado un `HashMap<String, Alumno>`, muestra los alumnos cuyo nombre empieza por `A`.
4. Dado un `HashMap<String, Producto>`, elimina los productos con stock 0.
5. Dado un `ArrayList<Prestamo>`, cuenta prestamos por DNI.
6. Dado un `ArrayList<Alumno>`, agrupa por curso.

## Mini reto

Crea estas clases:

```text
Producto
- codigo
- nombre
- precio
- stock

Tienda
- HashMap<String, Producto> productos
```

Metodos de `Tienda`:

```text
agregarProducto(Producto producto)
buscarProducto(String codigo)
venderProducto(String codigo, int unidades)
mostrarSinStock()
calcularValorInventario()
```

Regla: `venderProducto` debe comprobar si el producto existe y si hay stock suficiente.
