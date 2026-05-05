package Mascotas.Ms.Mascotas.Factory;

import Mascotas.Ms.Mascotas.Model.Mascota;

public class MascotaFactory {

    public static Mascota crearReporte(String tipoReporte, Mascota mascota) {
        
        
        if (tipoReporte == null || tipoReporte.trim().isEmpty()) {
            mascota.setEstadoReporte("REGISTRO NORMAL");
            return mascota;
        }

        if (tipoReporte.equalsIgnoreCase("PERDIDA")) {
            mascota.setEstadoReporte("ALERTA: MASCOTA PERDIDA");
            return mascota;
        } else if (tipoReporte.equalsIgnoreCase("ENCONTRADA")) {
            mascota.setEstadoReporte("EN REFUGIO: MASCOTA ENCONTRADA");
            return mascota;
        } else if (tipoReporte.equalsIgnoreCase("ADOPCION")) {
            mascota.setEstadoReporte("DISPONIBLE PARA ADOPCIÓN");
            return mascota;
        } else {
            
            mascota.setEstadoReporte("REGISTRO NORMAL");
            return mascota;
        }
    }
}
