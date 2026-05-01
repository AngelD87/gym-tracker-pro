package com.dwes.ProyectoConMySQL.service;

import com.dwes.ProyectoConMySQL.dto.EjercicioCreateDTO;
import com.dwes.ProyectoConMySQL.dto.EjercicioResponseDTO;
import com.dwes.ProyectoConMySQL.dto.EjercicioUpdateDTO;
import com.dwes.ProyectoConMySQL.model.Ejercicio;
import com.dwes.ProyectoConMySQL.model.Musculo;
import com.dwes.ProyectoConMySQL.repository.EjercicioRepository;
import com.dwes.ProyectoConMySQL.repository.MusculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EjercicioService {

    private final EjercicioRepository ejercicioRepository;
    private final MusculoRepository musculoRepository;

    public EjercicioService(
            EjercicioRepository ejercicioRepository,
            MusculoRepository musculoRepository) {
        this.ejercicioRepository = ejercicioRepository;
        this.musculoRepository = musculoRepository;
    }

    private EjercicioResponseDTO toResponseDTO(Ejercicio e) {
        return EjercicioResponseDTO.builder()
                .idEjercicio(e.getIdEjercicio())
                .nombre(e.getNombre())
                .descripcion(e.getDescripcion())
                .videoUrl(e.getVideoUrl())
                .dificultad(e.getDificultad())
                .activo(e.getActivo())
                .idMusculo(e.getMusculo().getIdMusculo())
                .nombreMusculo(e.getMusculo().getNombre())
                .build();
    }

    //CREAR (DTO)
    public EjercicioResponseDTO crearDesdeDTO(EjercicioCreateDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del ejercicio es obligatorio");
        }

        if (dto.getIdMusculo() == null) {
            throw new IllegalArgumentException("El músculo es obligatorio");
        }

        Musculo musculo = musculoRepository.findById(dto.getIdMusculo())
                .orElseThrow(() ->
                        new IllegalArgumentException("El músculo no existe"));

        Ejercicio e = new Ejercicio();
        e.setNombre(dto.getNombre());
        e.setDescripcion(dto.getDescripcion());
        e.setVideoUrl(dto.getVideoUrl());
        e.setDificultad(dto.getDificultad());
        e.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        e.setMusculo(musculo);

        Ejercicio guardado = ejercicioRepository.save(e);
        return toResponseDTO(guardado);
    }

    //LISTAR (DTO)
    public List<EjercicioResponseDTO> listarDTO() {
        return ejercicioRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    //BUSCAR POR ID (DTO)
    public EjercicioResponseDTO buscarPorIdDTO(Long id) {
        Ejercicio e = ejercicioRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("El ejercicio no existe"));

        return toResponseDTO(e);
    }

    //ACTUALIZAR (DTO)
    public EjercicioResponseDTO actualizarDesdeDTO(Long id, EjercicioUpdateDTO dto) {
        Ejercicio e = ejercicioRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("El ejercicio no existe"));

        if (dto.getNombre() != null) {
            if (dto.getNombre().trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre no puede estar vacío");
            }
            e.setNombre(dto.getNombre());
        }

        if (dto.getDescripcion() != null) {
            e.setDescripcion(dto.getDescripcion());
        }

        if (dto.getVideoUrl() != null) {
            e.setVideoUrl(dto.getVideoUrl());
        }

        if (dto.getDificultad() != null) {
            e.setDificultad(dto.getDificultad());
        }

        if (dto.getActivo() != null) {
            e.setActivo(dto.getActivo());
        }

        if (dto.getIdMusculo() != null) {
            Musculo musculo = musculoRepository.findById(dto.getIdMusculo())
                    .orElseThrow(() ->
                            new IllegalArgumentException("El músculo no existe"));
            e.setMusculo(musculo);
        }

        Ejercicio actualizado = ejercicioRepository.save(e);
        return toResponseDTO(actualizado);
    }

    //LISTAR POR MUSCULO (DTO)
    public List<EjercicioResponseDTO> listarPorMusculoDTO(Long idMusculo) {
        return ejercicioRepository.findByMusculoIdMusculo(idMusculo)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    //ELIMINAR
    public void eliminar(Long id) {
        Ejercicio e = ejercicioRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("El ejercicio que intentas eliminar no existe"));

        ejercicioRepository.deleteById(id);
    }
}