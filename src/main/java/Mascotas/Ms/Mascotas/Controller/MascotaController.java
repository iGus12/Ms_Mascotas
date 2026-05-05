package Mascotas.Ms.Mascotas.Controller; 

import Mascotas.Ms.Mascotas.Model.Mascota;
import Mascotas.Ms.Mascotas.Service.IMascotaService;
import Mascotas.Ms.Mascotas.Factory.MascotaFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    @Autowired
    private IMascotaService Service;

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
        
        // 1. Pasamos los datos por tu Fábrica manual y estricta
        Mascota mascotaProcesada = MascotaFactory.crearReporte(tipo, mascota);
        
        // 2. Guardamos la mascota usando tu variable "Service"
        return ResponseEntity.ok(Service.guardar(mascotaProcesada));
    }
}