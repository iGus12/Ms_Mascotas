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
}