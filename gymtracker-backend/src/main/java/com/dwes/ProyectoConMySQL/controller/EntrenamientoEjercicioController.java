package com.dwes.ProyectoConMySQL.controller;

import com.dwes.ProyectoConMySQL.dto.EntrenamientoEjercicioCreateDTO;
import com.dwes.ProyectoConMySQL.dto.EntrenamientoEjercicioResponseDTO;
import com.dwes.ProyectoConMySQL.dto.EntrenamientoEjercicioUpdateDTO;
import com.dwes.ProyectoConMySQL.service.EntrenamientoEjercicioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entrenamiento-ejercicios")
public class EntrenamientoEjercicioController {

    private final EntrenamientoEjercicioService entrenamientoEjercicioService;

    public EntrenamientoEjercicioController(EntrenamientoEjercicioService entrenamientoEjercicioService) {
        this.entrenamientoEjercicioService = entrenamientoEjercicioService;
    }

    //AÑADIR EJERCICIO
    @PostMapping
    public ResponseEntity<EntrenamientoEjercicioResponseDTO> añadir(@RequestBody EntrenamientoEjercicioCreateDTO dto) {
        return ResponseEntity.ok(entrenamientoEjercicioService.añadirEjercicioDTO(dto));
    }

    //LISTAR POR ENTRENAMIENTO
    @GetMapping("/entrenamiento/{idEntrenamiento}")
    public ResponseEntity<List<EntrenamientoEjercicioResponseDTO>> listarPorEntrenamiento(
            @PathVariable Long idEntrenamiento) {
        return ResponseEntity.ok(entrenamientoEjercicioService.listarPorEntrenamientoDTO(idEntrenamiento));
    }

    //BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<EntrenamientoEjercicioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(entrenamientoEjercicioService.buscarPorIdDTO(id));
    }

    //ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<EntrenamientoEjercicioResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody EntrenamientoEjercicioUpdateDTO dto) {
        return ResponseEntity.ok(entrenamientoEjercicioService.actualizarDTO(id, dto));
    }

    //ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        entrenamientoEjercicioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}