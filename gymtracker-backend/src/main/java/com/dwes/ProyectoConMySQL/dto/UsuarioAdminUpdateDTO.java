package com.dwes.ProyectoConMySQL.dto;

import com.dwes.ProyectoConMySQL.model.Rol;
import lombok.Data;

@Data
public class UsuarioAdminUpdateDTO {
    private Boolean isActive;
    private Rol rol;
}