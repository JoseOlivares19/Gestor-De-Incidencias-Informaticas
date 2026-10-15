package com.gestorincidencias.service;

import com.gestorincidencias.exception.BusinessException;
import com.gestorincidencias.exception.ResourceNotFoundException;
import com.gestorincidencias.model.dto.UsuarioRequestDto;
import com.gestorincidencias.model.dto.UsuarioResponseDto;
import com.gestorincidencias.model.entity.Rol;
import com.gestorincidencias.model.entity.RolNombre;
import com.gestorincidencias.model.entity.Usuario;
import com.gestorincidencias.repository.RolRepository;
import com.gestorincidencias.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDto> listar() {
        return usuarioRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDto> listarTecnicos() {
        return usuarioRepository.findByRolNombreAndActivoTrue(RolNombre.TECNICO_SOPORTE)
                .stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Usuario buscarEntidad(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + id));
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDto buscar(Long id) {
        return toResponse(buscarEntidad(id));
    }

    @Transactional
    public UsuarioResponseDto crear(UsuarioRequestDto request) {
        String correo = request.getCorreoCorporativo().trim().toLowerCase(Locale.ROOT);
        if (usuarioRepository.existsByCorreoCorporativo(correo)) {
            throw new BusinessException("Ya existe un usuario con ese correo corporativo");
        }

        Rol rol = buscarRol(request.getRol());
        Usuario usuario = new Usuario(
                correo,
                passwordEncoder.encode(request.getPassword()),
                request.getArea().trim(),
                rol,
                request.getActivo());
        return toResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public UsuarioResponseDto actualizar(Long id, UsuarioRequestDto request) {
        Usuario usuario = buscarEntidad(id);
        String correo = request.getCorreoCorporativo().trim().toLowerCase(Locale.ROOT);

        usuarioRepository.findByCorreoCorporativo(correo)
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new BusinessException("Ya existe un usuario con ese correo corporativo");
                });

        usuario.setCorreoCorporativo(correo);
        usuario.setArea(request.getArea().trim());
        usuario.setRol(buscarRol(request.getRol()));
        usuario.setActivo(request.getActivo());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        return toResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public void eliminar(Long id) {
        Usuario usuario = buscarEntidad(id);
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    private Rol buscarRol(String value) {
        RolNombre nombre;
        try {
            nombre = RolNombre.valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new BusinessException("Rol inválido. Valores permitidos: ADMINISTRADOR_TI, TECNICO_SOPORTE, COLABORADOR");
        }
        return rolRepository.findByNombre(nombre)
                .orElseThrow(() -> new ResourceNotFoundException("El rol no está registrado: " + nombre));
    }

    public UsuarioResponseDto toResponse(Usuario usuario) {
        return new UsuarioResponseDto(
                usuario.getId(),
                usuario.getCorreoCorporativo(),
                usuario.getArea(),
                usuario.getRol().getNombre().name(),
                usuario.getActivo());
    }
}
