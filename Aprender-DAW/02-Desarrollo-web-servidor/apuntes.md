# Apuntes: Desarrollo web en servidor (Spring Boot)

> Apuntes construidos a partir de la API de **PomodoroZion**:
> Spring Boot 4 + Java + Spring Security, con login, registro, CRUD de tareas
> y cambio de contraseña.

## 1. Servidores web y el modelo cliente-servidor

- El **cliente** (navegador) envía peticiones HTTP.
- El **servidor** (Spring Boot) recibe, procesa, toca la BD y devuelve respuestas (JSON).
- Spring Boot incluye un servidor HTTP embebido (Tomcat) -> no hay que montar uno aparte.
- Las rutas por defecto: el puerto se configura con `server.port=${PORT:8080}`
  (usar el puerto que dé el entorno, y 8080 por defecto).

## 2. Rutas y Controladores (Controllers)

Un **Controller** expone los endpoints de la API. Un endpoint = URL + método HTTP + acción.

- `@RestController` -> devuelve JSON directamente.
- `@RequestMapping("/api/tasks")` -> prefijo común del controlador.
- `GetMapping`, `PostMapping`, `PutMapping`, `DeleteMapping` -> métodos HTTP.

```java
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAll() { ... }
    @PostMapping
    public ResponseEntity<TaskDTO> create(@Valid @RequestBody TaskCreateDTO dto) { ... }
}
```

> Lección aprendida: **una ruta clara y consistente por recurso** (`/api/tasks` para todo
> el CRUD de tareas) en vez de rutas sueltas por acción. Evita enlaces rotos y duplicados.

### API REST (buenas prácticas)

- Usar **sustantivos en plural**: `/api/tasks` (no `/api/getTasks`).
- El **método HTTP** ya dice la acción: GET=leer, POST=crear, PUT=actualizar, DELETE=borrar.
- Prefijo `/api/...` separa las rutas de datos de las de páginas.
- Devolver **status codes** correctos: 200 OK, 201 Created, 400 Bad request, 401/403 sin permiso.

## 3. Modelos y persistencia (JPA)

- `@Entity` -> una clase que se guarda como fila en la BD.
- `@Id @GeneratedValue` -> la clave primaria autoincremental (`id`).
- Unos campos por clase: en `User` -> `id`, `username`, `passwordHash`, `createdAt`.
- En `Task` -> `id`, `title`, `done`, `userId` (a qué usuario pertenece).

**Referencias entre tablas:** en estas entidades guardo `Long userId` (solo el id),
no una relación JPA con `@ManyToOne`. Es más simple, pero **no hay borrado en cascada**
automático en la BD: si borro un usuario, hay que borrar sus datos a mano.
Aquí vemos el debate entre "sencillo" y "correcto con FK/cascada".

## 4. Servicios (Services)

Capa intermedia entre el Controlador y el Repositorio. Contiene la **lógica de negocio**:
qué se puede y qué no, cálculos, reglas.

```java
@Service
public class TaskService {
    public TaskDTO createTask(TaskCreateDTO dto, Long userId) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setUserId(userId);   // la tarea pertenece a un usuario
        return toDTO(taskRepository.save(task));
    }
    private Task findOwnedTask(Long id, Long userId) {
        Task task = taskRepository.findById(id).orElseThrow(...);
        if (!task.getUserId().equals(userId)) {   // solo el dueño puede tocarla
            throw new ...("No tienes permiso");
        }
        return task;
    }
}
```

> Idea clave: **cada usuario solo ve y modifica SUS datos** (el `userId` se filtra en todo).
> Eso es la base de la seguridad de a quién pertenece la información.

## 5. Validación

Con `@Valid` + anotaciones de validación en los DTO:

```java
public class CambiarPasswordDTO {
    @NotBlank private String currentPassword;
    @NotBlank private String newPassword;
}
```

- `@NotBlank` -> no puede ser vacío ni solo espacios.
- `@Valid @RequestBody` -> Spring comprueba las reglas antes de entrar al método.
- Solo el **Backend** debe validar de verdad; el frontend es amable pero no fiable.

## 6. Sesiones, Autenticación y Autorización

- **Autenticación** = confirmar QUIÉN eres (el login: usuario + contraseña correctos).
- **Autorización** = confirmar QUÉ puedes hacer (solo tu propio usuario / tus propias tareas).
- Spring Security intercepta las rutas y pide identificación.

### Gestión del usuario autenticado

```java
@Service
public class AuthenticatedUserService {
    public Long getUserId() {   // objeto del usuario que hace la petición
        ...
    }
}
```

Con esto, cada controlador obtiene **el `userId` de la sesión** y filtra sus datos.
Nunca se confía en que el cliente diga "soy el 3" desde el navegador.

## 7. Seguridad en el backend

- **Contraseñas con hash** (`passwordEncoder`): nunca se guardan en texto plano.
  `encode()` al guardar, `matches()` para comparar al iniciar sesión.

```java
public void changePassword(User user, String old, String newPw) {
    if (!passwordEncoder.matches(old, user.getPasswordHash())) {
        throw new ...("Contraseña actual incorrecta");
    }
    user.setPasswordHash(passwordEncoder.encode(newPw));
}
```

- **No filtrar campos sensibles**: el `passwordHash` nunca debe ir en las respuestas API
  (se devuelve un `UserDTO` sin él).
- **Identificar al usuario por servidor/sesión**, no por parámetros del cliente.

## 8. Cambio de contraseña (flujo completo)

1. El usuario envía `currentPassword` y `newPassword` a `POST /api/auth/change-password`.
2. El servicio verifica que `currentPassword` coincide (`matches`).
3. Guarda `newPassword` con `encode`.
4. Devuelve el `UserDTO` actualizado.
5. Sin verificar antes, peticiones con contraseña vieja devuelven 401 y con la nueva 200.

## 9. Logs (lado servidor)

Spring/Java emiten logs (info, warn, error). En producción se leen desde Render.
Los `System.out` y los loggers de Spring ayudan a seguir qué petición entra y qué falla.

## 10. Prácticas del temario y cómo se cubren

- [x] API de tareas -> `TaskController` (CRUD de `/api/tasks`).
- [x] CRUD de usuarios -> registro + autenticación de `User`.
- [x] Login y registro -> `AuthController` (`/api/auth`).
- [x] Panel privado -> cada usuario ve solo sus datos (filtro por `userId`).
- [x] API conectada a base de datos -> JPA/hibernate + PostgreSQL/H2.

## Dudas pendientes

- [ ] Refactor a entidades con `@ManyToOne` y borrado en cascada (Reto).
- [ ] Autenticación "stateless" con JWT frente a sesiones de cookie.

## Repaso

- [ ] Lo entiendo.
- [ ] Lo he practicado (registro, login, CRUD, cambio de contraseña).
- [ ] Podría explicarlo a otra persona.
