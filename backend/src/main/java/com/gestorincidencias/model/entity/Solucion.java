package com.gestorincidencias.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "soluciones")
public class Solucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "incidencia_id", nullable = false, unique = true)
    private Incidencia incidencia;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String diagnostico;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String accionesCorrectivas;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String detalleSolucion;

    @Column(nullable = false)
    private LocalDateTime fechaCierre;

    @Column(nullable = false)
    private Long tiempoAtencionMinutos;

    public Solucion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Incidencia getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(Incidencia incidencia) {
        this.incidencia = incidencia;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getAccionesCorrectivas() {
        return accionesCorrectivas;
    }

    public void setAccionesCorrectivas(String accionesCorrectivas) {
        this.accionesCorrectivas = accionesCorrectivas;
    }

    public String getDetalleSolucion() {
        return detalleSolucion;
    }

    public void setDetalleSolucion(String detalleSolucion) {
        this.detalleSolucion = detalleSolucion;
    }

    public LocalDateTime getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDateTime fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Long getTiempoAtencionMinutos() {
        return tiempoAtencionMinutos;
    }

    public void setTiempoAtencionMinutos(Long tiempoAtencionMinutos) {
        this.tiempoAtencionMinutos = tiempoAtencionMinutos;
    }
}
