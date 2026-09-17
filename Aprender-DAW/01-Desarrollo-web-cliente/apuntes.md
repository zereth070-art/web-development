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

### Mini-taller: expresiones regulares (lo que valida el email del registro)

Una **expresión regular (regex)** es un patrón de texto. Se escribe entre `/ /`
y deja que JS "detecte" lo que pides. Usamos `escapaHtml` con regex sin saberlo:
`/&/g` busca todos los `&` — la `g` final = *global* (todas, no solo la primera).

- `[a-z]` = UN carácter; `[0-9]` = una cifra; `[a-zA-Z]` = letra de cualquier caja.
- **El patrón manda:** si el texto no tiene lo que pides, `match()` no inventa nada
  (devuelve las pocas coincidencias que haya) y `replace()` no toca nada.
- **`match()`** recolecta las coincidencias (array); **`replace()`** las cambia.
- **`+`** = la pieza anterior **una o más** veces (exige mínimo 1: "campo con contenido").
- **`*`** = **cero o más** veces (perdona la ausencia: "puede ir vacío").
- **El patrón camina hasta la frontera:** `[a-z]+` se traga todas las letras seguidas
  y se detiene en el primer carácter que no es letra (un `@`, un número...).

Diferencia vista con `texto2 = "solo@ @fin @ @medio@medio"`:
`/[a-z]+@[a-z]+/` → solo `medio@medio` (exige letras a ambos lados).
`/[a-z]*@[a-z]*/` → hasta los `@` solitarios.

El **email** se valida con `test()` — el corazón del chequeo del formulario:

```js
const valido = /^[a-z]+@[a-z]+$/.test("hola@prueba");   // true (^ inicio, $ final)
```

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

### AJAX clásico (el temario pregunta esto) vs `fetch` (lo que usamos)

- **AJAX** (Asynchronous JavaScript And XML) = la TÉCNICA de hablar con el servidor
  **sin recargar la página**. No es un lenguaje: es el concepto. `fetch` ES AJAX.
- Antes de `fetch`, el estándar era **`XMLHttpRequest`** (XHR). Mismo viaje, sintaxis fea:

```js
const xhr = new XMLHttpRequest();              // crear la petición
xhr.open("GET", "/api/tasks");                 // configurar método + URL
xhr.onload = () => { console.log(xhr.responseText); };  // qué hacer cuando llegue
xhr.send();                                    // lanzar
```

- `fetch` = evolución moderna del mismo concepto: más corto, devuelve **promesas**
  (`await`), y maneja JSON con `res.json()` en vez de `xhr.responseText`.
- Reconocerlo en examen: si ves `new XMLHttpRequest()`, `onreadystatechange`,
  `readyState`, `responseText`, `send()` → están hablando de **AJAX clásico/XHR**.
- **Regla de oro:** el resultado del tema es saber que AMBOS hacen lo mismo
  (petición al servidor desde JS, sin recarga) — `fetch` es el moderno, XHR el abuelo.

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

### El CICLO DE VIDA de un dato (lo que une fetch, el estado y XSS)

No son 3 temas sueltos: es EL MISMO dato recorriendo un camino. El conducto que lo
transporta (fetch) y la parada donde se pinta (renderTasks) son los 2 puntos frágiles.

```
[1] ENTRADA              [2] MOSTRAR                [3] RIESGO
fetch / res.json()  →    estado + applySearch() →   innerHTML sin escapar
(how the data arrives)   (how it lives / paints)    (si falla aquí → XSS)
```

La conexión killer: **la MISMA función que pinta es la que puede matarte.**
`renderTasks()` es la que protagonizó el bug del buscador (pintaba todo sin respetar
el filtro) *y* es la que mete `innerHTML` (donde un dato malvado se vuelve código).
Por eso la regla vale doble: **un solo camino de pintado + desinfectar lo que toca
la pantalla**.

Mi analogía (de la ronda de repaso): fetch = pedir la pizza; mostrar = enseñar qué
hay en la casa; XSS = pedir una pizza *con el ingrediente malvado* y que se la
entreguen en casa de OTRA persona — y lo que ella ve/live vuelve a mis manos. 🍕🧨

Extra que descubrí y vale preguntas de entrevista: "estado optimista" (actualizar la
UI desde la memoria en vez de re-fetch) es real y se usa, pero duplica el estado
(memoria + BD) y hay que revertir si el servidor dice que no. El re-fetch de BD que
hacemos aquí mantiene a todas las pestañas sincronizadas "sin pagar complejidad".

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

## 12. React: arquitectura SPA y estructura con Vite (apuntes de clase)

> Este trimestre se recrea **PcComponentes con React**. JS vanilla (DOM, fetch, SPA) sigue
> valiendo de fundamento; esto es el salto a componentes.

### Arquitectura: SPA / CSR (Client Side Rendering)

```
cliente web ────────────────►  servidor NODEJS
                                   │
                                   ▼
                        proyecto servicios RESTful / API REST
                                   │
                                   ▼
                             servidor BD MongoDB
```

- **Una única descarga al principio** (cuando se pide la URL inicial): `app-REACT` recibe
  el **bundle** (código JS + HTML) y ya se ejecuta en el navegador.
- **A partir de ahí se interactúa en el cliente**: nada de pedirle páginas al servidor.
- ¿¿Persistencia de datos?? El **storage del navegador no sirve** (se limpia). Para
  intercambiar datos puntuales hace falta un servidor: **API REST / RESTful**.

> Lección que ya te pagaste en pedidos: *en memoria se muere*. Aquí el profe te da la
> teoría: la data no vive en el cliente, vive en el servidor (Mongo).

### Crear un proyecto React con Vite

```bash
npm create vite@latest my-app -- --template react
```

Arranca un servidor de desarrollo en el puerto **5173** (`http://localhost:5173`).

```
directorios:
  - public   contenido estático del portal (imágenes, js de paquetes externos, ...)
  - src      contenido de la app React (ficheros de componentes .jsx)
```

### Estructura por defecto

- **`src/main.jsx`** — fichero de entrada principal de la app:

```jsx
import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'

createRoot(document.getElementById('root'))   // monta la jerarquía (árbol) de componentes
  .render(
    <StrictMode>   // 1º componente a insertar en <div id="root">
      <App />      // 2º componente hijo
    </StrictMode>,
  )
```

- **`src/App.jsx`** — componente inicial; usa hooks como `useState`:

```jsx
function App() {
  const [count, setCount] = useState(0)
  return (
    <>
      <button type="button" onClick={() => setCount((count) => count + 1)}>
        Count is {count}
      </button>
    </>
  )
}
export default App
```

- **`index.html`** — único fichero HTML (SPA).

> El **bundle** de Vite es el mismo concepto del que habla **0614 Despliegue**: construir y
> servir estáticos. Lo estás viendo en dos asignaturas.

## 13. Prácticas del temario y cómo se cubren

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

## 14. Componentes React y Virtual DOM (apuntes de clase, pasados a limpio)

### Virtual DOM (diffing / reconciliación)

React construye **en memoria** un DOM virtual a partir del DOM real. ¿Para qué? Para
evitar el **refresco/repintado continuo** que haría el motor del navegador ante
cualquier cambio del DOM desde el código (muy ineficaz).

> Aclaración: el que pinta es el motor de renderizado del navegador. Cada vez que
> tocas el DOM real, el navegador recalcula estilos, layout y repinta: eso es lo
> caro que se quiere evitar.

A partir de los nodos del DOM real, se mapea cada tag/nodo a un **objeto JS puro**:
eso son los **COMPONENTES de React**. El resultado es el **DOM virtual** (un árbol
en memoria, rápido de crear y de comparar).

Si hay un cambio (por acción del usuario y sus eventos) en alguno de esos componentes:

1. React **compara en el DOM virtual los componentes antiguos con los nuevos**
   ← busca *diferencias* (**DIFFING**).
2. **Modifica en el DOM virtual solo los objetos que han cambiado**, no todo el
   DOM (que sería lo que haría el navegador). *(El profe lo llama "REFACTORING",
   aunque el nombre técnico real es **RECONCILIACIÓN** (reconciliation). No
   confundir con "refactorizar código", que es otra cosa: reorganizar código sin
   cambiar su comportamiento.)*
3. Una vez actualizado el DOM virtual, **lo vuelca al DOM real** y el navegador pinta.

> Matiz fino: en realidad React no edita el DOM virtual "viejo": construye un árbol
> nuevo con el estado nuevo y el **diffing** calcula el conjunto mínimo de cambios
> que aplicar en el DOM real. Para clase basta con el concepto del profe.

**Ventajas:**

- **Rapidez**: no se actualiza continuamente todo el DOM.
- **Eficacia**: no repites nodos de la página; creas componentes reutilizables.
- El diffing **establece prioridades** al aplicar los cambios de los componentes
  modificados para que el usuario tenga **sensación de inmediatez**.

> Eso de "prioridades" en React moderno se llama *concurrent features* / suspensión.
> A nivel de curso, quédate con la idea: React decide qué actualizar primero para
> que la interfaz responda rápido.

### Componentes React: reglas

- Son la **unidad básica** de trabajo: un **fichero con extensión `.jsx`** (JS + HTML).
- Las primeras líneas son las **importaciones** de recursos necesarios:

```jsx
import { useState } from 'react'        // del paquete react
import './Registro.css'                 // estilos del componente
```

- Un componente es una **función JS** y **SIEMPRE empieza en MAYÚSCULA**;
  si no, React no lo reconoce (`<Registro />` funciona, `<registro />` no).

```jsx
function NombreComponente(props) {      // puede recibir props (propiedades)
  // código JS

  return <div>...</div>                 // devuelve UN único nodo HTML (o un fragment)
}

export default NombreComponente         // lo hace reutilizable
```

**Reglas de oro:**

1. Nombre en **Mayúscula** (obligatorio).
2. Devuelve **SIEMPRE un único nodo raíz**... o un **fragmento** `<> ... </>`.

> Con React moderno (16.2+) un componente puede devolver varios nodos si los
> envuelves en un **fragment** `<>...</>`, un "nodo invisible" que no crea etiqueta
> real. Es la razón del `<>` al principio de `Registro.jsx`.

3. Las **props** son los datos que el padre pasa al hijo (solo lectura).
4. `export default` hace el componente **reutilizable/importable** en otros ficheros.

### Conexión con PomodoroZion

El "renderTasks" y el repintado manual con `innerHTML` eran justo lo que React
automatiza con el diffing: tú declaras cómo debe verse la UI según el estado y
React aplica solo los cambios. Mismo concepto, sin "pintar a mano".

## 15. Práctica: Login/Registro con React (estado, props y validación)

Mini-práctica hecha en clase: recrear el formulario de login y registro de
PcComponentes como componentes React, con validación y navegación entre pantallas.

### `useState` a fondo (la "caja" que vigila React)

`useState` **no comprueba nada**. Es una "caja" que React vigila y que te devuelve
una pareja:

```jsx
const [valorActual, funcionQueLoCambia] = useState(estadoInicial);
```

- `valorActual` → para **leer** el valor (va en el `value` del input).
- `funcionQueLoCambia` → **la única forma** de cambiarlo.

Cuando llamas al setter (ej. `setExito(true)`):

1. React se entera de que algo cambió.
2. React **vuelve a ejecutar tu función-componente** (re-render).
3. Compara el árbol viejo y el nuevo (diffing) y **repinta solo lo que cambió**.

> **Por qué NO sirve `exito = true`:** React no vigila variables; solo se entera
> cuando llamas a la función del setter. Además, con `const` reasignar lanza error.

> **Regla de los hooks:** todos los `useState` van SIEMPRE arriba del componente,
> antes de cualquier `return`. Si un `return` condicionado (ej. `if (exito) return`)
> va delante de un hook, React pierde la cuenta de sus hooks y lanza el error de
> "render inconsistente". Los `return` condicionados van **después** de todos los hooks.

### Props y "levantar el estado" (lifting state up)

Solo se ve una pantalla (login o registro), decidida por un estado que vive en el
**padre común**, `App.jsx`. Los hijos **no tienen ese estado**: reciben una
**prop-función** para pedir el cambio.

```jsx
// App.jsx — el estado vive aquí
const [pantalla, setPantalla] = useState('login');

return pantalla === 'login'
  ? <Login onCambiarPantalla={() => setPantalla('registro')} />
  : <Registro onCambiarPantalla={() => setPantalla('login')} />;
```

```jsx
// Login.jsx — recibe la función por props y la usa al pinchar
function Login({ onCambiarPantalla }) {
  return (
    <p>¿No tienes cuenta?
      <button type="button" onClick={onCambiarPantalla}>Regístrate</button>
    </p>
  );
}
```

Flujo: el usuario pincha → se ejecuta la función **que vive en App** → App cambia
su estado → App re-renderiza → pinta la otra pantalla. Es el mantra:
**"las props bajan, los eventos suben"**.

> La prop se desestructura en la firma: `function Login({ onCambiarPantalla })`.
> Sin las llaves, recibirías **el objeto props entero** y `onCambiarPantalla` sería
> el objeto, no la función.

### Validación de formularios (patrón usado)

1. `validar()` devuelve un **objeto de errores** vacío si todo está bien:
   ```jsx
   const nuevosErrores = {};
   if (!!!validarEmail(email)) nuevosErrores.email = 'El email no es valido';
   return nuevosErrores;
   ```
2. `handleSubmit` llama a `validar()`; si hay errores los guarda en el estado;
   si no, marca éxito:
   ```jsx
   if (Object.keys(nuevosErrores).length > 0) setErrores(nuevosErrores);
   else setExito(true);
   ```
3. El mensaje se pinta solo si existe error:
   ```jsx
   {errores.email && <p className="error">{errores.email}</p>}
   ```
4. `noValidate` en el `<form>` para que el navegador **no meta su burbuja nativa**
   (la que salía en inglés) y mande solo nuestra validación.

**Trampa de las contraseñas vacías:** dos contraseñas vacías **son iguales**
(`'' === ''` es `true`), así que el chequeo "deben coincidir" solo no basta.
Por eso comprobamos también `password.trim() === ''` → "obligatoria".
El orden importa: en Registro se comprueba "coinciden" y luego "obligatoria"
(la última asignación gana).

### Limpiar el error al escribir (spread a la defensiva)

El error se quedaba "todo el rato" aunque corrigieras. Solución: al escribir,
borrar el error de **ese campo** sin tocar los demás:

```jsx
onChange={(e) => {
  setPassword(e.target.value);
  setErrores({ ...errores, password: undefined });
}}
```

- `...errores` → copia todos los errores que había.
- `, password: undefined` → deja el de password sin valor.
- Sin el spread, `{ password: undefined }` **destruiría los demás errores**:
  corregir el email borraría los avisos de nombre y contraseñas.

> Regla de la función flecha en JSX: una sola instrucción → `(e) => algo`; dos o
> más → `(e) => { algo; yOtraCosa; }` (con llaves). Las dos sueltas sin llaves no
> compilan.

### Trampa del botón dentro del `<form>`

Un `<button>` dentro de un `<form>` es por defecto `type="submit"`: al pulsarlo
dispara el `onSubmit`. Si solo quieres navegar (no validar), ponle
`type="button"` — lo convierte en botón "tonto" que solo hace tu `onClick`.

### DRY aplicado: validaciones compartidas

La regex del email estaba duplicada en Login y Registro. Se extrae a un módulo
común y se importa donde haga falta (misma lección que el `showToast` de
PomodoroZion):

```jsx
// src/utils/validaciones.js
export const validarEmail = (email) =>
  /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$/.test(String(email).toLowerCase());
```

```jsx
// Login.jsx y Registro.jsx
import { validarEmail } from '../../../utils/validaciones.js';
```

### Minievaluación (3/5) — repasar 1 y 2

- ✅ "Las dos contraseñas vacías pasan el chequeo de coincidencia" (trampa vacío).
- ✅ El spread mantiene los demás errores.
- 🔁 `useState`: el setter es la única vía para que React repinte; no se "comprueba".
- 🔁 props: el hijo guarda y llama la función que le da el padre; el estado vive arriba.
- ➖ El `type="button"` evita que el botón de navegación dispare el submit.

## 16. React por dentro, en cristiano (la cocina)

Mental model que haga *click* sin jerga.

### El Virtual DOM es el boceto en papel

Antes de pintar la pared de verdad, React hace un **boceto en papel** de lo que va a
pintar. Ese papel es el Virtual DOM: **objetos JS planos**, no elementos HTML reales.
Cuando algo cambia, tira el boceto viejo, hace uno nuevo y **solo pinta en la pared
lo que se ha movido** (diffing). No repinta todo.

> El JSX `<input value="hola" />` se compila a `React.createElement('input', {...})`
> que devuelve un objeto plano tipo `{ type: 'input', props: { value: 'hola' } }`.
> Ese objeto es el Virtual DOM. No es el `HTMLInputElement` del navegador.

### Componente = receta

Un componente es una **función**: la receta. React la ejecuta de arriba abajo y lo
que devuelve por `return` es el plato servido (lo que ve el usuario). Cuando un
setter cambia el estado, React **vuelve a ejecutar la receta**, vuelve a mirar el
boceto y pinta la diferencia.

| Cocina | React |
|---|---|
| Ingredientes | Estado (`useState`) |
| Pasos de cocina | Funciones (`validar()`, `handleSubmit`) |
| El plato servido | El `return` (JSX) |
| El camarero que avisa | El setter (`setPassword`) |

### `useState` = post-it + boli

```jsx
const [password, setPassword] = useState('');
```

React te da un **post-it** (`password`) y un **boli** (`setPassword`). El post-it
solo se escribe con ese boli:

- `setPassword('abc')` → avisa a React y repinta. ✅
- `password = 'abc'` → cambias el post-it en secreto y React no se entera → la
  pared no cambia nunca. ❌

El `''` es el valor de arranque: se usa **solo en el primer render**. Después,
`useState` devuelve lo que haya en su slot interno (React guarda el estado por
posición de los hooks, por eso deben ir siempre arriba y en el mismo orden).

### La regla del tomate (estado vs. no-estado)

- 🌱 **No cambia** → `const` normal, sin `useState`:
  ```jsx
  const titulo = 'Bienvenido';
  ```
  Mejor aún **fuera del componente** si no depende del estado, para no re-crearlo
  en cada render.
- 🍳 **Cambia pero solo mientras se cocina** (temporal: `const nuevosErrores` en
  `validar()`) → `const` local, se recalcula sola en cada ejecución.
- 🔴 **Cambia con el tiempo y la UI debe enterarse** → `useState` con su setter.

> Consejo: `useState` solo para lo que necesita vivir entre renders y repintar.
> Un componente sin estado (como el `Contador` hijo que solo recibe props) es
> perfectamente válido: "cocina en crudo".
