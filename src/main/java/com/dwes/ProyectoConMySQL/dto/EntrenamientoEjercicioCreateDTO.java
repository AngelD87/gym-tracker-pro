package com.dwes.ProyectoConMySQL.dto;

import lombok.Data;

@Data
public class EntrenamientoEjercicioCreateDTO {
    private Long idEntrenamiento;
    private Long idEjercicio;
    private Integer orden;
    private String notas;
}