# Apuntes: Desarrollo web en cliente (JavaScript en el navegador)

> Apuntes construidos a partir del frontend de **PomodoroZion**:
> JS moderno, DOM, eventos, formularios, Fetch, y dos retos de seguridad
> (XSS y un sistema de notificaciones toast).

## 1. JavaScript moderno (sintaxis que usamos)

- **const / let** para declarar variables (evita `var`).
- **Arrow functions**: `(x) => x * 2` (más cortas que `function`).
- **Template literals** (comillas invertidas) para interpolar: `` `Hola ${nombre}` ``.
- **async/await** para código asíncrono legible (en vez de cadenas de `.then`).
- **Destructuring y spread**: `const { id, title } = tarea`.

## 2. El DOM (Document Object Model)

El navegador convierte el HTML en un árbol de objetos. Con JS lo recorremos y modificamos.

```js
document.getElementById("task-input")  // pillo un elemento por su id
element.value                          // su valor (input)
element.innerHTML = "..."              // su HTML interior
element.classList.add("oculto")        // añadir/quitar clases CSS
element.addEventListener("click", f)   // escuchar eventos
```

> Patrón que usamos: **seleccionar un elemento, colgarle un `addEventListener`,
> y de dentro leer campos o mostrar resultados** (p. ej. leer la tarea y listarla).

## 3. Eventos

Un **evento** es una acción que el navegador detecta: clic, teclado, envío de formulario.

- `click` en botones.
- `submit` en formularios -> casi siempre `e.preventDefault()` para que no recargue la página.
- `change` / `input` en campos.
- `keydown` / `keyup` para teclado.

```js
form.addEventListener("submit", async (e) => {
  e.preventDefault();            // no recargar la página
  const titulo = input.value;
  // ...enviar a la API
});
```

## 4. Formularios

- Capturar valores de inputs y validar antes de enviar.
- **Nunca fiarse solo del frontend**: es bueno validar aquí para dar feedback rápido,
  pero el backend vuelve a validar.
- Evitar recargar la página al enviar (`preventDefault`).

## 5. Fetch y APIs (conectar frontend con backend)

`fetch` hace peticiones HTTP desde el navegador y devuelve una promesa.

```js
const res = await fetch(`${API_URL}/tasks`, {
  method: "POST",
  headers: { "Content-Type": "application/json" },
  body: JSON.stringify({ title: "Mi tarea" }),   // 'Authorization' con la sesión si hace falta
});
const data = await res.json();
```

- `GET` para leer, `POST` para crear, `PUT` para actualizar, `DELETE` para borrar.
- `await res.json()` transforma la respuesta a objeto JS.
- La URL de la API suele estar centralizada (`const API_URL = "/api/tasks"`).

## 6. Estado en el cliente y "SPA"

- **Estado** = los datos "vivos" de la interfaz (lista de tareas, usuario conectado...).
- Tras crear/modificar/borrar, **recargamos la lista desde el servidor** para que UI y datos
  cuadren.
- Un **SPA** (Single Page Application) actualiza partes de la página sin recargarla.
  Nuestro `script.js` hace eso: una sola página que se repinta según la acción.

## 7. Reto: escape de HTML (seguridad XSS) — concepto clave

Cuando insertamos datos del usuario en el HTML (`innerHTML`), un texto malicioso como
`<img src=x onerror=...>` **se ejecuta**. Eso es **XSS (Cross-Site Scripting)**.

La defensa: **escapar el HTML** = convertir caracteres peligrosos en entidades seguras,
SIEMPRE con `&` primero:

```js
function escapeHtml(str) {
  return str
    .replace(/&/g, "&amp;")
    .replace(/</g, "&lt;")
    .replace(/>/g, "&gt;")
    .replace(/"/g, "&quot;")
    .replace(/'/g, "&#039;");
}
```

- `&` -> `&amp;`
- `<` y `>` -> `&lt;` / `&gt;` (los corchetes ya no abren etiquetas)
- `"` y `'` -> entidades de comillas

Así el navegador lo muestra como **texto**, no como HTML ejecutable.

> Regla: **nunca inyectar datos del usuario en `innerHTML` sin escapar**.
> Esto es seguridad de alto valor y difícil de "ver" — por eso es un gran logro.

## 8. Reto: notificaciones tipo "toast" y DRY

Reemplazamos un montón de `alert()` y 3 funciones repetidas por **una sola función
reutilizable** (`showToast`):

```js
function showToast(mensaje) {
  const toast = document.getElementById("toast");
  toast.textContent = mensaje;
  toast.classList.add("visible");
  setTimeout(() => toast.classList.remove("visible"), 3000);  // se oculta sola
}
```

### Principio DRY (Don't Repeat Yourself)

Había 3+ funciones casi iguales (`showAuthError`, `showTaskError`, más los `alert`).
**DRY = si repites el mismo código, extráelo a UNA función y úsala en todos lados.**

- Refactorificar = **reorganizar código sin cambiar su comportamiento**, para dejarlo más limpio.
- Ventajas: menos duplicación, tocar en un solo sitio si algo cambia, más fácil de leer.
- Es lo que separa código "amateur" de código "profesional".

## 9. Reto: buscador con filtros (filtrado en el cliente)

Un buscador que filtra la lista **sin consultar al servidor** (los datos ya están en memoria).

### La pieza clave: centralizar el pintado en UNA función

Antes del refactor, cada acción repintaba la lista a su manera. Ahora hay **una sola
función que pinta** (`renderTasks`) y un **único punto donde se decide el filtro**:

```js
function applySearch() {
  const texto = taskSearch.value.trim().toLowerCase();
  const filtradas = allTasks.filter((t) => t.title.toLowerCase().includes(texto));
  renderTasks(filtradas);
}
```

- `.trim()` quita espacios sobrantes (que " hola " buscara igual).
- `.toLowerCase()` hace la búsqueda **insensible a mayúsculas** ("HOLA" == "hola").
  Hay que bajar a minúsculas AMBOS lados (el texto y el título).
- `.includes()` mira si la cadena contiene el texto en cualquier posición.

### Por qué es así (el bug del "solo aparecían al buscar")

La función que trae los datos del servidor (`loadTasks`) **no devolvía a llamar al filtro**:
la lista arrancaba vacía y lo único que la pintaba era el buscador. La clave del patrón:

```js
async function loadTasks() {
  const response = await fetch(API_URL);
  allTasks = await response.json();
  applySearch();        // respeta lo que haya escrito el usuario
}
```

> Regla: **toda recarga de datos debe pasar por el MISMO camino de pintado** que el buscador.
> Si un botón (crear, +1 pomodoro, eliminar) repinta de otra forma, se pierden los filtros
> y la UI queda "mentirosa" (se ve toda la lista aunque busques "hola").

### El mensaje "No se encontraron tareas"

Va DENTRO de `renderTasks`, no en el listener del buscador, para que aplique siempre
(también cuando la lista esté vacía de verdad):

```js
if (filtradas.length === 0) {
  taskList.innerHTML = "<li class='empty'>No se encontraron tareas</li>";
  return;
}
```

## 10. Reto (secundario): manejo de sesión en el cliente

Cuando el servidor pierde la sesión (p. ej. tras un redeploy), las peticiones devuelven
**401/403**. Si el frontend las ignora, se queda en un **bucle infinito** de errores.

Solución en `poll()`:

```js
if (response.status === 401 || response.status === 403) {
  sessionAlive = false;
  if (ws) ws.close();
  showAuth();            // vuelve a mostrar el login
  return;
}
```

- Detectar el error HTTP (no es solo "funciona o no").
- Cortar el bucle (WebSocket y polling) cuando la sesión ha muerto.
- Devolver al usuario al login de forma explícita.

> Lección: **el frontend debe reaccionar al estado real de la API**. "No hace nada" y
> "no deja loguear" suelen ser JS roto por un error de filtrado/carga, no magia.

## 11. Accesibilidad frontend (contigo empezando en los retos)

A destacar de cara al temario:
- Texto de enlaces descriptivo ("Portfolio", no "click aquí").
- Los `alt` en las imágenes (descripción significativa).
- Contraste de color y feedback claro de errores (el toast da feedback audible/visible).
- `_blank` en enlaces externos.

## 12. Prácticas del temario y cómo se cubren

- [x] Lista de tareas -> CRUD de tareas en `script.js`.
- [x] Consumo de API -> `fetch` a `/api/tasks` y `/api/auth`.
- [x] Formularios -> registro, login, creación de tarea, cambio de contraseña.
- [x] SPA -> una sola página que se repinta sin recargar.
- [x] Buscador con filtros -> reto completado (sección 9).
- [ ] Carrito de compra / panel admin (fuera de este proyecto, retos aparte).

## Dudas pendientes

- [x] Buscador con filtros sobre la lista de tareas -> hecho.
- [ ] Autenticación en el frontend (cómo se guarda/envía la sesión).

## Repaso

- [ ] Lo entiendo.
- [ ] Lo he practicado (frontend real de PomodoroZion + retos XSS/toast/buscador/sesiones).
- [ ] Podría explicarlo a otra persona.
