package com.dwes.ProyectoConMySQL.service;

import com.dwes.ProyectoConMySQL.dto.UsuarioAdminUpdateDTO;
import com.dwes.ProyectoConMySQL.dto.UsuarioCreateDTO;
import com.dwes.ProyectoConMySQL.dto.UsuarioResponseDTO;
import com.dwes.ProyectoConMySQL.dto.UsuarioUpdateDTO;
import com.dwes.ProyectoConMySQL.model.Rol;
import com.dwes.ProyectoConMySQL.model.Usuario;
import com.dwes.ProyectoConMySQL.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .idUsuario(usuario.getIdUsuario())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .pesoCorporal(usuario.getPesoCorporal())
                .altura(usuario.getAltura())
                .build();
    }

    //CREAR USUARIO (DTO)
    public UsuarioResponseDTO crearUsuarioDesdeDTO(UsuarioCreateDTO dto) {

        //VALIDACION: EMAIL duplicado
        String email = dto.getEmail().trim().toLowerCase();
        if(usuarioRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        //VALIDACION: Peso Corporal
        if(dto.getPesoCorporal() != null) {
            if(dto.getPesoCorporal() < 30 || dto.getPesoCorporal() > 300) {
                throw new IllegalArgumentException("El peso debe estar entre 30 y 300kg");
            }
        }

        //VALIDACION: altura
        if(dto.getAltura() != null) {
            if(dto.getAltura() < 1.0 || dto.getAltura() > 3.0) {
                throw new IllegalArgumentException("La altura debe estar entre 1 y 3 metros");
            }
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(email);
        usuario.setPasswordHash(dto.getPassword());
        usuario.setPesoCorporal(dto.getPesoCorporal());
        usuario.setAltura(dto.getAltura());

        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setIsActive(true);
        usuario.setRol(Rol.USUARIO);

        Usuario guardado = usuarioRepository.save(usuario);
        return toResponseDTO(guardado);
    }

    //LISTAR USUARIOS (DTO)
    public List<UsuarioResponseDTO> listarUsuariosDTO() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    //BUSCAR USUARIO POR ID (DTO)
    public UsuarioResponseDTO buscarPorIdDTO(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El usuario no existe"));

        return toResponseDTO(usuario);
    }

    //ACTUALIZAR DATOS PERSONALES (DTO) - NORMAL
    public UsuarioResponseDTO actualizarDesdeDTO(Long id, UsuarioUpdateDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El usuario no existe"));

        if (dto.getNombre() != null) {
            usuario.setNombre(dto.getNombre());
        }

        if (dto.getEmail() != null) {
            usuario.setEmail(dto.getEmail());
        }

        if (dto.getPassword() != null) {
            usuario.setPasswordHash(dto.getPassword());
        }

        if (dto.getPesoCorporal() != null) {
            usuario.setPesoCorporal(dto.getPesoCorporal());
        }

        if (dto.getAltura() != null) {
            usuario.setAltura(dto.getAltura());
        }

        Usuario actualizado = usuarioRepository.save(usuario);
        return toResponseDTO(actualizado);
    }

    //ACTUALIZAR COMO ADMIN (DTO) - SOLO rol/isActive
    public UsuarioResponseDTO actualizarComoAdmin(Long id, UsuarioAdminUpdateDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El usuario no existe"));

        if (dto.getIsActive() != null) {
            usuario.setIsActive(dto.getIsActive());
        }

        if (dto.getRol() != null) {
            usuario.setRol(dto.getRol());
        }

        Usuario actualizado = usuarioRepository.save(usuario);
        return toResponseDTO(actualizado);
    }

    //ELIMINAR USUARIO
    public void eliminarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El usuario no existe"));

        usuarioRepository.deleteById(id);
    }
}