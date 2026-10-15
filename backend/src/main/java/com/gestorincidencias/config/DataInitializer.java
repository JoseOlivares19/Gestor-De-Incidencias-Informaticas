package com.gestorincidencias.config;

import com.gestorincidencias.model.entity.Rol;
import com.gestorincidencias.model.entity.RolNombre;
import com.gestorincidencias.model.entity.Usuario;
import com.gestorincidencias.repository.RolRepository;
import com.gestorincidencias.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeData(
            RolRepository rolRepository,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            // 1. Inicializar roles si no existen
            for (RolNombre rolNombre : RolNombre.values()) {
                if (rolRepository.findByNombre(rolNombre).isEmpty()) {
                    rolRepository.save(new Rol(rolNombre));
                }
            }

            // 2. Crear usuarios por defecto si no existen
            crearUsuarioSiNoExiste(
                    usuarioRepository,
                    rolRepository,
                    passwordEncoder,
                    "admin@empresa.com",
                    "password123",
                    "TI y Sistemas",
                    RolNombre.ADMINISTRADOR_TI
            );

            crearUsuarioSiNoExiste(
                    usuarioRepository,
                    rolRepository,
                    passwordEncoder,
                    "tecnico@empresa.com",
                    "password123",
                    "Soporte Técnico",
                    RolNombre.TECNICO_SOPORTE
            );

            crearUsuarioSiNoExiste(
                    usuarioRepository,
                    rolRepository,
                    passwordEncoder,
                    "colaborador@empresa.com",
                    "password123",
                    "Operaciones",
                    RolNombre.COLABORADOR
            );
        };
    }

    private void crearUsuarioSiNoExiste(
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository,
            PasswordEncoder passwordEncoder,
            String correo,
            String password,
            String area,
            RolNombre rolNombre) {
        if (!usuarioRepository.existsByCorreoCorporativo(correo)) {
            Rol rol = rolRepository.findByNombre(rolNombre)
                    .orElseThrow(() -> new IllegalStateException("Rol no encontrado: " + rolNombre));
            Usuario usuario = new Usuario(
                    correo,
                    passwordEncoder.encode(password),
                    area,
                    rol,
                    true
            );
            usuarioRepository.save(usuario);
        }
    }
}

