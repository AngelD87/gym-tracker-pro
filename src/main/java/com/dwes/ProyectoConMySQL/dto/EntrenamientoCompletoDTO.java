package com.dwes.ProyectoConMySQL.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class EntrenamientoCompletoDTO {
    private Long idEntrenamiento;
    private LocalDateTime inicio;
    private LocalDateTime fin;
    private Integer valoracion;
    private String comentario;
    private List<EntrenamientoEjercicioCompletoDTO> ejercicios;
}