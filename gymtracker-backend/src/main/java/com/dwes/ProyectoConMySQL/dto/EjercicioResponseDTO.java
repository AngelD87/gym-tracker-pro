package com.dwes.ProyectoConMySQL.dto;

import com.dwes.ProyectoConMySQL.model.Dificultad;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EjercicioResponseDTO {
    private Long idEjercicio;
    private String nombre;
    private String descripcion;
    private String videoUrl;
    private Dificultad dificultad;
    private Boolean activo;
    private Long idMusculo;
    private String nombreMusculo;
}