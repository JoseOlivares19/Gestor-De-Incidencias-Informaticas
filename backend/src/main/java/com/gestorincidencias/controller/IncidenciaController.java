package com.gestorincidencias.controller;

import com.gestorincidencias.model.dto.*;
import com.gestorincidencias.security.services.UserDetailsImpl;
import com.gestorincidencias.service.IncidenciaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidencias")
public class IncidenciaController {

    private final IncidenciaService incidenciaService;

    public IncidenciaController(IncidenciaService incidenciaService) {
        this.incidenciaService = incidenciaService;
    }

    @PostMapping
    public ResponseEntity<IncidenciaResponseDto> crear(
            @Valid @RequestBody IncidenciaRequestDto request,
            @AuthenticationPrincipal UserDetailsImpl principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(incidenciaService.crear(request, principal));
    }

    @GetMapping
    public ResponseEntity<List<IncidenciaResponseDto>> listar(
            @AuthenticationPrincipal UserDetailsImpl principal) {
        return ResponseEntity.ok(incidenciaService.listar(principal));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidenciaResponseDto> buscar(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl principal) {
        return ResponseEntity.ok(incidenciaService.buscar(id, principal));
    }

    @PutMapping("/{id}/asignar")
    @PreAuthorize("hasRole('ADMINISTRADOR_TI')")
    public ResponseEntity<IncidenciaResponseDto> asignar(
            @PathVariable Long id, @Valid @RequestBody AsignarTecnicoDto request) {
        return ResponseEntity.ok(incidenciaService.asignar(id, request));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<IncidenciaResponseDto> cambiarEstado(
            @PathVariable Long id,
            @Valid @RequestBody EstadoRequestDto request,
            @AuthenticationPrincipal UserDetailsImpl principal) {
        return ResponseEntity.ok(incidenciaService.cambiarEstado(id, request.getEstado(), principal));
    }

    @PostMapping("/{id}/solucion")
    public ResponseEntity<IncidenciaResponseDto> resolver(
            @PathVariable Long id,
            @Valid @RequestBody SolucionRequestDto request,
            @AuthenticationPrincipal UserDetailsImpl principal) {
        return ResponseEntity.ok(incidenciaService.resolver(id, request, principal));
    }

    @GetMapping("/estadisticas")
    @PreAuthorize("hasRole('ADMINISTRADOR_TI')")
    public ResponseEntity<Object> estadisticas() {
        return ResponseEntity.ok(incidenciaService.estadisticas());
    }
}
