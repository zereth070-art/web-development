package com.zion.pomodorozion;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;

@SpringBootTest // arranca la aplicacion completa pero sin puerto
@AutoConfigureMockMvc // inyecta el objeto Monkmbvc para hacer peticiones falsas
// rollback automatico tras cada test (rollback es que revierte los cambios y
// vuelve al estado original)

class HealthApiTest {

        @Autowired // Spring te da el MockMvc ya construido
        private MockMvc mockMvc;

        // session va DENTRO de cada test, no aqui arriba

        @Test

        void healthEsPublicoYDevuelveLaApp() throws Exception {
                mockMvc.perform(get("/api/health"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.app").value("PomodoroZion"));
        }

        @Test
        void tareasProtegidasSinSesion() throws Exception {
                mockMvc.perform(post("/api/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                {"username": "pepe123", "password": "clave123"}
                                                """))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.username").value("pepe123"));

        }

        @Test
        void registroDuplicadoDevuelve409() throws Exception {
                mockMvc.perform(post("/api/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                { "username": "pepe123" , "password": "clave123" }
                                                 """))
                                .andExpect(status().isConflict())
                                .andExpect(jsonPath("$.message").value("Este usuario ya existe"));
        }

        @Test
        void loginIncorrecto() throws Exception {
                MockHttpSession session = new MockHttpSession();
                mockMvc.perform(post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                {"username": "pepe12", "password": "clave123"}
                                                        """)
                                .session(session))
                                .andExpect(status().isUnauthorized())
                                .andExpect(jsonPath("$.message").value("Credenciales incorrectas"));
        }

        @Test
        void loginCorrecto() throws Exception {
                MockHttpSession session = new MockHttpSession();
                mockMvc.perform(post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                {"username": "pepe123", "password": "clave123"}
                                                        """)
                                .session(session))
                                .andExpect(status().isOk());

                mockMvc.perform(get("/api/tasks")
                                .session(session))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").isArray());
        }

        @Test
        void inicioSesionCrearTareaMirarRegistro() throws Exception {
                MockHttpSession session = new MockHttpSession();
                mockMvc.perform(post("/api/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                {"username": "sesion01", "password": "clave123"}
                                                """))
                                .andExpect(status().isCreated());
                // inicio de sesion
                mockMvc.perform(post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                {"username": "sesion01", "password": "clave123"}
                                                        """)
                                .session(session))
                                .andExpect(status().isOk());
                // creo la tarea
                mockMvc.perform(post("/api/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                {"title": "Mi tarea", "estimatedPomodoros": 3}
                                                        """)
                                .session(session))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.title").value("Mi tarea"))
                                .andExpect(jsonPath("$.status").value("PENDING"));

                // al recojo
                mockMvc.perform(get("/api/tasks")
                                .session(session))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].title").value("Mi tarea"));
        }

        @Test
        void Ownership() throws Exception {
                MockHttpSession session1 = new MockHttpSession();
                MockHttpSession session2 = new MockHttpSession();
                // primera sesion
                mockMvc.perform(post("/api/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                { "username" : "paquitoElChocolatero", "password" : "123456"}
                                                                """)
                                .session(session1))
                                .andExpect(status().isCreated());

                // segunda sesion
                mockMvc.perform(post("/api/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                { "username" : "paquitoElChocolatero2", "password" : "123456"}
                                                                """)
                                .session(session2))
                                .andExpect(status().isCreated());
                // inicio primera sesion
                mockMvc.perform(post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                {"username": "paquitoElChocolatero", "password": "123456"}
                                                        """)
                                .session(session1))
                                .andExpect(status().isOk());
                // crear tarea con la primera sesion
                String respuesta = mockMvc.perform(post("/api/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                {"title": "Mi polla con peluca", "estimatedPomodoros": 3}
                                                                """)
                                .session(session1))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.title").value("Mi polla con peluca"))
                                .andExpect(jsonPath("$.status").value("PENDING"))
                                .andReturn()
                                .getResponse()
                                .getContentAsString();
                // inicio de sesion 2

                mockMvc.perform(post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                {"username": "paquitoElChocolatero2", "password": "123456"}
                                                        """)
                                .session(session2))
                                .andExpect(status().isOk());

                // recojo tareas
                mockMvc.perform(get("/api/tasks")
                                .session(session2))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").isArray())
                                .andExpect(jsonPath("$").isEmpty());

                int id = com.jayway.jsonpath.JsonPath.read(respuesta, "$.id");

                mockMvc.perform(get("/api/tasks/" + id)
                                .session(session2))
                                .andExpect(status().isNotFound());

        }

}
