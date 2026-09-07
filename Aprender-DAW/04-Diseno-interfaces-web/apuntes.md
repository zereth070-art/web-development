# Apuntes: Diseño de interfaces web

> Apuntes construidos a partir del frontend de **PomodoroZion** y del curso
> **Responsive Web Design** de freeCodeCamp que estoy haciendo.

## 1. Jerarquía visual

Ordenar lo que se ve por importancia: lo que el ojo debe leer primero va arriba y/o más grande.

- Títulos (`h1`, `h2`...) organizan la página.
- Un "botón de acción principal" debe destacar sobre el resto.
- El contraste entre lo importante y lo secundario guía al usuario.

## 2. Color

- **Color de fondo vs. color de contenido**: debe haber contraste suficiente para leer.
- En PomodoroZion usamos un esquema de color con foco en la app (estilos en `style.css`).
- En el ejercicio de "business card" usé `rosybrown` como fondo de página.
- Paleta: elegir una **paleta reducida y consistente** (más fiable que muchos colores sueltos).

```css
body {
  background-color: rosybrown;      /* color de fondo */
  font-family: Arial, sans-serif;    /* tipografía */
}
```

## 3. Tipografía

- `font-family` define la fuente; se pueden dar **fallbacks** separados por coma:
  `font-family: Arial, sans-serif;` -> "prueba Arial; si no, una sans-serif genérica".
- El sistema de fallbacks hace más resistente el diseño.
- Tamaño de texto legible; los títulos más grandes que el cuerpo.

## 4. Espaciado y el modelo de caja (box model)

Cada elemento es una caja: contenido + padding + borde + margen.

```css
.business-card {
  width: 300px;
  padding: 20px;                 /* espacio DENTRO del borde */
  margin-top: 100px;             /* espacio FUERA (arriba) */
  margin-left: auto;             /* centrar horizontalmente */
  margin-right: auto;
  text-align: center;
  font-size: 16px;
}
```

- **Padding**: espacio entre el contenido y el borde (por dentro).
- **Margin**: espacio entre la caja y las demás (por fuera).
- **`margin-left: auto; margin-right: auto;` + un `width`** -> centra el elemento.

## 5. Componentes e interfaces

- Reutilizar **clases** (`business-card`, `profile-image`, `portfolio-link`) en vez de
  estilar elemento por elemento.
- Los componentes se componen: foto + nombre + puesto + empresa + enlaces.
- Separar estructura (HTML), estilo (CSS) y comportamiento (JS).

## 6. Responsive design (el foco del curso que estoy haciendo)

Hacer que el diseño **se adapte a cualquier pantalla** (móvil, tablet, escritorio).

- Imágenes fluidas con `max-width: 100%` (que nunca desborden):
  ```css
  .profile-image {
    max-width: 100%;
  }
  ```
- Enlaces sin subrayado se pueden estilar así:
  ```css
  a {
    text-decoration: none;
  }
  ```
- La base del responsive: **porcentajes y max-width** en vez de anchos fijos absolutos.

### (Pendiente de profundizar en el curso)

- **Flexbox** (disposición en fila/columna flexible).
- **CSS Grid** (layout en rejilla de 2D).
- **Media queries** (`@media (max-width: 600px) { ... }`) para ajustar en pantallas pequeñas.
- Unidades relativas (`rem`, `em`, `vh`, `vw`).

## 7. Wireframes y prototipos

- **Wireframe**: esquema en blanco y negro de la estructura (muy rápido de dibujar).
- **Prototipo**: versión más acabada que se puede probar con un usuario.
- Se hacen ANTES de escribir el HTML/CSS final para definir dónde va cada cosa.

## 8. Accesibilidad (aplicada al curso)

- `alt` en imágenes con descripción significativa (p.ej. "a red flower").
- Texto de enlaces claro (uso del texto visible como "Portfolio" / "Twitter", no "click aquí").
- Contraste adecuado texto/fondo.
- La accesibilidad no es un lujo: es para que pueda usarlo todo el mundo.

## 9. Prácticas del temario y cómo se cubren

- [x] Rediseñar una página simple -> retoques a PomodoroZion.
- [x] Crear un sistema de botones -> estilos de botones de la app.
- [ ] Crear una tabla responsive (pendiente).
- [ ] Diseñar un formulario largo (pendiente).
- [ ] Mejorar accesibilidad de una interfaz (empezado con alt/contraste).

## Dudas pendientes

- [ ] Flexbox y Grid (en curso en freeCodeCamp).
- [ ] Media queries para que PomodoroZion se vea bien en móvil.

## Repaso

- [ ] Lo entiendo.
- [ ] Lo he practicado (estilos de la app + ejercicios de responsive).
- [ ] Podría explicarlo a otra persona.
