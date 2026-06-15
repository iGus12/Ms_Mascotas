package Mascotas.Ms.Mascotas.Controller;

import Mascotas.Ms.Mascotas.Model.Mascota;
import Mascotas.Ms.Mascotas.Repositorio.MascotaRepositorio;
import Mascotas.Ms.Mascotas.Service.IMascotaService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MascotaController.class)
public class MascotaControllerTest {

    @Autowired
    private MockMvc mockMvc; // Nuestro "Postman" automatizado

    @Autowired
    private ObjectMapper objectMapper; // Transforma objetos Java a JSON

    // ==========================================
    // SIMULADORES (MockBeans)
    // Se necesitan porque el Controller tiene @Autowired de todos estos
    // ==========================================
    @MockBean
    private IMascotaService mascotaService;

    @MockBean
    private MascotaRepositorio mascotaRepositorio;

    @MockBean
    private JdbcTemplate jdbcTemplate;

    // Objeto de prueba
    private Mascota mascotaRocco;

    @BeforeEach
    void setUp() {
        mascotaRocco = new Mascota();
        mascotaRocco.setId(37L);
        mascotaRocco.setNombre("Rocco");
        mascotaRocco.setEspecie("Perro");
        mascotaRocco.setEstadoReporte("REGISTRO NORMAL");
    }

    // ==========================================
    // PRUEBA 1: GET /api/mascotas/listar
    // ==========================================
    @Test
    public void deberiaRetornarListaYStatus200() throws Exception {
        // Arrange: Simulamos que el servicio devuelve a Rocco
        when(mascotaService.obtenerTodas()).thenReturn(Arrays.asList(mascotaRocco));

        // Act & Assert: Hacemos la petición GET y exigimos respuestas
        mockMvc.perform(get("/api/mascotas/listar")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Exigimos que responda un Status 200 (OK)
                .andExpect(jsonPath("$[0].nombre").value("Rocco")) // Exigimos que el JSON traiga el nombre Rocco
                .andExpect(jsonPath("$[0].id").value(37));
    }

    // ==========================================
    // PRUEBA 2: POST /api/mascotas/crear
    // ==========================================
    @Test
    public void deberiaCrearMascotaYStatus200() throws Exception {
        // Arrange
        when(mascotaService.guardar(any(Mascota.class))).thenReturn(mascotaRocco);

        // Act & Assert: Enviamos un JSON por POST
        mockMvc.perform(post("/api/mascotas/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mascotaRocco))) // Convertimos a Rocco a String JSON
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Rocco"));
    }

    // ==========================================
    // PRUEBA 3: DELETE /api/mascotas/{id} (Trampa JdbcTemplate)
    // ==========================================
    @Test
    public void deberiaEliminarMascotaAdminYStatus200() throws Exception {
        // Arrange: Simulamos que el JdbcTemplate logra borrar 1 fila
        when(jdbcTemplate.update(anyString(), any(Object.class))).thenReturn(1);

        // Act & Assert: Hacemos el DELETE
        mockMvc.perform(delete("/api/mascotas/37"))
                .andExpect(status().isOk()); // Debe dar 200 porque afectó 1 fila
    }

    // ==========================================
    // PRUEBA 4: DELETE FALLIDO (El Camino Triste)
    // ==========================================
    @Test
    public void deberiaRetornarNotFoundAlEliminarAdmin() throws Exception {
        // Arrange: Simulamos que el JdbcTemplate NO encuentra la mascota (borra 0 filas)
        when(jdbcTemplate.update(anyString(), any(Object.class))).thenReturn(0);

        // Act & Assert
        mockMvc.perform(delete("/api/mascotas/999"))
                .andExpect(status().isNotFound()); // Debe dar 404 porque afectó 0 filas (tu código retorna notFound())
    }
}