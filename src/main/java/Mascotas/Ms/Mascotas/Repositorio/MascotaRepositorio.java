package Mascotas.Ms.Mascotas.Repositorio;

import Mascotas.Ms.Mascotas.Model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MascotaRepositorio extends JpaRepository<Mascota, Long> {
    List<Mascota> findByDueñoId(Long dueñoId);
}