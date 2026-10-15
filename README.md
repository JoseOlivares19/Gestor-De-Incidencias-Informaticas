# Sistema de Gestión de Incidencias Informáticas

Proyecto integrador que comprende **Backend (Spring Boot)** y **Frontend (Angular)** para la gestión y seguimiento de incidencias de TI.

---

## 📁 Estructura del Proyecto

```
web integrado/
├── backend/          # API REST con Spring Boot 3.4 + Spring Security + JWT + JPA / Hibernate
├── frontend/         # SPA con Angular (Standalone Components, Signals, Reactive Forms, SCSS)
└── README.md
```

---

## 🚀 Requisitos Previos

- **Java 21**
- **Maven** (o usar el wrapper `./mvnw`)
- **Node.js** (v20 o superior recomendado)
- **Angular CLI**
- **MySQL** (puerto 3306, base de datos `gestorincidencias` o configurar en `application.properties`)

---

## 🛠️ Ejecución del Proyecto

### 1. Backend (Spring Boot)

```bash
cd backend
./mvnw spring-boot:run
```
El servidor backend se ejecutará en: `http://localhost:8080`

### 2. Frontend (Angular)

```bash
cd frontend
npm install
ng serve
```
La aplicación web se ejecutará en: `http://localhost:4200`

> **Nota:** La aplicación frontend cuenta con configuración de proxy (`proxy.conf.json`) que redirige automáticamente las peticiones de `/api/*` al backend en `http://localhost:8080`.

---

## 👥 Cuentas de Acceso por Defecto (Demo)

El sistema inicializa automáticamente las siguientes cuentas al arrancar el backend:

| Rol | Correo Corporativo | Contraseña | Área |
|---|---|---|---|
| **Administrador TI** | `admin@empresa.com` | `password123` | TI y Sistemas |
| **Técnico Soporte** | `tecnico@empresa.com` | `password123` | Soporte Técnico |
| **Colaborador** | `colaborador@empresa.com` | `password123` | Operaciones |

> La pantalla de Login incluye botones de acceso rápido de un solo clic para cada uno de estos roles.
