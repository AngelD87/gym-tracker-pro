package com.dwes.ProyectoConMySQL.repository;

import com.dwes.ProyectoConMySQL.model.EntrenamientoEjercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntrenamientoEjercicioRepository extends JpaRepository<EntrenamientoEjercicio, Long> {
    List<EntrenamientoEjercicio> findByEntrenamientoIdEntrenamientoOrderByOrden(Long idEntrenamiento);
}