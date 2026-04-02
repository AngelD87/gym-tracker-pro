package com.dwes.ProyectoConMySQL.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioResponseDTO {
    private Long idUsuario;
    private String nombre;
    private String email;
    private Double pesoCorporal;
    private Double altura;
}