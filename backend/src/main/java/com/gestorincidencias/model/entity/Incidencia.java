package com.gestorincidencias.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "incidencias")
public class Incidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String codigo;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false, length = 100)
    private String areaAfectada;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoIncidencia estado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CategoriaIncidencia categoria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PrioridadIncidencia prioridad;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_reportador_id", nullable = false)
    private Usuario usuarioReportador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnico_asignado_id")
    private Usuario tecnicoAsignado;

    @OneToOne(mappedBy = "incidencia", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Solucion solucion;

    @PrePersist
    public void prePersist() {
        if (this.fechaCreacion == null) {
            this.fechaCreacion = LocalDateTime.now();
        }
        if (this.estado == null) {
            this.estado = EstadoIncidencia.PENDIENTE;
        }
    }

    public Incidencia() {
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

    public EstadoIncidencia getEstado() {
        return estado;
    }

    public void setEstado(EstadoIncidencia estado) {
        this.estado = estado;
    }

    public CategoriaIncidencia getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaIncidencia categoria) {
        this.categoria = categoria;
    }

    public PrioridadIncidencia getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(PrioridadIncidencia prioridad) {
        this.prioridad = prioridad;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario getUsuarioReportador() {
        return usuarioReportador;
    }

    public void setUsuarioReportador(Usuario usuarioReportador) {
        this.usuarioReportador = usuarioReportador;
    }

    public Usuario getTecnicoAsignado() {
        return tecnicoAsignado;
    }

    public void setTecnicoAsignado(Usuario tecnicoAsignado) {
        this.tecnicoAsignado = tecnicoAsignado;
    }

    public Solucion getSolucion() {
        return solucion;
    }

    public void setSolucion(Solucion solucion) {
        this.solucion = solucion;
        if (solucion != null) {
            solucion.setIncidencia(this);
        }
    }
}
