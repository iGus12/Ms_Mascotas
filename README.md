#  Ms_Mascotas - Sanos y Salvos

Microservicio encargado de la gestión centralizada de mascotas para el sistema **Sanos y Salvos**. Este componente maneja la persistencia y la lógica de negocio relacionada con el registro de animales, reportes de mascotas perdidas y encontradas, y su historial de salud.

---

# Funcionalidades Principales:
* *Registro de Mascotas:* Creación de perfiles detallados incluyendo especie, raza, edad, características físicas y ubicación.
* *Gestión de Reportes:** Clasificación de estado mediante alertas (`ENCONTRADA`, `PERDIDA`, `REGISTRO NORMAL`).
* *Historial de Salud:** Seguimiento del estado reproductivo (castración) y registro de vacunas.
* *Sincronización de Coordenadas:** Proveedor de datos referenciales para la renderización de mapas en el Frontend.

---

 Stack Tecnológico:
* *Framework:* Spring Boot (Java)
* *Gestor de Dependencias:* Maven
* *Base de Datos:** MySQL
* *Arquitectura:** Diseño basado en Microservicios
* *ORM:** Spring Data JPA / Hibernate

---

#  Configuración y Ejecución local

# Requisitos previos
* Java Development Kit (JDK) 17 o superior.
* Maven instalado.
* Servidor MySQL corriendo localmente.

