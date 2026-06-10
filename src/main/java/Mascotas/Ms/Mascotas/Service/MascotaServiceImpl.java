package Mascotas.Ms.Mascotas.Service;

import Mascotas.Ms.Mascotas.Model.Mascota;
import Mascotas.Ms.Mascotas.Repositorio.MascotaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MascotaServiceImpl implements IMascotaService {

    @Autowired
    private MascotaRepositorio repositorio;

    @Override
    public List<Mascota> obtenerTodas() {
        return repositorio.findAll();
    }

 @Override
public long contarTodas() {
    // Cuenta el total de filas reales en la tabla de MySQL
    return repositorio.count();
}

@Override
public long contarPorEstado(String estado) {
    // Petición real filtrada a la base de datos
    return repositorio.countByEstadoReal(estado);
}

    @Override
    public Mascota guardar(Mascota mascota) {
        return repositorio.save(mascota);
    }

    @Override
    public List<Mascota> obtenerPorDueño(Long dueñoId) {
        return repositorio.findByDueñoId(dueñoId);
    }
}