package com.gestorincidencias.model.dto;

import java.time.LocalDateTime;

public class IncidenciaResponseDto {

    private Long id;
    private String codigo;
    private String titulo;
    private String descripcion;
    private String areaAfectada;
    private String estado;
    private String categoria;
    private String prioridad;
    private LocalDateTime fechaCreacion;
    private Long usuarioReportadorId;
    private String usuarioReportadorCorreo;
    private Long tecnicoAsignadoId;
    private String tecnicoAsignadoCorreo;

    public IncidenciaResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getUsuarioReportadorId() {
        return usuarioReportadorId;
    }

    public void setUsuarioReportadorId(Long usuarioReportadorId) {
        this.usuarioReportadorId = usuarioReportadorId;
    }

    public String getUsuarioReportadorCorreo() {
        return usuarioReportadorCorreo;
    }

    public void setUsuarioReportadorCorreo(String usuarioReportadorCorreo) {
        this.usuarioReportadorCorreo = usuarioReportadorCorreo;
    }

    public Long getTecnicoAsignadoId() {
        return tecnicoAsignadoId;
    }

    public void setTecnicoAsignadoId(Long tecnicoAsignadoId) {
        this.tecnicoAsignadoId = tecnicoAsignadoId;
    }

    public String getTecnicoAsignadoCorreo() {
        return tecnicoAsignadoCorreo;
    }

    public void setTecnicoAsignadoCorreo(String tecnicoAsignadoCorreo) {
        this.tecnicoAsignadoCorreo = tecnicoAsignadoCorreo;
    }
}
