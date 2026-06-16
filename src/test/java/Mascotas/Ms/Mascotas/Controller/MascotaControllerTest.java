package Mascotas.Ms.Mascotas.Controller;

import Mascotas.Ms.Mascotas.Model.Mascota;
import Mascotas.Ms.Mascotas.Repositorio.MascotaRepositorio;
import Mascotas.Ms.Mascotas.Service.IMascotaService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// 🔥 USAMOS MOCKITO PURO PARA EVITAR LOS BUGS DE SPRING BOOT 🔥
@ExtendWith(MockitoExtension.class)
public class MascotaControllerTest {

    private MockMvc mockMvc; // Nuestro "Postman" manual

    private ObjectMapper objectMapper; // Para JSON

    // ==========================================
    // SIMULADORES CLÁSICOS (Sin depender de Spring)
    // ==========================================
    @Mock
    private IMascotaService mascotaService;

    @Mock
    private MascotaRepositorio mascotaRepositorio;

    @Mock
    private JdbcTemplate jdbcTemplate;

    // 🔥 ESTA ES LA MAGIA: Inyecta los simuladores directo al Controller
    @InjectMocks
    private MascotaController mascotaController;

    private Mascota mascotaRocco;

    @BeforeEach
    void setUp() {
        // Inicializamos el Postman invisible apuntando solo a tu Controller
        mockMvc = MockMvcBuilders.standaloneSetup(mascotaController).build();
        objectMapper = new ObjectMapper();

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
        when(mascotaService.obtenerTodas()).thenReturn(Arrays.asList(mascotaRocco));

        mockMvc.perform(get("/api/mascotas/listar")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Rocco"))
                .andExpect(jsonPath("$[0].id").value(37));
    }

    // ==========================================
    // PRUEBA 2: POST /api/mascotas/crear
    // ==========================================
    @Test
    public void deberiaCrearMascotaYStatus200() throws Exception {
        when(mascotaService.guardar(any(Mascota.class))).thenReturn(mascotaRocco);

        mockMvc.perform(post("/api/mascotas/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mascotaRocco)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Rocco"));
    }

    // ==========================================
    // PRUEBA 3: DELETE /api/mascotas/{id}
    // ==========================================
    @Test
    public void deberiaEliminarMascotaAdminYStatus200() throws Exception {
        when(jdbcTemplate.update(anyString(), any(Object.class))).thenReturn(1);

        mockMvc.perform(delete("/api/mascotas/37"))
                .andExpect(status().isOk());
    }

    // ==========================================
    // PRUEBA 4: DELETE FALLIDO
    // ==========================================
    @Test
    public void deberiaRetornarNotFoundAlEliminarAdmin() throws Exception {
        when(jdbcTemplate.update(anyString(), any(Object.class))).thenReturn(0);

        mockMvc.perform(delete("/api/mascotas/999"))
                .andExpect(status().isNotFound());
    }
}