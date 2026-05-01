package com.dwes.ProyectoConMySQL.repository;

import com.dwes.ProyectoConMySQL.model.Ejercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EjercicioRepository extends JpaRepository<Ejercicio, Long> {
    //FILTRAR EJERCICIO POR MUSCULO
    List<Ejercicio> findByMusculoIdMusculo(Long idMusculo);
}