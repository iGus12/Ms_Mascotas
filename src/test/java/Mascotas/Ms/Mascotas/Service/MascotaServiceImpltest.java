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

    // Objeto de prueba que reutilizaremos
    private Mascota mascotaRocco;

    @BeforeEach
    void setUp() {
        // Esto se ejecuta antes de cada prueba para tener datos limpios
        mascotaRocco = new Mascota();
        mascotaRocco.setId(37L);
        mascotaRocco.setNombre("Rocco");
        mascotaRocco.setEspecie("Perro");
        mascotaRocco.setEstadoReporte("EN REFUGIO: MASCOTA ENCONTRADA");
        mascotaRocco.setDueñoId(1L);
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
        // Arrange (Camino Límite: BD vacía)
        when(repositorio.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Mascota> resultado = mascotaService.obtenerTodas();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    

    @Test
    public void deberiaGuardarMascota_Exitosamente() {
        // Arrange
        Mascota nuevaMascota = new Mascota();
        nuevaMascota.setNombre("Alfred");
        
        when(repositorio.save(any(Mascota.class))).thenReturn(mascotaRocco);

        // Act
        Mascota resultado = mascotaService.guardar(nuevaMascota);

        // Assert
        assertNotNull(resultado);
        assertEquals(37L, resultado.getId());
        verify(repositorio, times(1)).save(nuevaMascota);
    }

 

    @Test
    public void deberiaRetornarMascotasDelDueño_CuandoDueñoTieneMascotas() {
        // Arrange
        Long dueñoId = 1L;
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
