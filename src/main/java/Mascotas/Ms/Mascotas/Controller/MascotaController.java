package Mascotas.Ms.Mascotas.Controller; 

import Mascotas.Ms.Mascotas.Model.Mascota;
import Mascotas.Ms.Mascotas.Repositorio.MascotaRepositorio;
import Mascotas.Ms.Mascotas.Service.IMascotaService;
import Mascotas.Ms.Mascotas.Factory.MascotaFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map; 

@RestController
@RequestMapping("/api/mascotas")
@CrossOrigin(origins = "*") 
public class MascotaController {
   
    @Autowired
    private IMascotaService Service;

    @Autowired
    private MascotaRepositorio mascotaRepositorio; 

   
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/listar")
    public ResponseEntity<List<Mascota>> listar() {
        return ResponseEntity.ok(Service.obtenerTodas());
    }

    @PostMapping("/crear")
    public ResponseEntity<Mascota> crear(@RequestBody Mascota mascota) {
        return ResponseEntity.ok(Service.guardar(mascota));
    }

    @GetMapping("/dueño/{id}")
    public ResponseEntity<List<Mascota>> porDueño(@PathVariable Long id) {
        return ResponseEntity.ok(Service.obtenerPorDueño(id));
    }

    @PostMapping("/reportar")
    public ResponseEntity<Mascota> reportarMascota(
            @RequestBody Mascota mascota, 
            @RequestParam(required = false) String tipo) {
        
        Mascota mascotaProcesada = MascotaFactory.crearReporte(tipo, mascota);
        return ResponseEntity.ok(Service.guardar(mascotaProcesada));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> contarMascotas(@RequestParam(required = false) String estado) {
        if (estado != null && !estado.isEmpty()) {
            return ResponseEntity.ok(Service.contarPorEstado(estado));
        }
        return ResponseEntity.ok(Service.contarTodas());
    }

    @GetMapping("/ultimos")
    public ResponseEntity<List<Mascota>> obtenerUltimos() {
        return ResponseEntity.ok(Service.obtenerTodas());
    }
    @PostMapping("/crear-admin")
    public ResponseEntity<?> crearMascotaNativo(@RequestBody Map<String, Object> datos) {
        try {
            // Forzamos dueño_id = 1 para que MySQL no rechace la fila
            String sql = "INSERT INTO mascotas (nombre, especie, ubicacion, estado_reporte, dueño_id) VALUES (?, ?, ?, ?, 1)";
            jdbcTemplate.update(sql, 
                datos.get("nombre"), 
                datos.get("especie"), 
                datos.get("ubicacion"), 
                datos.get("estadoReporte")
            );
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("❌ Error en SQL INSERT nativo: " + e.getMessage());
            return ResponseEntity.status(500).body("Error SQL: " + e.getMessage());
        }
    }

  
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarMascotaAdmin(@PathVariable Long id, @RequestBody Map<String, Object> datosNuevos) {
        try {
            String sql = "UPDATE mascotas SET nombre = ?, especie = ?, ubicacion = ?, estado_reporte = ? WHERE id = ?";
            int filas = jdbcTemplate.update(sql, 
                datosNuevos.get("nombre"), 
                datosNuevos.get("especie"), 
                datosNuevos.get("ubicacion"), 
                datosNuevos.get("estadoReporte"), 
                id
            );
            if (filas > 0) return ResponseEntity.ok().build();
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.out.println("❌ Error en SQL UPDATE nativo: " + e.getMessage());
            return ResponseEntity.status(500).body("Error SQL: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMascotaAdmin(@PathVariable Long id) {
        try {
            String sql = "DELETE FROM mascotas WHERE id = ?";
            int filas = jdbcTemplate.update(sql, id);
            if (filas > 0) return ResponseEntity.ok().build();
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.out.println("❌ Error en SQL DELETE nativo: " + e.getMessage());
            return ResponseEntity.status(500).body("Error SQL: " + e.getMessage());
        }
    }
}