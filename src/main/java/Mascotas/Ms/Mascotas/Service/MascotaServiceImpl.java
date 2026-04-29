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
    public Mascota guardar(Mascota mascota) {
        return repositorio.save(mascota);
    }

    @Override
    public List<Mascota> obtenerPorDueño(Long dueñoId) {
        return repositorio.findByDueñoId(dueñoId);
    }
}