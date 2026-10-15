# Gestor de Incidencias Informáticas

Backend de una intranet para gestionar incidencias informáticas en una empresa de telecomunicaciones. La solución ofrece una API REST con autenticación y autorización para registrar, asignar y dar seguimiento a incidencias.

> Este repositorio contiene el backend. El frontend está previsto para desarrollarse con Angular.

## Tecnologías

- Java 21 y Spring Boot 3
- Spring Web, Spring Data JPA y Spring Security
- JWT para autenticación
- MySQL
- Maven

## Funcionalidades

- Inicio de sesión y autenticación mediante token JWT.
- Gestión de usuarios y roles: administrador de TI, técnico de soporte y colaborador.
- Registro y consulta de incidencias.
- Asignación de técnicos, actualización de estados y registro de soluciones.
- Estadísticas de incidencias para administración.

## Requisitos

- JDK 21
- MySQL

## Configuración y ejecución

1. Crea la base de datos en MySQL:

   ```sql
   CREATE DATABASE gestorincidencias;
   ```

2. Configura las credenciales de conexión. La aplicación usa `root` y contraseña vacía por defecto; puedes definir otras mediante variables de entorno en PowerShell:

   ```powershell
   $env:DB_USERNAME = "tu_usuario"
   $env:DB_PASSWORD = "tu_contraseña"
   ```

3. Desde la carpeta del proyecto, inicia la aplicación:

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

   Hibernate crea o actualiza las tablas al iniciar. Los roles disponibles se inicializan automáticamente.

## API

Las rutas protegidas requieren el encabezado `Authorization: Bearer <token>`.

| Método | Endpoint | Acceso / propósito |
|---|---|---|
| `POST` | `/api/auth/login` | Iniciar sesión y obtener un JWT |
| `POST` | `/api/incidencias` | Registrar una incidencia |
| `GET` | `/api/incidencias` | Consultar incidencias |
| `GET` | `/api/incidencias/{id}` | Consultar una incidencia |
| `PUT` | `/api/incidencias/{id}/asignar` | Asignar técnico; administrador de TI |
| `PUT` | `/api/incidencias/{id}/estado` | Cambiar el estado |
| `POST` | `/api/incidencias/{id}/solucion` | Registrar una solución |
| `GET` | `/api/incidencias/estadisticas` | Consultar estadísticas; administrador de TI |
| `GET`, `POST`, `PUT`, `DELETE` | `/api/usuarios` | Administrar usuarios; administrador de TI |

## Estado

Proyecto académico en desarrollo.
