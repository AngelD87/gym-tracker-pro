package com.dwes.ProyectoConMySQL.repository;

import com.dwes.ProyectoConMySQL.model.Musculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusculoRepository extends JpaRepository<Musculo, Long> {
}