package com.gestorincidencias.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El nombre del rol es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 30)
    private RolNombre nombre;

    public Rol() {
    }

    public Rol(RolNombre nombre) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre del rol no puede ser nulo");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RolNombre getNombre() {
        return nombre;
    }

    public void setNombre(RolNombre nombre) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre del rol no puede ser nulo");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rol rol)) {
            return false;
        }
        return Objects.equals(id, rol.id) && nombre == rol.nombre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }

    @Override
    public String toString() {
        return "Rol{" +
                "id=" + id +
                ", nombre=" + nombre +
                '}';
    }
}
