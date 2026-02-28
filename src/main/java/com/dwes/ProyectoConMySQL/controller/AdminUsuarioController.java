package com.dwes.ProyectoConMySQL.controller;

import com.dwes.ProyectoConMySQL.dto.UsuarioAdminUpdateDTO;
import com.dwes.ProyectoConMySQL.dto.UsuarioResponseDTO;
import com.dwes.ProyectoConMySQL.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/usuarios")
public class AdminUsuarioController {

    private final UsuarioService usuarioService;

    public AdminUsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    //ACTUALIZAR COMO ADMIN (activar/desactivar, cambiar rol)
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarComoAdmin(
            @PathVariable Long id,
            @RequestBody UsuarioAdminUpdateDTO dto) {
        return ResponseEntity.ok(usuarioService.actualizarComoAdmin(id, dto));
    }
}