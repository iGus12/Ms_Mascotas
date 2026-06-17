package Mascotas.Ms.Mascotas.Repositorio;

import Mascotas.Ms.Mascotas.Model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MascotaRepositorio extends JpaRepository<Mascota, Long> {
    
@Query(value = "SELECT * FROM mascotas WHERE dueño_id = :dueñoId", nativeQuery = true)
java.util.List<Mascota> findByDueñoId(@Param("dueñoId") Long dueñoId);

    
    @Query(value = "SELECT COUNT(*) FROM mascotas WHERE estado_reporte= :estado", nativeQuery = true)
    long countByEstadoReal(@Param("estado") String estado);

}