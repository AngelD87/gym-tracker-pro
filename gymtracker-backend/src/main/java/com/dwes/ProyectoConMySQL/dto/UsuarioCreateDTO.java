package com.dwes.ProyectoConMySQL.dto;

import lombok.Data;

@Data
public class UsuarioCreateDTO {
    private String nombre;
    private String email;
    private String password;
    private Double pesoCorporal;
    private Double altura;
}