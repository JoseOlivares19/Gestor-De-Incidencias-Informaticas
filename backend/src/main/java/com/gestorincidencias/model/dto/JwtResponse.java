package com.gestorincidencias.model.dto;

public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String correoCorporativo;
    private String rol;

    public JwtResponse(String token, Long id, String correoCorporativo, String rol) {
        this.token = token;
        this.id = id;
        this.correoCorporativo = correoCorporativo;
        this.rol = rol;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
