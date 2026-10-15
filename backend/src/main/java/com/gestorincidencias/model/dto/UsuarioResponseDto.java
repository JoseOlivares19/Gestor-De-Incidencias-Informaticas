package com.gestorincidencias.model.dto;

public class UsuarioResponseDto {

    private Long id;
    private String correoCorporativo;
    private String area;
    private String rol;
    private Boolean activo;

    public UsuarioResponseDto() {
    }

    public UsuarioResponseDto(Long id, String correoCorporativo, String area, String rol, Boolean activo) {
        this.id = id;
        this.correoCorporativo = correoCorporativo;
        this.area = area;
        this.rol = rol;
        this.activo = activo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorreoCorporativo() {
        return correoCorporativo;
    }

    public void setCorreoCorporativo(String correoCorporativo) {
        this.correoCorporativo = correoCorporativo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
