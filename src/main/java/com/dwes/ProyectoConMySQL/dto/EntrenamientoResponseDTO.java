package com.dwes.ProyectoConMySQL.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EntrenamientoResponseDTO {
    private Long idEntrenamiento;
    private Long idUsuario;
    private LocalDateTime inicio;
    private LocalDateTime fin;
    private Integer valoracion;
    private String comentario;
}