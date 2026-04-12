package com.dwes.ProyectoConMySQL.repository;

import com.dwes.ProyectoConMySQL.model.Ejercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EjercicioRepository extends JpaRepository<Ejercicio, Long> {

}