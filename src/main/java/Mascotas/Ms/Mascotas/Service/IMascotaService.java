package Mascotas.Ms.Mascotas.Service;

import Mascotas.Ms.Mascotas.Model.Mascota;
import java.util.List;

public interface IMascotaService {
    List<Mascota> obtenerTodas();
    Mascota guardar(Mascota mascota);
    List<Mascota> obtenerPorDueño(Long duenoId);
}
