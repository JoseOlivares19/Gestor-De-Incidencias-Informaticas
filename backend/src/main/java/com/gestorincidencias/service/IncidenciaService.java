package com.gestorincidencias.service;

import com.gestorincidencias.exception.BusinessException;
import com.gestorincidencias.exception.ResourceNotFoundException;
import com.gestorincidencias.model.dto.AsignarTecnicoDto;
import com.gestorincidencias.model.dto.IncidenciaRequestDto;
import com.gestorincidencias.model.dto.IncidenciaResponseDto;
import com.gestorincidencias.model.dto.SolucionRequestDto;
import com.gestorincidencias.model.entity.*;
import com.gestorincidencias.repository.IncidenciaRepository;
import com.gestorincidencias.repository.SolucionRepository;
import com.gestorincidencias.repository.UsuarioRepository;
import com.gestorincidencias.security.services.UserDetailsImpl;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class IncidenciaService {

    private final IncidenciaRepository incidenciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final SolucionRepository solucionRepository;

    public IncidenciaService(
            IncidenciaRepository incidenciaRepository,
            UsuarioRepository usuarioRepository,
            SolucionRepository solucionRepository) {
        this.incidenciaRepository = incidenciaRepository;
        this.usuarioRepository = usuarioRepository;
        this.solucionRepository = solucionRepository;
    }

    @Transactional
    public IncidenciaResponseDto crear(IncidenciaRequestDto request, UserDetailsImpl principal) {
        Usuario reportador = usuario(principal.getId());
        Incidencia incidencia = new Incidencia();
        incidencia.setCodigo(generarCodigo());
        incidencia.setTitulo(request.getTitulo().trim());
        incidencia.setDescripcion(request.getDescripcion().trim());
        incidencia.setAreaAfectada(request.getAreaAfectada().trim());
        incidencia.setCategoria(parseCategoria(request.getCategoria()));
        incidencia.setPrioridad(parsePrioridad(request.getPrioridad()));
        incidencia.setUsuarioReportador(reportador);
        return toResponse(incidenciaRepository.save(incidencia));
    }

    @Transactional(readOnly = true)
    public List<IncidenciaResponseDto> listar(UserDetailsImpl principal) {
        List<Incidencia> incidencias;
        RolNombre rol = rol(principal);
        if (rol == RolNombre.COLABORADOR) {
            incidencias = incidenciaRepository.findAll().stream()
                    .filter(i -> i.getUsuarioReportador().getId().equals(principal.getId()))
                    .toList();
        } else if (rol == RolNombre.TECNICO_SOPORTE) {
            incidencias = incidenciaRepository.findAll().stream()
                    .filter(i -> i.getTecnicoAsignado() == null
                            || i.getTecnicoAsignado().getId().equals(principal.getId()))
                    .toList();
        } else {
            incidencias = incidenciaRepository.findAll();
        }
        return incidencias.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public IncidenciaResponseDto buscar(Long id, UserDetailsImpl principal) {
        Incidencia incidencia = buscarEntidad(id);
        validarAccesoLectura(incidencia, principal);
        return toResponse(incidencia);
    }

    @Transactional
    public IncidenciaResponseDto asignar(Long id, AsignarTecnicoDto request) {
        Incidencia incidencia = buscarEntidad(id);
        Usuario tecnico = usuario(request.getTecnicoId());
        if (tecnico.getRol().getNombre() != RolNombre.TECNICO_SOPORTE || !tecnico.getActivo()) {
            throw new BusinessException("Solo se puede asignar un técnico de soporte activo");
        }
        incidencia.setTecnicoAsignado(tecnico);
        if (incidencia.getEstado() == EstadoIncidencia.PENDIENTE) {
            incidencia.setEstado(EstadoIncidencia.EN_PROCESO);
        }
        return toResponse(incidenciaRepository.save(incidencia));
    }

    @Transactional
    public IncidenciaResponseDto cambiarEstado(Long id, String estado, UserDetailsImpl principal) {
        Incidencia incidencia = buscarEntidad(id);
        validarAccesoEdicion(incidencia, principal);
        EstadoIncidencia nuevoEstado = parseEstado(estado);
        if (nuevoEstado == EstadoIncidencia.RESUELTO && incidencia.getSolucion() == null) {
            throw new BusinessException("Una incidencia debe tener una solución para marcarse como RESUELTO");
        }
        incidencia.setEstado(nuevoEstado);
        return toResponse(incidenciaRepository.save(incidencia));
    }

    @Transactional
    public IncidenciaResponseDto resolver(Long id, SolucionRequestDto request, UserDetailsImpl principal) {
        Incidencia incidencia = buscarEntidad(id);
        validarAccesoEdicion(incidencia, principal);
        if (incidencia.getSolucion() != null) {
            throw new BusinessException("La incidencia ya tiene una solución registrada");
        }

        LocalDateTime cierre = LocalDateTime.now();
        Solucion solucion = new Solucion();
        solucion.setIncidencia(incidencia);
        solucion.setDiagnostico(request.getDiagnostico().trim());
        solucion.setAccionesCorrectivas(request.getAccionesCorrectivas().trim());
        solucion.setDetalleSolucion(request.getDetalleSolucion().trim());
        solucion.setFechaCierre(cierre);
        solucion.setTiempoAtencionMinutos(Math.max(0, Duration.between(
                incidencia.getFechaCreacion(), cierre).toMinutes()));
        incidencia.setSolucion(solucion);
        incidencia.setEstado(EstadoIncidencia.RESUELTO);
        solucionRepository.save(solucion);
        return toResponse(incidenciaRepository.save(incidencia));
    }

    @Transactional(readOnly = true)
    public Incidencia buscarEntidad(Long id) {
        return incidenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incidencia no encontrada: " + id));
    }

    @Transactional(readOnly = true)
    public Object estadisticas() {
        return new Estadisticas(
                incidenciaRepository.count(),
                incidenciaRepository.countByEstado(EstadoIncidencia.PENDIENTE),
                incidenciaRepository.countByEstado(EstadoIncidencia.EN_PROCESO),
                incidenciaRepository.countByEstado(EstadoIncidencia.RESUELTO),
                solucionRepository.getAverageResolucionTime(),
                incidenciaRepository.countGroupedByCategoria().stream()
                        .collect(java.util.stream.Collectors.toMap(
                                row -> ((CategoriaIncidencia) row[0]).name(),
                                row -> ((Number) row[1]).longValue())));
    }

    private void validarAccesoLectura(Incidencia incidencia, UserDetailsImpl principal) {
        RolNombre rol = rol(principal);
        boolean permitido = rol == RolNombre.ADMINISTRADOR_TI
                || incidencia.getUsuarioReportador().getId().equals(principal.getId())
                || (incidencia.getTecnicoAsignado() != null
                && incidencia.getTecnicoAsignado().getId().equals(principal.getId()));
        if (!permitido) {
            throw new AccessDeniedException("No tiene permiso para consultar esta incidencia");
        }
    }

    private void validarAccesoEdicion(Incidencia incidencia, UserDetailsImpl principal) {
        RolNombre rol = rol(principal);
        boolean permitido = rol == RolNombre.ADMINISTRADOR_TI
                || (rol == RolNombre.TECNICO_SOPORTE
                && incidencia.getTecnicoAsignado() != null
                && incidencia.getTecnicoAsignado().getId().equals(principal.getId()));
        if (!permitido) {
            throw new AccessDeniedException("Solo el administrador o el técnico asignado puede modificar la incidencia");
        }
    }

    private Usuario usuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + id));
    }

    private RolNombre rol(UserDetailsImpl principal) {
        return principal.getAuthorities().stream()
                .findFirst()
                .map(authority -> RolNombre.valueOf(authority.getAuthority().replace("ROLE_", "")))
                .orElseThrow(() -> new AccessDeniedException("El usuario no tiene un rol asignado"));
    }

    private String generarCodigo() {
        String codigo;
        do {
            codigo = "INC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ROOT);
        } while (incidenciaRepository.existsByCodigo(codigo));
        return codigo;
    }

    private CategoriaIncidencia parseCategoria(String value) {
        try {
            return CategoriaIncidencia.valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new BusinessException("Categoría inválida");
        }
    }

    private PrioridadIncidencia parsePrioridad(String value) {
        try {
            return PrioridadIncidencia.valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new BusinessException("Prioridad inválida");
        }
    }

    private EstadoIncidencia parseEstado(String value) {
        try {
            return EstadoIncidencia.valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new BusinessException("Estado inválido");
        }
    }

    private IncidenciaResponseDto toResponse(Incidencia incidencia) {
        IncidenciaResponseDto response = new IncidenciaResponseDto();
        response.setId(incidencia.getId());
        response.setCodigo(incidencia.getCodigo());
        response.setTitulo(incidencia.getTitulo());
        response.setDescripcion(incidencia.getDescripcion());
        response.setAreaAfectada(incidencia.getAreaAfectada());
        response.setEstado(incidencia.getEstado().name());
        response.setCategoria(incidencia.getCategoria().name());
        response.setPrioridad(incidencia.getPrioridad().name());
        response.setFechaCreacion(incidencia.getFechaCreacion());
        response.setUsuarioReportadorId(incidencia.getUsuarioReportador().getId());
        response.setUsuarioReportadorCorreo(incidencia.getUsuarioReportador().getCorreoCorporativo());
        if (incidencia.getTecnicoAsignado() != null) {
            response.setTecnicoAsignadoId(incidencia.getTecnicoAsignado().getId());
            response.setTecnicoAsignadoCorreo(incidencia.getTecnicoAsignado().getCorreoCorporativo());
        }
        return response;
    }

    public record Estadisticas(
            long total,
            long pendientes,
            long enProceso,
            long resueltas,
            Double promedioAtencionMinutos,
            java.util.Map<String, Long> porCategoria) {
    }
}
