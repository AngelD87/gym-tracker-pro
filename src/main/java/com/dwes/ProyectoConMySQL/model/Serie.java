package com.dwes.ProyectoConMySQL.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "series")
@Data
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_serie")
    private Long idSerie;

    @ManyToOne
    @JoinColumn(name = "id_entrenamiento_ejercicio", nullable = false)
    private EntrenamientoEjercicio entrenamientoEjercicio;

    @Column(name = "numero_serie", nullable = false)
    private Integer numeroSerie;

    @Column(nullable = false)
    private Integer repeticiones;

    @Column(nullable = false)
    private Double peso;

    private Integer rir;
}