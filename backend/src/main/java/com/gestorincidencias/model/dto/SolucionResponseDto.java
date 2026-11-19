package com.gestorincidencias.model.dto;

import java.time.LocalDateTime;

public class SolucionResponseDto {

    private Long id;
    private String diagnostico;
    private String accionesCorrectivas;
    private String detalleSolucion;
    private LocalDateTime fechaCierre;
    private Long tiempoAtencionMinutos;

    public SolucionResponseDto() {
    }

    public SolucionResponseDto(Long id, String diagnostico, String accionesCorrectivas,
                               String detalleSolucion, LocalDateTime fechaCierre, Long tiempoAtencionMinutos) {
        this.id = id;
        this.diagnostico = diagnostico;
        this.accionesCorrectivas = accionesCorrectivas;
        this.detalleSolucion = detalleSolucion;
        this.fechaCierre = fechaCierre;
        this.tiempoAtencionMinutos = tiempoAtencionMinutos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
