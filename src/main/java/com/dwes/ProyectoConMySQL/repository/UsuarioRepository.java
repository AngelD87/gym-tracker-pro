package com.dwes.ProyectoConMySQL.repository;

import com.dwes.ProyectoConMySQL.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}