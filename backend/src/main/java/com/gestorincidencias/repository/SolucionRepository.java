package com.gestorincidencias.repository;

import com.gestorincidencias.model.entity.Solucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SolucionRepository extends JpaRepository<Solucion, Long> {
    Optional<Solucion> findByIncidenciaId(Long incidenciaId);

    @Query("SELECT AVG(s.tiempoAtencionMinutos) FROM Solucion s")
    Double getAverageResolucionTime();
}
