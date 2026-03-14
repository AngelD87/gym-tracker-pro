package com.dwes.ProyectoConMySQL.service;

import com.dwes.ProyectoConMySQL.dto.LoginRequestDTO;
import com.dwes.ProyectoConMySQL.dto.LoginResponseDTO;
import com.dwes.ProyectoConMySQL.model.Usuario;
import com.dwes.ProyectoConMySQL.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() ->
                        new IllegalArgumentException("Email o contraseña incorrectos"));

        if (!usuario.getPasswordHash().equals(dto.getPassword())) {
            throw new IllegalArgumentException("Email o contraseña incorrectos");
        }

        if (!usuario.getIsActive()) {
            throw new IllegalStateException("Usuario desactivado");
        }

        return LoginResponseDTO.builder()
                .idUsuario(usuario.getIdUsuario())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .rol(usuario.getRol())
                .build();
    }
}