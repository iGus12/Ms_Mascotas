package cl.sanosysalvo.ms_mascotas.MascotaController; // Ajustado al nombre de tu carpeta

import cl.sanosysalvo.ms_mascotas.model.Mascota;
import cl.sanosysalvo.ms_mascotas.service.IMascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    @Autowired
    private IMascotaService service;

    @GetMapping("/listar")
    public ResponseEntity<List<Mascota>> listar() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @PostMapping("/crear")
    public ResponseEntity<Mascota> crear(@RequestBody Mascota mascota) {
        return ResponseEntity.ok(service.guardar(mascota));
    }

    @GetMapping("/dueno/{id}")
    public ResponseEntity<List<Mascota>> porDueno(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorDueno(id));
    }
}