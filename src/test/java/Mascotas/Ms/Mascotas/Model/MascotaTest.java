package Mascotas.Ms.Mascotas.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class MascotaTest {

    @Test
    void debeCrearMascota() {
        Mascota mascota = new Mascota();

        assertNotNull(mascota);
    }

    @Test
    void debeAsignarDatosAMascota() {
        Mascota mascota = new Mascota();

        mascota.setId(1L);
        mascota.setNombre("Firulais");
        mascota.setEspecie("Perro");
        mascota.setRaza("Labrador");
        mascota.setEdad(5);
        mascota.setDueñoId(10L);
        mascota.setVacunas("Al día");
        mascota.setEstadoReporte("Perdido");

        assertEquals(1L, mascota.getId());
        assertEquals("Firulais", mascota.getNombre());
        assertEquals("Perro", mascota.getEspecie());
        assertEquals("Labrador", mascota.getRaza());
        assertEquals(5, mascota.getEdad());
        assertEquals(10L, mascota.getDueñoId());
        assertEquals("Al día", mascota.getVacunas());
        assertEquals("Perdido", mascota.getEstadoReporte());
    }
}