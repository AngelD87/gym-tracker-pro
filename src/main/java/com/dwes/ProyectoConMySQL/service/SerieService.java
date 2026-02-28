package com.dwes.ProyectoConMySQL.service;

import com.dwes.ProyectoConMySQL.dto.SerieCreateDTO;
import com.dwes.ProyectoConMySQL.dto.SerieResponseDTO;
import com.dwes.ProyectoConMySQL.dto.SerieUpdateDTO;
import com.dwes.ProyectoConMySQL.model.EntrenamientoEjercicio;
import com.dwes.ProyectoConMySQL.model.Serie;
import com.dwes.ProyectoConMySQL.repository.EntrenamientoEjercicioRepository;
import com.dwes.ProyectoConMySQL.repository.SerieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieService {

    private final SerieRepository serieRepository;
    private final EntrenamientoEjercicioRepository entrenamientoEjercicioRepository;

    public SerieService(
            SerieRepository serieRepository,
            EntrenamientoEjercicioRepository entrenamientoEjercicioRepository) {

        this.serieRepository = serieRepository;
        this.entrenamientoEjercicioRepository = entrenamientoEjercicioRepository;
    }

    //CONVERTIR ENTITY → DTO
    private SerieResponseDTO toResponseDTO(Serie serie) {
        return SerieResponseDTO.builder()
                .idSerie(serie.getIdSerie())
                .idEntrenamientoEjercicio(serie.getEntrenamientoEjercicio().getIdEntrenamientoEjercicio())
                .numeroSerie(serie.getNumeroSerie())
                .repeticiones(serie.getRepeticiones())
                .peso(serie.getPeso())
                .rir(serie.getRir())
                .build();
    }

    //AÑADIR SERIE (DTO)
    public SerieResponseDTO añadirSerieDTO(SerieCreateDTO dto) {
        EntrenamientoEjercicio ee =
                entrenamientoEjercicioRepository.findById(dto.getIdEntrenamientoEjercicio())
                        .orElseThrow(() ->
                                new IllegalArgumentException("El ejercicio no existe"));

        //VALIDACIÓN: Entrenamiento cerrado
        if(ee.getEntrenamiento().getFin() != null) {
            throw new IllegalStateException("No se pueden añadir series a un entrenamiento cerrado");
        }

        //VALIDACIONES DE DATOS
        if(dto.getNumeroSerie() == null || dto.getNumeroSerie() <= 0) {
            throw new IllegalArgumentException("El número de serie debe ser mayor que 0");
        }

        //VALIDACIÓN: Serie duplicada
        boolean existeSerieDuplicada =
                serieRepository
                        .existsByEntrenamientoEjercicioIdEntrenamientoEjercicioAndNumeroSerie(
                                ee.getIdEntrenamientoEjercicio(),
                                dto.getNumeroSerie());

        if (existeSerieDuplicada) {
            throw new IllegalStateException(
                    "Ya existe una serie con el número "
                            + dto.getNumeroSerie()
                            + " en este ejercicio"
            );
        }

        if(dto.getRepeticiones() == null || dto.getRepeticiones() <= 0) {
            throw new IllegalArgumentException("Las repeticiones deben ser mayores que 0");
        }

        if (dto.getPeso() == null || dto.getPeso() < 0) {
            throw new IllegalArgumentException("El peso no puede ser negativo");
        }

        if (dto.getRir() != null && (dto.getRir() < 0 || dto.getRir() > 10)) {
            throw new IllegalArgumentException("El RIR debe estar entre 0 y 10");
        }

        Serie serie = new Serie();
        serie.setEntrenamientoEjercicio(ee);
        serie.setNumeroSerie(dto.getNumeroSerie());
        serie.setRepeticiones(dto.getRepeticiones());
        serie.setPeso(dto.getPeso());
        serie.setRir(dto.getRir());

        Serie guardada = serieRepository.save(serie);

        return toResponseDTO(guardada);
    }

    //LISTAR SERIES DE UN EJERCICIO (DTO)
    public List<SerieResponseDTO> listarPorEntrenamientoEjercicioDTO(Long idEntrenamientoEjercicio) {
        return serieRepository
                .findByEntrenamientoEjercicioIdEntrenamientoEjercicioOrderByNumeroSerie(idEntrenamientoEjercicio)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    //BUSCAR POR ID (DTO)
    public SerieResponseDTO buscarPorIdDTO(Long id) {
        Serie serie = serieRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("La serie no existe"));

        return toResponseDTO(serie);
    }

    //ACTUALIZAR SERIE (DTO)
    public SerieResponseDTO actualizarDTO(Long id, SerieUpdateDTO dto) {
        Serie serie = serieRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("La serie no existe"));

        //VALIDACIÓN: Entrenamiento cerrado
        if(serie.getEntrenamientoEjercicio().getEntrenamiento().getFin() != null) {
            throw new IllegalStateException("No se pueden modificar series de un entrenamiento cerrado");
        }

        //VALIDACIONES DE DATOS
        if (dto.getRepeticiones() != null && dto.getRepeticiones() <= 0) {
            throw new IllegalArgumentException("Las repeticiones deben ser mayores que 0");
        }

        if (dto.getPeso() != null && dto.getPeso() < 0) {
            throw new IllegalArgumentException("El peso no puede ser negativo");
        }

        if (dto.getRir() != null && (dto.getRir() < 0 || dto.getRir() > 10)) {
            throw new IllegalArgumentException("El RIR debe estar entre 0 y 10");
        }

        if (dto.getRepeticiones() != null) {
            serie.setRepeticiones(dto.getRepeticiones());
        }
        if (dto.getPeso() != null) {
            serie.setPeso(dto.getPeso());
        }
        if (dto.getRir() != null) {
            serie.setRir(dto.getRir());
        }

        Serie actualizada = serieRepository.save(serie);
        return toResponseDTO(actualizada);
    }

    //ELIMINAR SERIE
    public void eliminar(Long id) {
        Serie serie = serieRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("La serie que intentas eliminar no existe"));

        //VALIDACIÓN: Entrenamiento cerrado
        if (serie.getEntrenamientoEjercicio().getEntrenamiento().getFin() != null) {
            throw new IllegalStateException("No se pueden eliminar series de un entrenamiento cerrado");
        }

        serieRepository.deleteById(id);
    }
}