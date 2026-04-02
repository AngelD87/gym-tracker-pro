package com.dwes.ProyectoConMySQL.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MusculoResponseDTO {
    private Long idMusculo;
    private String nombre;
    private String descripcion;
}