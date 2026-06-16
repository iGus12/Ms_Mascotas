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


@ExtendWith(MockitoExtension.class)
public class MascotaControllerTest {

    private MockMvc mockMvc; 

    private ObjectMapper objectMapper; 

    
    @Mock
    private IMascotaService mascotaService;

    @Mock
    private MascotaRepositorio mascotaRepositorio;

    @Mock
    private JdbcTemplate jdbcTemplate;


    @InjectMocks
    private MascotaController mascotaController;

    private Mascota mascotaRocco;

    @BeforeEach
    void setUp() {
      
        mockMvc = MockMvcBuilders.standaloneSetup(mascotaController).build();
        objectMapper = new ObjectMapper();

        mascotaRocco = new Mascota();
        mascotaRocco.setId(37L);
        mascotaRocco.setNombre("Rocco");
        mascotaRocco.setEspecie("Perro");
        mascotaRocco.setEstadoReporte("REGISTRO NORMAL");
    }


    @Test
    public void deberiaRetornarListaYStatus200() throws Exception {
        when(mascotaService.obtenerTodas()).thenReturn(Arrays.asList(mascotaRocco));

        mockMvc.perform(get("/api/mascotas/listar")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Rocco"))
                .andExpect(jsonPath("$[0].id").value(37));
    }


    @Test
    public void deberiaCrearMascotaYStatus200() throws Exception {
        when(mascotaService.guardar(any(Mascota.class))).thenReturn(mascotaRocco);

        mockMvc.perform(post("/api/mascotas/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mascotaRocco)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Rocco"));
    }

    
    @Test
    public void deberiaEliminarMascotaAdminYStatus200() throws Exception {
        when(jdbcTemplate.update(anyString(), any(Object.class))).thenReturn(1);

        mockMvc.perform(delete("/api/mascotas/37"))
                .andExpect(status().isOk());
    }

   
    @Test
    public void deberiaRetornarNotFoundAlEliminarAdmin() throws Exception {
        when(jdbcTemplate.update(anyString(), any(Object.class))).thenReturn(0);

        mockMvc.perform(delete("/api/mascotas/999"))
                .andExpect(status().isNotFound());
    }
}