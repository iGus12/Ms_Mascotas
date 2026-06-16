package Mascotas.Ms.Mascotas.Service;

import Mascotas.Ms.Mascotas.Model.Mascota;
import Mascotas.Ms.Mascotas.Repositorio.MascotaRepositorio;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MascotaServiceImpltest {

    @Mock
    private MascotaRepositorio repositorio;

    @InjectMocks
    private MascotaServiceImpl mascotaService;


    private Mascota mascotaRocco;

    @BeforeEach
    void setUp() {
        
        mascotaRocco = new Mascota();
        mascotaRocco.setId(37L);
        mascotaRocco.setNombre("Rocco");
        mascotaRocco.setEspecie("Perro");
        mascotaRocco.setRaza("Labrador");
        mascotaRocco.setEdad(5);
        mascotaRocco.setDueñoId(10L);
        mascotaRocco.setVacunas("Al día");
        mascotaRocco.setEstadoReporte("REGISTRO NORMAL");
    }

   
    @Test
    public void deberiaRetornarListaDeMascotas_CuandoExistenMascotas() {
        // Arrange
        when(repositorio.findAll()).thenReturn(Arrays.asList(mascotaRocco));

        // Act
        List<Mascota> resultado = mascotaService.obtenerTodas();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Rocco", resultado.get(0).getNombre());
        verify(repositorio, times(1)).findAll();
    }


    @Test
    public void deberiaRetornarListaVacia_CuandoNoHayMascotas() {
        // Arrange
        when(repositorio.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Mascota> resultado = mascotaService.obtenerTodas();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

 
    @Test
    public void deberiaGuardarMascota_Exitosamente() {
        // Arrange: 
        when(repositorio.save(any(Mascota.class))).thenReturn(mascotaRocco);

        // Act
        Mascota resultado = mascotaService.guardar(mascotaRocco);

        // Assert
        assertNotNull(resultado);
        assertEquals(37L, resultado.getId());
        assertEquals("Rocco", resultado.getNombre());
        verify(repositorio, times(1)).save(mascotaRocco);
    }

 
    @Test
    public void deberiaRetornarMascotasDelDueño_CuandoDueñoTieneMascotas() {
        // Arrange: 
        Long dueñoId = 10L;
        when(repositorio.findByDueñoId(dueñoId)).thenReturn(Arrays.asList(mascotaRocco));

        // Act
        List<Mascota> resultado = mascotaService.obtenerPorDueño(dueñoId);

        // Assert
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(dueñoId, resultado.get(0).getDueñoId());
    }

    @Test
    public void deberiaRetornarTotalDeMascotas() {
        // Arrange
        when(repositorio.count()).thenReturn(15L);

        // Act
        long total = mascotaService.contarTodas();

        // Assert
        assertEquals(15L, total);
        verify(repositorio, times(1)).count();
    }


    @Test
    public void deberiaRetornarConteoPorEstado() {
        // Arrange
        String estadoBuscado = "ALERTA: MASCOTA PERDIDA";
        when(repositorio.countByEstadoReal(estadoBuscado)).thenReturn(5L);

        // Act
        long totalEstado = mascotaService.contarPorEstado(estadoBuscado);

        // Assert
        assertEquals(5L, totalEstado);
        verify(repositorio, times(1)).countByEstadoReal(estadoBuscado);
    }
}