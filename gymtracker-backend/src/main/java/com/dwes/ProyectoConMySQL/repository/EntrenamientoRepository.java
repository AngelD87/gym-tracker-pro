package com.dwes.ProyectoConMySQL.repository;

import com.dwes.ProyectoConMySQL.model.Entrenamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntrenamientoRepository extends JpaRepository<Entrenamiento, Long> {
    List<Entrenamiento> findByUsuarioIdUsuario(Long idUsuario);
    boolean existsByUsuarioIdUsuarioAndNombreIgnoreCase(Long idUsuario, String nombre);

}