package com.gestorincidencias.model.dto;

import jakarta.validation.constraints.NotNull;

public class AsignarTecnicoDto {

    @NotNull
    private Long tecnicoId;

    public AsignarTecnicoDto() {
    }

    public Long getTecnicoId() {
        return tecnicoId;
    }

    public void setTecnicoId(Long tecnicoId) {
        this.tecnicoId = tecnicoId;
    }
}
