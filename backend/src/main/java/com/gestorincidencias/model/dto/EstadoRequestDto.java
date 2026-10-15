package com.gestorincidencias.model.dto;

import jakarta.validation.constraints.NotBlank;

public class EstadoRequestDto {

    @NotBlank
    private String estado;

    public EstadoRequestDto() {
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
