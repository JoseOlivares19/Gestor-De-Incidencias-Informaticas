package com.gestorincidencias.repository;

import com.gestorincidencias.model.entity.Usuario;
import com.gestorincidencias.model.entity.RolNombre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreoCorporativo(String correoCorporativo);
    boolean existsByCorreoCorporativo(String correoCorporativo);
    List<Usuario> findByRolNombreAndActivoTrue(RolNombre rolNombre);
}
