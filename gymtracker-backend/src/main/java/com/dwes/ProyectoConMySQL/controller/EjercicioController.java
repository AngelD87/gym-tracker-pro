package com.dwes.ProyectoConMySQL.controller;

import com.dwes.ProyectoConMySQL.dto.EjercicioCreateDTO;
import com.dwes.ProyectoConMySQL.dto.EjercicioResponseDTO;
import com.dwes.ProyectoConMySQL.dto.EjercicioUpdateDTO;
import com.dwes.ProyectoConMySQL.service.EjercicioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ejercicios")
public class EjercicioController {

    private final EjercicioService ejercicioService;

    public EjercicioController(EjercicioService ejercicioService) {
        this.ejercicioService = ejercicioService;
    }

    //CREAR
    @PostMapping
    public ResponseEntity<EjercicioResponseDTO> crear(@RequestBody EjercicioCreateDTO dto) {
        return ResponseEntity.ok(ejercicioService.crearDesdeDTO(dto));
    }

    //LISTAR
    @GetMapping
    public ResponseEntity<List<EjercicioResponseDTO>> listar() {
        return ResponseEntity.ok(ejercicioService.listarDTO());
    }

    //LISTAR POR MUSCULO
    @GetMapping("/musculo/{idMusculo}")
    public ResponseEntity<List<EjercicioResponseDTO>> listarPorMusculo(@PathVariable Long idMusculo) {
        return ResponseEntity.ok(ejercicioService.listarPorMusculoDTO(idMusculo));
    }


    //BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<EjercicioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ejercicioService.buscarPorIdDTO(id));
    }

    //ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<EjercicioResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody EjercicioUpdateDTO dto) {
        return ResponseEntity.ok(ejercicioService.actualizarDesdeDTO(id, dto));
    }

    //ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        ejercicioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}