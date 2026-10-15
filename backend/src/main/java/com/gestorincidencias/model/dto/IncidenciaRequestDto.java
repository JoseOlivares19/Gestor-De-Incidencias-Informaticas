package com.gestorincidencias.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class IncidenciaRequestDto {

    @NotBlank
    @Size(max = 150)
    private String titulo;

    @NotBlank
    private String descripcion;

    @NotBlank
    @Size(max = 100)
    private String areaAfectada;

    @NotBlank
    private String categoria;

    @NotBlank
    private String prioridad;

    public IncidenciaRequestDto() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAreaAfectada() {
        return areaAfectada;
    }

    public void setAreaAfectada(String areaAfectada) {
        this.areaAfectada = areaAfectada;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }
}
