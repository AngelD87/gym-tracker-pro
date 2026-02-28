package com.dwes.ProyectoConMySQL.service;

import com.dwes.ProyectoConMySQL.dto.EntrenamientoEjercicioCreateDTO;
import com.dwes.ProyectoConMySQL.dto.EntrenamientoEjercicioResponseDTO;
import com.dwes.ProyectoConMySQL.dto.EntrenamientoEjercicioUpdateDTO;
import com.dwes.ProyectoConMySQL.model.Ejercicio;
import com.dwes.ProyectoConMySQL.model.Entrenamiento;
import com.dwes.ProyectoConMySQL.model.EntrenamientoEjercicio;
import com.dwes.ProyectoConMySQL.repository.EjercicioRepository;
import com.dwes.ProyectoConMySQL.repository.EntrenamientoEjercicioRepository;
import com.dwes.ProyectoConMySQL.repository.EntrenamientoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntrenamientoEjercicioService {

    private final EntrenamientoEjercicioRepository entrenamientoEjercicioRepository;
    private final EntrenamientoRepository entrenamientoRepository;
    private final EjercicioRepository ejercicioRepository;

    public EntrenamientoEjercicioService(
            EntrenamientoEjercicioRepository entrenamientoEjercicioRepository,
            EntrenamientoRepository entrenamientoRepository,
            EjercicioRepository ejercicioRepository) {

        this.entrenamientoEjercicioRepository = entrenamientoEjercicioRepository;
        this.entrenamientoRepository = entrenamientoRepository;
        this.ejercicioRepository = ejercicioRepository;
    }

    private EntrenamientoEjercicioResponseDTO toResponseDTO(EntrenamientoEjercicio ee) {
        return EntrenamientoEjercicioResponseDTO.builder()
                .idEntrenamientoEjercicio(ee.getIdEntrenamientoEjercicio())
                .idEntrenamiento(ee.getEntrenamiento().getIdEntrenamiento())
                .idEjercicio(ee.getEjercicio().getIdEjercicio())
                .nombreEjercicio(ee.getEjercicio().getNombre())
                .orden(ee.getOrden())
                .notas(ee.getNotas())
                .build();
    }

    //AÑADIR EJERCICIO A UN ENTRENAMIENTO (DTO)
    public EntrenamientoEjercicioResponseDTO añadirEjercicioDTO(EntrenamientoEjercicioCreateDTO dto) {
        Entrenamiento entrenamiento = entrenamientoRepository.findById(dto.getIdEntrenamiento())
                .orElseThrow(() ->
                        new IllegalArgumentException("El entrenamiento no existe"));

        Ejercicio ejercicio = ejercicioRepository.findById(dto.getIdEjercicio())
                .orElseThrow(() ->
                        new IllegalArgumentException("El ejercicio no existe"));

        //VALIDACIÓN: No añadir ejercicios a entrenamientos cerrados
        if(entrenamiento.getFin() != null) {
            throw new IllegalStateException("No se pueden añadir ejercicios a un entrenamiento cerrado");
        }

        //VALIDACIÓN: Orden mayor a 0
        if(dto.getOrden() == null || dto.getOrden() <= 0) {
            throw new IllegalArgumentException("El orden debe ser mayor que 0");
        }

        EntrenamientoEjercicio ee = new EntrenamientoEjercicio();
        ee.setEntrenamiento(entrenamiento);
        ee.setEjercicio(ejercicio);
        ee.setOrden(dto.getOrden());
        ee.setNotas(dto.getNotas());

        EntrenamientoEjercicio guardado = entrenamientoEjercicioRepository.save(ee);
        return toResponseDTO(guardado);
    }

    //LISTAR EJERCICIOS DE UN ENTRENAMIENTO (DTO)
    public List<EntrenamientoEjercicioResponseDTO> listarPorEntrenamientoDTO(Long idEntrenamiento) {
        return entrenamientoEjercicioRepository.findByEntrenamientoIdEntrenamientoOrderByOrden(idEntrenamiento)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    //BUSCAR POR ID (DTO)
    public EntrenamientoEjercicioResponseDTO buscarPorIdDTO(Long id) {
        EntrenamientoEjercicio ee = entrenamientoEjercicioRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("El ejercicio del entrenamiento no existe"));

        return toResponseDTO(ee);
    }

    //ACTUALIZAR ORDEN / NOTAS (DTO)
    public EntrenamientoEjercicioResponseDTO actualizarDTO(Long id, EntrenamientoEjercicioUpdateDTO dto) {
        EntrenamientoEjercicio ee = entrenamientoEjercicioRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("El ejercicio del entrenamiento no existe"));

        //VALIDACIÓN: No actualizar ejercicios de entrenamientos cerrados
        if(ee.getEntrenamiento().getFin() != null) {
            throw new IllegalStateException("No se puede actualizar un ejercicio de un entrenamiento cerrado");
        }

        //VALIDACIÓN: Orden mayor a 0
        if (dto.getOrden() != null && dto.getOrden() <= 0) {
            throw new IllegalArgumentException("El orden debe ser mayor que 0");
        }

        if (dto.getOrden() != null) {
            ee.setOrden(dto.getOrden());
        }
        if (dto.getNotas() != null) {
            ee.setNotas(dto.getNotas());
        }

        EntrenamientoEjercicio actualizado = entrenamientoEjercicioRepository.save(ee);
        return toResponseDTO(actualizado);
    }

    //ELIMINAR EJERCICIO DEL ENTRENAMIENTO
    public void eliminar(Long id){
        EntrenamientoEjercicio ee = entrenamientoEjercicioRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("El ejercicio que intentas eliminar no existe"));

        //VALIDACIÓN: No eliminar ejercicios de entrenamientos cerrados
        if (ee.getEntrenamiento().getFin() != null) {
            throw new IllegalStateException("No se pueden eliminar ejercicios de un entrenamiento cerrado");
        }

        entrenamientoEjercicioRepository.deleteById(id);
    }
}