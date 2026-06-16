package Mascotas.Ms.Mascotas.Model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class MascotaTest {

    // ==========================================
    // PRUEBA 1: Verificar que el objeto se crea bien
    // ==========================================
    @Test
    void deberiaCrearMascotaVacia() {
        // Act: Instanciamos el modelo
        Mascota mascota = new Mascota();

        // Assert: Verificamos que no sea nulo
        assertNotNull(mascota, "El objeto mascota no debería ser nulo al instanciarse");
    }

    // ==========================================
    // PRUEBA 2: Verificar el funcionamiento de Getters y Setters
    // ==========================================
    @Test
    void deberiaAsignarYObtenerDatosDeMascota() {
        // Arrange: Preparamos los datos reales de Rocco
        Mascota mascota = new Mascota();

        // Act: Asignamos todos los datos (Setters)
        mascota.setId(37L);
        mascota.setNombre("Rocco");
        mascota.setEspecie("Perro");
        mascota.setRaza("Labrador");
        mascota.setEdad(5);
        mascota.setDueñoId(10L);
        mascota.setVacunas("Al día");
        mascota.setEstadoReporte("REGISTRO NORMAL");

        // Assert: Validamos que coincida con lo que tu microservicio espera
        assertAll("Verificando propiedades exactas de la mascota en el modelo",
            () -> assertEquals(37L, mascota.getId(), "El ID no coincide con el de Rocco"),
            () -> assertEquals("Rocco", mascota.getNombre(), "El nombre no coincide"),
            () -> assertEquals("Perro", mascota.getEspecie(), "La especie no coincide"),
            () -> assertEquals("Labrador", mascota.getRaza(), "La raza no coincide"),
            () -> assertEquals(5, mascota.getEdad(), "La edad no coincide"),
            () -> assertEquals(10L, mascota.getDueñoId(), "El ID del dueño no coincide"),
            () -> assertEquals("Al día", mascota.getVacunas(), "El estado de vacunas no coincide"),
            () -> assertEquals("REGISTRO NORMAL", mascota.getEstadoReporte(), "El estado de reporte no coincide")
        );
    }
}