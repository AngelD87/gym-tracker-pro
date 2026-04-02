package com.dwes.ProyectoConMySQL.controller;

import com.dwes.ProyectoConMySQL.dto.UsuarioCreateDTO;
import com.dwes.ProyectoConMySQL.dto.UsuarioResponseDTO;
import com.dwes.ProyectoConMySQL.dto.UsuarioUpdateDTO;
import com.dwes.ProyectoConMySQL.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    //CREAR USUARIO
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crear(@RequestBody UsuarioCreateDTO dto) {
        return ResponseEntity.ok(usuarioService.crearUsuarioDesdeDTO(dto));
    }

    //LISTAR USUARIOS
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        return ResponseEntity.ok(usuarioService.listarUsuariosDTO());
    }

    //BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorIdDTO(id));
    }

    //ACTUALIZAR DATOS PERSONALES
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody UsuarioUpdateDTO dto) {
        return ResponseEntity.ok(usuarioService.actualizarDesdeDTO(id, dto));
    }

    //ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}