package com.dwes.ProyectoConMySQL.controller;

import com.dwes.ProyectoConMySQL.dto.MusculoCreateDTO;
import com.dwes.ProyectoConMySQL.dto.MusculoResponseDTO;
import com.dwes.ProyectoConMySQL.dto.MusculoUpdateDTO;
import com.dwes.ProyectoConMySQL.service.MusculoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/musculos")
public class MusculoController {

    private final MusculoService musculoService;

    public MusculoController(MusculoService musculoService) {
        this.musculoService = musculoService;
    }

    //CREAR
    @PostMapping
    public ResponseEntity<MusculoResponseDTO> crear(@RequestBody MusculoCreateDTO dto) {
        return ResponseEntity.ok(musculoService.crearDesdeDTO(dto));
    }

    //LISTAR
    @GetMapping
    public ResponseEntity<List<MusculoResponseDTO>> listar() {
        return ResponseEntity.ok(musculoService.listarDTO());
    }

    //BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<MusculoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(musculoService.buscarPorIdDTO(id));
    }

    //ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<MusculoResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody MusculoUpdateDTO dto) {
        return ResponseEntity.ok(musculoService.actualizarDesdeDTO(id, dto));
    }

    //ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        musculoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}