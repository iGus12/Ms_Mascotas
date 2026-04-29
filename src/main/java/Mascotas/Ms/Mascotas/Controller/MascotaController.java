package Mascotas.Ms.Mascotas.Controller; 

import Mascotas.Ms.Mascotas.Model.Mascota;
import Mascotas.Ms.Mascotas.Service.IMascotaService;

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

    @GetMapping("/dueno/{id}")
    public ResponseEntity<List<Mascota>> porDueno(@PathVariable Long id) {
        return ResponseEntity.ok(Service.obtenerPorDueño(id));
    }
}