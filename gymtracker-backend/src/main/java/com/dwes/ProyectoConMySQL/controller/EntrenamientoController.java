package com.dwes.ProyectoConMySQL.controller;

import com.dwes.ProyectoConMySQL.dto.EntrenamientoCerrarDTO;
import com.dwes.ProyectoConMySQL.dto.EntrenamientoCompletoDTO;
import com.dwes.ProyectoConMySQL.dto.EntrenamientoCreateDTO;
import com.dwes.ProyectoConMySQL.dto.EntrenamientoResponseDTO;
import com.dwes.ProyectoConMySQL.service.EntrenamientoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entrenamientos")
public class EntrenamientoController {

    private final EntrenamientoService entrenamientoService;

    public EntrenamientoController(EntrenamientoService entrenamientoService) {
        this.entrenamientoService = entrenamientoService;
    }

    //CREAR
    @PostMapping
    public ResponseEntity<EntrenamientoResponseDTO> crear(@RequestBody EntrenamientoCreateDTO dto) {
        return ResponseEntity.ok(entrenamientoService.crearEntrenamientoDTO(dto));
    }

    //LISTAR
    @GetMapping
    public ResponseEntity<List<EntrenamientoResponseDTO>> listar() {
        return ResponseEntity.ok(entrenamientoService.listarDTO());
    }

    //BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<EntrenamientoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(entrenamientoService.buscarPorIdDTO(id));
    }

    //LISTAR POR USUARIO
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<EntrenamientoResponseDTO>> listarPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(entrenamientoService.listarPorUsuarioDTO(idUsuario));
    }

    //CERRAR ENTRENAMIENTO
    @PutMapping("/{id}/cerrar")
    public ResponseEntity<EntrenamientoResponseDTO> cerrar(
            @PathVariable Long id,
            @RequestBody EntrenamientoCerrarDTO dto) {
        return ResponseEntity.ok(entrenamientoService.cerrarEntrenamientoDesdeDTO(id, dto));
    }

    //ENTRENAMIENTO COMPLETO
    @GetMapping("/{id}/completo")
    public ResponseEntity<EntrenamientoCompletoDTO> obtenerCompleto(@PathVariable Long id) {
        return ResponseEntity.ok(entrenamientoService.obtenerEntrenamientoCompleto(id));
    }

    //ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        entrenamientoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}