package com.gestorincidencias.repository;

import com.gestorincidencias.model.entity.Rol;
import com.gestorincidencias.model.entity.RolNombre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(RolNombre nombre);
}
