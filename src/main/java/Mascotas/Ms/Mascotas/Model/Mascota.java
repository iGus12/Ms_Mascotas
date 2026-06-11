package Mascotas.Ms.Mascotas.Model;

import jakarta.persistence.*;
import lombok.Data; 

@Entity
@Table(name = "mascotas")
@Data
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String especie;
    private String raza;
    private Integer edad;
    private String fecha;
    @Column(columnDefinition = "LONGTEXT")
    private String foto;

    @Column(name = "dueño_id") 
    private Long dueñoId;

    private String vacunas;

    @Column(name = "estado_reporte") 
    private String estadoReporte;

    private String color;
    private String ubicacion;
    private String contacto;
    private String descripcion;
}