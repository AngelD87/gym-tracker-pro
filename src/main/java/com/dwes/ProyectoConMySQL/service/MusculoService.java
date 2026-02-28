package com.dwes.ProyectoConMySQL.service;

import com.dwes.ProyectoConMySQL.dto.MusculoCreateDTO;
import com.dwes.ProyectoConMySQL.dto.MusculoResponseDTO;
import com.dwes.ProyectoConMySQL.dto.MusculoUpdateDTO;
import com.dwes.ProyectoConMySQL.model.Musculo;
import com.dwes.ProyectoConMySQL.repository.MusculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusculoService {

    private final MusculoRepository musculoRepository;

    public MusculoService(MusculoRepository musculoRepository) {
        this.musculoRepository = musculoRepository;
    }

    private MusculoResponseDTO toResponseDTO(Musculo m) {
        return MusculoResponseDTO.builder()
                .idMusculo(m.getIdMusculo())
                .nombre(m.getNombre())
                .descripcion(m.getDescripcion())
                .build();
    }

    //CREAR (DTO)
    public MusculoResponseDTO crearDesdeDTO(MusculoCreateDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del músculo es obligatorio");
        }

        Musculo m = new Musculo();
        m.setNombre(dto.getNombre());
        m.setDescripcion(dto.getDescripcion());

        Musculo guardado = musculoRepository.save(m);
        return toResponseDTO(guardado);
    }

    //LISTAR (DTO)
    public List<MusculoResponseDTO> listarDTO() {
        return musculoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    //BUSCAR POR ID (DTO)
    public MusculoResponseDTO buscarPorIdDTO(Long id) {
        Musculo m = musculoRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("El músculo no existe"));

        return toResponseDTO(m);
    }

    //ACTUALIZAR (DTO)
    public MusculoResponseDTO actualizarDesdeDTO(Long id, MusculoUpdateDTO dto) {
        Musculo m = musculoRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("El músculo no existe"));

        if (dto.getNombre() != null) {
            if (dto.getNombre().trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre no puede estar vacío");
            }
            m.setNombre(dto.getNombre());
        }

        if (dto.getDescripcion() != null) {
            m.setDescripcion(dto.getDescripcion());
        }

        Musculo actualizado = musculoRepository.save(m);
        return toResponseDTO(actualizado);
    }

    //ELIMINAR
    public void eliminar(Long id) {
        Musculo m = musculoRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("El músculo no existe"));

        musculoRepository.deleteById(id);
    }
}