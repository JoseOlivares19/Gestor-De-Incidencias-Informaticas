package com.gestorincidencias.model.dto;

import jakarta.validation.constraints.NotBlank;

public class SolucionRequestDto {

    @NotBlank
    private String diagnostico;

    @NotBlank
    private String accionesCorrectivas;

    @NotBlank
    private String detalleSolucion;

    public SolucionRequestDto() {
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
}
