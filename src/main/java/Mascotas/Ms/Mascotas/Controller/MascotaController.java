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

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Mascota>> porUsuario(@PathVariable Long id) {
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
    public ResponseEntity<?> crearMascotaAdmin(@RequestBody Map<String, Object> datosNuevos) {
        try {
            String sql = "INSERT INTO mascotas (nombre, especie, raza, color, edad, contacto, ubicacion, estado_reporte, descripcion, reproductivo, foto, fecha, dueño_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1)";
            jdbcTemplate.update(sql, 
                datosNuevos.get("nombre"), 
                datosNuevos.get("especie"), 
                datosNuevos.get("raza"),           
                datosNuevos.get("color"),          
                datosNuevos.get("edad"),           
                datosNuevos.get("contacto"),       
                datosNuevos.get("ubicacion"), 
                datosNuevos.get("estadoReporte"), 
                datosNuevos.get("descripcion"),    
                datosNuevos.get("reproductivo"), 
                datosNuevos.get("foto"),
                datosNuevos.get("fecha")
            );
            return ResponseEntity.ok(Map.of("mensaje", "¡Mascota registrada con éxito total!"));
        } catch (Exception e) {
            System.out.println("❌ Error en Java al guardar: " + e.getMessage());
            return ResponseEntity.status(500).body("Error al guardar en BD: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarMascotaAdmin(@PathVariable Long id, @RequestBody Map<String, Object> datosNuevos) {
        try {
            String sql = "UPDATE mascotas SET nombre = ?, especie = ?, raza = ?, color = ?, edad = ?, contacto = ?, ubicacion = ?, estado_reporte = ?, descripcion = ?, fecha = ? WHERE id = ?";
            
            int filas = jdbcTemplate.update(sql, 
                datosNuevos.get("nombre"), 
                datosNuevos.get("especie"), 
                datosNuevos.get("raza"),          
                datosNuevos.get("color"),         
                datosNuevos.get("edad"),        
                datosNuevos.get("contacto"),    
                datosNuevos.get("ubicacion"), 
                datosNuevos.get("estadoReporte"), 
                datosNuevos.get("descripcion"),   
                datosNuevos.get("fecha"),
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
            System.out.println(" Error en SQL DELETE nativo: " + e.getMessage());
            return ResponseEntity.status(500).body("Error SQL: " + e.getMessage());
        }
    }
}