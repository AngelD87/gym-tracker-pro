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

        String email = dto.getEmail().trim().toLowerCase();
        //BUSCAR USUARIO POR EMAIL
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("Email o contraseña incorrectos"));

        //VERIFICAR CONTRASEÑA
        if (!usuario.getPasswordHash().equals(dto.getPassword())) {
            throw new IllegalArgumentException("Email o contraseña incorrectos");
        }

        //QUE ESTE ACTIVO
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