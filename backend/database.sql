-- =============================================================================
-- SISTEMA DE GESTIÓN DE INCIDENCIAS INFORMÁTICAS
-- Script Completo de Base de Datos (DDL + DML Semilla)
-- Motor: MySQL 8.0+
-- Codificación: UTF-8 (utf8mb4)
-- Clave por defecto para todos los usuarios: password123
-- Hash BCrypt: $2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO
-- =============================================================================

CREATE DATABASE IF NOT EXISTS gestorincidencias
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE gestorincidencias;

-- -----------------------------------------------------------------------------
-- 1. ESTRUCTURA (DDL)
-- -----------------------------------------------------------------------------

-- Desactivar temporalmente revisión de llaves foráneas para limpieza segura
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS soluciones;
DROP TABLE IF EXISTS incidencias;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS roles;

SET FOREIGN_KEY_CHECKS = 1;

-- TABLA: roles
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- TABLA: usuarios
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    correo_corporativo VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    area VARCHAR(100) NOT NULL,
    rol_id BIGINT NOT NULL,
    activo BIT(1) NOT NULL DEFAULT 1,
    CONSTRAINT fk_usuarios_roles FOREIGN KEY (rol_id) REFERENCES roles (id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- TABLA: incidencias
CREATE TABLE incidencias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(30) NOT NULL UNIQUE,
    titulo VARCHAR(150) NOT NULL,
    descripcion TEXT NOT NULL,
    area_afectada VARCHAR(100) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    categoria VARCHAR(20) NOT NULL,
    prioridad VARCHAR(20) NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    usuario_reportador_id BIGINT NOT NULL,
    tecnico_asignado_id BIGINT NULL,
    CONSTRAINT fk_incidencias_reportador FOREIGN KEY (usuario_reportador_id) REFERENCES usuarios (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_incidencias_tecnico FOREIGN KEY (tecnico_asignado_id) REFERENCES usuarios (id) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- TABLA: soluciones
CREATE TABLE soluciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    incidencia_id BIGINT NOT NULL UNIQUE,
    diagnostico TEXT NOT NULL,
    acciones_correctivas TEXT NOT NULL,
    detalle_solucion TEXT NOT NULL,
    fecha_cierre DATETIME NOT NULL,
    tiempo_atencion_minutos BIGINT NOT NULL,
    CONSTRAINT fk_soluciones_incidencia FOREIGN KEY (incidencia_id) REFERENCES incidencias (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- 2. DATOS INICIALES (DML)
-- -----------------------------------------------------------------------------

-- 2.1 Inserción de Roles
INSERT INTO roles (id, nombre) VALUES
(1, 'ADMINISTRADOR_TI'),
(2, 'TECNICO_SOPORTE'),
(3, 'COLABORADOR');

-- 2.2 Inserción de Usuarios (10 Administradores, 10 Técnicos, 10 Colaboradores)
-- Password para todos: "password123"
INSERT INTO usuarios (id, correo_corporativo, password, area, rol_id, activo) VALUES
-- Administradores TI (rol_id = 1)
(1,  'admin@empresa.com',            '$2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO', 'TI y Sistemas',             1, 1),
(2,  'carlos.mendoza@empresa.com',   '$2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO', 'Infraestructura TI',         1, 1),
(3,  'maria.torres@empresa.com',     '$2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO', 'Ciberseguridad',             1, 1),
(4,  'luis.castillo@empresa.com',    '$2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO', 'Redes y Telecomunicaciones', 1, 1),
(5,  'ana.flores@empresa.com',       '$2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO', 'Gestión de Servicios TI',    1, 1),
(6,  'jorge.ramirez@empresa.com',    '$2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO', 'Arquitectura de Software',   1, 1),
(7,  'patricia.paredes@empresa.com', '$2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO', 'Administración de BD',       1, 1),
(8,  'raul.navarro@empresa.com',     '$2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO', 'DevOps & Cloud',            1, 1),
(9,  'elena.vargas@empresa.com',     '$2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO', 'Auditoría TI y Calidad',    1, 1),
(10, 'diego.quispe@empresa.com',     '$2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO', 'Sistemas Empresariales',     1, 1),

-- Técnicos de Soporte (rol_id = 2)
(11, 'tecnico@empresa.com',          '$2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO', 'Soporte Técnico',           2, 1),
(12, 'pedro.gomez@empresa.com',      '$2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO', 'Mesa de Ayuda',             2, 1),
(13, 'lucia.sanchez@empresa.com',    '$2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO', 'Soporte Nivel 2',          2, 1),
(14, 'miguel.herrera@empresa.com',   '$2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO', 'Mantenimiento Hardware',    2, 1),
(15, 'andrea.morales@empresa.com',   '$2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO', 'Redes y Conectividad',      2, 1),
(16, 'fernando.castro@empresa.com',  '$2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO', 'Telefonía IP y Comunicaciones', 2, 1),
(17, 'gabriela.rojas@empresa.com',   '$2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO', 'Soporte Aplicaciones',      2, 1),
(18, 'ricardo.espinoza@empresa.com', '$2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO', 'Mantenimiento Preventivo',  2, 1),
(19, 'valeria.salazar@empresa.com',  '$2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO', 'Helpdesk Central',          2, 1),
(20, 'victor.alvarez@empresa.com',   '$2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO', 'Soporte en Terreno',        2, 1),

-- Colaboradores (rol_id = 3)
(21, 'colaborador@empresa.com',      '$2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO', 'Operaciones',               3, 1),
(22, 'juan.perez@empresa.com',       '$2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO', 'Contabilidad',              3, 1),
(23, 'rosa.diaz@empresa.com',        '$2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO', 'Recursos Humanos',          3, 1),
(24, 'manuel.silva@empresa.com',     '$2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO', 'Ventas y Marketing',        3, 1),
(25, 'carolina.chavez@empresa.com',  '$2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO', 'Logística y Almacén',      3, 1),
(26, 'roberto.flores@empresa.com',   '$2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO', 'Finanzas y Tesorería',      3, 1),
(27, 'silvia.gutierrez@empresa.com', '$2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO', 'Legal y Cumplimiento',      3, 1),
(28, 'cesar.campos@empresa.com',     '$2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO', 'Atención al Cliente',       3, 1),
(29, 'monica.rios@empresa.com',      '$2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO', 'Compras y Adquisiciones',   3, 1),
(30, 'alberto.delgado@empresa.com',  '$2a$10$rG1QY83Epe3/1CSMIGKgxOsdBSnSTTmSdGwrvYqozHwWjaZ8BqoZO', 'Administración General',    3, 1);

-- 2.3 Inserción de Incidencias de Prueba
INSERT INTO incidencias (id, codigo, titulo, descripcion, area_afectada, estado, categoria, prioridad, fecha_creacion, usuario_reportador_id, tecnico_asignado_id) VALUES
(1,  'INC-A1B2C3D4', 'Falla en encendido de estación de trabajo', 'La computadora del puesto 4 de Contabilidad no enciende tras corte eléctrico.', 'Contabilidad', 'RESUELTO', 'HARDWARE', 'ALTA', '2026-10-10 08:30:00', 22, 11),
(2,  'INC-E5F6G7H8', 'Error de licencia en suite de ofimática', 'Aparece aviso de activación no válida en Microsoft Office en varias PCs.', 'Recursos Humanos', 'RESUELTO', 'SOFTWARE', 'MEDIA', '2026-10-10 09:15:00', 23, 12),
(3,  'INC-I9J0K1L2', 'Sin conexión a la red cableada en piso 3', 'Múltiples estaciones de trabajo perdieron enlace DHCP en el switch central.', 'Ventas y Marketing', 'RESUELTO', 'RED', 'ALTA', '2026-10-11 10:00:00', 24, 15),
(4,  'INC-M3N4O5P6', 'Teléfono IP sin tono de marcado', 'El anexo 3402 no registra contra el servidor Asterisk.', 'Logística y Almacén', 'RESUELTO', 'TELEFONIA_IP', 'BAJA', '2026-10-11 11:30:00', 25, 16),
(5,  'INC-Q7R8S9T0', 'Lentitud severa en sistema ERP contable', 'Las consultas del módulo de facturación tardan más de 30 segundos.', 'Finanzas y Tesorería', 'EN_PROCESO', 'SOFTWARE', 'ALTA', '2026-10-12 14:00:00', 26, 13),
(6,  'INC-U1V2W3X4', 'Impresora multifuncional atascada y no imprime', 'La impresora departamental presenta error de rodillo y papel trabado.', 'Legal y Cumplimiento', 'EN_PROCESO', 'HARDWARE', 'MEDIA', '2026-10-12 15:45:00', 27, 14),
(7,  'INC-Y5Z6A7B8', 'Pérdida intermitente de señal Wi-Fi', 'La red inalámbrica para invitados y personal sufre caídas cada 15 minutos.', 'Atención al Cliente', 'EN_PROCESO', 'RED', 'MEDIA', '2026-10-13 09:20:00', 28, 15),
(8,  'INC-C9D0E1F2', 'Monitor parpadea continuamente', 'El monitor secundario conectado por HDMI muestra líneas verdes horizontales.', 'Compras y Adquisiciones', 'EN_PROCESO', 'HARDWARE', 'BAJA', '2026-10-13 11:10:00', 29, 11),
(9,  'INC-G3H4I5J6', 'Solicitud de instalación de software estadístico', 'Requerimiento de instalación de software R y Python para análisis.', 'Operaciones', 'PENDIENTE', 'SOFTWARE', 'BAJA', '2026-10-14 08:00:00', 21, NULL),
(10, 'INC-K7L8M9N0', 'Imposibilidad de acceder a carpeta compartida de red', 'Al intentar entrar a \\\\servidor\\recursos aparece error de permisos.', 'Administración General', 'PENDIENTE', 'RED', 'ALTA', '2026-10-14 10:40:00', 30, NULL),
(11, 'INC-O1P2Q3R4', 'Auricular de teléfono IP con ruido blanco', 'Llamadas entrantes del área de reclamos se escuchan con fuerte interferencia.', 'Atención al Cliente', 'PENDIENTE', 'TELEFONIA_IP', 'MEDIA', '2026-10-14 13:15:00', 28, NULL),
(12, 'INC-S5T6U7V8', 'Teclado no responde a varias teclas numéricas', 'Teclado mecánico presenta fallo en bloque numérico y tecla Enter.', 'Contabilidad', 'PENDIENTE', 'HARDWARE', 'BAJA', '2026-10-15 09:00:00', 22, NULL);

-- 2.4 Inserción de Soluciones (Para incidencias en estado RESUELTO: IDs 1, 2, 3, 4)
INSERT INTO soluciones (id, incidencia_id, diagnostico, acciones_correctivas, detalle_solucion, fecha_cierre, tiempo_atencion_minutos) VALUES
(1, 1,
 'Fuente de poder de 500W dañada por sobretensión en la línea de suministro eléctrico.',
 'Se procedió al reemplazo de la fuente por una certificada 80 Plus Bronze y se conectó la PC a un estabilizador UPS.',
 'Equipo encendido correctamente, pruebas de voltaje nominal satisfactorias y operatividad restablecida.',
 '2026-10-10 10:15:00', 105),

(2, 2,
 'Servidor KMS de licencias corporativas no estaba respondiendo por cambio en la política de firewall local.',
 'Se actualizó la regla de entrada en el puerto 1688 y se ejecutó slmgr /ato en las estaciones afectadas.',
 'Licenciamiento validado exitosamente en todos los equipos del área.',
 '2026-10-10 11:00:00', 105),

(3, 3,
 'Patch cord deteriorado en el rack del piso 3 que conectaba el switch secundario con el core.',
 'Se reemplazó el cable UTP Categoría 6 por uno certificado y se reconfiguró el puerto troncal.',
 'Conectividad Gigabit restaurada con latencia inferior a 1ms hacia el gateway.',
 '2026-10-11 11:40:00', 100),

(4, 4,
 'Parámetros SIP desconfigurados en el terminal Grandstream debido a reinicio de fábrica accidental.',
 'Se aprovisionó nuevamente el archivo de configuración XML con credenciales del anexo 3402.',
 'Registro exitoso contra servidor PBX y pruebas de llamada bidireccionales completadas.',
 '2026-10-11 12:45:00', 75);

-- =============================================================================
-- FIN DEL SCRIPT
-- =============================================================================
