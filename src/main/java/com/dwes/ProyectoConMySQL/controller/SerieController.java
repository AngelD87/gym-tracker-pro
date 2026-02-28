package com.dwes.ProyectoConMySQL.controller;

import com.dwes.ProyectoConMySQL.dto.SerieCreateDTO;
import com.dwes.ProyectoConMySQL.dto.SerieResponseDTO;
import com.dwes.ProyectoConMySQL.dto.SerieUpdateDTO;
import com.dwes.ProyectoConMySQL.service.SerieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/series")
public class SerieController {

    private final SerieService serieService;

    public SerieController(SerieService serieService) {
        this.serieService = serieService;
    }

    //CREAR
    @PostMapping
    public ResponseEntity<SerieResponseDTO> crear(@RequestBody SerieCreateDTO dto) {
        return ResponseEntity.ok(serieService.añadirSerieDTO(dto));
    }

    //LISTAR POR EJERCICIO
    @GetMapping("/entrenamiento-ejercicio/{idEntrenamientoEjercicio}")
    public ResponseEntity<List<SerieResponseDTO>> listarPorEjercicio(
            @PathVariable Long idEntrenamientoEjercicio) {
        return ResponseEntity.ok(serieService.listarPorEntrenamientoEjercicioDTO(idEntrenamientoEjercicio));
    }

    //BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<SerieResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(serieService.buscarPorIdDTO(id));
    }

    //ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<SerieResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody SerieUpdateDTO dto) {
        return ResponseEntity.ok(serieService.actualizarDTO(id, dto));
    }

    //ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        serieService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
