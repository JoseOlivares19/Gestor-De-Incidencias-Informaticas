package com.gestorincidencias.repository;

import com.gestorincidencias.model.entity.Incidencia;
import com.gestorincidencias.model.entity.EstadoIncidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Long>, JpaSpecificationExecutor<Incidencia> {

    boolean existsByCodigo(String codigo);
    
    @Query("SELECT COUNT(i) FROM Incidencia i WHERE i.estado = :estado")
    long countByEstado(EstadoIncidencia estado);
    
    @Query("SELECT i.categoria, COUNT(i) FROM Incidencia i GROUP BY i.categoria")
    List<Object[]> countGroupedByCategoria();
}
