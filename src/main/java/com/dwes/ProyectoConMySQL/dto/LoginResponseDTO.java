package com.dwes.ProyectoConMySQL.dto;

import com.dwes.ProyectoConMySQL.model.Rol;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private Long idUsuario;
    private String nombre;
    private String email;
    private Rol rol;
}
